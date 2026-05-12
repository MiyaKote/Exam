package scoremanager.main;

import java.util.List;

import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        SubjectDao sDao = new SubjectDao();

        //科目一覧表示
        List<Subject> subjects = sDao.findAll();

        // 3. JSP（画面）に渡すために、リクエストにお盆を載せる
        req.setAttribute("subjects", subjects);

        // 4. 表示担当の「subject_list.jsp」へフォワード！
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}