package ljk.comment.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljk.comment.handler.CommentHandlerAdapter;

public interface Controller {
	public CommentHandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}