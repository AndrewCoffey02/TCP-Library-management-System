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
        u.getUserId().equals(studentId));
    	 
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
    
    //Return ID using email and password of that student.
    public String returnUserID(String email, String password) {
    	for (User user : users) {
            if(user.getEmail().equals(email)) {
            	if (user.getPassword().equals(password)) {
            		return user.getUserId();
            	}
            }
    	}
    	return "None";
    }
    
    // Add created user.
    public String addUser(String name, String userId, String email, String password, String dept, String role) {
    	UserRole roletype;
    	
    	//Check for duplicates
    	if(checkStudentId(userId)) {
			return "Sorry, your studentID is already being used. Please try again\n";
		}
		else if (checkEmail(email)) {
			return "Sorry, your email is already being used. Please try again\n";
		}
    	
    	//Change to enumerable.
    	if (role.equals("1")) {
    		roletype = UserRole.STUDENT;
    	}
    	else if(role.equals("2")) {
    		roletype = UserRole.LIBRARIAN;
    	}
    	else {
    		roletype = UserRole.STUDENT;
    	}
    	
    	//Create and add to List of users
    	User user = new User(name, userId, email, password, dept, roletype);
    	users.add(user);
    	return "1";
    }

    //Print all users.
    public String printUsers() {
    	StringBuilder sb = new StringBuilder("\n===== Registered Users =====\n");

	    for (User user : users) {
	        sb.append(user.printUser()).append("\n");
	    }
	    return sb.toString();
    }
    
    // Add a book to the list
    public void addBook(String name, String ID) {
    	books.add(Book.createBook(name, ID));
    }
    
    // Print all books.
    public String printBooks() {
    	StringBuilder sb = new StringBuilder("\n===== Books =====\n");

	    for (Book book : books) {
	        sb.append(book.printBook()).append("\n");
	    }
	    return sb.toString();
    }
    
    //Find a random librarian to assign it to.
    public String findLibrarian() {
    	//Define random generator
    	Random r = new Random();
    	
    	// Create lists of all librarians
    	ArrayList<User> librarians = new ArrayList<>(
                users.stream()
                     .filter(u -> u.getRole() == UserRole.LIBRARIAN)
                     .toList()
        );
    	//If there is no librarians, return nothing.
    	if (librarians.isEmpty()) {
            return "None";
        }
    	// Return random generated Librarian ID.
    	return librarians.get(
    			r.nextInt(librarians.size())
    			).getUserId();
    	
    }
    
    // Verify if user ID has a librarian role.
    public UserRole checkUserRole(String userId) {
    	
    	for (User user : users ) {
    		if(user.getUserId().equals(userId)) {
    			return user.getRole();
    		}
    	}
    	return null;
    }
    
    // assign a book to a random librarian.
    public String assignBorrowRequest(String recordId, String userId) {
    	Book currentBook = null;
    	String libId = findLibrarian();
    	if (libId.equals("None")) { //Check for any librarians on the system.
    		return "Sorry, there are no librarians on the system.";
    	}
    	
    	for (Book book : books) {
    		if (book.recordId().equals(recordId)) {
				currentBook = book;
			}
    	}
    	if(currentBook == null) { // Unsuccessful/Invalid request.
    		return "Sorry, this book is not recognised on the system...";
    	}
    	if(currentBook.status() == RecordStatus.BORROWED) {
    		return "Sorry, that book is already borrowed.";
    	}
    	if(currentBook.status() == RecordStatus.REQUESTED) {
    		return "Sorry, that book is already requested.";
    	}
    	if(currentBook.status() == RecordStatus.RETURNED) {
    		return "Sorry, that book has not fully been returned yet.";
    	}
		// Verify successful request.
    	int index = books.indexOf(currentBook);
    	books.set(index, currentBook.assignBookRequest(libId, userId));
		return "Book has been requested!";
    }
    
    //Print all requests assigned to the requested ID.
    public String assignedToLibrarian(String id) {
    	StringBuilder sb = new StringBuilder("\n===== Books assigned to you =====\n");
    	
    	for(Book book : books) {
    		if(book.userId().equals(id)) {
    			sb.append(book.printBook()).append("\n");
    		}
    		if(book.librarianId() == null) {
    			continue;
    		}
    		else if(book.librarianId().equals(id)) {
    			sb.append(book.printBook()).append("\n");
    		}
    	}
    	return sb.toString();
    }
    
    public String assignedToStudent(String id) {
    	StringBuilder sb = new StringBuilder("\n===== Books assigned to you =====\n");
    	
    	for(Book book : books) {
    		if(book.userId() == id) {
    			if(book.status() == RecordStatus.BORROWED)
    			sb.append(book.printBook()).append("\n");
    		}
    	}
    	return sb.toString();
    }
    
    public String processLibrarian(String recordId, String libId) {
    	Book currentBook = null;
    	for (Book book : books ) {
    		if (book.recordId().equals(recordId)) {
    			if(book.librarianId().equals(libId)) {
    				currentBook = book;
    			}
    			else if(book.userId().equals(libId)) {
    				currentBook = book;
    			}
    		}
    	}
    	if(currentBook == null) {
    		return "";
    	}
    	if(currentBook.status() == RecordStatus.REQUESTED) {
			return "1";
		}
    	if(currentBook.status() == RecordStatus.BORROWED) {
    		return "2";
		}
    	if(currentBook.status() == RecordStatus.RETURNED) {
    		return "3";
    	}
    	else {
    		return "";
    	}
    }
    
    public String processStudent(String ID, String stuID) {
    	Book currentBook = null;
    	for (Book book : books ) {
    		if (book.recordId().equals(ID)) {
    			currentBook = book;
    		}
    	}
    	if(currentBook == null) {
    		return "";
    	}
    	if(currentBook.status() == RecordStatus.BORROWED) {
    		return "2";
		}
    	else {
    		return "";
    	}
    }
    
    public String authoriseProcess(String ID, String userId) {
    	Book currentBook = null;
    	for (Book book : books ) {
    		if (book.recordId().equals(ID)) {
    			currentBook = book;
    		}
    	}
    	if(currentBook == null) {
    		return "";
    	}
    	if(currentBook.status() == RecordStatus.REQUESTED) {  // Change to borrowed
			int index = books.indexOf(currentBook);
			books.set(index, currentBook.bookBorrow());
		}
    	if(currentBook.status() == RecordStatus.BORROWED) {  // Search for librarian id then change to returned.
    		String libID = findLibrarian();
			int index = books.indexOf(currentBook);
			books.set(index, currentBook.bookReturn(libID));
		}
    	if(currentBook.status() == RecordStatus.RETURNED) {  // Change to Available.
			int index = books.indexOf(currentBook);
			books.set(index, currentBook.bookAvailable());
		}
    	return "Book has been processed.";
    }
    
    public String updatePassword(String ID, String password) {
    	
    	for(User user : users) {
    		if(user.getUserId() == ID) {
    			user.setPassword(password);
    			return "Password changed!";
    		}
    	}
    	
    	return "Sorry, user not recognised.";
    }
}
