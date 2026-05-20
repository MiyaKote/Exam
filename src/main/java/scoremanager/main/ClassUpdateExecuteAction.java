package scoremanager.main;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req,
		HttpServletResponse res) throws Exception {

		// セッション
		HttpSession session =
			req.getSession();

		Teacher teacher =
			(Teacher)session.getAttribute("user");

		// 変更前
		String old_class_num =
			req.getParameter("old_class_num");

		// 変更後
		String new_class_num =
			req.getParameter("class_num");

		// DAO
		ClassNumDao classNumDao =
			new ClassNumDao();

		// 変更前データ取得
		ClassNum classData =
			classNumDao.get(
				old_class_num,
				teacher.getSchool());

		// 更新
		classNumDao.save(
			classData,
			new_class_num);

		// 完了画面
		req.getRequestDispatcher(
			"class_update_done.jsp")
			.forward(req, res);

	}


}