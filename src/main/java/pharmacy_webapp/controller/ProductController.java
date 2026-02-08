package pharmacy_webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pharmacy_webapp.dto.ApiResponse;
import pharmacy_webapp.dto.ProductDto;
import pharmacy_webapp.model.Product;
import pharmacy_webapp.service.CloudinaryService;
import pharmacy_webapp.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(value ="/create-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Product>> createProduct(
            @RequestParam String manufacturerId,
            @RequestParam String categoriesId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam int quantity,
            @RequestParam(defaultValue = "0") int purchaseCount,
            @RequestPart(value = "image", required = false)List<MultipartFile> images
    ){
        try{
            ProductDto productDto = new ProductDto(
                    manufacturerId, categoriesId, name, description,
                    price, quantity, purchaseCount
            );

            Product product = productService.createProduct(productDto, images);

            return ResponseEntity.ok(
                    ApiResponse.success("Product created successfully", product)
            );

        } catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @GetMapping("/get-all-products")
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        try{

            List<Product> products = productService.getALlProducts();

            return ResponseEntity.ok(
                    ApiResponse.success("All products successfully", products)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable String productId) {
        try{
            Product product = productService.getProductById(productId);

            return ResponseEntity.ok(
                    ApiResponse.success("Get Product successfully", product)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @PutMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Product>> updateProduct(
            @PathVariable String productId,
            @RequestParam String manufacturerId,
            @RequestParam String categoriesId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam int quantity,
            @RequestParam(defaultValue = "0") int purchaseCount,
            @RequestPart(value = "image", required = false)List<MultipartFile> images
    ){
        try{
            ProductDto productDto = new ProductDto(
                    manufacturerId,
                    categoriesId,
                    name,
                    description,
                    price,
                    quantity,
                    purchaseCount
            );

            Product product = productService.updateProduct(productId, productDto, images);

            return ResponseEntity.ok(
                    ApiResponse.success("Product updated successfully", product)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable String productId) {
        try{
            String res = productService.deleteProduct(productId);

            return ResponseEntity.ok(
                    ApiResponse.success(res)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Product>>> searchProducts(@RequestParam String name){
        try{
            List<Product> products = productService.searchProductByName(name);
            return ResponseEntity.ok(
                    ApiResponse.success("Search products successfully", products)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }
}
