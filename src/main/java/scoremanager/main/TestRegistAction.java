package scoremanager.main;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の宣言 1
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		String no = ""; // 学生番号
		Student student = null; // 学生
		StudentDao studentDao = new StudentDao(); // 学生DAO

		// リクエストパラメータの取得 2
		no = req.getParameter("no");

		// DBからデータ取得 3
		// 学生番号が指定されている場合は学生情報を取得
		if (no != null && !no.isEmpty()) {
			student = studentDao.get(no);
		}


		req.setAttribute("no", no);
		req.setAttribute("student", student);

		// JSPへフォワード 7
		req.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(req, res);
	}
}
