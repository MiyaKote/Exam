package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req,
		HttpServletResponse res) throws Exception {

		HttpSession session =
			req.getSession();

		Teacher teacher =
			(Teacher)session.getAttribute("user");

		String class_num =
			req.getParameter("class_num");

		ClassNum classData =
			new ClassNum();

		ClassNumDao classNumDao =
			new ClassNumDao();

		Map<String, String> errors =
			new HashMap<>();

		// 未入力
		if (class_num == null
			|| class_num.isEmpty()) {

			errors.put(
				"1",
				"クラス番号を入力してください");

		} else {

			// 重複
			if (classNumDao.get(
				class_num,
				teacher.getSchool())
				!= null) {

				errors.put(
					"1",
					"クラス番号が重複しています");

			} else {

				classData.setClass_num(
					class_num);

				classData.setSchool(
					teacher.getSchool());

				classNumDao.save(
					classData);
			}
		}

		req.setAttribute(
			"class_num",
			class_num);

		req.setAttribute(
			"errors",
			errors);

		if (errors.isEmpty()) {

			req.getRequestDispatcher(
				"class_create_done.jsp")
				.forward(req, res);

		} else {

			req.getRequestDispatcher(
				"class_create.jsp")
				.forward(req, res);

		}
	}
}