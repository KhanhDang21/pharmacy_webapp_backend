package pharmacy_webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
