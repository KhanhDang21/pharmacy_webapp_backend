package pharmacy_webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharmacy_webapp.model.ShoppingCart;
import pharmacy_webapp.model.User;
import pharmacy_webapp.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    public ShoppingCart createShoppingCart(String userId){
        User user = userService.getUserById(userId);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart getShoppingCart(String userId){
        return shoppingCartRepository.getShoppingCartByUser_Id(userId);
    }

    public ShoppingCart addProductToShoppingCart(String userId, String productId){
        ShoppingCart shoppingCart = getShoppingCart(userId);

        if(shoppingCart == null){
            throw new RuntimeException("Shopping cart is not created");
        }

        shoppingCart.getItems().merge(productId, 1, Integer::sum);

        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart decreaseProductFromShoppingCart(String userId, String productId){
        ShoppingCart shoppingCart = getShoppingCart(userId);

        if(shoppingCart == null){
            throw new RuntimeException("Shopping cart is not created");
        }

        shoppingCart.getItems().computeIfPresent(productId, (k, v) -> {
            int newQuantity = v - 1;
            return newQuantity > 0 ? newQuantity : null;
        });

        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart removeProductFromShoppingCart(String userId, String productId){
        ShoppingCart shoppingCart = getShoppingCart(userId);

        if(shoppingCart == null){
            throw new RuntimeException("Shopping cart is not created");
        }

        shoppingCart.getItems().remove(productId);
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart clearShoppingCart(String userId) {
        ShoppingCart shoppingCart = getShoppingCart(userId);

        if(shoppingCart == null){
            throw new RuntimeException("Shopping cart is not created");
        }

        shoppingCart.getItems().clear();
        return shoppingCartRepository.save(shoppingCart);
    }
}
