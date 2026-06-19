package bean;

import java.io.Serializable;

public class Score implements Serializable {

	/**
	 * 成績ID：int
	 */
	private int id;

	/**
	 * 学生：Student
	 */
	private Student student;

	/**
	 * 科目名：String
	 */
	private String subject;

	/**
	 * 得点：int
	 */
	private int point;

	/**
	 * ゲッタ・セッタ
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
}
