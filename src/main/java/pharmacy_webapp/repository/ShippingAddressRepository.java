package pharmacy_webapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pharmacy_webapp.model.ShippingAddress;

import java.util.List;

@Repository
public interface ShippingAddressRepository extends MongoRepository<ShippingAddress, String> {
    List<ShippingAddress> findAllByUser_Id(String userId);
}
