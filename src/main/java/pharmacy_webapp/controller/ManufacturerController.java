package pharmacy_webapp.controller;

import com.cloudinary.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pharmacy_webapp.dto.ApiResponse;
import pharmacy_webapp.dto.ManufacturerDto;
import pharmacy_webapp.model.Manufacturer;
import pharmacy_webapp.service.ManufacturerService;

import java.util.List;

@RestController
@RequestMapping("/api/manufacturer")
public class ManufacturerController {
    @Autowired
    ManufacturerService manufacturerService;

    @PostMapping(value = "/create-manufacturer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Manufacturer>> createManufacturer(
            @RequestParam String name,
            @RequestParam String description,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try{
            ManufacturerDto manufacturerDto = new ManufacturerDto(
                    name, description
            );

            Manufacturer manufacturer = manufacturerService.createManufacturer(manufacturerDto, image);

            return ResponseEntity.ok(
                    ApiResponse.success("Create manufacturer successfully", manufacturer)
            );
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @GetMapping("/get-all-manufacturer")
    public ResponseEntity<ApiResponse<List<Manufacturer>>> getAllManufacturer() {
        try{
            List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();

            return ResponseEntity.ok(
                    ApiResponse.success("Get all manufacturer successfully", manufacturers)
            );
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @GetMapping("/{manufacturerId}")
    public ResponseEntity<ApiResponse<Manufacturer>> getManufacturerById(@PathVariable String manufacturerId) {
        try{
            Manufacturer manufacturer = manufacturerService.getManufacturerById(manufacturerId);

            return ResponseEntity.ok(
                    ApiResponse.success("Get manufacturer successfully", manufacturer)
            );
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @PutMapping(value = "/{manufacturerId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Manufacturer>> updateManufacturer(
            @PathVariable String manufacturerId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try{
            ManufacturerDto manufacturerDto = new ManufacturerDto(
                    name, description
            );

            Manufacturer manufacturer = manufacturerService.updateManufacturer(manufacturerId, manufacturerDto, image);

            return ResponseEntity.ok(
                    ApiResponse.success("Update manufacturer successfully", manufacturer)
            );
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @DeleteMapping("/{manufacturerId}")
    public ResponseEntity<ApiResponse<String>> deleteManufacturer(@PathVariable String manufacturerId) {
        try{
            String res = manufacturerService.deleteManufacturer(manufacturerId);

            return ResponseEntity.ok(
                    ApiResponse.success("Delete manufacturer successfully", res)
            );
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }
}
