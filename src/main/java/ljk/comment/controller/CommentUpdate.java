package ljk.comment.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljk.comment.control.Controller;
import ljk.comment.dao.CommentDAO;
import ljk.comment.dto.CommentDTO;
import ljk.comment.handler.CommentHandlerAdapter;

public class CommentUpdate implements Controller {
	
	private static final Logger logger = Logger.getLogger(CommentUpdate.class.getName());

    @Override
    public CommentHandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
        // 요청 파라미터에서 댓글의 고유 식별자(comment_num)와 수정할 내용(comment_content)을 받아옴
        String commentNumString = request.getParameter("comment_num");
        logger.info(commentNumString);
        String commentContent = request.getParameter("comment_content");
        logger.info(commentContent);

        // 댓글 DAO 생성
        CommentDAO commentDAO = new CommentDAO();

        // null 체크를 하고 변환 작업을 수행합니다.
        int commentNum = 0; // 기본값으로 초기화합니다.
        try {
        	if (commentNumString != null && !commentNumString.isEmpty()) {
                commentNum = Integer.parseInt(commentNumString);
                System.out.println("commentNum값이" + commentNum + "으로 인식됨.");
            } else {
                System.out.println("commentNum값이 null입니다.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // 해당 comment_num에 해당하는 댓글을 불러와서 수정
        CommentDTO commentDTO = commentDAO.selectByCommentNum(commentNum);
        if (commentDTO != null) {
            // 수정할 내용을 설정
            commentDTO.setComment_content(commentContent);

            // 댓글 업데이트
            commentDTO = commentDAO.comment_update(commentDTO);

            // 업데이트가 완료된 댓글 정보를 request에 저장
            request.setAttribute("updatedComment", commentDTO);
            // 수정 완료 후 처음의 JSP 페이지로 이동
            CommentHandlerAdapter commentHandlerAdapter = new CommentHandlerAdapter();
            // 처음의 JSP 페이지로의 경로 설정
            commentHandlerAdapter.setPath("/WEB-INF/content/commentUpdate.jsp");
            return commentHandlerAdapter;
        } else {
            // 해당 comment_num에 해당하는 댓글이 존재하지 않을 경우
            request.setAttribute("errorMessage", "해당 댓글을 찾을 수 없습니다.");
            // 에러 메시지를 처리하거나 다른 처리를 수행할 수 있음
            // 여기서는 처음의 JSP 페이지로 이동
            CommentHandlerAdapter commentHandlerAdapter = new CommentHandlerAdapter();
            // 처음의 JSP 페이지로의 경로 설정
            commentHandlerAdapter.setPath("/WEB-INF/content/commentUpdate.jsp");
            return commentHandlerAdapter;
        }
    }
}
