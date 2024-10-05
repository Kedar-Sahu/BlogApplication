package in.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.main.payloads.CommentDto;
import in.main.services.CommentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	
	@PostMapping("/post/{postId}")
	public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId){
		Boolean save=commentService.createComment(commentDto, postId);
		if(!save) {
			return new ResponseEntity<>("comment create failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>("comment create successfully",HttpStatus.CREATED);
		}
	}
	
	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable Integer commentId){
		Boolean delete=commentService.deleteComment(commentId);
		if(!delete) {
			return new ResponseEntity<>("comment delete failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>("comment delete successfully",HttpStatus.OK);
		}
	}
	
}
