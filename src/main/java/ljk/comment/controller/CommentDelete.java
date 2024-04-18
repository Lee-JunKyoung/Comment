package ljk.comment.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljk.comment.control.Controller;
import ljk.comment.dao.CommentDAO;
import ljk.comment.handler.CommentHandlerAdapter;

public class CommentDelete implements Controller {

	@Override
	public CommentHandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CommentDAO commentDAO = new CommentDAO();
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		commentDAO.comment_delete(comment_num);
		System.out.println("삭제 번호 내용"+comment_num);
		return null;
	}
}
