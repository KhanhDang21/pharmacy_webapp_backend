package pharmacy_webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pharmacy_webapp.dto.ApiResponse;
import pharmacy_webapp.dto.SectionDto;
import pharmacy_webapp.model.Section;
import pharmacy_webapp.service.SectionService;

@RestController
@RequestMapping("/api/section")
public class SectionController {
    @Autowired
    SectionService sectionService;

    @PostMapping(value = "/create-section")
    public ResponseEntity<ApiResponse<Section>> createSection(
            @RequestParam String title,
            @RequestParam String type,
            @RequestParam Boolean enabled,
            @RequestParam int order) {
        try {
            SectionDto sectionDto = new SectionDto(
                    title, type, enabled, order);

            Section section = sectionService.createSection(sectionDto);

            return ResponseEntity.ok(
                    ApiResponse.success("Create section successfully", section));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/get-all-section")
    public ResponseEntity<ApiResponse<List<Section>>> getAllSection() {
        try {
            List<Section> sections = sectionService.getAllSections();

            return ResponseEntity.ok(
                    ApiResponse.success("Get all section successfully", sections));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{sectionId}")
    public ResponseEntity<ApiResponse<Section>> getSectionById(@PathVariable String sectionId) {
        try {
            Section section = sectionService.getSectionById(sectionId);

            return ResponseEntity.ok(
                    ApiResponse.success("Get section by id successfully", section));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{sectionId}")
    public ResponseEntity<ApiResponse<Section>> updateSection(
            @PathVariable String sectionId,
            @RequestParam Boolean enabled,
            @RequestParam int order) {
        try {
            SectionDto sectionDto = new SectionDto();
            sectionDto.setEnabled(enabled);
            sectionDto.setOrder(order);

            Section section = sectionService.updateSection(sectionId, sectionDto);

            return ResponseEntity.ok(
                    ApiResponse.success("Update section successfully", section));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{sectionId}")
    public ResponseEntity<ApiResponse<String>> deleteSection(@PathVariable String sectionId) {
        try {
            String res = sectionService.deleteSection(sectionId);

            return ResponseEntity.ok(
                    ApiResponse.success("Delete section successfully", res));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage()));
        }
    }
}