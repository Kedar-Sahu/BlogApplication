package in.main.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.main.payloads.CategoryDto;
import in.main.services.CategoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		Boolean category=categoryService.createCategory(categoryDto);
		if(!category) {
			return new ResponseEntity<>("category ctreate failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>("category ctreate successfully",HttpStatus.CREATED);
		}
	}
	
	@PutMapping("/update/{categoryId}")
	public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
		Boolean update=categoryService.updateCategory(categoryDto, categoryId);
		if(!update) {
			return new ResponseEntity<>("category update failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>("category update successfully",HttpStatus.OK);
		}
	}
	
	@GetMapping("/getById/{categoryId}")
	public ResponseEntity<?> getCategoryById(@PathVariable Integer categoryId){
		CategoryDto categoryDto=categoryService.getCategoryById(categoryId);
		if(ObjectUtils.isEmpty(categoryDto)) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>(categoryDto,HttpStatus.OK);
		}
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllCategory(){
		List<CategoryDto> categoryDtoList=categoryService.getAllCategory();
		if(CollectionUtils.isEmpty(categoryDtoList)) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>(categoryDtoList,HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId){
		Boolean delete=categoryService.delateCategory(categoryId);
		if(!delete) {
			return new ResponseEntity<>("category delete failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>("category delete successfully",HttpStatus.OK);
		}
	}
}
