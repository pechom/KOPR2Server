package server;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;

public class MysqlDAOTest {

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
		UUID uuid = studentDAO.insertStudent("Captain", "Morgain");
		Student result = studentDAO.findByUUID(uuid);
		assertEquals(uuid, result.getUuid());
		studentDAO.deleteStudent(uuid);
	}

	@Test(expected = NullPointerException.class)
	public void testDeleteStudent() {
		System.out.println("deleteStudent");
		UUID uuid = studentDAO.insertStudent("Knight", "Gael");
		studentDAO.deleteStudent(uuid);
		Student blank = studentDAO.findByUUID(uuid);
		fail(blank.getUuid().toString());
	}

	@Test
	public void testFindStudent() {
		System.out.println("findStudent");
		UUID uuid = studentDAO.insertStudent("Abyss", "Watcher");
		Student result = studentDAO.findByUUID(uuid);
		assertEquals(uuid, result.getUuid());
		studentDAO.deleteStudent(uuid);
	}

	@Test
	public void testInsertCourse() {
		System.out.println("insertCourse");
		UUID uuid = courseDAO.insertCourse("KOPR");
		Course result = courseDAO.findByUUID(uuid);
		assertEquals(uuid, result.getUuid());
		courseDAO.deleteCourse(uuid);
	}

	@Test(expected = NullPointerException.class)
	public void testDeleteCourse() {
		System.out.println("deleteCourse");
		UUID uuid = courseDAO.insertCourse("KOPR");
		courseDAO.deleteCourse(uuid);
		Course blank = courseDAO.findByUUID(uuid);
		fail(blank.getUuid().toString());
	}

	@Test
	public void testFindCourse() {
		System.out.println("findCourse");
		UUID uuid = courseDAO.insertCourse("KOPR");
		Course result = courseDAO.findByUUID(uuid);
		assertEquals(uuid, result.getUuid());
		courseDAO.deleteCourse(uuid);
	}

	@Test
	public void testInsertAttendance() throws WrongInputException {
		System.out.println("insertAttendance");
		UUID courseUUID = courseDAO.insertCourse("KOPR");
		List<UUID> students = new ArrayList<>();
		UUID suuid = studentDAO.insertStudent("Artorias", "Abysswalker");
		students.add(suuid);
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(117, 1, 25, 14, 0, 0);
		calendar.clear(Calendar.MILLISECOND);
		Date time = calendar.getTime();
		UUID auuid = attendanceDAO.insertAttendance(courseUUID, time, students);
		Attendance attendance = attendanceDAO.findByUUID(auuid);
		assertEquals(attendance.getCourseName(), "KOPR");
		assertEquals(attendance.getTime(), time);
		studentDAO.deleteStudent(suuid);
		attendanceDAO.delete(auuid);
		courseDAO.deleteCourse(courseUUID);
	}

	@Test(expected = NullPointerException.class)
	public void testDeleteAttendance() throws WrongInputException {
		System.out.println("deleteAttendance");
		UUID courseUUID = courseDAO.insertCourse("KOPR");
		List<UUID> students = new ArrayList<>();
		UUID suuid = studentDAO.insertStudent("Everlasting", "Dragon");
		students.add(suuid);
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(117, 1, 25, 14, 0, 0);
		calendar.clear(Calendar.MILLISECOND);
		Date time = calendar.getTime();
		UUID auuid = attendanceDAO.insertAttendance(courseUUID, time, students);
		attendanceDAO.delete(auuid);
		Attendance blank = attendanceDAO.findByUUID(auuid);
		studentDAO.deleteStudent(suuid);
		courseDAO.deleteCourse(courseUUID);
		fail(blank.getUuid().toString());
	}

	@Test
	public void testFindAttendance() throws WrongInputException {
		System.out.println("findAttendance");
		UUID courseUUID = courseDAO.insertCourse("KOPR");
		List<UUID> students = new ArrayList<>();
		UUID suuid = studentDAO.insertStudent("Everlasting", "Dragon");
		students.add(suuid);
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(117, 1, 25, 14, 0, 0);
		calendar.clear(Calendar.MILLISECOND);
		Date time = calendar.getTime();
		UUID auuid = attendanceDAO.insertAttendance(courseUUID, time, students);
		Attendance result = attendanceDAO.findByUUID(auuid);
		assertEquals(result.getCourseName(), "KOPR");
		assertEquals(result.getUuid(), auuid);
		studentDAO.deleteStudent(suuid);
		attendanceDAO.delete(auuid);
		courseDAO.deleteCourse(courseUUID);
	}

	@Test
	public void testFindAttendees() throws WrongInputException {
		System.out.println("findAttendees");
		UUID courseUUID = courseDAO.insertCourse("KOPR");
		List<UUID> students = new ArrayList<>();
		UUID suuid1 = studentDAO.insertStudent("Pontyff", "Sulyvahn");
		UUID suuid2 = studentDAO.insertStudent("Chaoshander", "Giantdad");
		students.add(suuid1);
		students.add(suuid2);
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(117, 1, 25, 14, 0, 0);
		calendar.clear(Calendar.MILLISECOND);
		Date time = calendar.getTime();
		UUID auuid = attendanceDAO.insertAttendance(courseUUID, time, students);
		List<String> results = attendanceDAO.findAttendees(auuid);
		assertEquals(students.size(), results.size());
		studentDAO.deleteStudent(suuid1);
		studentDAO.deleteStudent(suuid2);
		attendanceDAO.delete(auuid);
		courseDAO.deleteCourse(courseUUID);
	}

	@Test
	public void testFindAttendancesOfAttendee() throws WrongInputException {
		System.out.println("AttendancesOfAttendee");
		UUID suuid = studentDAO.insertStudent("Dragonslayer", "Ornstein");
		UUID courseUUID = courseDAO.insertCourse("KAPR");
		List<UUID> students = new ArrayList<>();
		students.add(suuid);
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(117, 1, 25, 14, 0, 0);
		calendar.clear(Calendar.MILLISECOND);
		Date time = calendar.getTime();
		UUID auuid1 = attendanceDAO.insertAttendance(courseUUID, time, students);
		UUID auuid2 = attendanceDAO.insertAttendance(courseUUID, time, students);
		List<Attendance> results = attendanceDAO.attendancesOfAttendee(suuid);
		assertEquals(results.size(), 2);
		studentDAO.deleteStudent(suuid);
		attendanceDAO.delete(auuid1);
		attendanceDAO.delete(auuid2);
		courseDAO.deleteCourse(courseUUID);
	}
}
