package server;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Attendance {

	private UUID uuid;
	private UUID courseUUID;
	private String courseName;
	private Date time;
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public UUID getCourseUUID() {
		return courseUUID;
	}
	public void setCourseUUID(UUID courseUUID) {
		this.courseUUID = courseUUID;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	private List<Student> students;
	
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
