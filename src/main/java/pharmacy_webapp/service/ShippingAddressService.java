package pharmacy_webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharmacy_webapp.dto.ShippingAddressDto;
import pharmacy_webapp.model.ShippingAddress;
import pharmacy_webapp.model.User;
import pharmacy_webapp.repository.ShippingAddressRepository;

import java.util.List;

@Service
public class ShippingAddressService {
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private UserService userService;

    public ShippingAddress createShippingAddress(String userId, ShippingAddressDto shippingAddress) {
        User user = userService.getUserById(userId);

        ShippingAddress shippingAdd = new ShippingAddress();
        shippingAdd.setUser(user);
        shippingAdd.setRecipientName(shippingAddress.getRecipientName());
        shippingAdd.setNumPhone(shippingAddress.getNumPhone());
        shippingAdd.setAddressLine(shippingAddress.getAddressLine());
        shippingAdd.setCity(shippingAddress.getCity());
        shippingAdd.setDistrict(shippingAddress.getDistrict());

        return shippingAddressRepository.save(shippingAdd);
    }

    public List<ShippingAddress> getAllShippingAddressByUserId(String userId) {
        return shippingAddressRepository.findAllByUser_Id(userId);
    }

    public ShippingAddress getShippingAddressById(String shippingAddressId) {
        return shippingAddressRepository.findById(shippingAddressId).get();
    }

    public ShippingAddress updateShippingAddress(String shippingAddressId, ShippingAddressDto shippingAddress) {
        try{
            ShippingAddress shippingAdd = shippingAddressRepository.findById(shippingAddressId)
                    .orElseThrow(() -> new Exception("Shipping Address Not Found"));

            shippingAdd.setRecipientName(shippingAddress.getRecipientName());
            shippingAdd.setNumPhone(shippingAddress.getNumPhone());
            shippingAdd.setAddressLine(shippingAddress.getAddressLine());
            shippingAdd.setCity(shippingAddress.getCity());
            shippingAdd.setDistrict(shippingAddress.getDistrict());

            return shippingAddressRepository.save(shippingAdd);
        }catch (Exception e){
            throw new RuntimeException("Shipping Address Not Successfully updated");
        }
    }

    public String deleteShippingAddress(String shippingAddressId) {
        ShippingAddress shippingAddress = getShippingAddressById(shippingAddressId);

        if(shippingAddress == null){
            throw new RuntimeException("Shipping Address Not Found");
        }

        shippingAddressRepository.delete(shippingAddress);
        return "Shipping Address Deleted Successfully";
    }
}
