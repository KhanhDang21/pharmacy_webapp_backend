package pharmacy_webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pharmacy_webapp.dto.ApiResponse;
import pharmacy_webapp.dto.TagDto;
import pharmacy_webapp.model.Tag;
import pharmacy_webapp.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    TagService  tagService;

    @PostMapping("/create-tag")
    public ResponseEntity<ApiResponse<Tag>> createTag(
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam Boolean enabled,
            @RequestParam Integer order) {
        try {
            TagDto tagDto = new TagDto(
                    name, type, enabled, order);

            Tag tag = tagService.createTag(tagDto);

            return ResponseEntity.ok(
                    ApiResponse.success("Create tag successfully", tag)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/get-all-tag")
    public ResponseEntity<ApiResponse<List<Tag>>> getAllTag() {
        try {
            List<Tag> tags = tagService.getAllTags();

            return ResponseEntity.ok(
                    ApiResponse.success("Get all tag successfully", tags)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<ApiResponse<Tag>> getTagById(@PathVariable String tagId) {
        try {
            Tag tag = tagService.getTagById(tagId);

            return ResponseEntity.ok(
                    ApiResponse.success("Get tag successfully", tag));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @PutMapping("{tagId}")
    public ResponseEntity<ApiResponse<Tag>> updateTag(
            @PathVariable String tagId,
            @RequestParam Boolean enabled,
            @RequestParam Integer order
    ) {
        try {
            TagDto tagDto = new TagDto();
            tagDto.setEnabled(enabled);
            tagDto.setOrder(order);

            Tag tag = tagService.updateTag(tagId, tagDto);

            return ResponseEntity.ok(ApiResponse.success("Update tag successfully", tag));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<ApiResponse<String>> deleteTag(@PathVariable String tagId) {
        try {
            String res = tagService.deleteTag(tagId);

            return ResponseEntity.ok(
                    ApiResponse.success("Delete tag successfully", res));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }
}
