package ljk.comment.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ljk.comment.control.Controller;
import ljk.comment.controller.CommentDelete;
import ljk.comment.controller.CommentInsert;
import ljk.comment.controller.CommentList;
import ljk.comment.controller.CommentUpdate;
import ljk.comment.handler.CommentHandlerAdapter;

@WebServlet("/CommentDispatcherServlet")
public class CommentDispatcherServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(CommentDispatcherServlet.class);

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI( );
		String contextPath = request.getContextPath( );
		String pathURL = requestURI.substring(contextPath.length( ));
		log.info("맵핑명 조회 - " + pathURL);
		CommentHandlerAdapter commentHandlerAdapter = null;
		
		Controller controller = null;
		
        if(pathURL.equals("/CommentStart.co")) {
        	commentHandlerAdapter = new CommentHandlerAdapter();
			commentHandlerAdapter.setPath("/WEB-INF/content/content.jsp");
			log.info("경로 확인 - " + commentHandlerAdapter);
        }
        
        else if(pathURL.equals("/CommentList.co")) {        	
			controller = new CommentList( );
			commentHandlerAdapter = controller.execute(request, response);
			log.info("경로 확인 - " + commentHandlerAdapter);
		}
		
		else if(pathURL.equals("/CommentInsert.co")) {
			controller = new CommentInsert( );
			commentHandlerAdapter = controller.execute(request, response);
			log.info("경로 확인 - " + commentHandlerAdapter);
		}
        
		else if(pathURL.equals("/CommentUpdate.co")) {
			controller = new CommentUpdate( );
			commentHandlerAdapter = controller.execute(request, response);
			log.info("수정 경로 확인 - " + commentHandlerAdapter);
		}
        
		else if(pathURL.equals("/CommentDelete.co")) {
			controller = new CommentDelete( );
			commentHandlerAdapter = controller.execute(request, response);
			log.info("경로 확인 - " + commentHandlerAdapter);
		}
        
        if(commentHandlerAdapter != null) {
			if(commentHandlerAdapter.isRedirect( )) {
				response.sendRedirect(commentHandlerAdapter.getPath( ));
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(commentHandlerAdapter.getPath( ));
				dispatcher.forward(request, response);
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}
}
