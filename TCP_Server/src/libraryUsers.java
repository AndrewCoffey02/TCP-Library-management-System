import java.util.*;

public class libraryUsers {
	private List<User> users = new ArrayList<>();
    public boolean ListIsEmpty() {
    	if (users.isEmpty()) {
    		return true;
    	}else {
    		return false;
    	}
    }

    public boolean checkStudentId(String studentId) {
    	boolean exists = users.stream().anyMatch(u ->
        u.getStudentId().equals(studentId));
    	 
    	return exists;
    }
    
    public boolean checkEmail(String email) {
    	boolean exists = users.stream().anyMatch(u ->
        u.getEmail().equals(email));
    	
    	return exists;
    }
    
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
    
    public void addUser(User user) {
    	users.add(user);
    }

    public String printList() {
    	StringBuilder sb = new StringBuilder("===== Registered Users =====\n");

	    for (User user : users) {
	        sb.append(user.contains()).append("\n");
	    }

	    return sb.toString();
    }
}
