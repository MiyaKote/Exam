package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の宣言 1
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		String entYearStr = "";    // 入学年度（文字列）
		String classNum   = "";    // クラス番号
		String subjectCd  = "";    // 科目コード
		int entYear = 0;           // 入学年度（数値）
		List<Test> tests = null;   // 成績リスト

		LocalDate todaysDate = LocalDate.now(); // LocalDate
		int year = todaysDate.getYear();        // 現在年度

		TestDao testDao = new TestDao();             // 成績DAO
		ClassNumDao classNumDao = new ClassNumDao(); // クラスDAO

		// リクエストパラメータの取得 2
		entYearStr = req.getParameter("f1");
		classNum   = req.getParameter("f2");
		subjectCd  = req.getParameter("f3");

		// 入学年度の変換
		if (entYearStr != null) {
			entYear = Integer.parseInt(entYearStr);
		}
		// クラスの初期値
		if (classNum == null) {
			classNum = "0";
		}
		// 科目の初期値
		if (subjectCd == null) {
			subjectCd = "0";
		}

		// 入学年度・クラス・科目がすべて選択されている場合のみDB検索 3
		if (entYear != 0 && !classNum.equals("0") && !subjectCd.equals("0")) {
			tests = testDao.filter(
				teacher.getSchool().getCd(),
				entYear,
				classNum,
				subjectCd
			);
		}

		// 入学年度のセレクトボックス用リスト（現在年度から10年分）
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		// クラス一覧
		List<String> classNumSet = classNumDao.filter(teacher.getSchool());

		// 科目一覧
		List<String[]> subjectSet = testDao.getSubjects(teacher.getSchool().getCd());

		// レスポンス値をセット 6
		req.setAttribute("tests",        tests);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumSet);
		req.setAttribute("subject_set",  subjectSet);
		req.setAttribute("f1",           entYear);
		req.setAttribute("f2",           classNum);
		req.setAttribute("f3",           subjectCd);

		// JSPへフォワード 7
		req.getRequestDispatcher("test_list.jsp").forward(req, res);
	}
}

