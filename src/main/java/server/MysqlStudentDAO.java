package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class MysqlStudentDAO implements StudentDAO {

	private JdbcTemplate jdbcTemplate;

	public MysqlStudentDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;

	}

	@Override
	public UUID insertStudent(String name, String surname) {
		UUID uuid = UUID.randomUUID();
		jdbcTemplate.update(SQLQueries.INSERT_STUDENT, uuid.toString(), name, surname);
		return uuid;
	}

	@Override
	public void deleteStudent(UUID uuid) {
		String UUIDForMysql = ("'" + uuid.toString() + "'");
		jdbcTemplate.update(SQLQueries.DELETE_STUDENT + UUIDForMysql);

	}

	@Override
	public Student findByUUID(UUID uuid) {
		String UUIDForMysql = ("'" + uuid.toString() + "'");
		return jdbcTemplate.query(SQLQueries.SELECT_STUDENT_BY_UUID + UUIDForMysql, new ResultSetExtractor<Student>() {
			public Student extractData(ResultSet rs) throws SQLException, DataAccessException {
				Student student = null;
				while (rs.next()) {
					if (student == null || student.getUuid() != uuid) {
						student = new Student();
						student.setUuid(uuid);
						student.setName(rs.getString("meno"));
						student.setSurname(rs.getString("priezvisko"));
					}
				}
				return student;
			}
		});
	}
}
