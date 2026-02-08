package pharmacy_webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pharmacy_webapp.dto.ProductDto;
import pharmacy_webapp.model.Categories;
import pharmacy_webapp.model.Manufacturer;
import pharmacy_webapp.model.Product;
import pharmacy_webapp.repository.CategoriesRepository;
import pharmacy_webapp.repository.ManufacturerRepository;
import pharmacy_webapp.repository.ProductRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public Product createProduct(ProductDto productDto, List<MultipartFile> images) throws IOException {
        Manufacturer manufacturer = manufacturerRepository.findById(productDto.getManufacturerId())
                .orElseThrow(() -> new RuntimeException("Manufacturer not found"));

        Categories categories = categoriesRepository.findById(productDto.getCategoriesId())
                .orElseThrow(() -> new RuntimeException("Categories not found"));

        List<String> imageUrls = new ArrayList<>();
        if(images != null && !images.isEmpty()) {
            imageUrls = cloudinaryService.uploadMultipleImages(images);
        }

        Product product = new Product();
        product.setManufacturer(manufacturer);
        product.setCategories(categories);
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setPurchaseCount(productDto.getPurchaseCount());
        product.setUrlImages(imageUrls);

        return productRepository.save(product);
    }

    public List<Product> getALlProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product updateProduct(String productId, ProductDto productDto, List<MultipartFile> images) throws IOException {
        Product product = getProductById(productId);

        if(productDto.getManufacturerId() != null &&
                !productDto.getManufacturerId().equals(product.getManufacturer().getId())) {
            Manufacturer manufacturer = manufacturerRepository.findById(productDto.getManufacturerId())
                    .orElseThrow(() -> new RuntimeException("Manufacturer not found"));
            product.setManufacturer(manufacturer);
        }

        if (productDto.getCategoriesId() != null &&
                !productDto.getCategoriesId().equals(product.getCategories().getId())) {
            Categories categories = categoriesRepository.findById(productDto.getCategoriesId())
                    .orElseThrow(() -> new RuntimeException("Categories not found"));
            product.setCategories(categories);
        }

        if(images != null && !images.isEmpty()) {
            List<String> imageUrls = cloudinaryService.uploadMultipleImages(images);

            if(product.getUrlImages() == null){
                product.setUrlImages(new ArrayList<>());
            }

            product.getUrlImages().addAll(imageUrls);
        }

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setPurchaseCount(productDto.getPurchaseCount());

        return productRepository.save(product);
    }

    public String deleteProduct(String productId) throws IOException {
        Product product = getProductById(productId);

        if(product.getUrlImages() != null && !product.getUrlImages().isEmpty()) {
            cloudinaryService.deleteMultipleImages(product.getUrlImages());
        }
        productRepository.delete(product);
        return "Product deleted";
    }

    public void deleteImage(String productId, String imageUrl) throws IOException {
        Product product = getProductById(productId);

        if(product.getUrlImages() == null || !product.getUrlImages().contains(imageUrl)) {
            throw new RuntimeException("Image not found in product");
        }

        cloudinaryService.deleteImage(imageUrl);

        product.getUrlImages().remove(imageUrl);
        productRepository.save(product);
    }

    public List<Product> searchProductByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> getProductByCategories(String categoriesId) {
        return productRepository.findByCategories_Id(categoriesId);
    }

    public List<Product> getProductByManufacturer(String manufacturerId) {
        return productRepository.findByManufacturer_Id(manufacturerId);
    }
}
