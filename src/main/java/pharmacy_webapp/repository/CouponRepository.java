package pharmacy_webapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pharmacy_webapp.model.Coupon;

@Repository
public interface CouponRepository extends MongoRepository<Coupon, String> {
}
