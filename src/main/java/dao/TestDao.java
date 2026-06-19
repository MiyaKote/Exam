package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Test;

public class TestDao extends Dao {

	/**
	 * 成績一覧を絞り込み条件で取得する
	 * TEST テーブルと STUDENT・SUBJECT テーブルを JOIN して返す
	 *
	 * @param schoolCd   学校コード（必須）
	 * @param entYear    入学年度（0の場合は全年度）
	 * @param classNum   クラス番号（"0"または空の場合は全クラス）
	 * @param subjectCd  科目コード（"0"または空の場合は全科目）
	 * @return 成績リスト
	 */
	public List<Test> filter(String schoolCd, int entYear, String classNum, String subjectCd) throws Exception {

		// リストを初期化
		List<Test> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		// SQL文（STUDENT・SUBJECTをJOIN）
		StringBuilder sql = new StringBuilder(
			"SELECT t.student_no, t.subject_cd, t.school_cd, t.no, t.point, t.class_num, " +
			"       st.name AS student_name, st.ent_year, st.is_attend, " +
			"       sub.name AS subject_name " +
			"FROM test t " +
			"INNER JOIN student st ON t.student_no = st.no " +
			"LEFT  JOIN subject sub ON t.subject_cd = sub.cd AND t.school_cd = sub.school_cd " +
			"WHERE t.school_cd = ?"
		);

		// 入学年度の条件
		if (entYear != 0) {
			sql.append(" AND st.ent_year = ?");
		}
		// クラスの条件
		if (classNum != null && !classNum.equals("0") && !classNum.isEmpty()) {
			sql.append(" AND t.class_num = ?");
		}
		// 科目の条件
		if (subjectCd != null && !subjectCd.equals("0") && !subjectCd.isEmpty()) {
			sql.append(" AND t.subject_cd = ?");
		}

		sql.append(" ORDER BY st.no ASC, t.subject_cd ASC, t.no ASC");

		try {
			statement = connection.prepareStatement(sql.toString());

			// バインド変数のインデックス
			int idx = 1;
			statement.setString(idx++, schoolCd);
			if (entYear != 0) {
				statement.setInt(idx++, entYear);
			}
			if (classNum != null && !classNum.equals("0") && !classNum.isEmpty()) {
				statement.setString(idx++, classNum);
			}
			if (subjectCd != null && !subjectCd.equals("0") && !subjectCd.isEmpty()) {
				statement.setString(idx++, subjectCd);
			}

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// 学校情報を取得（全件共通）
			SchoolDao schoolDao = new SchoolDao();
			School school = schoolDao.get(schoolCd);

			// リザルトセットを全件走査
			while (resultSet.next()) {
				// 学生インスタンスを生成
				Student student = new Student();
				student.setNo(resultSet.getString("student_no"));
				student.setName(resultSet.getString("student_name"));
				student.setEntYear(resultSet.getInt("ent_year"));
				student.setClassNum(resultSet.getString("class_num"));
				student.setAttend(resultSet.getBoolean("is_attend"));
				student.setSchool(school);

				// 成績インスタンスを生成
				Test test = new Test();
				test.setStudentNo(resultSet.getString("student_no"));
				test.setSubjectCd(resultSet.getString("subject_cd"));
				test.setSchoolCd(resultSet.getString("school_cd"));
				test.setNo(resultSet.getInt("no"));
				test.setPoint(resultSet.getInt("point"));
				test.setClassNum(resultSet.getString("class_num"));
				test.setStudent(student);
				// SUBJECTテーブルに科目名があればそちらを、なければ科目コードをセット
				String subjectName = resultSet.getString("subject_name");
				test.setSubjectName(subjectName != null ? subjectName : resultSet.getString("subject_cd"));

				list.add(test);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;
	}

	/**
	 * 学校に紐づく科目一覧を取得する（科目セレクトボックス用）
	 *
	 * @param schoolCd 学校コード
	 * @return 科目コードと科目名のマップリスト [cd, name]
	 */
	public List<String[]> getSubjects(String schoolCd) throws Exception {

		List<String[]> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(
				"SELECT cd, name FROM subject WHERE school_cd = ? ORDER BY cd ASC"
			);
			statement.setString(1, schoolCd);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				list.add(new String[]{ resultSet.getString("cd"), resultSet.getString("name") });
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try { statement.close(); } catch (SQLException sqle) { throw sqle; }
			}
			if (connection != null) {
				try { connection.close(); } catch (SQLException sqle) { throw sqle; }
			}
		}

		return list;
	}
}
