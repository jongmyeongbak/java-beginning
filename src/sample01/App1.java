package sample01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class App1 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// 1. JDBC 드라이버를 JVM의 드라이버레지스트리에 등록하기
		Class.forName("oracle.jdbc.OracleDriver");
		
		// 2. JVM의 드라이버레지스트리에 등록된 JDBC 드라이버를 이용하여
		//		데이터베이스와 연결을 담당하는 Connection 인터페이스 구현객체 획득하기
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "hr";
		String password = "zxcv1234";
		Connection conn = DriverManager.getConnection(url, user, password);

		// 3. SQL을 작성한다.
		String sql = "insert into sample_scores"
				+ "(student_no, student_name, kor_score, eng_score, math_score)"
				+ "values"
				+ "(sample_student_seq.nextval, ?, ?, ?, ?)";
		
		// 4. PreparedStatement객체를 획득하기
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		// 5. 바인딩변수(?)에 값 설정하기
		pstmt.setString(1, "안중근");
		pstmt.setInt(2, 80);
		pstmt.setInt(3, 50);
		pstmt.setInt(4, 60);
		
		// 6. SQL을 DBMS로 전송해 실행시키기
		int rowCount = pstmt.executeUpdate();
		System.out.println(rowCount + "개의 행이 추가되었습니다.");
		
		// 7. 자원 반납하기
		pstmt.close();
		conn.close();
		
	}
}
