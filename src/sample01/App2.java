package sample01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.ConnUtils;

public class App2 {

	public static void main(String[] args) throws SQLException {
		
		String sql = "select student_no, student_name, kor_score, eng_score, math_score "
				+ "from sample_scores "
				+ "order by student_no ";
		
		Connection con = ConnUtils.getConnections();
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			int no = rs.getInt("student_no");
			String name = rs.getString("student_name");
			int kor = rs.getInt("kor_score");
			int eng = rs.getInt("eng_score");
			int math = rs.getInt("math_score");
			
			System.out.println("학번: " + no);
			System.out.println("이름: " + name);
			System.out.println("국어: " + kor);
			System.out.println("영어: " + eng);
			System.out.println("수학: " + math);
			System.out.println();
		}
		
		rs.close();
		pstmt.close();
		con.close();
	}
}
