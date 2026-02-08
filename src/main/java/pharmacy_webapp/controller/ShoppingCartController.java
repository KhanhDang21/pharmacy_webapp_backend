package pharmacy_webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pharmacy_webapp.dto.ApiResponse;
import pharmacy_webapp.model.ShoppingCart;
import pharmacy_webapp.model.UserPrincipal;
import pharmacy_webapp.service.ShoppingCartService;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/create-shopping-cart")
    public ResponseEntity<ApiResponse<ShoppingCart>> createShoppingCart(Authentication authentication){
        try{
            UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
            String userId = user.getUserId();

            ShoppingCart shoppingCart = shoppingCartService.createShoppingCart(userId);

            return ResponseEntity.ok(
                    ApiResponse.success("Create shopping cart successfully", shoppingCart)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @GetMapping("/get-shopping-cart")
    public ResponseEntity<ApiResponse<ShoppingCart>> getShoppingCart(Authentication authentication){
        try{
            UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
            String userId = user.getUserId();

            ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(userId);

            return ResponseEntity.ok(
                    ApiResponse.success("Get shopping cart successfully", shoppingCart)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

   @PutMapping("/add-product-to-shopping-cart")
    public ResponseEntity<ApiResponse<ShoppingCart>> addProductToShoppingCart(Authentication authentication, String productId){
        try{
            UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
            String userId = user.getUserId();

            ShoppingCart shoppingCart = shoppingCartService.addProductToShoppingCart(userId, productId);

            return ResponseEntity.ok(
                    ApiResponse.success("Add product successfully", shoppingCart)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
   }

   @PutMapping("/decrease-product-from-shopping-cart")
    public ResponseEntity<ApiResponse<ShoppingCart>> decreaseProductFromShoppingCart(Authentication authentication, String productId){
        try{
            UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
            String userId = user.getUserId();

            ShoppingCart shoppingCart = shoppingCartService.decreaseProductFromShoppingCart(userId, productId);

            return ResponseEntity.ok(
                    ApiResponse.success("Decrease product successfully", shoppingCart)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
   }

   @PutMapping("/remove-product-from-shopping-cart")
    public ResponseEntity<ApiResponse<ShoppingCart>> removeProductFromShoppingCart(Authentication authentication, String productId){
        try{
            UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
            String userId = user.getUserId();

            ShoppingCart shoppingCart = shoppingCartService.removeProductFromShoppingCart(userId, productId);

            return ResponseEntity.ok(
                    ApiResponse.success("Remove product successfully", shoppingCart)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
   }

   @PutMapping("/clear-shopping-cart")
    public ResponseEntity<ApiResponse<ShoppingCart>> clearShoppingCart(Authentication authentication){
        try{
            UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
            String userId = user.getUserId();

            ShoppingCart shoppingCart = shoppingCartService.clearShoppingCart(userId);

            return ResponseEntity.ok(
                    ApiResponse.success("Clear shopping cart successfully", shoppingCart)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
   }
}
