package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// 1. セッションからログインユーザー（先生）の情報を取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// 2. JSPのフォームから送られてきた値（cd と name）を受け取る
		String cd = req.getParameter("cd");
		String name = req.getParameter("name");

		// 3. DaoとBeanを準備して、値をセットする
		SubjectDao sDao = new SubjectDao();
		Subject subject = new Subject();
		
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(teacher.getSchool()); // 先生が所属する学校の科目に設定！

		// 4. データベースに保存！
		// ※SubjectDaoに登録用のsaveメソッドがある前提だよ
		sDao.save(subject);

		// 5. 登録が終わったら、一覧画面へ自動でジャンプ（リダイレクト）
		res.sendRedirect("SubjectList.action");
	}
}