package ljk.comment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ljk.comment.dto.CommentDTO;
import ljk.comment.service.CommentService;


public class CommentDAO implements CommentService{
	private static Log log = LogFactory.getLog(CommentDAO.class);
	
	public ArrayList<CommentDTO> comment_select_all() {
		ArrayList<CommentDTO> arrayList = new ArrayList<CommentDTO>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/OracleDB");
			connection = dataSource.getConnection();
			String sql = "select * from VIDEO_COMMENT order by comment_num";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				CommentDTO commentDTO = new CommentDTO();
				commentDTO.setUser_id(resultSet.getString("user_id"));
				commentDTO.setComment_content(resultSet.getString("comment_content"));
				commentDTO.setComment_date(resultSet.getTimestamp("comment_date"));
				commentDTO.setComment_num(resultSet.getInt("comment_num"));
				arrayList.add(commentDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace( );
			}
		}
		return arrayList;
	}

	public CommentDTO comment_insert(CommentDTO commentDTO) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/OracleDB");
			connection = dataSource.getConnection();
			String sql = "insert into VIDEO_COMMENT (comment_num, user_id, comment_content, comment_date, video_num)";
			sql += " values (comment_num_seq.nextval, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, commentDTO.getUser_id());
			preparedStatement.setString(2, commentDTO.getComment_content());
			java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
			preparedStatement.setTimestamp(3, currentTimestamp);
			preparedStatement.setInt(4, commentDTO.getVideo_num());
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace( );
			}
		}
		return commentDTO;
	}
	
	public CommentDTO comment_update(CommentDTO commentDTO) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    try {
	        Context context = new InitialContext();
	        DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/OracleDB");
	        connection = dataSource.getConnection();

	        String sql = "UPDATE VIDEO_COMMENT SET comment_content = ?, comment_date = ?, user_id = ?, video_num = ? WHERE comment_num = ?";
	        log.info("UPSQL"+sql);
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1,commentDTO.getComment_content());
	        preparedStatement.setTimestamp(2,commentDTO.getComment_date());
	        preparedStatement.setString(3,commentDTO.getUser_id());
	        preparedStatement.setInt(4,commentDTO.getVideo_num());
	        preparedStatement.setInt(5, commentDTO.getComment_num());
	      
	        preparedStatement.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) preparedStatement.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return commentDTO;
	}
	public CommentDTO selectByCommentNum(int commentNum) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CommentDTO commentDTO = null;
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/OracleDB");
            connection = dataSource.getConnection();
            String sql = "SELECT * FROM VIDEO_COMMENT WHERE comment_num = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, commentNum);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                commentDTO = new CommentDTO();
                commentDTO.setUser_id(resultSet.getString("user_id"));
                commentDTO.setComment_content(resultSet.getString("comment_content"));
                commentDTO.setComment_date(resultSet.getTimestamp("comment_date"));
                commentDTO.setComment_num(resultSet.getInt("comment_num"));
                commentDTO.setVideo_num(resultSet.getInt("video_num"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return commentDTO;
    }

	public CommentDTO comment_delete(int comment_num){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/OracleDB");
			connection = dataSource.getConnection();
			String sql = "delete from VIDEO_COMMENT where comment_num = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, comment_num);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace( );
			}
		}
		return null;
	}
}
