package server;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class AJSServiceTest {

	private AJSService ajsService;
	private InterfaceStudentDAO studentDAO;
	private InterfaceCourseDAO courseDAO;
	private InterfaceAttendanceDAO attendanceDAO;
	private JdbcTemplate jdbcTemplate;

	@BeforeClass
	public static void setUpClass() {

	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
		ajsService = ObjectFactory.INSTANCE.getAJSService();
		studentDAO = ObjectFactory.INSTANCE.getStudentDAO();
		courseDAO = ObjectFactory.INSTANCE.getCourseDAO();
		attendanceDAO = ObjectFactory.INSTANCE.getAttendanceDAO();
		jdbcTemplate = ObjectFactory.INSTANCE.getJdbcTemplate();
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testInsertStudent() {
		System.out.println("insertStudent");
		UUID uuid = ajsService.insertStudent("Captain", "Morgain");
		Student result = studentDAO.findByUUID(uuid);
		assertEquals(uuid, result.getUuid());
		assertEquals("Captain", result.getName());
		assertEquals("Morgain", result.getSurname());
		studentDAO.deleteStudent(uuid);
	}

	@Test
	public void testInsertCourse() {
		System.out.println("insertCourse");
		UUID uuid = ajsService.insertCourse("KOPR");
		Course result = courseDAO.findByUUID(uuid);
		assertEquals(uuid, result.getUuid());
		assertEquals("KOPR", result.getName());
		courseDAO.deleteCourse(uuid);
	}

	@Test
	public void testInsertAttendance() {
		System.out.println("insertAttendance");
		UUID courseUUID = ajsService.insertCourse("KOPR");
		List<UUID> students = new ArrayList<>();
		UUID suuid = ajsService.insertStudent("Artorias", "Abysswalker");
		students.add(suuid);
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(117, 1, 25, 14, 0, 0);
		calendar.clear(Calendar.MILLISECOND);
		Date time = calendar.getTime();
		UUID auuid = ajsService.insertAttendance(courseUUID, time, students);
		Attendance attendance = attendanceDAO.findByUUID(auuid);
		assertEquals(attendance.getCourseName(), "KOPR");
		assertEquals(attendance.getTime(), time);
		studentDAO.deleteStudent(suuid);
		attendanceDAO.delete(auuid);
		courseDAO.deleteCourse(courseUUID);
	}

	@Test
	public void testFindAttendees() {
		System.out.println("findAttendees");
		UUID courseUUID = ajsService.insertCourse("KOPR");
		List<UUID> students = new ArrayList<>();
		UUID suuid1 = ajsService.insertStudent("Pontyff", "Sulyvahn");
		UUID suuid2 = ajsService.insertStudent("Chaoshander", "Giantdad");
		students.add(suuid1);
		students.add(suuid2);
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(117, 1, 25, 14, 0, 0);
		calendar.clear(Calendar.MILLISECOND);
		Date time = calendar.getTime();
		UUID auuid = ajsService.insertAttendance(courseUUID, time, students);
		List<String> results = ajsService.findAttendees(auuid);
		assertEquals(students.size(), results.size());
		studentDAO.deleteStudent(suuid1);
		studentDAO.deleteStudent(suuid2);
		attendanceDAO.delete(auuid);
		courseDAO.deleteCourse(courseUUID);
	}

	@Test
	public void testFindAttendancesOfAttendee() {
		System.out.println("AttendancesOfAttendee");
		UUID suuid = ajsService.insertStudent("Dragonslayer", "Ornstein");
		UUID courseUUID = ajsService.insertCourse("KAPR");
		List<UUID> students = new ArrayList<>();
		students.add(suuid);
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(117, 1, 25, 14, 0, 0);
		calendar.clear(Calendar.MILLISECOND);
		Date time = calendar.getTime();
		UUID auuid1 = ajsService.insertAttendance(courseUUID, time, students);
		UUID auuid2 = ajsService.insertAttendance(courseUUID, time, students);
		List<Attendance> results = ajsService.attendancesOfAttendee(suuid);
		assertEquals(results.size(), 2);
		studentDAO.deleteStudent(suuid);
		attendanceDAO.delete(auuid1);
		attendanceDAO.delete(auuid2);
		courseDAO.deleteCourse(courseUUID);
	}

	@Test(expected = WrongInputException.class)
	public void testInsertAttendanceFailedCourse() {
		System.out.println("insertFailedCourseAttendance");
		List<UUID> students = new ArrayList<>();
		UUID suuid = ajsService.insertStudent("Artorias", "Abysswalker");
		students.add(suuid);
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(117, 1, 25, 14, 0, 0);
		calendar.clear(Calendar.MILLISECOND);
		Date time = calendar.getTime();
		try {
			ajsService.insertAttendance(UUID.fromString("21f87078-0b25-11e8-b159-086266b31825"), time, students);
		} finally {
			studentDAO.deleteStudent(suuid);
		}
	}

	@Test(expected = WrongInputException.class)
	public void testInsertAttendanceFailedStudent() {
		System.out.println("insertFailedStudentAttendance");
		UUID courseUUID = ajsService.insertCourse("KAPR");
		List<UUID> students = new ArrayList<>();
		students.add(UUID.fromString("21f87078-0b25-11e8-b159-086266b31825"));
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(117, 1, 25, 14, 0, 0);
		calendar.clear(Calendar.MILLISECOND);
		Date time = calendar.getTime();
		try {
			ajsService.insertAttendance(courseUUID, time, students);
		} finally {
			courseDAO.deleteCourse(courseUUID);
		}
	}

	@Test(expected = WrongInputException.class)
	public void testInsertAttendanceFailedBoth() {
		System.out.println("insertFailedBothAttendance");
		List<UUID> students = new ArrayList<>();
		students.add(UUID.fromString("21f87078-0b25-11e8-b159-086266b31825"));
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(117, 1, 25, 14, 0, 0);
		calendar.clear(Calendar.MILLISECOND);
		Date time = calendar.getTime();
		ajsService.insertAttendance(UUID.fromString("291dea46-0b28-11e8-b159-086266b31825"), time, students);
	}

	@Test(expected = WrongInputException.class)
	public void testFindAttendeesFailed() {
		System.out.println("failedFindFAttendees");
		UUID auuid = UUID.fromString("291dea46-0b28-11e8-b159-086266b31825");
		ajsService.findAttendees(auuid);
	}

	@Test(expected = WrongInputException.class)
	public void testFindAttendancesOfAttendeeFailed() {
		System.out.println("AttendancesOfAttendeeFailed");
		UUID suuid = UUID.fromString("291dea46-0b28-11e8-b159-086266b31825");
		ajsService.attendancesOfAttendee(suuid);
	}

}
