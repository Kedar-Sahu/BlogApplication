package in.main.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min=4 ,message="username must be min of 4 characters !!")
	private String name;
	
	@Email(message ="email address in not valid !!")
	private String email;
	
	@NotNull
	@Size(min=3,max=10,message="password must be min of 3 and max of 10 !!")
	private String password;
	
	@NotNull
	private String about;
}
