package database;

import java.util.ArrayList;
import java.util.List;

import model.user;

public class userDatabase {
	
	private List<user> dummy = new ArrayList<>();

	public userDatabase() {
			
		dummy.add(new user("akash","akash@mail.com","ak"));
		dummy.add(new user("kriti","kriti@mail.com","kt"));
	}
	
	public List<user> getAlluser()
	{
		return this.dummy;
	}
	
	public boolean insertuser(user persons) throws Exception
	{
		for(user users : dummy)
		{
			if(users.getEmail().equals(users.getEmail()))
				throw new Exception("User cannot be registered as email already exists");
		}
		dummy.add(persons);
		return true;
	}
	
	public user getUserByEmail(String email)
	{
		user persons = null;
		for(user users : dummy)
		{
			if(users.getEmail().equals(email))
			{
				persons = users;
				break;
			}
		}
		return persons;
	}
	
	
	public boolean login(String email,String password)
	{
		for(user users : dummy)
		{
			if(users.getEmail().equals(email))
			{
				if(users.getPassword().equals(password))
					return true;
			}
		}
		return false;
	}
	
	
	
}
