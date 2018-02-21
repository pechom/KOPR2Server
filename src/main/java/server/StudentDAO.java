package server;
import java.util.UUID;

public interface StudentDAO {

	UUID insertStudent(String name, String surname);

	void deleteStudent(UUID uuid);

	Student findByUUID(UUID uuid);
}
