package server;

import org.springframework.jdbc.core.JdbcTemplate;
import com.mysql.cj.jdbc.MysqlDataSource;

public enum ObjectFactory {

	INSTANCE;

	private InterfaceStudentDAO studentDAO;
	private InterfaceCourseDAO courseDAO;
	private InterfaceAttendanceDAO attendanceDAO;
	private JdbcTemplate jdbcTemplate;
	private AJSService ajsService;

	private ObjectFactory() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setDatabaseName("cvika");
		dataSource.setURL("jdbc:mysql://localhost/cvika?serverTimezone=Europe/Bratislava");
		dataSource.setUser("root");
		dataSource.setPassword("yareyare");
		jdbcTemplate = new JdbcTemplate(dataSource);

		studentDAO = new MysqlStudentDAO(jdbcTemplate);
		courseDAO = new MysqlCourseDAO(jdbcTemplate);
		attendanceDAO = new MysqlAttendanceDAO(jdbcTemplate);
	}

	public JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate != null) {
			return jdbcTemplate;
		} else {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setDatabaseName("cvika");
			dataSource.setURL("jdbc:mysql://localhost/cvika?serverTimezone=Europe/Bratislava");
			dataSource.setUser("root");
			dataSource.setPassword("yareyare");
			jdbcTemplate = new JdbcTemplate(dataSource);
			return jdbcTemplate;
		}
	}

	public InterfaceStudentDAO getStudentDAO() {
		if (studentDAO == null) {
			studentDAO = new MysqlStudentDAO(jdbcTemplate);
		}
		return studentDAO;
	}

	public InterfaceCourseDAO getCourseDAO() {
		if (courseDAO == null) {
			courseDAO = new MysqlCourseDAO(jdbcTemplate);
		}
		return courseDAO;
	}

	public InterfaceAttendanceDAO getAttendanceDAO() {
		if (attendanceDAO == null) {
			attendanceDAO = new MysqlAttendanceDAO(jdbcTemplate);
		}
		return attendanceDAO;
	}

	public AJSService getAJSService() {
		if (ajsService == null) {
			ajsService = new DefaultAJSService();
		}
		return ajsService;
	}
}
