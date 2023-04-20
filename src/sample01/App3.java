package sample01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.ConnUtils;

public class App3 {

	public static void main(String[] args) throws SQLException {
		
		// Jobs 테이블의 모든 직종 정보를 조회하기
		String sql = "select job_id, job_title, min_salary, max_salary "
				+ "from jobs "
				+ "order by job_id ";
		
		Connection con = ConnUtils.getConnections();
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		ArrayList<Object[]> jobs = new ArrayList<>();
		while (rs.next()) {
			Object[] obj = {rs.getString("job_id"), rs.getString("job_title"), rs.getInt("min_salary"), rs.getInt("max_salary")};
			jobs.add(obj);
		}
		rs.close();
		pstmt.close();
		con.close();
		
		jobs.stream().forEach(obj -> {
		    String jobId = (String) obj[0];
		    String jobTitle = (String) obj[1];
		    int minSalary = (int) obj[2];
		    int maxSalary = (int) obj[3];
		    System.out.println(jobId + ", " + jobTitle + ", " + minSalary + ", " + maxSalary);
		});
	}
}
