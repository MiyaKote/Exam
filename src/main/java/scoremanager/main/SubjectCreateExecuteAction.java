package scoremanager.main;

import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        //JSPのフォームから送られてきた値（cd と name）を受け取る
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        //DaoとBeanを準備して、値をセットする
        SubjectDao sDao = new SubjectDao();
        Subject subject = new Subject();
        
        subject.setCd(cd);
        subject.setName(name);

        //データベースに保存！
        sDao.save(subject);

        //一覧画面へ
        res.sendRedirect("SubjectList.action");
    }
}