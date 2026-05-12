package scoremanager.main;

import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        String cd = req.getParameter("cd");

        SubjectDao sDao = new SubjectDao();

        //科目削除
        sDao.delete(cd);

        // 一覧画面へ
        res.sendRedirect("SubjectList.action");
    }
}