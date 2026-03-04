package pharmacy_webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pharmacy_webapp.dto.CategoriesDto;
import pharmacy_webapp.model.Categories;
import pharmacy_webapp.repository.CategoriesRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class CategoriesService {
    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public static String toSlug(String input) {
        if (input == null || input.isEmpty()) return "";

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String noAccent = pattern.matcher(normalized).replaceAll("");

        noAccent = noAccent.replaceAll("đ", "d").replaceAll("Đ", "d");

        noAccent = noAccent.toLowerCase(Locale.ROOT);

        noAccent = noAccent.replaceAll("\\s+", "-");

        noAccent = noAccent.replaceAll("[^a-z0-9-]", "");

        noAccent = noAccent.replaceAll("-{2,}", "-");

        noAccent = noAccent.replaceAll("^-|-$", "");

        return noAccent;
    }

    public Categories createCategories(CategoriesDto categoriesDto, MultipartFile image) throws IOException {
        String urlImage = "";
        if(image != null && !image.isEmpty()) {
            urlImage = cloudinaryService.uploadImage(image);
        }

        Categories categories = new Categories();
        categories.setName(categoriesDto.getName());

        String slugCategories = toSlug(categoriesDto.getName());
        categories.setSlug(slugCategories);
        categories.setUrlImg(urlImage);
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
