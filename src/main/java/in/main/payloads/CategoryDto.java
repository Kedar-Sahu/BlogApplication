package in.main.payloads;

import in.main.entities.Category;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {

	private Integer categoryId;
	
	@NotEmpty(message="category title canot be empty")
	private String categoryTitle;
	
	@NotEmpty(message="category description title canot be empty")
	private String categoryDescription;
}
