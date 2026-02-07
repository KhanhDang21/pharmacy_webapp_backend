package pharmacy_webapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pharmacy_webapp.model.Categories;

public interface CategoriesRepository extends MongoRepository<Categories, String> {
}
