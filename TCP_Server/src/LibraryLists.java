import java.time.LocalDate;
import java.util.*;

public class LibraryLists {
	private List<User> users = new ArrayList<>();
	private List<Book> books = new ArrayList<>();
	
	// Check if list there is no users.
    public boolean usersIsEmpty() {
    	if (users.isEmpty()) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    //check for duplicate student ID.
    public boolean checkStudentId(String studentId) {
    	boolean exists = users.stream().anyMatch(u ->
        u.getStudentId().equals(studentId));
    	 
    	return exists;
    }
    
    //Check for duplicate email.
    public boolean checkEmail(String email) {
    	boolean exists = users.stream().anyMatch(u ->
        u.getEmail().equals(email));
    	
    	return exists;
    }
    
    // Authorise login attempt.
    public boolean successfulLogin(String email, String password) {
    	for (User user : users) {
            if(user.getEmail().equals(email)) {
            	if (user.getPassword().equals(password)) {
            		return true;
            	}
            }
    	}
    	return false;
    }
    
    public String returnStudentID(String email, String password) {
    	for (User user : users) {
            if(user.getEmail().equals(email)) {
            	if (user.getPassword().equals(password)) {
            		return user.getStudentId();
            	}
            }
    	}
    	return "None";
    }
    
    // Add created user.
    public String addUser(String Name, String StudentID, String Email, String Password, String Dept, String Occupation) {
    	
    	//Check for duplicates
    	if(checkStudentId(StudentID)) {
			return "Sorry, your studentID is already being used. Please try again\n";
		}
		else if (checkEmail(Email)) {
			return "Sorry, your email is already being used. Please try again\n";
		}
    	
    	//Create and add to List of users
    	User user = new User(Name, StudentID, Email, Password, Dept, Occupation);
    	users.add(user);
    	return "You have succesfully been registered!";
    }

    //Print all users.
    public String printUsers() {
    	StringBuilder sb = new StringBuilder("\n===== Registered Users =====\n");

	    for (User user : users) {
	        sb.append(user.printUser()).append("\n");
	    }

	    return sb.toString();
    }
    
    public Book createBook(
    	    String bookName,
    	    String studentId
    	) {
    		LocalDate localdate = LocalDate.now();
    		
    		return new Book(
    				bookName,
    				RecordType.NEW_BOOK_ENTRY, 
    				UUID.randomUUID().toString(), 
    				localdate, 
    				studentId,
    				RecordStatus.AVAILABLE, 
    				null
    		);	
    	}
    
    public void addBook(String name, String ID) {
    	books.add(createBook(name, ID));
    }
    
    // Print all books.
    public String printBooks() {
    	StringBuilder sb = new StringBuilder("\n===== Books =====\n");

	    for (Book book : books) {
	        sb.append(book.printBooks()).append("\n");
	    }

	    return sb.toString();
    }
}
