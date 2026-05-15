package pharmacy_webapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pharmacy_webapp.model.Section;

public interface SectionRepository extends MongoRepository<Section, String> {
}
