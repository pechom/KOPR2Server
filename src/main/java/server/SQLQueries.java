package server;

public class SQLQueries {
	
	public static final String NUMBER_OF_COURSES = "SELECT COUNT(*) FROM predmet WHERE predmet.puuid = ";
	
	public static final String NUMBER_OF_ATTENDANCIES = "SELECT COUNT(*) FROM prezencka WHERE prezencka.auuid = ";
	
	public static final String NUMBER_OF_ATTENDANDS = "SELECT COUNT(*) FROM ucastnik WHERE ucastnik.uuuid = ";
	
	public static final String INSERT_STUDENT = "INSERT INTO ucastnik (uuuid, meno, priezvisko) VALUES (?, ?, ?)";

	public static final String INSERT_COURSE = "INSERT INTO predmet (puuid, nazov) VALUES (?, ?)";

	public static final String INSERT_ATTENDANCE = "INSERT INTO prezencka (auuid, cas, predmet_uuid) values (?, ?, ?)";

	public static final String INSERT_ATTENDEES = "INSERT INTO ucastnici_prezencky (prezencka_uuid, ucastnik_uuid) VALUES (?, ?)";

	public static final String DELETE_STUDENT = "DELETE FROM ucastnik WHERE uuuid = ";

	public static final String DELETE_COURSE = "DELETE FROM predmet WHERE puuid = ";

	public static final String DELETE_ATTENDANCE = "DELETE FROM prezencka WHERE auuid = ";

	public static final String SELECT_STUDENT_BY_UUID = "SELECT ucastnik.uuuid, ucastnik.meno, ucastnik.priezvisko FROM ucastnik WHERE ucastnik.uuuid = ";

	public static final String SELECT_COURSE_BY_UUID = "SELECT predmet.puuid, predmet.nazov FROM predmet WHERE predmet.puuid = ";

	public static final String SELECT_ATTENDANCE_BY_UUID = "SELECT prezencka.auuid, prezencka.cas, prezencka.predmet_uuid, predmet.nazov FROM prezencka "
			+ "LEFT JOIN predmet ON prezencka.predmet_uuid = predmet.puuid WHERE prezencka.auuid = ";

	public static final String SELECT_NAMES_OF_ATTENDEES = "SELECT meno, priezvisko FROM ucastnik WHERE ucastnik.uuuid IN "
			+ "(SELECT ucastnik_uuid FROM ucastnici_prezencky WHERE prezencka_uuid = ";

	public static final String SELECT_ATTENDANCIES_OF_STUDENT = "SELECT prezencka.auuid, prezencka.cas, prezencka.predmet_uuid, predmet.nazov FROM prezencka "
			+ "LEFT JOIN predmet ON predmet.puuid = prezencka.predmet_uuid "
			+ "LEFT JOIN ucastnici_prezencky ON ucastnici_prezencky.prezencka_uuid = prezencka.auuid AND ucastnici_prezencky.ucastnik_uuid = ";

	// nepouzite
	public static final String SELECT_ATTENDANCIES_OF_STUDENT2 = "SELECT aUUID, cas, predmet_UUID FROM prezencka WHERE UUID IN "
			+ "(SELECT prezencka_UUID FROM ucastnici_prezencky WHERE ucastnik_UUID = ";
	public static final String SELECT_NAMES_OF_ATTENDEES2 = "SELECT ucastnik.meno, ucastnik.priezvisko FROM ucastnik "
			+ "LEFT JOIN ucastnici_prezencky ON ucastnik.UUID = ucastnici_prezencky.ucastnik_UUID AND ucastnici_prezencky.prezencka_UUID = ";
	public static final String SELECT_STUDENTS_OF_ATTENDANCE = "SELECT uuuid, meno, priezvisko FROM ucastnik WHERE ucastnik.uuuid IN "
			+ "(SELECT ucastnik_uuid FROM ucastnici_prezencky WHERE prezencka_uuid = ";

}
