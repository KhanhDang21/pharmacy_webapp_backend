package pharmacy_webapp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pharmacy_webapp.dto.SectionDto;
import pharmacy_webapp.model.Section;
import pharmacy_webapp.repository.SectionRepository;

@Service
public class SectionService {
    @Autowired
    SectionRepository sectionRepository;

    public Section createSection(SectionDto sectionDto) throws IOException {
        Section section = new Section();
        section.setTitle(sectionDto.getTitle());
        section.setType(sectionDto.getType());
        section.setEnabled(sectionDto.getEnabled());
        section.setOrder(sectionDto.getOrder());
        return sectionRepository.save(section);
    }

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public Section getSectionById(String sectionId) {
        return sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Section not found"));
    }

    public Section updateSection(String sectionId, SectionDto sectionDto) throws IOException {
        Section section = getSectionById(sectionId);

        if (section == null) {
            throw new RuntimeException("Section not found");
        }

        section.setEnabled(sectionDto.getEnabled());
        section.setOrder(sectionDto.getOrder());

        return sectionRepository.save(section);
    }

    public String deleteSection(String sectionId) {
        Section section = getSectionById(sectionId);

        if (section == null) {
            throw new RuntimeException("Section not found");
        }

        sectionRepository.delete(section);

        return "Section deleted";
    }

}
