
enum UserRole{
		STUDENT,
		LIBRARIAN
}

public class User {
    private String name;
    private String userId;   // must be unique
    private String email;       // must be unique
    private String password;
    private String department;
    private UserRole role;

    public User(String name, String userId, String email, String password, String department, UserRole role) {
        this.name = name;
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.department = department;
        this.role = role;
    }

 // ---- Getters ----
    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDepartment() {
        return department;
    }

    public UserRole getRole() {
        return role;
    }

    // ---- Setters ----
    public void setName(String name) {
        this.name = name;
    }

    public void setStudentId(String userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setRole(UserRole role) {
    	this.role = role;
        
    }
    
    public String printUser() {
    	return "Name: " + name +
	           "\nStudent ID: " + userId +
	           "\nEmail: " + email +
	           "\nDepartment: " + department +
	           "\nRole: " + role +
	           "\n---------------------------";
    }
}