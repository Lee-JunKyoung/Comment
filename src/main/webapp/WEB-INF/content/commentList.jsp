<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Comment List</title>
</head>
<body>
	<div>
		<ul id="comment_list">
			<!-- 댓글 목록 표시 -->
			<c:forEach var="comment" items="${list}">
				<li class="comment_item" id="comment_item" data-comment_num="${comment.comment_num}" value="${comment.comment_num}">
					<p class="writer">
						<span class="name"> <c:out value="${comment.user_id}" /> 님
						</span> <span><c:out value="${comment.comment_date}" /> </span>
						<!-- 수정 버튼 -->
						<input type="button" value="수정하기" class="edit_btn">
						<!-- 삭제 버튼 -->
						<input type="button" value="삭제하기" class="delete_btn">
					</p> <!-- 댓글 내용 -->
					<p class="comment_content">
						<c:out value="${comment.comment_content}" />
					</p>
					<!-- 수정 창 -->
					<div id="edit_textarea" class="edit_textarea">
						<textarea name="edit_textarea_input" id="edit_textarea_input" required></textarea>
						<input type="button" value="수정 완료" class="edit_complete_btn">
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>
