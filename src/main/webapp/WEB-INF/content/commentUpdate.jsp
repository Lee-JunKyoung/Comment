<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="ljk.comment.dao.CommentDAO"%>
<%@page import="ljk.comment.dto.CommentDTO"%>
<%@page import="java.sql.Date"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>댓글 수정 결과</title>
</head>
<body>
    <h2>댓글 수정 결과</h2>
    
    <%-- 수정된 댓글 내용을 표시합니다 --%>
    <div>
        <% 
            CommentDTO updatedComment = (CommentDTO) request.getAttribute("updatedComment");
            if (updatedComment != null) {
                String updatedContent = updatedComment.getComment_content();
        %>
        <p>수정된 댓글 내용: <%= updatedContent %></p>
        <% 
            } else {
                out.println("댓글 수정에 실패하였습니다.");
            }
        %>
    </div>
    
    <%-- 수정 결과 메시지를 표시합니다 --%>
    <div>
        <% 
            String errorMessage = (String)request.getAttribute("errorMessage");
            if (errorMessage != null) {
                out.println("오류 메시지: " + errorMessage);
            }
        %>
    </div>
</body>
</html>
