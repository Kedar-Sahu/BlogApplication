package in.main.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import in.main.entities.User;
import in.main.exceptions.ResourceNotFoundException;
import in.main.payloads.UserDto;
import in.main.repositorys.UserRepository;
import in.main.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private ModelMapper modelMapper;

	@Override
	public Boolean createUser(UserDto userDto) {
		User saveUser= modelMapper.map(userDto, User.class);
		User save=userRepository.save(saveUser);
		if(ObjectUtils.isEmpty(save)) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public Boolean updateUser(UserDto userDto, Integer id) {
		User user=userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User "," id ",id));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updateUser=userRepository.save(user);
		if(ObjectUtils.isEmpty(updateUser)) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public UserDto getUSerById(Integer id) {
		User user=userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User "," id ",id));
		UserDto userDto=modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> listUser=userRepository.findAll();
		List<UserDto> userDtoList=listUser.stream().map(user->modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
		return userDtoList;
	}

	@Override
	public Boolean deleteUser(Integer id) {
		User user=userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User "," id ",id));
		if(ObjectUtils.isEmpty(user)) {
			return false;
		}
		else {
			userRepository.delete(user);
			return true;
		}
	}

	

}
