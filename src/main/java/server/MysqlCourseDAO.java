package server;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class MysqlCourseDAO implements InterfaceCourseDAO {

	private JdbcTemplate jdbcTemplate;

	public MysqlCourseDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;

	}

	@Override
	public UUID insertCourse(String name) {
		UUID uuid = UUID.randomUUID();
		jdbcTemplate.update(SQLQueries.INSERT_COURSE, uuid.toString(), name);
		return uuid;
	}

	@Override
	public void deleteCourse(UUID uuid) {
		String UUIDForMysql = ("'" + uuid.toString() + "'");
		jdbcTemplate.update(SQLQueries.DELETE_COURSE + UUIDForMysql);

	}

	@Override
	public Course findByUUID(UUID uuid) {
		String UUIDForMysql = ("'" + uuid.toString() + "'");
		return jdbcTemplate.query(SQLQueries.SELECT_COURSE_BY_UUID + UUIDForMysql, new ResultSetExtractor<Course>() {
			public Course extractData(ResultSet rs) throws SQLException, DataAccessException {
				Course course = null;
				while (rs.next()) {
					if (course == null || course.getUuid() != uuid) {
						course = new Course();
						course.setUuid(uuid);
						course.setName(rs.getString("nazov"));
					}
				}
				return course;
			}
		});
	}

}
