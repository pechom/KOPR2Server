package server;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface InterfaceAttendanceDAO {

	UUID insertAttendance(UUID courseUUID, Date time, List<UUID> attendees);

	void delete(UUID uuid);

	Attendance findByUUID(UUID uuid);

	List<String> findAttendees(UUID uuid);

	List<Attendance> attendancesOfAttendee(UUID uuid);

}