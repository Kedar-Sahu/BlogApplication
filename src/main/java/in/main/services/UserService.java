package in.main.services;

import java.util.List;

import in.main.entities.User;
import in.main.payloads.UserDto;

public interface UserService {

	Boolean createUser(UserDto userDto);
	Boolean updateUser(UserDto userDto,Integer id);
	UserDto getUSerById(Integer id);
	List<UserDto> getAllUser();
	Boolean deleteUser(Integer id);
}
