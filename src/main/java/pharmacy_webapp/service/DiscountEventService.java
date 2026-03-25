package pharmacy_webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharmacy_webapp.dto.DiscountEventDto;
import pharmacy_webapp.model.DiscountEvent;
import pharmacy_webapp.repository.DiscountEventRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
public class DiscountEventService {
    @Autowired
    private DiscountEventRepository discountEventRepository;

    public DiscountEvent createEvent(DiscountEventDto eventDto) {

        if (eventDto.getStartDate() == null || eventDto.getEndDate() == null) {
            throw new RuntimeException("Event must have start and end date");
        }
        if (eventDto.getEndDate().isBefore(eventDto.getStartDate())) {
            throw new RuntimeException("End date must be after start date");
        }
        if (eventDto.getDiscountPercent() == null
                || eventDto.getDiscountPercent() < 0
                || eventDto.getDiscountPercent() > 100) {
            throw new RuntimeException("Discount percent must be between 0 and 100");
        }

        DiscountEvent event = new DiscountEvent();
        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.setStartDate(eventDto.getStartDate());
        event.setEndDate(eventDto.getEndDate());
        event.setActive(eventDto.isActive());
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        if (eventDto.getProductIds() == null) {
            event.setProductIds(new HashSet<>());
        }
        event.setDiscountPercent(eventDto.getDiscountPercent());
        event.setProductIds(eventDto.getProductIds());
        return discountEventRepository.save(event);
    }

    public DiscountEvent updateEvent(String eventId, DiscountEventDto eventDto) {
        DiscountEvent event = getEventById(eventId);

        if (eventDto.getStartDate() == null || eventDto.getEndDate() == null) {
            throw new RuntimeException("Event must have start and end date");
        }
        if (eventDto.getEndDate().isBefore(eventDto.getStartDate())) {
            throw new RuntimeException("End date must be after start date");
        }
        if (eventDto.getDiscountPercent() == null
                || eventDto.getDiscountPercent() < 0
                || eventDto.getDiscountPercent() > 100) {
            throw new RuntimeException("Discount percent must be between 0 and 100");
        }

        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.setStartDate(eventDto.getStartDate());
        event.setEndDate(eventDto.getEndDate());
        event.setActive(eventDto.isActive());
        event.setDiscountPercent(eventDto.getDiscountPercent());
        event.setProductIds(eventDto.getProductIds());
        event.setUpdatedAt(LocalDateTime.now());

        return discountEventRepository.save(event);
    }

    public String deleteEvent(String eventId) {
        discountEventRepository.deleteById(eventId);
        return "Discount event deleted";
    }

    public DiscountEvent getEventById(String eventId) {
        return discountEventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found: " + eventId));
    }

    public List<DiscountEvent> getAllEvents() {
        return discountEventRepository.findAll();
    }

    public DiscountEvent addProductToEvent(String eventId, String productId) {
        DiscountEvent event = getEventById(eventId);
        if (!event.getProductIds().contains(productId)) {
            event.getProductIds().add(productId);
            event.setUpdatedAt(LocalDateTime.now());
            discountEventRepository.save(event);
        }
        return event;
    }

    public DiscountEvent removeProductFromEvent(String eventId, String productId) {
        DiscountEvent event = getEventById(eventId);
        event.getProductIds().remove(productId);
        event.setUpdatedAt(LocalDateTime.now());
        return discountEventRepository.save(event);
    }

    public int getBestEventDiscountPercent(String productId, LocalDateTime purchaseTime) {
        List<DiscountEvent> allActiveEvents = discountEventRepository.findByActiveTrue();

        return allActiveEvents.stream()
                .filter(event ->
                        !purchaseTime.isBefore(event.getStartDate()) &&
                                !purchaseTime.isAfter(event.getEndDate()) &&
                                event.getProductIds() != null &&
                                event.getProductIds().contains(productId)
                )
                .mapToInt(DiscountEvent::getDiscountPercent)
                .max()
                .orElse(0);
    }

    public double calculateFinalPrice(double price,
                                      int productDiscountPercent,
                                      int eventDiscountPercent) {
        int appliedDiscount = Math.max(productDiscountPercent, eventDiscountPercent);
        return price * (100 - appliedDiscount) / 100.0;
    }

}