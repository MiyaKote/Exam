package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Score;
import bean.Student;

public class ScoreDao extends Dao {

	/**
	 * 学生番号で成績一覧を取得する
	 *
	 * @param student 対象学生
	 * @return 成績リスト
	 */
	public List<Score> findByStudent(Student student) throws Exception {

		// リストを初期化
		List<Score> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
				"select * from score where student_no = ? order by subject asc"
			);
			// プリペアードステートメントに学生番号をバインド
			statement.setString(1, student.getNo());
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// リザルトセットを全件走査
			while (resultSet.next()) {
				// 成績インスタンスを初期化
				Score score = new Score();
				// 成績インスタンスに検索結果をセット
				score.setId(resultSet.getInt("id"));
				score.setStudent(student);
				score.setSubject(resultSet.getString("subject"));
				score.setPoint(resultSet.getInt("point"));
				// リストに追加
				list.add(score);
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
	 * 学校・入学年度・クラスで成績一覧を取得する
	 *
	 * @param schoolCd  学校コード
	 * @param entYear   入学年度（0の場合は全年度）
	 * @param classNum  クラス番号（"0"の場合は全クラス）
	 * @return 成績リスト
	 */
	public List<Score> findByFilter(String schoolCd, int entYear, String classNum) throws Exception {

		// リストを初期化
		List<Score> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		// SQL文（studentテーブルをJOIN）
		StringBuilder sql = new StringBuilder(
			"select sc.id, sc.student_no, sc.subject, sc.point, " +
			"st.name, st.ent_year, st.class_num, st.is_attend " +
			"from score sc " +
			"inner join student st on sc.student_no = st.no " +
			"where st.school_cd = ?"
		);

		// 入学年度の条件
		if (entYear != 0) {
			sql.append(" and st.ent_year = ?");
		}
		// クラスの条件
		if (classNum != null && !classNum.equals("0")) {
			sql.append(" and st.class_num = ?");
		}

		sql.append(" order by st.no asc, sc.subject asc");

		try {
			statement = connection.prepareStatement(sql.toString());

			// バインド変数のインデックス
			int idx = 1;
			statement.setString(idx++, schoolCd);
			if (entYear != 0) {
				statement.setInt(idx++, entYear);
			}
			if (classNum != null && !classNum.equals("0")) {
				statement.setString(idx++, classNum);
			}

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// 学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();
			bean.School school = schoolDao.get(schoolCd);

			// リザルトセットを全件走査
			while (resultSet.next()) {
				// 学生インスタンスを生成
				Student student = new Student();
				student.setNo(resultSet.getString("student_no"));
				student.setName(resultSet.getString("name"));
				student.setEntYear(resultSet.getInt("ent_year"));
				student.setClassNum(resultSet.getString("class_num"));
				student.setAttend(resultSet.getBoolean("is_attend"));
				student.setSchool(school);

				// 成績インスタンスを生成
				Score score = new Score();
				score.setId(resultSet.getInt("id"));
				score.setStudent(student);
				score.setSubject(resultSet.getString("subject"));
				score.setPoint(resultSet.getInt("point"));
				// リストに追加
				list.add(score);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
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
}
