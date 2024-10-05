package in.main.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import in.main.entities.Category;
import in.main.exceptions.ResourceNotFoundException;
import in.main.payloads.CategoryDto;
import in.main.repositorys.CategoryRepository;
import in.main.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public Boolean createCategory(CategoryDto categoryDto) {
		Category category=modelMapper.map(categoryDto, Category.class);
		if(!ObjectUtils.isEmpty(category)) {
			categoryRepository.save(category);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Boolean updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category ","category Id ",categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updateCategory=categoryRepository.save(category);
		
		if(!ObjectUtils.isEmpty(updateCategory)) {
			return true;
		}	
		else {
			return false;
		}
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category ","category Id ",categoryId));
		CategoryDto categoryDto=modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> listCategory=categoryRepository.findAll();
		List<CategoryDto> categoryDtoList=listCategory.stream().map(category->modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
		return categoryDtoList;
	}

	@Override
	public Boolean delateCategory(Integer categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category ","category Id ",categoryId));
		if(!ObjectUtils.isEmpty(category)){
			categoryRepository.delete(category);
			return true;
		}
		else {
			return false;
		}
	}

}
