package UI;

import java.util.Scanner;

//import database.CustomerDBConnection;
import database.userDatabase;
import model.user;
import service.userService;

public class Menu {
	
	public static void adminMenu()
	{
		System.out.println("1. See All customers");
		System.out.println("2. Fetch customer by email");
		System.out.println("3. Logout");
	}

	public static void customerMenu()
	{
		// do not show the password
		System.out.println("1. Profile");
		// ask the user to enter new password
		System.out.println("2. Change password");
		// do not touch the password
		System.out.println("3. Edit Profile");
		System.out.println("4. Delete customer account");
		System.out.println("5. Logout");
	}

	public static void main(String[] args) {

		userDatabase customerDb = new userDatabase();
//		CustomerDBConnection customerDb = new CustomerDBConnection();
//		CustomerService customerService = new CustomerService(customerDb);

		Scanner sc = new Scanner(System.in);
		String email, password, phone, city, name;
		int choice;
		boolean flag = true;
		do {
			// Home page
			System.out.println("Choose one of the options");
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. Exit");
			choice = sc.nextInt();
			switch(choice)
			{
			case 1:
				// login part
				System.out.println("Please enter email id");
				email = sc.next();
				System.out.println("Please enter password");
				password = sc.next();
				if(email.equals("admin@a.c") && password.equals("admin"))
				{
					adminDashboard(sc, userService);
				}
				else {
					try {
						if(userService.validateCredentials(email, password))
						{
							customerDashboard(sc, userService, email);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			case 2:
				System.out.println("Please enter details to register");
				System.out.println("enter email");
				email = sc.next();
				System.out.println("Enter your name");
				name= sc.next();
				System.out.println("Enter your city");
				city = sc.next();
				System.out.println("enter your phone");
				phone = sc.next();
				System.out.println("Enter your password");
				password = sc.next();
				user c1 = new user(email, name, password);
				try {
					userService.registerUser(c1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
				break;
			case 3: System.out.println("Thanks exiting the application");
			flag = false;
			break;
			default : System.out.println("Wrong option");
			}
		}while(flag);

	}

	public static void adminDashboard(Scanner sc, userService customerService)
	{
		boolean flag = true;
		do {
			System.out.println("\n ********** admin dashboard ********** \n");
			adminMenu();
			int choice = sc.nextInt();
			switch(choice)
			{
			case 1:
				try {
					for(user customer: customerService.getAlluser())
						System.out.println(customer);
					break;
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			case 2:
				System.out.println("Enter email of customer");
				String email = sc.next();
				try {
					user customer = customerService.getUserByEmail(email);
					System.out.println("Name "+customer.getUsername());
					System.out.println("Email "+customer.getEmail());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3:
				System.out.println("Logging off");
				flag = false;
				break;
			default:System.out.println("Wrong choice");
			}
		}while(flag);

	}

	public static void customerDashboard(Scanner sc, userService UserService, String email)
	{
		boolean flag = true;
		do {
			System.out.println("\n ********** Customer dashboard ********** \n");
			customerMenu();
			int choice = sc.nextInt();
			switch(choice)
			{
			case 1: 
				// can i assume the customer is already logged in
				System.out.println("Customer Profile");
				user customer;
				try {
					customer = UserService.getUserByEmail(email);
					System.out.println("Name "+customer.getUsername());
					System.out.println("Email "+email);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				System.out.println("Enter a new password to change your old password");
				String pwd = sc.next();
				try {
					UserService.changePassword(email, pwd);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3: 
				System.out.println("Edit Profile");
				System.out.println("Enter your name");
				String name= sc.next();
				user c1 = new user();
				c1.setUsername(name);
				c1.setEmail(email);
				try {
					UserService.updateUser(c1);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				System.out.println("Delete customer");
				System.out.println("Are you sure you want to delete your account?");
				System.out.println("Enter Yes/yes to delete or cancel");
				String shouldDelete = sc.next();
				if(shouldDelete.equalsIgnoreCase("Yes"))
				{
					try {
						UserService.deleteUser(email);
						flag = false;
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				break;
			case 5: 
				System.out.println("Logging off");
				flag = false;
				break;

			}
		}while(flag);
	}

}
