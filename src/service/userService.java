package service;

import java.util.List;

import database.userDatabase;
import model.Customer;
import model.user;

public class userService {
	
	private userDatabase UserDatabase;
	
	public userService(userDatabase UserDatabase)
	{
		this.UserDatabase = UserDatabase;
	}
	
	public List<user> getAlluser() throws Exception
	{
		if(UserDatabase.getAlluser().size() == 0)
			throw new Exception("No customers registered yet");
		
		return this.UserDatabase.getAlluser();
	}
	
	public boolean registerUser(user users) throws Exception
	{
		if(users.getEmail() == null || users.getEmail().isEmpty())
			throw new Exception("email cannot be empty or null");
		
		try {
			if(UserDatabase.insertuser(users));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return true;
	}
	
	public user getUserByEmail(String email) throws Exception
	{
		if(email==null || email.isEmpty())
			throw new Exception("email cannot be empty or null");
		user cust = this.UserDatabase.getUserByEmail(email);
		if(cust == null)
			throw new Exception("Customer with email "+email+" does not exist");
		return cust;
	}
	
	public boolean validateCredentials(String email, String password) throws Exception
	{
		if(email==null || email.isEmpty())
			throw new Exception("email cannot be empty or null");
		
		return this.UserDatabase.login(email, password);
	}
	public boolean updateUser(user customer) throws Exception
	{
		try {
			this.UserDatabase.updateUser(customer);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return true;
	}
	public boolean deleteUser(String email) throws Exception
	{
		try {
			this.UserDatabase.deleteUser(email);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return true;
	}
	public boolean changePassword(String email, String newPassword) throws Exception
	{
		try {
			this.UserDatabase.updatePassword(newPassword, email);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return true;
	}

}
