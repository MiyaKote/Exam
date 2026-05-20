package scoremanager.main;

import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassListAction extends Action {

	@Override
	public void execute(HttpServletRequest req,
		HttpServletResponse res) throws Exception {

		// セッション
		HttpSession session =
			req.getSession();

		Teacher teacher =
			(Teacher)session.getAttribute("user");

		// DAO
		ClassNumDao classNumDao =
			new ClassNumDao();

		// クラス一覧
		List<String> class_num_set =
			null;

		// DBから取得
		class_num_set =
			classNumDao.filter(
				teacher.getSchool());

		// リクエストへセット
		req.setAttribute(
			"class_num_set",
			class_num_set);

		req.setAttribute(
			"school",
			teacher.getSchool());

		// JSPへフォワード
		req.getRequestDispatcher(
			"class_list.jsp")
			.forward(req, res);

	}

}