package pharmacy_webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pharmacy_webapp.dto.ApiResponse;
import pharmacy_webapp.dto.BuyNowRequest;
import pharmacy_webapp.dto.CheckoutRequest;
import pharmacy_webapp.dto.PaymentUrlResponse;
import pharmacy_webapp.model.Bill;
import pharmacy_webapp.model.UserPrincipal;
import pharmacy_webapp.service.BillService;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @PostMapping("/check-out")
    public ResponseEntity<ApiResponse<PaymentUrlResponse>> checkOutFromShoppingCart(
            Authentication authentication,
            @RequestBody CheckoutRequest checkoutRequest,
            HttpServletRequest request
    ){
        try{
            UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
            String userId = user.getUserId();

            PaymentUrlResponse response = billService.checkOutFromShoppingCart(userId, checkoutRequest, request);

            return ResponseEntity.ok(
                    ApiResponse.success("Order created successfully", response)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @PostMapping("/buy-now")
    public ResponseEntity<ApiResponse<PaymentUrlResponse>> buyNow(
            Authentication authentication,
            @RequestBody BuyNowRequest buyNowRequest,
            HttpServletRequest request
    ){
        try{
            UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
            String userId = user.getUserId();

            PaymentUrlResponse response = billService.buyNow(userId, buyNowRequest, request);

            return ResponseEntity.ok(
                    ApiResponse.success("Order created successfully", response)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<List<Bill>>> getMyOrders(
            Authentication authentication
    ){
        try{
            UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
            String userId = user.getUserId();

            List<Bill> bills = billService.getUserBills(userId);

            return ResponseEntity.ok(
                    ApiResponse.success("Get my orders successfully", bills)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @GetMapping("/{billId}")
    public ResponseEntity<ApiResponse<Bill>> getBillById(@PathVariable String billId){
        try{
            Bill bill = billService.getBillById(billId);
            return ResponseEntity.ok(
                    ApiResponse.success("Get bill successfully", bill)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @PutMapping("/{billId}/cancel")
    public ResponseEntity<ApiResponse<Bill>> cancelBill(
            @PathVariable String billId,
            Authentication authentication
    ){
        try{
            UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
            String userId = user.getUserId();

            Bill bill = billService.cancelBill(billId, userId);

            return ResponseEntity.ok(
                    ApiResponse.success("Cancel bill successfully", bill)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @PutMapping("/{billId}/update-payment-status")
    public ResponseEntity<ApiResponse<Bill>> updatePaymentStatus(
            @PathVariable String billId,
            @RequestParam Integer paymentStatus
    ){
        try{
            Bill bill = billService.updatePaymentStatus(billId, paymentStatus);

            return ResponseEntity.ok(
                    ApiResponse.success("Update payment status successfully", bill)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @PutMapping("/{billId}/update-order-status")
    public ResponseEntity<ApiResponse<Bill>> updateOrderStatus(
            @PathVariable String billId,
            @RequestParam Integer orderStatus
    ){
        try{
            Bill bill = billService.updateOrderStatus(billId, orderStatus);

            return ResponseEntity.ok(
                    ApiResponse.success("Update order status successfully", bill)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }
}