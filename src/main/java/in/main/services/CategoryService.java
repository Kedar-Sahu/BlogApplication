package in.main.services;

import java.util.List;

import in.main.payloads.CategoryDto;

public interface CategoryService {

	Boolean createCategory(CategoryDto categoryDto);
	Boolean updateCategory(CategoryDto categoryDto,Integer categoryId);
	CategoryDto getCategoryById(Integer categoryId);
	List<CategoryDto> getAllCategory();
	Boolean delateCategory(Integer categoryId);
}
