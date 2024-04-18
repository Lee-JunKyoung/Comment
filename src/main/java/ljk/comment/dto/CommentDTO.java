package ljk.comment.dto;

import java.sql.Timestamp;

public class CommentDTO {
	private int comment_num;
	private String comment_content;
	private Timestamp comment_date;
	private String user_id;
	private int video_num;
	
	public int getComment_num() {
		return comment_num;
	}
	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public Timestamp getComment_date() {
		return comment_date;
	}
	public void setComment_date(Timestamp comment_date) {
		this.comment_date = comment_date;
	}
	public int getVideo_num() {
		return video_num;
	}
	public void setVideo_num(int video_num) {
		this.video_num = video_num;
	}
	
	@Override
	public String toString() {
		return "CommentDTO [comment_num=" + comment_num +  ", user_id=" + user_id + ", comment_content=" + comment_content + ", comment_date=" + comment_date
				+ ", video_num=" + video_num + "]";
	}
}
