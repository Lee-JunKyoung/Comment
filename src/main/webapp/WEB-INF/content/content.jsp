<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="ljk.comment.dto.CommentDTO"%>
<%@page import="java.util.List"%>
<%@page import="ljk.comment.dao.CommentDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Comment System</title>
<style type="text/css">
* {
	padding: 0;
	margin: 0;
	color: #333;
}

#container {
	width: 70%; /* 화면 너비의 70%로 설정 */
	margin: 50px auto; /* 페이지 중앙 정렬을 위한 마진 유지 */
	background-color: #f8f9fa;
	border: 1px solid #ced4da;
	border-radius: 5px;
	padding: 20px;
}

h1 {
	font-size: large;
	border-left: 10px solid #7BAEB5;
	border-bottom: 1px solid #7BAEB5;
	padding: 10px;
	width: auto;
	margin-bottom: 20px; /* 제목과 내용 사이 간격 */
}

label {
	display: inline-block;
	width: 80px;
	font-size: 14px;
	font-weight: bold;
	margin-bottom: 10px;
}

input[type='text'], textarea, #edit_textarea_input {
	border: 1px solid #ccc;
	vertical-align: middle;
	padding: 3px 10px;
	font-size: 12px;
	line-height: 150%;
	width: 100%; /* 입력 요소 전체 너비 사용 */
	box-sizing: border-box; /* 내부 패딩 포함하여 너비 설정 */
	margin-bottom: 10px; /* 입력 요소 간 간격 */
	resize: none; /* 사용자가 크기 조정할 수 없도록 설정 */
	overflow: hidden; /* 내용이 넘칠 때 스크롤 막기 */
}

.comment_item {
	font-size: 13px;
	color: #333;
	padding: 15px;
	border-bottom: 1px dotted #ccc;
	line-height: 150%;
}

.comment_item .writer {
	color: #555;
	line-height: 200%;
}

.comment_item .writer input {
	vertical-align: middle;
}

.comment_item .writer .name {
	color: #222;
	font-weight: bold;
	font-size: 14px;
}

#edit_textarea {
	display: none; /* 수정 창 기본적으로 숨겨진 상태 */
}

#edit_textarea_input {
	/* 기본 스타일 */
	border: 1px solid #ccc;
	padding: 3px 10px;
	font-size: 12px;
	line-height: 150%;
	width: 100%;
	box-sizing: border-box;
	margin-bottom: 10px;
	resize: none;
	overflow-y: auto; /* 세로 스크롤 자동 표시 */
	/* 자동 높이 조절 */
	height: auto; /* 초기값 설정 */
	min-height: 100px; /* 최소 높이 지정 */
	max-height: 300px; /* 최대 높이 지정 */
}

#comment_section {
	border: 10px solid #ffffff;
	border-radius: 5px;
	padding: 20px;
	margin-top: 20px;
	background-color: #f8f9fa;
}

input[type='submit'], button {
	background-color: #007bff;
	color: #fff;
	border: none;
	padding: 10px 20px;
	font-size: 14px;
	cursor: pointer;
	border-radius: 5px;
	margin-top: 10px; /* 저장하기 버튼과 입력 요소 사이 간격 */
}

input[type='submit']:hover, button:hover {
	background-color: #0056b3;
}
</style>

<script src="js/jquery-3.5.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		// 글자 수 표시 함수
		function commentCount() {
			var maxContentLength = 250; // 최대 글자 수
			var contentLength = $("#comment_content").val().length; // 입력된 글자 수
			$("#contentCount").text(contentLength + "/" + maxContentLength);
		}
		
		function getCurrentVideoNum() {
		    // 현재 페이지의 URL 가져오기
		    var currentUrl = window.location.href;

		    // URL에서 파라미터 부분 가져오기
		    var urlParams = new URLSearchParams(currentUrl);

		    // video_num 파라미터 값 가져오기
		    var videoNum = urlParams.get('video_num');

		    return videoNum;
		}

		// 페이지 로드 시 글자 수 표시 업데이트
		commentCount();

		// 댓글 내용이 변경될 때마다 글자 수 표시 업데이트
		$("#comment_content").on("input", function() {
			commentCount();
		});
		
		$(document).ready(function() {
			$('#comment_content').on('input', function() {
				this.style.height = 'auto';
				this.style.height = (this.scrollHeight) + 'px';
			});
		});

		// 기본 댓글 목록 불러오기
		$.ajax({
			url : "CommentList.co",
			type : "GET",
			success : function(data) {
				$("#comment_list").html(data);
			},
			error : function() {
				alert("댓글 목록을 불러오는데 실패하였습니다. 잠시후에 다시 시도해 주세요.");
			}
		});

		// 댓글 내용 저장 이벤트
		$("#comment_form").submit(function(event) {
			event.preventDefault(); // 기본 이벤트 제거

			var formData = $(this).serialize(); // 폼 데이터 직렬화
			var commentContent = $("#comment_content").val(); // 댓글 내용 가져오기

			// 댓글 내용의 길이 검사
			if (commentContent.length > 250) {
				alert("댓글 내용은 최대 250자까지 입력할 수 있습니다.");
				return; // 함수 종료
			}

			// AJAX를 통한 댓글 추가
			$.ajax({
				url : "CommentInsert.co",
				type : "POST",
				data : formData,
				success : function() {
					// 댓글 추가 후 목록 다시 불러오기
					$.ajax({
						url : "CommentList.co",
						type : "GET",
						success : function(data) {
							$("#comment_list").html(data);
							$("#comment_content").val(""); // input 요소의 값 비우기
							$("#user_id").val(""); // input 요소의 값 비우기
							var userName = $("#user_id").text();
							alert(userName + "님의 댓글을 등록하였습니다.");
						},
						error : function() {
							alert("댓글 목록을 다시 불러오는데 실패하였습니다.");
						}
					});
				},
				error : function() {
					alert("댓글 작성에 실패하였습니다. 잠시 후에 다시 시도해 주세요.");
				}
			});
			$("#contentCount").text("0/250");
		});

		// 수정 버튼 클릭 이벤트
		$(document).on("click", ".edit_btn", function() {
			var $commentItem = $(this).closest(".comment_item");
			var $commentContent = $commentItem.find(".comment_content");
			var originalContent = $commentContent.text().trim();
			var $editTextarea = $commentItem.find(".edit_textarea textarea");
			var commentNum = $commentItem.data("comment_num"); // 댓글의 시퀀스 번호 가져오기

			// 수정 창에 원래 댓글 내용을 설정합니다.
			$editTextarea.val(originalContent);

			$commentContent.hide();
			// 수정 창 보이기
			$editTextarea.closest(".edit_textarea").show();

			// 수정하기 버튼과 삭제하기 버튼 가리기
			$commentItem.find(".edit_btn, .delete_btn").hide();
		});

		// 수정 완료 버튼 클릭 이벤트
		$(document).on(
				"click",
				".edit_complete_btn",
				function() {
					var $commentItem = $(this).closest(".comment_item");
					var editedContent = $commentItem.find(
							".edit_textarea textarea").val().trim();
					var $editTextarea = $commentItem.find(".edit_textarea");
					var commentNum = $commentItem.data("comment_num"); // 댓글의 시퀀스 번호 가져오기
					
					// 댓글 글자 수 확인
				    if (editedContent.length > 250) {
				        alert("250자 이상의 글자를 입력할 수 없습니다.");
				        return; // 글자 수가 250자 이상이면 함수 종료
				    }
					
					// 수정 창 숨기기
					$editTextarea.hide();

					// AJAX를 통해 댓글 수정
					$.ajax({
						url : "CommentUpdate.co",
						type : "POST",
						data : {
							comment_num : commentNum, // 수정할 댓글의 시퀀스 번호 전송
							comment_content : editedContent
						// 수정된 댓글 내용만 전송
						},
						success : function() {
							// 수정 후 목록 다시 불러오기
							$.ajax({
								url : "CommentList.co",
								type : "GET",
								success : function(data) {
									$("#comment_list").html(data);
									var userName = $("#user_id").text();
									alert(userName + "님의 댓글을 수정하였습니다.");
								},
								error : function() {
									alert("댓글 목록을 다시 불러오는데 실패하였습니다.");
								}
							});
						},
						error : function() {
							alert("댓글 수정에 실패하였습니다. 잠시 후에 다시 시도해 주세요.");
						}
					});

					// 수정 완료 후 수정 창 및 댓글 내용 표시
					$commentItem.find(".edit_textarea").hide();
					$commentItem.find(".comment_content").text(editedContent)
							.show();

					// 수정하기 버튼과 삭제하기 버튼 다시 보이기
					$commentItem.find(".edit_btn, .delete_btn").show();
				});

		// 삭제 버튼 클릭 이벤트
		$(document).on("click", ".delete_btn", function() {
			var comment_num = $(this).closest(".comment_item").val();
			console.log(comment_num)
			// AJAX를 통한 댓글 삭제
			$.ajax({
				url : "CommentDelete.co",
				type : "POST",
				data : {
					comment_num : comment_num
				},
				success : function() {
					// 삭제 후 목록 다시 불러오기
					$.ajax({
						url : "CommentList.co",
						type : "GET",
						success : function(data) {
							$("#comment_list").html(data);
							var userName = $("#user_id").text();
							alert(userName + "님의 댓글을 삭제하였습니다.");
						},
						error : function() {
							alert("댓글 목록을 다시 불러오는데 실패하였습니다.");
						}
					});
				},
				error : function() {
					alert("댓글 삭제에 실패하였습니다. 잠시 후에 다시 시도해 주세요.");
				}
			});
		});
	});
</script>
</head>
<body>
	<h1>댓글</h1>
	<div id="comment_section">
		<!-- 댓글 입력 부분 -->
		<div id="comment_write">
			<form id="comment_form">
				<!-- 작성자 입력 -->
				<div>
					<label for="user_name">작성자</label>
					<p id="user_id_output">
						<%=session.getAttribute("user_id")%>
					</p>
					<input type="text" name="user_id" id="user_id_input" required>
				</div>
				<!-- 댓글 내용 입력 -->
				<div>
					<label for="comment">댓글 내용</label>
					<textarea name="comment_content" id="comment_content" required></textarea>
					<small id="contentCount" class="form-text text-muted">0/250</small>
				</div>
				<!-- 댓글 저장 버튼 -->
				<input type="submit" value="저장하기">
			</form>
		</div>
		<!-- 댓글 목록 -->
		<div id="comment_list"></div>
	</div>
</body>
</html>
