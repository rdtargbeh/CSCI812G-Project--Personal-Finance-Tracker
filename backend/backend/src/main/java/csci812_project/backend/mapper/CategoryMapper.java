package csci812_project.backend.mapper;

import csci812_project.backend.dto.CategoryDTO;
import csci812_project.backend.entity.Category;
import csci812_project.backend.enums.CategoryType;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {
        return CategoryDTO.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .type(category.getType().name())
                .icon(category.getIcon())
                .colorCode(category.getColorCode())
                .userId(category.getUser() != null ? category.getUser().getUserId() : null)
                .isDeleted(category.isDeleted())
                .build();
    }

    public Category toEntity(CategoryDTO categoryDTO) {
        return Category.builder()
                .categoryId(categoryDTO.getCategoryId())
                .name(categoryDTO.getName())
                .type(CategoryType.valueOf(categoryDTO.getType()))
                .icon(categoryDTO.getIcon())
                .colorCode(categoryDTO.getColorCode())
                .isDeleted(categoryDTO.isDeleted())
                .build();
    }
}
