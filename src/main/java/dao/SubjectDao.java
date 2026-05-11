package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {
	
	private String baseSql = "select * from subject where school_cd = ?";
	
	public Subject get(String cd, School school) throws Exception {
		
		Subject subject = new Subject();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(baseSql+ "and cd = ?");
			statement.setString(1,cd);
			statement.setString(2, school.getCd());
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				subject.setCd(resultSet.getString("cd"));
				subject.setName(resultSet.getString("name"));
				subject.setSchool(school);
			} else {
				subject = null;
			}
		} catch (Exception e) {
				throw e;
		} finally {
				if(statement  != null) {
					try {
						statement.close();
					} catch (SQLException sqle) {
						throw sqle;
					}
				}
				if(connection != null) {
					try {
						connection.close();
					} catch (SQLException sqle) {
						throw sqle;
					}
				}
		}
			
			return subject;
	}
	
	public List<Subject> filter(School school) throws Exception {
		List<Subject> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(baseSql);
			statement.setString(1, school.getCd());
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Subject subject = new Subject();
				subject.setCd(resultSet.getString("cd"));
				subject.setName(resultSet.getString("name"));
				subject.setSchool(school);
				list.add(subject);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		return list;
	}
	
	// SubjectDao.java の中に追加する
	public boolean save(Subject subject) throws Exception {
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    int count = 0;

	    try {
	        // SQL文：科目を新しく登録する命令
	        statement = connection.prepareStatement(
	            "insert into subject (cd, name, school_cd) values (?, ?, ?)"
	        );
	        statement.setString(1, subject.getCd());
	        statement.setString(2, subject.getName());
	        statement.setString(3, subject.getSchool().getCd());

	        // 実行！
	        count = statement.executeUpdate();
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (statement != null) {
	            statement.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
	    }

	    if (count > 0) {
	        return true; // 1件以上登録できたら成功
	    } else {
	        return false; // 登録できなかったら失敗
	    }
	}
}
