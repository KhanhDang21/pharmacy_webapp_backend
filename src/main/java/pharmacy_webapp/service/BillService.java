package pharmacy_webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharmacy_webapp.constants.OrderStatusConstants;
import pharmacy_webapp.constants.PaymentMethodConstants;
import pharmacy_webapp.constants.PaymentStatusConstants;
import pharmacy_webapp.dto.BuyNowRequest;
import pharmacy_webapp.dto.CheckoutRequest;
import pharmacy_webapp.dto.PaymentUrlResponse;
import pharmacy_webapp.model.*;
import pharmacy_webapp.repository.BillRepository;
import pharmacy_webapp.repository.ProductRepository;
import pharmacy_webapp.repository.ShoppingCartRepository;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ShippingAddressService shippingAddressService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private VNPayService vnPayService;

    private double caculateTotalAmount(HashMap<String, Integer> products){
        double total = 0;
        for(HashMap.Entry<String, Integer> entry : products.entrySet()){
            Product product = productService.getProductById(entry.getKey());
            total += product.getPrice() * entry.getValue();
        }
        return total;
    }

    private Integer getInitialPaymentStatus(Integer paymentMethod) {
        if (paymentMethod.equals(PaymentMethodConstants.CASH_ON_DELIVERY)) {
            return PaymentStatusConstants.PENDING;
        }
        return PaymentStatusConstants.PENDING;
    }

    private void updateProductStock(HashMap<String, Integer> products) {
        for (HashMap.Entry<String, Integer> entry : products.entrySet()) {
            Product product = productService.getProductById(entry.getKey());
            product.setQuantity(product.getQuantity() - entry.getValue());
            productRepository.save(product);
        }
    }

    private void restoreProductStock(HashMap<String, Integer> products) {
        for (HashMap.Entry<String, Integer> entry : products.entrySet()) {
            Product product = productService.getProductById(entry.getKey());
            product.setQuantity(product.getQuantity() + entry.getValue());
            productRepository.save(product);
        }
    }

    private void validateProducts(HashMap<String, Integer> products) {
        for(HashMap.Entry<String, Integer> entry : products.entrySet()) {
            Product product = productService.getProductById(entry.getKey());
            if(product.getQuantity() < entry.getValue()) {
                throw new RuntimeException("Invalid product quantity");
            }
        }
    }

    private String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public Bill handleVNPayCallback(Map<String, String> params) {
        try {
            if (!vnPayService.verifyPaymentCallback(params)) {
                throw new RuntimeException("Invalid VNPay signature");
            }

            String billId = params.get("vnp_TxnRef");
            String responseCode = params.get("vnp_ResponseCode");

            Bill bill = getBillById(billId);

            if ("00".equals(responseCode)) {
                bill.setPaymentStatus(PaymentStatusConstants.PAID);
                bill.setOderStatus(OrderStatusConstants.CONFIRMED);
                bill.setPaidAt(LocalDateTime.now());

                System.out.println("VNPay payment SUCCESS for bill: " + billId);
            } else {
                bill.setPaymentStatus(PaymentStatusConstants.FAILED);
                restoreProductStock(bill.getProducts());

                System.out.println("VNPay payment FAILED for bill: " + billId + ", code: " + responseCode);
            }

            bill.setUpdatedAt(LocalDateTime.now());
            return billRepository.save(bill);

        } catch (Exception e) {
            System.err.println("ERROR in handleVNPayCallback: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("VNPay callback handling error: " + e.getMessage());
        }
    }

    public PaymentUrlResponse checkOutFromShoppingCart(String userId, CheckoutRequest checkoutRequest, HttpServletRequest request) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(userId);
        if(shoppingCart == null) {
            throw new RuntimeException("Shopping cart not found");
        }

        if(shoppingCart.getItems() == null || shoppingCart.getItems().isEmpty()) {
            throw new RuntimeException("Shopping cart is empty");
        }

        ShippingAddress shippingAddress = shippingAddressService.getShippingAddressById(checkoutRequest.getShippingAddressId());
        if(shippingAddress == null) {
            throw new RuntimeException("Shipping address not found");
        }

        HashMap<String, Integer> products = new HashMap<>(shoppingCart.getItems());
        validateProducts(products);

        double totalAmount = caculateTotalAmount(products);

        Bill bill = new Bill();
        bill.setUser(user);
        bill.setShippingAddress(shippingAddress);
        bill.setProducts(products);
        bill.setTotalAmount(totalAmount);
        bill.setPaymentMethod(checkoutRequest.getPaymentMethod());
        bill.setPaymentStatus(getInitialPaymentStatus(checkoutRequest.getPaymentMethod()));
        bill.setOderStatus(OrderStatusConstants.PENDING);
        bill.setCreatedAt(LocalDateTime.now());
        bill.setUpdatedAt(LocalDateTime.now());
        bill.setNote(checkoutRequest.getNote());

        Bill savedBill = billRepository.save(bill);

        shoppingCart.getItems().clear();
        shoppingCartRepository.save(shoppingCart);

        updateProductStock(products);

        String paymentUrl = null;

        if (checkoutRequest.getPaymentMethod().equals(PaymentMethodConstants.VNPAY)) {
            String ipAddress = getIpAddress(request);
            paymentUrl = vnPayService.createPaymentUrl(
                    savedBill.getId(),
                    (long) totalAmount,
                    "Pay for the order " + savedBill.getId(),
                    ipAddress
            );
        }

        PaymentUrlResponse response = new PaymentUrlResponse();
        response.setBillId(savedBill.getId());
        response.setPaymentUrl(paymentUrl);
        response.setTotalAmount(totalAmount);

        return response;
    }

    public PaymentUrlResponse buyNow(String userId, BuyNowRequest buyNowRequest, HttpServletRequest request){
        User user = userService.getUserById(userId);
        if(user == null) {
            throw new RuntimeException("User not found");
        }

        ShippingAddress shippingAddress = shippingAddressService.getShippingAddressById(buyNowRequest.getShippingAddressId());
        if(shippingAddress == null) {
            throw new RuntimeException("Shipping address not found");
        }

        validateProducts(buyNowRequest.getProducts());

        double totalAmount = caculateTotalAmount(buyNowRequest.getProducts());

        Bill bill = new Bill();
        bill.setUser(user);
        bill.setShippingAddress(shippingAddress);
        bill.setProducts(buyNowRequest.getProducts());
        bill.setTotalAmount(totalAmount);
        bill.setPaymentMethod(buyNowRequest.getPaymentMethod());
        bill.setPaymentStatus(getInitialPaymentStatus(buyNowRequest.getPaymentMethod()));
        bill.setOderStatus(OrderStatusConstants.PENDING);
        bill.setCreatedAt(LocalDateTime.now());
        bill.setUpdatedAt(LocalDateTime.now());
        bill.setNote(buyNowRequest.getNote());

        Bill savedBill = billRepository.save(bill);

        updateProductStock(buyNowRequest.getProducts());

        String paymentUrl = null;

        if (buyNowRequest.getPaymentMethod().equals(PaymentMethodConstants.VNPAY)) {
            String ipAddress = getIpAddress(request);
            paymentUrl = vnPayService.createPaymentUrl(
                    savedBill.getId(),
                    (long) totalAmount,
                    "Pay for the order " + savedBill.getId(),
                    ipAddress
            );
        }

        PaymentUrlResponse response = new PaymentUrlResponse();
        response.setBillId(savedBill.getId());
        response.setPaymentUrl(paymentUrl);
        response.setTotalAmount(totalAmount);

        return response;
    }

    public List<Bill> getUserBills(String userId) {
        List<Bill> bills = billRepository.findByUser_Id(userId);
        return bills;
    }

    public Bill getBillById(String billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        return bill;
    }

    public Bill cancelBill(String billId, String userId) {
        Bill bill = getBillById(billId);

        if(!bill.getUser().getId().equals(userId)) {
            throw new RuntimeException("You can not cancel this bill");
        }

        if(bill.getOderStatus() != OrderStatusConstants.PENDING &&
                bill.getOderStatus() != OrderStatusConstants.CONFIRMED) {
            throw new RuntimeException("You can not cancel this bill because " +
                    OrderStatusConstants.getDescription(bill.getOderStatus()));
        }

        bill.setOderStatus(OrderStatusConstants.CANCELLED);
        bill.setPaymentStatus(PaymentStatusConstants.CANCELLED);
        bill.setUpdatedAt(LocalDateTime.now());

        restoreProductStock(bill.getProducts());

        Bill updatedBill = billRepository.save(bill);

        return updatedBill;
    }

    public Bill updatePaymentStatus(String billId, Integer paymentStatus) {
        Bill bill = getBillById(billId);

        bill.setPaymentStatus(paymentStatus);
        bill.setUpdatedAt(LocalDateTime.now());

        if(paymentStatus.equals(PaymentStatusConstants.PAID)){
            bill.setPaidAt(LocalDateTime.now());
            bill.setOderStatus((OrderStatusConstants.CONFIRMED));
        }

        Bill updatedBill = billRepository.save(bill);
        return updatedBill;
    }

    public Bill updateOrderStatus(String billId, Integer orderStatus) {
        Bill bill = getBillById(billId);

        bill.setOderStatus(orderStatus);
        bill.setUpdatedAt(LocalDateTime.now());

        Bill updatedBill = billRepository.save(bill);
        return updatedBill;
    }
}