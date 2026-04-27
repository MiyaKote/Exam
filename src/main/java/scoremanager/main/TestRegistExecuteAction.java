package scoremanager.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		String no = ""; // 学生番号
		String subject = ""; // 科目名
		String testDate = ""; // 実施日
		String scoreStr = ""; // 得点（文字列）
		int score = 0; // 得点
		Student student = null; // 学生
		StudentDao studentDao = new StudentDao(); // 学生DAO
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		// リクエストパラメータの取得 2
		no = req.getParameter("no");
		subject = req.getParameter("subject");
		testDate = req.getParameter("test_date");
		scoreStr = req.getParameter("score");

		// DBからデータ取得 3
		student = studentDao.get(no);

		// ビジネスロジック 4
		// 入力値バリデーション
		if (subject == null || subject.isEmpty()) {
			errors.put("subject", "科目名を入力してください");
		}
		if (testDate == null || testDate.isEmpty()) {
			errors.put("test_date", "実施日を入力してください");
		}
		if (scoreStr == null || scoreStr.isEmpty()) {
			errors.put("score", "得点を入力してください");
		} else {
			score = Integer.parseInt(scoreStr);
			if (score < 0 || score > 100) {
				errors.put("score", "得点は0〜100の範囲で入力してください");
			}
		}

		// DBへデータ保存 5
		if (errors.isEmpty()) {
			// エラーなしの場合、testテーブルへINSERT
			Dao dao = new Dao();
			Connection connection = dao.getConnection();
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(
					"insert into test(no, school_cd, class_num, subject, test_date, score) values(?, ?, ?, ?, ?, ?)"
				);
				statement.setString(1, no);
				statement.setString(2, student.getSchool().getCd());
				statement.setString(3, student.getClassNum());
				statement.setString(4, subject);
				statement.setString(5, testDate);
				statement.setInt(6, score);
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
		req.setAttribute("no", no);
		req.setAttribute("student", student);
		req.setAttribute("subject", subject);
		req.setAttribute("test_date", testDate);
		req.setAttribute("score", scoreStr);
		req.setAttribute("errors", errors);

		// JSPへフォワード 7
		if (errors.isEmpty()) {
			// 登録成功 → 完了ページへ
			req.getRequestDispatcher("/scoremanager/main/test_regist_done.jsp").forward(req, res);
		} else {
			// バリデーションエラー → 登録フォームへ戻る
			req.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(req, res);
		}
	}
}
