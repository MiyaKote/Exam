package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;

public class SubjectDao extends Dao {
    
    private String baseSql = "select * from subject";
    
    public Subject get(String cd) throws Exception {
        Subject subject = new Subject();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        
        try {
            statement = connection.prepareStatement(baseSql + " where cd = ?");
            statement.setString(1, cd);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                subject.setCd(resultSet.getString("cd"));
                subject.setName(resultSet.getString("name"));
            } else {
                subject = null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return subject;
    }
    
    //全科目一覧
    public List<Subject> findAll() throws Exception { // 名前も filter から findAll に変えると分かりやすいよ
        List<Subject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        
        try {
            // SQL：全件取得なので baseSql そのままでOK
            statement = connection.prepareStatement(baseSql);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setCd(resultSet.getString("cd"));
                subject.setName(resultSet.getString("name"));
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
    
    //保存
    public boolean save(Subject subject) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement(
                "insert into subject (cd, name) values (?, ?)"
            );
            statement.setString(1, subject.getCd());
            statement.setString(2, subject.getName());

            count = statement.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return count > 0;
    }
    
    //削除
    public void delete(String cd) throws Exception {
        Connection connection = getConnection();
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement("delete from SUBJECT where CD=?");
            st.setString(1, cd);
            st.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (st != null) st.close();
            if (connection != null) connection.close();
        }
    }
}