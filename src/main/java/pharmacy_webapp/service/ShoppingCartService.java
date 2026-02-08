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

}
