package pharmacy_webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharmacy_webapp.dto.TagDto;
import pharmacy_webapp.model.Tag;
import pharmacy_webapp.repository.SectionRepository;
import pharmacy_webapp.repository.TagRepository;

import java.io.IOException;
import java.util.List;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;
    @Autowired
    private SectionRepository sectionRepository;

    public Tag createTag(TagDto tagDto) throws IOException {
        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        tag.setType(tagDto.getType());
        tag.setEnabled(tagDto.getEnabled());
        tag.setOrder(tagDto.getOrder());
        return tagRepository.save(tag);
    }

    public List<Tag> getAllTags() throws IOException {
        return tagRepository.findAll();
    }

    public Tag getTagById(String tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("tag not found"));
    }

    public Tag updateTag(String tagId, TagDto tagDto) throws IOException {
        Tag tag = getTagById(tagId);

        if (tag == null) {
            throw new RuntimeException("tag not found");
        }

        tag.setEnabled((tagDto.getEnabled()));
        tag.setOrder(tagDto.getOrder());

        return tagRepository.save(tag);
    }

    public String deleteTag(String tagId) {
        Tag tag = getTagById((tagId));

        if (tag == null) {
            throw new RuntimeException("tag not found");
        }

        tagRepository.delete(tag);

        return "tag deleted";
    }
}
