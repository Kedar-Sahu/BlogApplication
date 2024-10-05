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

import in.main.entities.User;
import in.main.payloads.ApiResponse;
import in.main.payloads.UserDto;
import in.main.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@PostMapping("/create")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto){
		Boolean save=userService.createUser(userDto);
		if(!save) {
			return new ResponseEntity<>("User create failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>("User create successfully",HttpStatus.CREATED);
		}
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto ,@PathVariable Integer id){
		Boolean update=userService.updateUser(userDto, id);
		if(!update) {
			return new ResponseEntity<>("User update failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>("User update successfully",HttpStatus.OK);
		}
	}
	
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Integer id){
		UserDto userDto=userService.getUSerById(id);
		if(ObjectUtils.isEmpty(userDto)) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>(userDto,HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllUsers(){
		List<UserDto> listUserDto=userService.getAllUser();
		if(CollectionUtils.isEmpty(listUserDto)) {
			return new ResponseEntity<>(listUserDto,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>(listUserDto,HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id){
		Boolean delete=userService.deleteUser(id);
		if(!delete) {
			return new ResponseEntity<>(new ApiResponse("user delete failed",false),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>(new ApiResponse("user delete successfully",true),HttpStatus.OK);
		}
	}
	
}
