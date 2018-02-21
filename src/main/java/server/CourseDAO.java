package server;
import java.util.UUID;

public interface CourseDAO {

	UUID insertCourse(String name);

	void deleteCourse(UUID uuid);

	Course findByUUID(UUID uuid);

}
