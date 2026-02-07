package pharmacy_webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharmacy_webapp.model.Manufacturer;
import pharmacy_webapp.repository.ManufacturerRepository;

import java.util.List;

@Service
public class ManufacturerService {
    @Autowired
    ManufacturerRepository manufacturerRepository;

    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Manufacturer getManufacturerById(String manufacturerId) {
        return manufacturerRepository.findById(manufacturerId).get();
    }

    public Manufacturer updateManufacturer(Manufacturer manufacturer) {
        Manufacturer manufacturerUpdated = new Manufacturer();
        manufacturerUpdated.setName(manufacturer.getName());
        manufacturerUpdated.setDescription(manufacturer.getDescription());

        return manufacturerRepository.save(manufacturerUpdated);
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
