package csci812_project.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
public class CategoryDTO {

    /** Unique category ID */
    private Long categoryId;
    /** Name of the category (e.g., "Food", "Salary") */
    private String name;
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

    // Constructor
    public CategoryDTO(){}
    public CategoryDTO(Long categoryId, String name, String type, String icon, String colorCode, Long userId, boolean isDeleted,
                       LocalDateTime dateCreated) {
        this.categoryId = categoryId;
        this.name = name;
        this.type = type;
        this.icon = icon;
        this.colorCode = colorCode;
        this.userId = userId;
        this.isDeleted = isDeleted;
        this.dateCreated = dateCreated;
    }

    // Getter and Setter
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
