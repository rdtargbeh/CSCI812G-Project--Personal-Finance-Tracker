package csci812_project.backend.controller;

import csci812_project.backend.dto.SubscriptionDTO;
import csci812_project.backend.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;


    /**
     * ✅ Get Subscription by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> getSubscriptionById(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionById(id));
    }

    /**
     * ✅ Get All Subscriptions for a User
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptionsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsByUser(userId));
    }

    /**
     * ✅ Create a New Subscription
     */
    @PostMapping
    public ResponseEntity<SubscriptionDTO> createSubscription(@RequestBody SubscriptionDTO dto) {
        return ResponseEntity.ok(subscriptionService.createSubscription(dto));
    }

    /**
     * ✅ Update an Existing Subscription
     */
    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> updateSubscription(@PathVariable Long id, @RequestBody SubscriptionDTO dto) {
        return ResponseEntity.ok(subscriptionService.updateSubscription(id, dto));
    }

    /**
     * ✅ Cancel a Subscription
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelSubscription(@PathVariable Long id) {
        subscriptionService.cancelSubscription(id);
        return ResponseEntity.ok("Subscription cancelled successfully.");
    }
}
