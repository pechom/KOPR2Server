package server;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "AJSService")
public class DefaultAJSService implements AJSService {

	private InterfaceStudentDAO studentDAO = ObjectFactory.INSTANCE.getStudentDAO();
	private InterfaceCourseDAO courseDAO = ObjectFactory.INSTANCE.getCourseDAO();
	private InterfaceAttendanceDAO attendanceDAO = ObjectFactory.INSTANCE.getAttendanceDAO();

	@Override
	@WebMethod
	public UUID insertCourse(@WebParam(name = "name") String name) {
		return courseDAO.insertCourse(name);
	}

	@Override
	@WebMethod
	public UUID insertStudent(@WebParam(name = "name") String name, @WebParam(name = "surname") String surname) {
		return studentDAO.insertStudent(name, surname);
	}

	@Override
	@WebMethod
	public UUID insertAttendance(@WebParam(name = "courseUUID") UUID courseUUID, @WebParam(name = "time") Date time,
			@WebParam(name = "attendees") List<UUID> attendees) {
		return attendanceDAO.insertAttendance(courseUUID, time, attendees);
	}

	@Override
	@WebMethod
	public List<String> findAttendees(@WebParam(name = "nuuid") UUID uuid) {
		return attendanceDAO.findAttendees(uuid);
	}

	@Override
	@WebMethod
	public List<Attendance> attendancesOfAttendee(@WebParam(name = "uuid") UUID uuid) {
		return attendanceDAO.attendancesOfAttendee(uuid);
	}

}
