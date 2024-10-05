package in.main.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import in.main.entities.Comment;
import in.main.entities.Post;
import in.main.exceptions.ResourceNotFoundException;
import in.main.payloads.CommentDto;
import in.main.repositorys.CommentRepository;
import in.main.repositorys.PostRepository;
import in.main.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Boolean createComment(CommentDto commentDto, Integer postId) {
		Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post ","post Id ",postId));
		Comment comment=modelMapper.map(commentDto,Comment.class);
		comment.setPost(post);
		Comment saveComment=commentRepository.save(comment);
		if(ObjectUtils.isEmpty(saveComment)) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public Boolean deleteComment(Integer commentId) {
		Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment ","commrnt Id ",commentId));
		if(!ObjectUtils.isEmpty(comment)) {
			commentRepository.delete(comment);
			return true;
		}
		else {
			return false;
		}
	}
}
