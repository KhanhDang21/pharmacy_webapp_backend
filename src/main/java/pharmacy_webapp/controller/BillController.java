package pharmacy_webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pharmacy_webapp.dto.ApiResponse;
import pharmacy_webapp.dto.BuyNowRequest;
import pharmacy_webapp.dto.CheckoutRequest;
import pharmacy_webapp.model.Bill;
import pharmacy_webapp.model.UserPrincipal;
import pharmacy_webapp.service.BillService;

import java.util.List;

@RestController
@RequestMapping("/api/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @PostMapping("/check-out")
    public ResponseEntity<ApiResponse<Bill>> checkOutFromShoppingCart(
            Authentication authentication,
            @RequestBody CheckoutRequest checkoutRequest
    ){
        try{
            UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
            String userId = user.getUserId();

            Bill bill = billService.checkOutFromShoppingCart(userId, checkoutRequest);

            return ResponseEntity.ok(
                    ApiResponse.success("Order successfully", bill)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @PostMapping("/buy-now")
    public ResponseEntity<ApiResponse<Bill>> buyNow(
            Authentication authentication,
            @RequestBody BuyNowRequest buyNowRequest
    ){
        try{
            UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
            String userId = user.getUserId();

            Bill bill = billService.buyNow(userId, buyNowRequest);

            return ResponseEntity.ok(
                    ApiResponse.success("Order successfully", bill)
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
            Integer paymentStatus
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
            Integer orderStatus
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
