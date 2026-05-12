package scoremanager.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.Dao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の宣言 1
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		String studentNo = ""; // 学生番号
		String subjectCd = ""; // 科目CD
		String schoolCd = ""; // 学校CD
		String classNum = ""; // クラス番号
		String pointStr = ""; // 得点（文字列）
		int point = 0; // 得点
		Student student = null; // 学生
		StudentDao studentDao = new StudentDao(); // 学生DAO
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		// リクエストパラメータの取得 2
		studentNo = req.getParameter("student_no");
		subjectCd = req.getParameter("subject_cd");
		schoolCd  = req.getParameter("school_cd");
		classNum  = req.getParameter("class_num");
		pointStr  = req.getParameter("point");

		// DBからデータ取得 3
		student = studentDao.get(studentNo);

		// ビジネスロジック 4
		// 入力値バリデーション
		if (subjectCd == null || subjectCd.isEmpty()) {
			errors.put("subject_cd", "科目CDを入力してください");
		}
		if (pointStr == null || pointStr.isEmpty()) {
			errors.put("point", "得点を入力してください");
		} else {
			point = Integer.parseInt(pointStr);
			if (point < 0 || point > 100) {
				errors.put("point", "得点は0〜100の範囲で入力してください");
			}
		}

		// DBへデータ保存 5
		if (errors.isEmpty()) {
			Dao dao = new Dao();
			Connection connection = dao.getConnection();
			PreparedStatement statement = null;

			try {
				// NOカラム（連番）をMAX+1で採番
				int no = 1;
				statement = connection.prepareStatement("select max(no) from test");
				ResultSet rs = statement.executeQuery();
				if (rs.next() && rs.getObject(1) != null) {
					no = rs.getInt(1) + 1;
				}
				statement.close();

				// testテーブルへINSERT
				statement = connection.prepareStatement(
					"insert into test(student_no, subject_cd, school_cd, no, point, class_num) values(?, ?, ?, ?, ?, ?)"
				);
				statement.setString(1, studentNo);
				statement.setString(2, subjectCd);
				statement.setString(3, schoolCd);
				statement.setInt(4, no);
				statement.setInt(5, point);
				statement.setString(6, classNum);
				statement.executeUpdate();

			} catch (Exception e) {
				throw e;
			} finally {
				if (statement != null) {
					try {
						statement.close();
					} catch (SQLException sqle) {
						throw sqle;
					}
				}
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException sqle) {
						throw sqle;
					}
				}
			}
		}

		// レスポンス値をセット 6
		req.setAttribute("no", studentNo);
		req.setAttribute("student", student);
		req.setAttribute("subject_cd", subjectCd);
		req.setAttribute("point", pointStr);
		req.setAttribute("errors", errors);

		// JSPへフォワード 7
		if (errors.isEmpty()) {
			req.getRequestDispatcher("/scoremanager/main/test_regist_done.jsp").forward(req, res);
		} else {
			req.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(req, res);
		}
	}
}
