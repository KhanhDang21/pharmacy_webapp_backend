package pharmacy_webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pharmacy_webapp.dto.ApiResponse;
import pharmacy_webapp.dto.DiscountEventDto;
import pharmacy_webapp.model.DiscountEvent;
import pharmacy_webapp.service.DiscountEventService;

import java.util.List;

@RestController
@RequestMapping("/api/discount-event")
public class DiscountEventController {
    @Autowired
    private DiscountEventService discountEventService;

    @PostMapping("/create-discount-event")
    public ResponseEntity<ApiResponse<DiscountEvent>> createDiscountEvent(@RequestBody DiscountEventDto discountEventDto) {
        try {
            DiscountEvent discountEvent = discountEventService.createEvent(discountEventDto);
            return ResponseEntity.ok(
                    ApiResponse.success("create discount event", discountEvent)
            );
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @GetMapping("/get-all-discount-event")
    public ResponseEntity<ApiResponse<List<DiscountEvent>>> getAllDiscountEvent() {
        try{
            List<DiscountEvent> discountEvents = discountEventService.getAllEvents();

            return ResponseEntity.ok(
                    ApiResponse.success("Get all discount event", discountEvents)
            );
        }catch(Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ApiResponse<DiscountEvent>> getDiscountEvent(@PathVariable String eventId) {
        try{
            DiscountEvent discountEvent = discountEventService.getEventById(eventId);

            return ResponseEntity.ok(
                    ApiResponse.success("Get discount event", discountEvent)
            );
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<ApiResponse<DiscountEvent>> updateDiscountEvent(
            @PathVariable String eventId,
            @RequestBody DiscountEventDto discountEventDto
    ){
        try{
            DiscountEvent discountEvent = discountEventService.updateEvent(eventId, discountEventDto);

            return ResponseEntity.ok(
                    ApiResponse.success("update discount event", discountEvent)
            );
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResponse<Void>> deleteDiscountEvent(@PathVariable String eventId) {
        try{
            String res = discountEventService.deleteEvent(eventId);

            return ResponseEntity.ok(
                    ApiResponse.success(res)
            );
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @PutMapping("/add-product-to-event")
    public ResponseEntity<ApiResponse<DiscountEvent>> addProductToDiscountEvent(
            @RequestParam String eventId,
            @RequestParam String productId
    ) {
        try{
            DiscountEvent discountEvent = discountEventService.addProductToEvent(eventId, productId);

            return ResponseEntity.ok(
                    ApiResponse.success("Add product to event", discountEvent)
            );
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @PutMapping("/remove-product-to-event")
    public ResponseEntity<ApiResponse<DiscountEvent>> removeProductFromDiscountEvent(
            @RequestParam String eventId,
            @RequestParam String productId
    ){
        try{
            DiscountEvent discountEvent = discountEventService.removeProductFromEvent(eventId, productId);

            return ResponseEntity.ok(
                    ApiResponse.success("Remove product from event", discountEvent)
            );
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

}
