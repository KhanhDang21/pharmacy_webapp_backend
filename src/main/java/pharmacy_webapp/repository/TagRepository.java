package pharmacy_webapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pharmacy_webapp.model.Tag;

public interface TagRepository extends MongoRepository<Tag,String> {
}
