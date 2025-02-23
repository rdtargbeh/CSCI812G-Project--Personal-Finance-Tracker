package csci812_project.backend.repository;

import csci812_project.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /** ✅ Find all active (non-deleted) categories */
    List<Category> findByIsDeletedFalse();

    /** ✅ Find all categories for a user */
    List<Category> findByUser_UserIdAndIsDeletedFalse(Long userId);
}
