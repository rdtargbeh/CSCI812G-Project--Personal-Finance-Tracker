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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);

        if (categoryDTO.getUserId() != null) {
            User user = userRepository.findById(categoryDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            category.setUser(user);
        }

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
