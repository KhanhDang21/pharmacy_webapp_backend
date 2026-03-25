package pharmacy_webapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pharmacy_webapp.model.DiscountEvent;

import java.time.LocalDateTime;
import java.util.List;

public interface DiscountEventRepository extends MongoRepository<DiscountEvent, String> {
    List<DiscountEvent> findByActiveTrue();
}
