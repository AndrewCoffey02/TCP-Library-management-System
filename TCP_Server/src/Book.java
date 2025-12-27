import java.time.LocalDate;
import java.util.*;

enum RecordType {
    NEW_BOOK_ENTRY,
    BORROW_REQUEST
}

enum RecordStatus {
    AVAILABLE,
    REQUESTED,
    BORROWED,
    RETURNED
}

public record Book(
	String name,
	RecordType recordType,
    String recordId,
    LocalDate date,
    String studentId,
    RecordStatus status,
    String librarianId   // nullable until processed
) {
	
	public String printBook() {
    	return "Book: " + name +
	           "\nID: " + recordId +
	           "\nDate added: " + date +
	           "\nCreated by: " + studentId +
	           "\nStatus: " + status +
	           "\n---------------------------";
    }
	
	public static Book createBook(
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
	
	public Book assignBookRequest(
    	    String librarian
    	) {
    		
    		return new Book(
    				name,
    				RecordType.BORROW_REQUEST, 
    				recordId, 
    				date, 
    				studentId,
    				RecordStatus.REQUESTED, 
    				librarian
    		);	
    	}
}
