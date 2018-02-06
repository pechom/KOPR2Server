package server;
import java.util.UUID;

public interface InterfaceStudentDAO {

	UUID insertStudent(String name, String surname);

	void deleteStudent(UUID uuid);

	Student findByUUID(UUID uuid);
}
