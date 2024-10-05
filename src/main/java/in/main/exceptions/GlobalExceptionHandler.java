package in.main.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import in.main.payloads.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String message=ex.getMessage();
		ApiResponse api=new ApiResponse(message,false);
		return new ResponseEntity<>(api,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
		Map<String,String> response=new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error-> {
			String filedName=((FieldError)error).getField();
			String message=error.getDefaultMessage();
			response.put(filedName, message);
		});
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
}
