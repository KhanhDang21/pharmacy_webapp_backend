package pharmacy_webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharmacy_webapp.dto.ManufacturerDto;
import pharmacy_webapp.model.Manufacturer;
import pharmacy_webapp.repository.ManufacturerRepository;

import java.util.List;

@Service
public class ManufacturerService {
    @Autowired
    ManufacturerRepository manufacturerRepository;

    public Manufacturer createManufacturer(ManufacturerDto manufacturerDto) {

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(manufacturerDto.getName());
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

    public Manufacturer updateManufacturer(String manufacturerId, ManufacturerDto manufacturerDto) {

        Manufacturer manufacturer = getManufacturerById(manufacturerId);

        if(manufacturer == null){
            throw new RuntimeException("Manufacturer not found");
        }

        manufacturer.setName(manufacturerDto.getName());
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
