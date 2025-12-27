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
	
	public String printBooks() {
    	return "Book: " + name +
	           "\nID: " + recordId +
	           "\nDate added: " + date +
	           "\nCreated by: " + studentId +
	           "\nStatus: " + status +
	           "\n---------------------------";
    }
}
