package ljk.comment.controller;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ljk.comment.control.Controller;
import ljk.comment.dao.CommentDAO;
import ljk.comment.dto.CommentDTO;
import ljk.comment.handler.CommentHandlerAdapter;

public class CommentInsert implements Controller {
	private static Log log = LogFactory.getLog(CommentInsert.class);

	@Override
	public CommentHandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("user_id");
		String content = request.getParameter("comment_content");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		CommentDTO commentDTO = new CommentDTO();
		CommentDAO commentDAO = new CommentDAO();
		ArrayList<CommentDTO> arrayList = new ArrayList<CommentDTO>();
		arrayList = commentDAO.comment_select_all();
		log.info(arrayList);
		request.setAttribute("list", arrayList);
		
		commentDTO.setComment_content(content);
		commentDTO.setComment_date(timestamp);
		commentDTO.setUser_id(name);
		
		commentDTO = commentDAO.comment_insert(commentDTO);
		log.info(commentDTO);
		request.setAttribute("commentDTO", commentDTO);
		
		CommentHandlerAdapter commentHandlerAdapter = new CommentHandlerAdapter( );
		commentHandlerAdapter.setPath("/WEB-INF/content/commentInsert.jsp");
		return commentHandlerAdapter;
	}

}
