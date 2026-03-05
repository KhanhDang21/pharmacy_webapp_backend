package pharmacy_webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pharmacy_webapp.dto.ManufacturerDto;
import pharmacy_webapp.model.Manufacturer;
import pharmacy_webapp.repository.ManufacturerRepository;

import java.io.IOException;
import java.util.List;

@Service
public class ManufacturerService {
    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Autowired
    CloudinaryService cloudinaryService;

    public Manufacturer createManufacturer(ManufacturerDto manufacturerDto, MultipartFile image) throws IOException {
        String urlImage = "";
        if(image != null && !image.isEmpty()) {
            urlImage = cloudinaryService.uploadImage(image);
        }

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(manufacturerDto.getName());
        manufacturer.setUrlImage(urlImage);
        manufacturer.setDescription(manufacturerDto.getDescription());

        return manufacturerRepository.save(manufacturer);
    }

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Manufacturer getManufacturerById(String manufacturerId) {
        return manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found"));
    }

    public Manufacturer updateManufacturer(String manufacturerId, ManufacturerDto manufacturerDto, MultipartFile image) throws IOException {
        Manufacturer manufacturer = getManufacturerById(manufacturerId);

        if(manufacturer == null){
            throw new RuntimeException("Manufacturer not found");
        }

        String urlImage = "";
        if(image != null && !image.isEmpty()) {
            cloudinaryService.deleteImage(manufacturer.getUrlImage());
            urlImage = cloudinaryService.uploadImage(image);
        }

        manufacturer.setName(manufacturerDto.getName());
        manufacturer.setUrlImage(urlImage);
        manufacturer.setDescription(manufacturerDto.getDescription());

        return manufacturerRepository.save(manufacturer);
    }

    public String deleteManufacturer(String manufacturerId) {
        Manufacturer manufacturer = getManufacturerById(manufacturerId);

        if(manufacturer == null) {
            throw new RuntimeException("Manufacturer not found");
        }

        manufacturerRepository.delete(manufacturer);

        return "Manufacturer deleted";
    }
}
