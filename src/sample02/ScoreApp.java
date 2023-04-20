package sample02;

import java.sql.SQLException;
import java.util.List;

import util.KeyboardReader;

public class ScoreApp {

	ScoreDao dao = new ScoreDao();
	KeyboardReader reader = new KeyboardReader();
	
	public void menu() {
		System.out.println("### 성적관리 프로그램 ###");
		System.out.println("----------------------------------------------");
		System.out.println("1.전체조회  2.조회  3.저장  0.종료");
		System.out.println("----------------------------------------------");
		System.out.println();
		
		System.out.print("### 메뉴선택: ");
		int menuNo = reader.readInt();
		System.out.println();
		
		try {
			if (menuNo == 1) {
				전체조회();
			} else if (menuNo == 2) {
				조회();
			} else if (menuNo == 3) {
				저장();
			} else if (menuNo == 0) {
				종료();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println();
		System.out.println();
		menu();
		
	}
	
	private void 전체조회() throws SQLException {
		System.out.println("<< 전체 성적정보 조회 >>");
		System.out.println("### 전체 성적정보를 확인하세요.");
		
		List<Score> scores = dao.getAllScores();
		
		System.out.println("-----------------------------------------------------");
		System.out.println("학번\t이름\t국어\t영어\t수학\t총점\t평균");
		System.out.println("-----------------------------------------------------");
		for (Score score : scores) {
			int kor = score.getKor();
			int eng = score.getEng();
			int math = score.getMath();
			int totalScore = kor + eng + math;
			int average = totalScore / 3;
			System.out.println(score.getStudentNo() + "\t"
					 + score.getStudentName() + "\t"
					 + kor + "\t"
					 + eng + "\t"
					 + math + "\t"
					 + totalScore + "\t"
					 + average);
		}
	}

	private void 조회() throws SQLException {
		System.out.println("<< 성적정보 조회 >>");
		System.out.println("### 학번을 입력하여 해당 학생의 성적을 확인하세요.");
		
		System.out.print("### 학번 입력: ");
		int no = reader.readInt();
		
		Score score = dao.getScoreByStudentNo(no);
		if (score == null) {
			System.out.println("### 성적정보가 존재하지 않습니다.");
		} else {
			System.out.println("----------------------------------------------");
			System.out.println("[" + score.getStudentName() + "]의 성적정보");
			System.out.println("----------------------------------------------");
			System.out.println("학번: " + no);
			System.out.println("국어: " + score.getKor());
			System.out.println("영어: " + score.getEng());
			System.out.println("수학: " + score.getMath());
			System.out.println("총점: " + score.getTotalScore());
			System.out.println("평균: " + score.getAverage());
			System.out.println("----------------------------------------------");
		}
		
	}

	private void 저장() {
		System.out.println("<< 성적정보 저장 >>");
		System.out.println("### 이름, 국어/영어/수학 점수를 입력하여 성적정보를 저장하세요.");
		
		System.out.print("### 이름 입력: ");
		String name = reader.readString();
		System.out.print("### 국어점수 입력: ");
		int kor = reader.readInt();
		System.out.print("### 영어점수 입력: ");
		int eng = reader.readInt();
		System.out.print("### 수학점수 입력: ");
		int math = reader.readInt();
		
		Score score = new Score();
		score.setStudentName(name);
		score.setKor(kor);
		score.setEng(eng);
		score.setMath(math);
		
		dao.insertScore(score);
		
		System.out.println("### 신규 성적정보가 저장되었습니다.");
	}

	private void 종료() {
		System.out.println("<< 프로그램 종료 >>");
		System.out.println("### 프로그램을 종료합니다.");
		System.exit(0);
	}

	public static void main(String[] args) {
		new ScoreApp().menu();
	}
}
