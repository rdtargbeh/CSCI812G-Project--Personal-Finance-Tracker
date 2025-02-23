package csci812_project.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    /** Unique category ID */
    private Long id;

    /** Name of the category (e.g., "Food", "Salary") */
    private String name;

    /** Type of category (INCOME or EXPENSE) */
    private String type;

    /** Icon representing the category (optional, for UI purposes) */
    private String icon;

    /** Color code for category display (optional, for UI purposes) */
    private String colorCode;

    /** User ID if the category is custom; NULL if it's a system-defined category */
    private Long userId;

    /** Indicates whether the category is deleted (soft delete) */
    private boolean isDeleted;

    /** Timestamp for when the category was created */
    private LocalDateTime dateCreated;
}
