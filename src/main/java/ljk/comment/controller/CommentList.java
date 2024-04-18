package ljk.comment.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ljk.comment.control.Controller;
import ljk.comment.dao.CommentDAO;
import ljk.comment.dto.CommentDTO;
import ljk.comment.handler.CommentHandlerAdapter;

public class CommentList implements Controller {
	private static Log log = LogFactory.getLog(CommentList.class);

	@Override
	public CommentHandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		CommentDAO commentDAO = new CommentDAO();
		CommentDTO commentDTO = new CommentDTO();
		log.info(commentDTO);
		List<CommentDTO> arrayList = new ArrayList<CommentDTO>();
		arrayList = commentDAO.comment_select_all();
		log.info(arrayList);
		request.setAttribute("list", arrayList);
		CommentHandlerAdapter  commentHandlerAdapter  = new CommentHandlerAdapter();
		log.info("댓글 정보 조회");
		
		commentHandlerAdapter.setPath("/WEB-INF/content/commentList.jsp");
		return commentHandlerAdapter;
	}
}
