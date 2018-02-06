package server;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class MysqlAttendanceDAO implements InterfaceAttendanceDAO {

	private java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private JdbcTemplate jdbcTemplate;

	public MysqlAttendanceDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public UUID insertAttendance(UUID courseUUID, Date time, List<UUID> attendees) {
		String SQLtime = sdf.format(time);
		UUID uuid = UUID.randomUUID();
		String CUUIDForMysql = ("'" + courseUUID.toString() + "'");
		int courseNumber = jdbcTemplate.queryForObject(SQLQueries.NUMBER_OF_COURSES + CUUIDForMysql, Integer.class);
		if (courseNumber == 0) {
			throw new WrongInputException("Course with ID you entered: " + courseUUID + " does not exist");
		}
		for (UUID auuid : attendees) {
			String AUUIDForMysql = ("'" + auuid.toString() + "'");
			int studentNumber = jdbcTemplate.queryForObject(SQLQueries.NUMBER_OF_ATTENDANDS + AUUIDForMysql, Integer.class);
			if (studentNumber == 0) {
				throw new WrongInputException("Student with ID you entered: " + auuid + " does not exist");
			}
		}
		jdbcTemplate.update(SQLQueries.INSERT_ATTENDANCE, uuid.toString(), SQLtime, courseUUID.toString());
		for (UUID auuid : attendees) {
			jdbcTemplate.update(SQLQueries.INSERT_ATTENDEES, uuid.toString(), auuid.toString());
		}
		return uuid;
	}

	@Override
	public void delete(UUID uuid) {
		String UUIDForMysql = ("'" + uuid.toString() + "'");
		jdbcTemplate.update(SQLQueries.DELETE_ATTENDANCE + UUIDForMysql);

	}

	@Override
	public Attendance findByUUID(UUID uuid) {
		String UUIDForMysql = ("'" + uuid.toString() + "'");
		return jdbcTemplate.query(SQLQueries.SELECT_ATTENDANCE_BY_UUID + UUIDForMysql,
				new ResultSetExtractor<Attendance>() {
					public Attendance extractData(ResultSet rs) throws SQLException, DataAccessException {
						Attendance attendance = null;
						while (rs.next()) {
							if (attendance == null || attendance.getUuid() != uuid) {
								attendance = new Attendance();
								attendance.setUuid(uuid);
								attendance.setCourseUUID(UUID.fromString(rs.getString("predmet_uuid")));
								attendance.setCourseName(rs.getString("predmet.nazov"));
								try {
									attendance.setTime(sdf.parse(rs.getString("cas")));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						return attendance;
					}
				});
	}

	@Override
	public List<String> findAttendees(UUID uuid) {
		List<String> students = new ArrayList<>();
		String UUIDForMysql = ("'" + uuid.toString() + "'");
		int attendanceNumber = jdbcTemplate.queryForObject(SQLQueries.NUMBER_OF_ATTENDANCIES + UUIDForMysql, Integer.class);
		if (attendanceNumber == 0) {
			throw new WrongInputException("Attendance with ID you entered: " + uuid + " does not exist");
		}
		UUIDForMysql = ("'" + uuid.toString() + "' )");
		return jdbcTemplate.query(SQLQueries.SELECT_NAMES_OF_ATTENDEES + UUIDForMysql,
				new ResultSetExtractor<List<String>>() {
					public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
						Attendance attendance = null;
						while (rs.next()) {
							String name = rs.getString("ucastnik.meno");
							String surname = rs.getString("ucastnik.priezvisko");
							students.add(name + " " + surname);
						}
						return students;
					}
				});
	}

	@Override
	public List<Attendance> attendancesOfAttendee(UUID uuid) {
		List<Attendance> attendances = new ArrayList<>();
		String UUIDForMysql = ("'" + uuid.toString() + "'");
		int studentNumber = jdbcTemplate.queryForObject(SQLQueries.NUMBER_OF_ATTENDANDS + UUIDForMysql, Integer.class);
		if (studentNumber == 0) {
			throw new WrongInputException("Student with ID you entered: " + uuid + " does not exist");
		}
		return jdbcTemplate.query(SQLQueries.SELECT_ATTENDANCIES_OF_STUDENT + UUIDForMysql,
				new ResultSetExtractor<List<Attendance>>() {
					public List<Attendance> extractData(ResultSet rs) throws SQLException, DataAccessException {
						Attendance attendance = null;
						while (rs.next()) {
							UUID uuid = UUID.fromString(rs.getString("prezencka.auuid"));
							if (attendance == null || attendance.getUuid() != uuid) {
								attendance = new Attendance();
								attendance.setUuid(uuid);
								attendance.setCourseUUID(UUID.fromString(rs.getString("prezencka.predmet_uuid")));
								attendance.setCourseName(rs.getString("predmet.nazov"));
								try {
									attendance.setTime(sdf.parse(rs.getString("prezencka.cas")));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							attendances.add(attendance);
						}
						return attendances;
					}
				});
	}

}
