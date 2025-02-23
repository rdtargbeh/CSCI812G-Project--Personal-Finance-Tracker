package csci812_project.backend.mapper;

import csci812_project.backend.dto.CategoryDTO;
import csci812_project.backend.entity.Category;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.CategoryType;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setName(category.getName());
        dto.setType(category.getType().name());
        dto.setIcon(category.getIcon());
        dto.setColorCode(category.getColorCode());
        dto.setUserId(category.getUser() != null ? category.getUser().getUserId() : null);
        dto.setDeleted(category.isDeleted());
        return dto;
    }

    public Category toEntity(CategoryDTO categoryDTO, User user) {
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setName(categoryDTO.getName());
        category.setType(CategoryType.valueOf(categoryDTO.getType()));
        category.setIcon(categoryDTO.getIcon());
        category.setColorCode(categoryDTO.getColorCode());
        category.setUser(user); // ✅ Correctly assign User object
        category.setDeleted(categoryDTO.isDeleted());
        return category;
    }

}
