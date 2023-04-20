package sample02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.ConnUtils;

public class ScoreDao {

	/**
	 * 성적정보를 전달받아 SAMPLE_SCORES 테이블에 추가한다.
	 * @param score 성적정보
	 */
	public void insertScore(Score score) {
		String sql = "insert into sample_scores "
				+ "(student_no, student_name, kor_score, eng_score, math_score) "
				+ "values "
				+ "(sample_student_seq.nextval, ?, ?, ?, ?)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnUtils.getConnections();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, score.getStudentName());
			pstmt.setInt(2, score.getKor());
			pstmt.setInt(3, score.getEng());
			pstmt.setInt(4, score.getMath());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * SAMPLE_SCORES 테이블의 모든 성적정보를 반환한다.
	 * @return 모든 성적정보, 존재하지 않으면 빈 {@code List<Score>} 객체
	 * @throws SQLException 
	 */
	public List<Score> getAllScores() throws SQLException {
		String sql = "select student_no, student_name, kor_score, eng_score, math_score, create_date "
				+ "from sample_scores "
				+ "order by student_no ";
		
		List<Score> scores = new ArrayList<>();
		Connection con = ConnUtils.getConnections();
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			int no = rs.getInt("student_no");
			String name = rs.getString("student_name");
			int kor = rs.getInt("kor_score");
			int eng = rs.getInt("eng_score");
			int math = rs.getInt("math_score");
			Date createDate = rs.getDate("create_date");
			
			Score score = new Score();
			score.setStudentNo(no);
			score.setStudentName(name);
			score.setKor(kor);
			score.setEng(eng);
			score.setMath(math);
			score.setCreateDate(createDate);
			
			scores.add(score);
		}
		
		rs.close();
		pstmt.close();
		con.close();
		
		return scores;
	}
	
	/**
	 * 전달받은 학생의 성적정보를 SAMPLE_SCORES 테이블에서 조회해 반환한다.
	 * @param studentNo 학생번호
	 * @return 성적정보, 존재하지 않으면 null
	 * @throws SQLException 
	 */
	public Score getScoreByStudentNo(int studentNo) throws SQLException {
		String sql = "select student_no, student_name, kor_score, eng_score, math_score, create_date "
				+ "from sample_scores "
				+ "where student_no = ? ";
		
		Score score = null; // BEWARE NOT new Score()
		
		Connection con = ConnUtils.getConnections();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, studentNo);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			int no = rs.getInt("student_no");
			String name = rs.getString("student_name");
			int kor = rs.getInt("kor_score");
			int eng = rs.getInt("eng_score");
			int math = rs.getInt("math_score");
			Date createDate = rs.getDate("create_date");
			
			score = new Score();
			score.setStudentNo(studentNo);
			score.setStudentName(name);
			score.setKor(kor);
			score.setEng(eng);
			score.setMath(math);
			score.setCreateDate(createDate);
		}
		
		rs.close();
		pstmt.close();
		con.close();
		
		return score;
	}
	
	/**
	 * 전달받은 학생의 성적정보를 SAMPLE_SCORES 테이블에서 삭제한다.
	 * @param studentNo 학생번호
	 * @throws SQLException 
	 */
	public void deleteScore(int studentNo) throws SQLException {
		String sql = "delete from sample_scores "
				+ "where student_no = ? ";
		Connection con = ConnUtils.getConnections();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, studentNo);
		pstmt.executeUpdate();
		
		pstmt.close();
		con.close();
	}
	
	/**
	 * 수정된 성적정보가 포함된 성적정보를 전달받아 SAMPLE_SCORES 테이블에 반영한다.
	 * @param score
	 */
	public void updateScore(Score score) {
		String sql = "update sample_score "
				+ "set kor_score = ?, eng_score = ?, math_score = ? "
				+ "where ";
		
	}
}
