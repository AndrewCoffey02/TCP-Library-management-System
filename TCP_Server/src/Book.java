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
    String userId,
    RecordStatus status,
    String librarianId   // nullable until processed
) {
	
	public String printBook() {
    	return "Book: " + name +
	           "\nID: " + recordId +
	           "\nDate added: " + date +
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
    	    String librarian,
    	    String userId
    	) {
    		return new Book(
    				name,
    				RecordType.BORROW_REQUEST, 
    				recordId, 
    				date, 
    				userId,
    				RecordStatus.REQUESTED, 
    				librarian
    		);	
    	}
	
	public Book bookBorrow() {
		return new Book(
				name,
				RecordType.BORROW_REQUEST, 
				recordId, 
				date, 
				userId,
				RecordStatus.BORROWED, 
				null
		);
	}
	public Book bookReturn(
			String librarianId
		) {
		return new Book(
				name,
				RecordType.BORROW_REQUEST, 
				recordId, 
				date, 
				userId,
				RecordStatus.RETURNED, 
				librarianId
		);
	}
	public Book bookAvailable() {
		return new Book(
				name,
				RecordType.BORROW_REQUEST, 
				recordId, 
				date, 
				userId,
				RecordStatus.AVAILABLE, 
				null
		);
	}
}
