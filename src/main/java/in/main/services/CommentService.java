package in.main.services;

import in.main.payloads.CommentDto;

public interface CommentService {

	Boolean createComment(CommentDto commentDto,Integer postId);
	Boolean deleteComment(Integer commentId);
}
