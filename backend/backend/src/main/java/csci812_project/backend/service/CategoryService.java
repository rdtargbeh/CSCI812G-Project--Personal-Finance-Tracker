package csci812_project.backend.service;

import csci812_project.backend.dto.CategoryDTO;
import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategoryById(Long id);

    List<CategoryDTO> getCategoriesByUser(Long userId);

    List<CategoryDTO> getAllCategories();

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}
