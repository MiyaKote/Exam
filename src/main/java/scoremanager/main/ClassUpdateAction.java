package scoremanager.main;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req,
		HttpServletResponse res) throws Exception {

		HttpSession session =
			req.getSession();

		Teacher teacher =
			(Teacher)session.getAttribute("user");

		String class_num =
			req.getParameter("class_num");

		ClassNumDao classNumDao =
			new ClassNumDao();

		ClassNum classData =
			classNumDao.get(
				class_num,
				teacher.getSchool());

		req.setAttribute(
			"class_num",
			classData.getClass_num());

		req.getRequestDispatcher(
			"class_update.jsp")
			.forward(req, res);

	}
}