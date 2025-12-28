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
    public boolean checkLibrarian(String userId) {
    	
    	for (User user : users ) {
    		if(user.getRole() == UserRole.LIBRARIAN) {
    			return true;
    		}
    	}
    	return false;
    }
    
    // assign a book to a random librarian.
    public String assignBorrowRequest(String ID) {
    	Book currentBook = null;
    	String id = findLibrarian();
    	if (id.equals("None")) { //Check for any librarians on the system.
    		return "Sorry, there are no librarians on the system.";
    	}
    	
    	for (Book book : books) {
    		if (book.recordId().equals(ID)) {
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
		// Verify successful request.
    	int index = books.indexOf(currentBook);
		books.set(index, currentBook.assignBookRequest(id));
		return "Book has been requested!";
    }
    
    //Print all requests assigned to the requested ID.
    public String printRequests(String id) {
    	StringBuilder sb = new StringBuilder("\n===== Books assigned to you =====\n");
    	
    	for(Book book : books) {
    		if(book.librarianId() == id) {
    			sb.append(book.printBook()).append("\n");
    		}
    	}
    	return sb.toString();
    }
    
    public String checkProcess(String ID) {
    	Book currentBook = null;
    	for (Book book : books ) {
    		if (book.recordId().equals(ID)) {
    			currentBook = book;
    		}
    	}
    	if(currentBook == null) {
    		return "9";
    	}
    	if(currentBook.status() == RecordStatus.REQUESTED) {
			return "1";
		}
    	
    	if(currentBook.status() == RecordStatus.BORROWED) {
    		return "2";
		}
    	
    	return "";
    }
    
    public String authoriseProcess(String ID) {
    	Book currentBook = null;
    	for (Book book : books ) {
    		if (book.recordId().equals(ID)) {
    			currentBook = book;
    		}
    	}
    	if(currentBook.status() == RecordStatus.REQUESTED) {
			int index = books.indexOf(currentBook);
			books.set(index, currentBook.bookBorrow());
		}
    	
    	if(currentBook.status() == RecordStatus.BORROWED) {
			int index = books.indexOf(currentBook);
			books.set(index, currentBook.bookReturn());
		}
    	return "Book has been processed.";
    }
}
