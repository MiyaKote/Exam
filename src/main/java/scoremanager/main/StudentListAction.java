package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// ローカル変数の�?�? 1
		String entYearStr = ""; // 入力された入学年度
		String classNum = ""; // 入力されたクラス番号
		String isAttendStr = ""; // 入力された在学フラグ
		int entYear = 0; // 入学年度
		boolean isAttend = false; // 在学フラグ
		List<Student> students = null; // 学生リス�?
		LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取�?
		int year = todaysDate.getYear(); // 現在の年を取�?
		StudentDao studentDao = new StudentDao(); // 学生Dao
		ClassNumDao classNumDao = new ClassNumDao(); // クラス番号Daoを�?�期�?
		Map<String, String> errors = new HashMap<>(); // エラーメ�?セージ

		// リクエストパラメーターの取�? 2
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		isAttendStr = req.getParameter("f3");

		// ビジネスロジ�?ク 4
		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}
		if (isAttendStr != null) { // 在学フラグがnullじゃなかった�?��?
			// 在学フラグをtrueに変換
			isAttend = true;
		}
		// リストを初期�?
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前か�?1年後まで年をリストに追�?
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		// DBから�?ータ取�? 3
		// ログインユーザーの学校コードをもとにクラス番号の�?覧を取�?
		List<String> list = classNumDao.filter(teacher.getSchool());

		if (entYear != 0 && !classNum.equals("0")) {
			// 入学年度とクラス番号を指�?
			students = studentDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
		} else if (entYear != 0 && classNum.equals("0")) {
			// 入学年度のみ�?�?
			students = studentDao.filter(teacher.getSchool(), entYear, isAttend);
		} else if (entYear == 0 && classNum == null || entYear == 0 && classNum.equals("0")) {
			// �?定な�?
			// 全学生情報を取�?
			students = studentDao.filter(teacher.getSchool(), isAttend);
		} else {
			errors.put("f1", "クラスを指定する�?�合�?�入学年度も指定してください");
			// リクエストにエラーメ�?セージをセ�?�?
			req.setAttribute("errors", errors);
			// 全学生情報を取�?
			students = studentDao.filter(teacher.getSchool(), isAttend);
		}

		// レスポンス値をセ�?�? 6
		// リクエストに入学年度をセ�?�?
		req.setAttribute("f1", entYear);
		// リクエストにクラス番号をセ�?�?
		req.setAttribute("f2", classNum);
		// 在学フラグが�?�信されて�?た�?��?
		if (isAttendStr != null) {
			// 在学フラグを立て�?
			isAttend = true;
			// リクエストに在学フラグをセ�?�?
			req.setAttribute("f3", isAttendStr);
		}
		// リクエストに学生リストをセ�?�?
		req.setAttribute("students", students);
		// リクエストに�?ータをセ�?�?
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);

		// JSPへフォワー�? 7
		req.getRequestDispatcher("student_list.jsp").forward(req, res);
	}

}
