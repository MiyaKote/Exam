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
}
