package csci812_project.backend.repository;

import csci812_project.backend.entity.Subscription;
import csci812_project.backend.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /** ✅ Get all subscriptions for a user */
    List<Subscription> findByUser_UserId(Long userId);

    /** ✅ Get all active subscriptions */
    List<Subscription> findByStatus(SubscriptionStatus status);

    /** ✅ Find subscriptions due for billing */
    List<Subscription> findByNextBillingDateBeforeAndStatus(LocalDateTime now, SubscriptionStatus status);
}

