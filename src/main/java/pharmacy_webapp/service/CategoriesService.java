package pharmacy_webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharmacy_webapp.dto.CategoriesDto;
import pharmacy_webapp.model.Categories;
import pharmacy_webapp.repository.CategoriesRepository;

import java.util.List;

@Service
public class CategoriesService {
    @Autowired
    private CategoriesRepository categoriesRepository;

    public Categories createCategories(CategoriesDto categoriesDto) {
        Categories categories = new Categories();
        categories.setName(categoriesDto.getName());
        categories.setDescription(categoriesDto.getDescription());
        return categoriesRepository.save(categories);
    }

    public Categories getCategories(String categoriesId) {
        return categoriesRepository.findById(categoriesId)
                .orElseThrow(() -> new RuntimeException("categories not found"));
    }

    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    public Categories updateCategories(String categoriesId, CategoriesDto categoriesDto) {

        Categories categories = getCategories(categoriesId);

        if(categories == null) {
            throw new RuntimeException("categories not found");
        }

        categories.setName(categoriesDto.getName());
        categories.setDescription(categoriesDto.getDescription());
        return categoriesRepository.save(categories);
    }

    public String deleteCategories(String categoriesId) {
        Categories categories = getCategories(categoriesId);

        if (categories == null) {
            throw new RuntimeException("categories not found");
        }

        categoriesRepository.delete(categories);
        return "Categories deleted";
    }
}
