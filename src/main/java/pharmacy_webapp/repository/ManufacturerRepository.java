package pharmacy_webapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pharmacy_webapp.model.Manufacturer;

public interface ManufacturerRepository extends MongoRepository<Manufacturer, String> {
}
