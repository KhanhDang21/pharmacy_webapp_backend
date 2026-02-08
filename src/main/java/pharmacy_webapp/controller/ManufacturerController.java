package pharmacy_webapp.controller;

import com.cloudinary.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/create-manufacturer")
    public ResponseEntity<ApiResponse<Manufacturer>> createManufacturer(@RequestBody ManufacturerDto manufacturerDto) {
        try{
            Manufacturer manufacturer = manufacturerService.createManufacturer(manufacturerDto);

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

    @PutMapping("/{manufacturerId}")
    public ResponseEntity<ApiResponse<Manufacturer>> updateManufacturer(@PathVariable String manufacturerId,@RequestBody ManufacturerDto manufacturerDto) {
        try{
            Manufacturer manufacturer = manufacturerService.updateManufacturer(manufacturerId, manufacturerDto);

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
