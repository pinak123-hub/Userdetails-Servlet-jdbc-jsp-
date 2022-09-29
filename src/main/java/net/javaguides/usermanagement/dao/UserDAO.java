package net.javaguides.usermanagement.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.javaguides.usermanagement.model.User;

public class UserDAO {
private String jdbcURL="jdbc:mysql://localhost:3306/demo?useSSL=false";
	private String jdbcUsername="root";
    private String jdbcPassword="Pinak@123";	

private static final String INSERT_USERS_SQL = "INSERT INTO users" + "(name, email,country) VALUES "+"( ? , ? , ? );";


private static final String SELECT_USER_BY_ID ="select id,name,email,country from users where id=?";
private static final String SELECT_ALL_USERS ="select*from users";
private static final String DELETE_USER_SQL="delete from users where id=?";
private static final String UPDATE_USERS_SQL="update users set name=?,email=?,country=? where id=?;";

protected Connection getConnection(){
	Connection connection=null;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
		
	} catch(Exception e) {
		e.printStackTrace();
	}
    return connection;
}


 //Create or Insert users
public void insertUser(User user) throws SQLException{
	try(Connection connection =getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(INSERT_USERS_SQL)){
				preparedStatement.setString(1, user.getName());
				preparedStatement.setString(2, user.getEmail());
				preparedStatement.setString(3, user.getCountry());
				preparedStatement.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
}

 //Update users

public boolean updateUser(User user)throws SQLException{
	boolean rowUpdated;
	try(Connection connection=getConnection();
	    PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_USERS_SQL)){
		preparedStatement.setString(1, user.getName());
		preparedStatement.setString(2, user.getEmail());
		preparedStatement.setString(3, user.getCountry());
		preparedStatement.setInt(4, user.getId());
		
		rowUpdated=preparedStatement.executeUpdate() > 0;		
	}
	return rowUpdated;
}



 //Select user by id

public User selectUser(int id){
	User user=null;
	try( Connection connection=getConnection();
	    PreparedStatement preparedStatement=connection.prepareStatement(SELECT_USER_BY_ID);){
		preparedStatement.setInt(1, id);
		System.out.println(preparedStatement);
		
		ResultSet rs=preparedStatement.executeQuery();
		 while(rs.next()) {
			 String name=rs.getString("name");
			 String email=rs.getString("email");
			 String country=rs.getString("country");
			 user = new User(id,name,email,country);
		 }
	}catch(Exception e) {
		e.printStackTrace();
	}
	return user;
}
  

 //select users

public java.util.List<User> selectAllUsers() {
           java.util.List<User> users=new ArrayList<>();
	try( Connection connection=getConnection();
	    PreparedStatement preparedStatement=connection.prepareStatement(SELECT_ALL_USERS);){
	
		System.out.println(preparedStatement);
		
		ResultSet rs=preparedStatement.executeQuery();
		 while(rs.next()) {
			 int id=rs.getInt("id");
			 String name=rs.getString("name");
			 String email=rs.getString("email");
			 String country=rs.getString("country");
			users.add(new User(id,name,email,country));
		 }
	}catch(Exception e) {
		e.printStackTrace();
	}
	return users;
}


 //Delete user
public boolean deleteUser(int id) throws SQLException{
	boolean rowDeleted;
	try(Connection connection=getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(DELETE_USER_SQL);){
		preparedStatement.setInt(1, id);
		rowDeleted=preparedStatement.executeUpdate() > 0;
		
	}
	return rowDeleted;
}










}