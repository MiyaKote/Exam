package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Score;
import bean.Teacher;
import dao.ClassNumDao;
import dao.ScoreDao;
import tool.Action;

public class TestListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の宣言 1
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		String entYearStr = "";       // 入学年度（文字列）
		String classNum = "";         // クラス番号
		int entYear = 0;              // 入学年度（数値）
		List<Score> scores = null;    // 成績リスト

		LocalDate today = LocalDate.now();
		int year = today.getYear();

		ScoreDao scoreDao = new ScoreDao();
		ClassNumDao classNumDao = new ClassNumDao();

		// リクエストパラメータの取得 2
		entYearStr = req.getParameter("f1");
		classNum   = req.getParameter("f2");

		// 入学年度の変換
		if (entYearStr != null && !entYearStr.isEmpty()) {
			entYear = Integer.parseInt(entYearStr);
		}
		// クラスの初期値
		if (classNum == null) {
			classNum = "0";
		}

		// DBからデータ取得 3
		// 入学年度・クラスで絞り込み
		scores = scoreDao.findByFilter(
			teacher.getSchool().getCd(),
			entYear,
			classNum
		);

		// 入学年度のセレクトボックス用リスト（現在年度から10年分）
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i <= year; i++) {
			entYearSet.add(i);
		}

		// クラス一覧
		List<String> classNumSet = classNumDao.filter(teacher.getSchool());

		// レスポンス値をセット 6
		req.setAttribute("scores",       scores);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumSet);
		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);

		// JSPへフォワード 7
		req.getRequestDispatcher("test_list.jsp").forward(req, res);
	}
}
