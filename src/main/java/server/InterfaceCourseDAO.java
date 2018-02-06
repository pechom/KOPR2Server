package server;
import java.util.UUID;

public interface InterfaceCourseDAO {

	UUID insertCourse(String name);

	void deleteCourse(UUID uuid);

	Course findByUUID(UUID uuid);

}
