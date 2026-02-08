package pharmacy_webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pharmacy_webapp.dto.ApiResponse;
import pharmacy_webapp.dto.CategoriesDto;
import pharmacy_webapp.model.Categories;
import pharmacy_webapp.service.CategoriesService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService;

    @PostMapping("/create-categories")
    public ResponseEntity<ApiResponse<Categories>> createCategories(@RequestBody CategoriesDto categories) {
        try{
            Categories categoriesRes = categoriesService.createCategories(categories);

            return ResponseEntity.ok(
                    ApiResponse.success("Create categories successfully ", categoriesRes)
            );
        }catch(Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @GetMapping("/get-all-categories")
    public ResponseEntity<ApiResponse<List<Categories>>> getAllCategories() {
        try{
            List<Categories> categoriesList = categoriesService.getAllCategories();

            return ResponseEntity.ok(
                    ApiResponse.success("Get all categories successfully ", categoriesList)
            );
        }catch(Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @GetMapping("/{categoriesId}")
    public ResponseEntity<ApiResponse<Categories>> getCategories(@PathVariable String categoriesId) {
        try{
            Categories categories = categoriesService.getCategories(categoriesId);

            return ResponseEntity.ok(
                    ApiResponse.success("Get categories successfully ", categories)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @PutMapping("/{categoriesId}")
    public ResponseEntity<ApiResponse<Categories>> updateCategories(@PathVariable String categoriesId, @RequestBody CategoriesDto categories) {
        try{
            Categories categoriesRes = categoriesService.updateCategories(categoriesId, categories);

            return ResponseEntity.ok(
                    ApiResponse.success("Update categories successfully ", categoriesRes)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @DeleteMapping("/{categoriesId}")
    public ResponseEntity<ApiResponse<String>> deleteCategories(@PathVariable String categoriesId) {
        try{
            String categoriesRes = categoriesService.deleteCategories(categoriesId);

            return ResponseEntity.ok(
                    ApiResponse.success("Delete categories successfully ", categoriesRes)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }
}
