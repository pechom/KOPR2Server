package server;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AJSService {
	
	UUID insertCourse (String name);

	UUID insertStudent (String name, String surname);
	
	UUID insertAttendance(UUID courseUUID, Date time, List<UUID> attendees) throws WrongInputException;
	
	List<String> findAttendees(UUID uuid) throws WrongInputException;
	
	List<Attendance> attendancesOfAttendee(UUID uuid) throws WrongInputException;
	
}
