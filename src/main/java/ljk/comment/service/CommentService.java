package ljk.comment.service;

import java.util.ArrayList;

import ljk.comment.dto.CommentDTO;

public interface CommentService {

		public ArrayList<CommentDTO> comment_select_all( );

		public CommentDTO comment_insert(CommentDTO commentDTO);
		
		public CommentDTO comment_update(CommentDTO commentDTO);

		public CommentDTO comment_delete(int comment_num);
}
