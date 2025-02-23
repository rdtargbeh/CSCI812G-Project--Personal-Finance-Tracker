package csci812_project.backend.service.implement;

import csci812_project.backend.dto.CategoryDTO;
import csci812_project.backend.entity.Category;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.CategoryType;
import csci812_project.backend.mapper.CategoryMapper;
import csci812_project.backend.repository.CategoryRepository;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplementation implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        User user = null; // ✅ Default to null in case it's a system category

        if (categoryDTO.getUserId() != null) {
            user = userRepository.findById(categoryDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        // ✅ Pass all required arguments to `toEntity()`
        Category category = categoryMapper.toEntity(categoryDTO, user);

        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toDTO(category);
    }

    @Override
    public List<CategoryDTO> getCategoriesByUser(Long userId) {
        return categoryRepository.findByUser_UserIdAndIsDeletedFalse(userId)
                .stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findByIsDeletedFalse()
                .stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setName(categoryDTO.getName());
        existingCategory.setType(CategoryType.valueOf(categoryDTO.getType()));
        existingCategory.setIcon(categoryDTO.getIcon());
        existingCategory.setColorCode(categoryDTO.getColorCode());

        return categoryMapper.toDTO(categoryRepository.save(existingCategory));
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setDeleted(true);
        categoryRepository.save(category);
    }
}
