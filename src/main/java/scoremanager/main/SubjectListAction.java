package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 1. セッションからログインユーザー（先生）の情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");

        // 2. 科目Daoをインスタンス化
        SubjectDao sDao = new SubjectDao();

        // 3. 先生の学校コードに紐づく科目一覧を取得（お盆に載せる）
        // ※SubjectDaoにfilter(School school)メソッドがある前提だよ！
        List<Subject> subjects = sDao.filter(teacher.getSchool());

        // 4. JSPで使うために、リクエストに「subjects」という名前でお盆をセット
        req.setAttribute("subjects", subjects);

        // 5. 最後に、表示担当の「subject_list.jsp」へバトンタッチ！
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}