package pharmacy_webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pharmacy_webapp.dto.ApiResponse;
import pharmacy_webapp.dto.ShippingAddressDto;
import pharmacy_webapp.model.ShippingAddress;
import pharmacy_webapp.model.UserPrincipal;
import pharmacy_webapp.service.ShippingAddressService;

import java.util.List;

@RestController
@RequestMapping("/api/shipping-address")
public class ShippingAddressController {
    @Autowired
    private ShippingAddressService shippingAddressService;

    @PostMapping("/create-shipping-address")
    public ResponseEntity<ApiResponse<ShippingAddress>> createShippingAddress(Authentication authentication, @RequestBody ShippingAddressDto shippingAddress) {
        try{
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            String userId = userPrincipal.getUserId();

            ShippingAddress shippingAdd = shippingAddressService.createShippingAddress(userId, shippingAddress);

            return ResponseEntity.ok(
                    ApiResponse.success("Create shipping address successfully", shippingAdd)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @GetMapping("/get-all-shipping-address")
    public ResponseEntity<ApiResponse<List<ShippingAddress>>> getAllShippingAddress(Authentication authentication) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            String userId = userPrincipal.getUserId();

            List<ShippingAddress> shippingAddressList = shippingAddressService.getAllShippingAddressByUserId(userId);

            return ResponseEntity.ok(
                    ApiResponse.success("Get shipping address successfully", shippingAddressList)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }

    }

    @GetMapping("/{shippingAddressId}")
    public ResponseEntity<ApiResponse<ShippingAddress>> getShippingAddress(@PathVariable String shippingAddressId) {
        try{
            ShippingAddress shippingAdd = shippingAddressService.getShippingAddressById(shippingAddressId);

            if(shippingAdd == null) {
                return ResponseEntity.badRequest().body(
                        ApiResponse.error("Shipping address not found")
                );
            }

            return ResponseEntity.ok(
                    ApiResponse.success("Get shipping address successfully", shippingAdd)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @PutMapping("/{shippingAddressId}")
    public ResponseEntity<ApiResponse<ShippingAddress>> updateShippingAddress(@PathVariable String shippingAddressId, @RequestBody ShippingAddressDto shippingAddress) {
        try{
            ShippingAddress shippingAdd = shippingAddressService.getShippingAddressById(shippingAddressId);

            if(shippingAdd == null){
                return ResponseEntity.badRequest().body(
                        ApiResponse.error("Shipping address not found")
                );
            }

            ShippingAddress shippingAddressUpdated = shippingAddressService.updateShippingAddress(shippingAddressId, shippingAddress);

            return ResponseEntity.ok(
                    ApiResponse.success("Update shipping address successfully", shippingAddressUpdated)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @DeleteMapping("/{shippingAddressId}")
    public ResponseEntity<ApiResponse<String>> deleteShippingAddress(@PathVariable String shippingAddressId) {
        try{
            String response = shippingAddressService.deleteShippingAddress(shippingAddressId);

            return ResponseEntity.ok(
                    ApiResponse.success(response)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }
}
