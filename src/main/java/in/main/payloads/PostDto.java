package in.main.payloads;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {

	private Integer postId;
	
	@NotEmpty(message="title cannot be null !!")
	private String title;
	
	@NotEmpty(message="content cannot be null !!")
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private List<CommentDto> comments=new ArrayList<>();
}
