package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.user;


public class userDBConnecton {
	
	Connection conn = DBConnection.getConnection();

	public List<user> getAllCustomers() throws Exception
	{
		String sql = "select * from user";
		/**
		 * 1. get DB connection
		 */
		//2. create statements
		List<user> customers = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next())
			{
				user c1 = new user();
				c1.setEmail(rs.getString(1));
				c1.setUsername(rs.getString(2));
				c1.setPassword(rs.getString(3));
				customers.add(c1);
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return customers;
	}

	public boolean insertCustomer(user customer) throws Exception
	{
		// SQL injection attacks - FYI
		// 1 2
		String sql = "insert into user values(?,?,?)";
		System.out.println(sql);
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, customer.getUsername());
			statement.setString(2, customer.getEmail());
			statement.setString(3, customer.getPassword());

			statement.execute();
		}
		catch(SQLException ex)
		{
			System.out.println("ERROR");
			throw new Exception(ex.getMessage());
		}


		return true;
	}
	
	public user getCustomerByEmail(String email) throws Exception
	{
		user customer = null;
		String sql = "select username from user where email=?";
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			if(rs.next())
			{
				customer = new user();
				customer.setUsername(rs.getString(1));
			}
			else
				throw new Exception("No customer with "+email+" found");
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return customer;
	}
	public boolean updateCustomer(user customer) throws Exception
	{
		String sql = "update user set username=? where email=?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, customer.getUsername());
			statement.setString(2, customer.getEmail());
			statement.execute();
		}
		catch(SQLException ex)
		{
			System.out.println("ERROR");
			throw new Exception(ex.getMessage());
		}

		return true;
	}
	public boolean updatePassword(String newPassword, String email) throws Exception
	{
		String sql = "update user set password=? where email=?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, newPassword);
			statement.setString(2, email);
			statement.execute();
		}
		catch(SQLException ex)
		{
			System.out.println("ERROR");
			throw new Exception(ex.getMessage());
		}

		return true;
	}
	public boolean deleteCustomer(String email) throws Exception
	{
		String sql = "delete from user where email=?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, email);
			statement.execute();
		}
		catch(SQLException ex)
		{
			System.out.println("ERROR");
			throw new Exception(ex.getMessage());
		}
		return true;
	}
	public boolean login(String email, String password) throws Exception
	{

		user customer = null;
		String sql = "select password from user where email=?";
		//select password from customer where email='sh@g.c'
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			if(rs.next())
			{
				if(password.equals(rs.getString(1)))
					return true;
			}
			throw new Exception("Invalid credentials");
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

}
