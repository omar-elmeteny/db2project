import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

public class Schema1 {
	private static Random random = new Random(1000);

	private static void deleteTable(String tableName, Connection conn) throws SQLException {
		System.out.println("Deleting from table " + tableName);
		String SQL = String.format("TRUNCATE TABLE \"%s\" CASCADE;", tableName);
		PreparedStatement pstmt = conn.prepareStatement(SQL,
				Statement.NO_GENERATED_KEYS);
		conn.setAutoCommit(false);
		pstmt.executeUpdate();
		conn.commit();
		pstmt.close();
		System.out.println("Done deleting from table " + tableName);
	}

	// //////////////////////////////////////////// Table Insertion Methods
	// ///////////////////////////////////////////////////////////////
	public static long insertDepartment(int building, String deptName,
			int budget, Connection conn) throws SQLException {
		String SQL = "INSERT INTO department(dep_name,building,budget) "
				+ "VALUES(?,?,?);";

		long id = 0;

		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement(SQL,
				Statement.RETURN_GENERATED_KEYS);

		pstmt.setInt(2, building);
		pstmt.setString(1, deptName);
		pstmt.setInt(3, budget);

		int affectedRows = pstmt.executeUpdate();
		// check the affected rows
		if (affectedRows > 0) {
			// get the ID back
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					id = rs.getLong(2);
					pstmt.close();
					conn.commit();
				}
			}
		}

		return id;
	}

	public static int insertInstructor(String name, int salary,
			String deptName, Connection conn) throws SQLException {
		String SQL = "INSERT INTO instructor(ID,name,salary,dep_name)"
				+ "VALUES(nextval('idsequence'),?,?,?);";

		int id = 0;
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement(SQL,
				Statement.RETURN_GENERATED_KEYS);

		pstmt.setString(1, name);
		pstmt.setInt(2, salary);
		pstmt.setString(3, deptName);

		int affectedRows = pstmt.executeUpdate();
		// check the affected rows
		if (affectedRows > 0) {
			// get the ID back
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					id = rs.getInt(1);
					pstmt.close();
					conn.commit();
				}
			}
		}

		return id;
	}

	public static long insertClassroom(int building, int roomNo, int capacity,
			Connection conn) throws SQLException {
		String SQL = "INSERT INTO classroom(building,room_number,capacity)"
				+ "VALUES(?,?,?);";

		long id = 0;
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement(SQL,
				Statement.RETURN_GENERATED_KEYS);

		pstmt.setInt(2, roomNo);
		pstmt.setInt(1, building);
		pstmt.setInt(3, capacity);

		int affectedRows = pstmt.executeUpdate();
		// check the affected rows
		if (affectedRows > 0) {
			// get the ID back
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					id = rs.getLong(1);
					pstmt.close();
					conn.commit();
				}
			}
		}
		return id;

	}

	public static long insertTimeSlot(int ID, String day, Time start, Time end,
			Connection conn) throws SQLException {
		String SQL = "INSERT INTO time_slot(id,day,start,end_time)"
				+ "VALUES(?,?,?,?);";

		long id = 0;
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement(SQL,
				Statement.RETURN_GENERATED_KEYS);

		pstmt.setString(2, day);
		pstmt.setInt(1, ID);
		pstmt.setTime(3, start);
		pstmt.setTime(4, end);

		int affectedRows = pstmt.executeUpdate();
		// check the affected rows
		if (affectedRows > 0) {
			// get the ID back
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					id = rs.getLong(1);
					pstmt.close();
					conn.commit();
				}
			}
		}
		return id;
	}

	public static int insertStudent(String name, int credit,
			String deptName, int advisorId, Connection conn) throws SQLException {
		String SQL = "INSERT INTO student(id,name,tot_credit,department,advisor_id)"
				+ "VALUES(nextval('idsequence'),?,?,?,?);";

		int id = 0;
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement(SQL,
				Statement.RETURN_GENERATED_KEYS);

		pstmt.setString(1, name);
		pstmt.setInt(2, credit);
		pstmt.setString(3, deptName);
		pstmt.setInt(4, advisorId);

		int affectedRows = pstmt.executeUpdate();
		// check the affected rows
		if (affectedRows > 0) {
			// get the ID back
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					id = rs.getInt(1);
					pstmt.close();
					conn.commit();
				}
			}
		}
		return id;
	}

	// CREATE TABLE course(course_id INT PRIMARY KEY, title VARCHAR(20), credits
	// INT, department VARCHAR(20) REFERENCES department);
	public static int insertCourse(String title, int credit,
			String deptName, Connection conn) throws SQLException {
		String SQL = "INSERT INTO course(course_id,title,credits,department)"
				+ "VALUES(nextval('idsequence'),?,?,?);";

		int id = 0;
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement(SQL,
				Statement.RETURN_GENERATED_KEYS);

		pstmt.setString(1, title);
		pstmt.setInt(2, credit);
		pstmt.setString(3, deptName);

		int affectedRows = pstmt.executeUpdate();
		// check the affected rows
		if (affectedRows > 0) {
			// get the ID back
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					id = rs.getInt(1);
					pstmt.close();
					conn.commit();
				}
			}
		}
		return id;
	}

	// CREATE TABLE pre_requiste(course_id INT, prereq_id INT,PRIMARY
	// KEY(course_id, prereq_id));
	public static long insertPrerequiste(int ID, int preID, Connection conn) throws SQLException {
		String SQL = "INSERT INTO pre_requiste(course_id,prereq_id)"
				+ "VALUES(?,?);";

		long id = 0;
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement(SQL,
				Statement.RETURN_GENERATED_KEYS);

		pstmt.setInt(2, preID);
		pstmt.setInt(1, ID);

		int affectedRows = pstmt.executeUpdate();
		// check the affected rows
		if (affectedRows > 0) {
			// get the ID back
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					id = rs.getLong(1);
					pstmt.close();
					conn.commit();
				}
			}
		}
		return id;
	}

	// CREATE TABLE section(section_id INT PRIMARY KEY, semester INT, year INT,
	// instructor_id INT REFERENCES instructor, course_id INT REFERENCES
	// course,classroom_building INT REFERENCES classroom(building),
	// classroom_room_no INT REFERENCES classroom(room_number));

	public static int insertSection(int semester, int year,
			int instID, int courseID, int classroomBuilding,
			int classroomRoomNo, Connection conn) throws SQLException {
		String SQL = "INSERT INTO section(section_id,semester,year,instructor_id,course_id,classroom_building,classroom_room_no)"
				+ "VALUES(nextval('idsequence'),?,?,?,?,?,?);";

		int id = 0;
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement(SQL,
				Statement.RETURN_GENERATED_KEYS);

		pstmt.setInt(1, semester);
		pstmt.setInt(2, year);
		pstmt.setInt(3, instID);
		pstmt.setInt(4, courseID);
		pstmt.setInt(5, classroomBuilding);
		pstmt.setInt(6, classroomRoomNo);

		int affectedRows = pstmt.executeUpdate();
		// check the affected rows
		if (affectedRows > 0) {
			// get the ID back
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					id = rs.getInt(1);
					pstmt.close();
					conn.commit();
				}
			}
		}
		return id;
	}

	// CREATE TABLE takes(student_id INT REFERENCES student, section_id INT
	// REFERENCES section, grade real, PRIMARY KEY(student_id, section_id));
	public static void insertTakes(int student_id, int secID, double grade,
			Connection conn) throws SQLException {
		String SQL = "INSERT INTO takes(student_id,section_id,grade)"
				+ "VALUES(?,?,?);";

		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement(SQL,
				Statement.RETURN_GENERATED_KEYS);

		pstmt.setInt(2, secID);
		pstmt.setInt(1, student_id);
		pstmt.setDouble(3, grade);

		try {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			if (!e.getSQLState().equals("23505")) {
				throw e;				
			}
		}
		pstmt.close();
		conn.commit();
	}

	// CREATE TABLE section_time(time_slot INT REFERENCES time_slot, section_id
	// INT REFERENCES section, PRIMARY KEY(time_slot, section_id));
	public static long insertSectionTime(int timeSlot, int secID,
			Connection conn) throws SQLException {
		String SQL = "INSERT INTO section_time(time_slot,section_id)"
				+ "VALUES(?,?);";

		long id = 0;
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement(SQL,
				Statement.RETURN_GENERATED_KEYS);

		pstmt.setInt(2, secID);
		pstmt.setInt(1, timeSlot);

		int affectedRows = pstmt.executeUpdate();
		// check the affected rows
		if (affectedRows > 0) {
			// get the ID back
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					id = rs.getLong(1);
					pstmt.close();
					conn.commit();
				}
			}
		}
		return id;
	}

	// ///////////////////////////////////////// Data Population Method
	// //////////////////////////////////////////////////////
	public static void populateDepartment(Connection conn) throws SQLException {
		System.out.println("populating departments");
		for (int i = 0; i < 60; i++) {
			char c1 = (char) ('E' + i / 20);
			char c2 = (char) ('A' + i % 20);
			String deptName = "CS" + c1 + c2;
			insertDepartment(i, deptName, i, conn);
			ArrayList<Integer> instructorIds = populateInstructor(conn, deptName);
			ArrayList<Integer> courseIds = populateCourse(conn, deptName);
			ArrayList<Integer> sectionIds = populateSection(conn, instructorIds, courseIds);
			ArrayList<Integer> studentIds = populateStudent(conn, deptName, instructorIds);
			populateTakes(conn, studentIds, sectionIds);
			populateSectionTime(conn, sectionIds);
		}
	}

	public static ArrayList<Integer> populateInstructor(Connection conn, String deptName) throws SQLException {
		System.out.println("populating instructors for dept " + deptName);
		int numberOfInstructors = random.nextInt(26) + 10;
		ArrayList<Integer> result = new ArrayList<>(numberOfInstructors);
		for (int i = 0; i < numberOfInstructors; i++) {
			int insId = insertInstructor("Name" + i, i, deptName, conn);
			result.add(insId);
		}
		return result;
	}

	public static void populateClassroom(Connection conn) throws SQLException {
		System.out.println("populating classrooms ");
		for (int i = 1; i <= 400; i++) {
			int cap = random.nextInt(5) * 10 + 50;
			insertClassroom(i, i, cap, conn);
		}
	}

	@SuppressWarnings("deprecation")
	public static void populateTimeSlot(Connection conn) throws SQLException {
		System.out.println("populating timeslots");
		for (int i = 1; i <= 10000; i++) {
			insertTimeSlot(i, "day" + i, new Time(12, 0, 0), new Time(13,
					0, 0), conn);
		}
	}

	public static ArrayList<Integer> populateStudent(Connection conn, String deptName, ArrayList<Integer> instructors)
			throws SQLException {
		System.out.println("populating students dept " + deptName);

		int nStudents = random.nextInt(1000) + 1100;
		ArrayList<Integer> result = new ArrayList<>();
		for (int i = 0; i < nStudents; i++) {
			int advisorId = instructors.get(random.nextInt(instructors.size()));
			int studentId = insertStudent("Student " + deptName + " " + i, i, deptName, advisorId, conn);
			result.add(studentId);
		}
		return result;
	}

	public static ArrayList<Integer> populateCourse(Connection conn, String deptName) throws SQLException {
		System.out.println("populating courses dept " + deptName);

		int numberOfCourses = random.nextInt(26) + 20;
		ArrayList<Integer> result = new ArrayList<>(numberOfCourses);
		for (int i = 0; i < numberOfCourses; i++) {
			int credit = random.nextInt(8) + 1;
			int courseID = insertCourse(String.format("%s%02d", deptName, i + 1), credit, deptName, conn);
			result.add(courseID);
		}
		return result;
	}

	public static void populatePrerequiste(Connection conn) throws SQLException {
		System.out.println("populating pre_requisites");
		for (int i = 1; i <= 10000; i++) {
			insertPrerequiste(i, i, conn);
		}
	}

	public static ArrayList<Integer> populateSection(Connection conn, ArrayList<Integer> instructors,
			ArrayList<Integer> courses) throws SQLException {
		System.out.println("populating sections");
		ArrayList<Integer> result = new ArrayList<>();

		for (int year = 2016; year < 2023; year++) {
			for (int semester = 1; semester < 3; semester++) {
				for (Integer courseId : courses) {
					int insId = instructors.get(random.nextInt(instructors.size()));
					int building = random.nextInt(400) + 1;
					int sectionId = insertSection(semester, year, insId, courseId, building, building, conn);
					result.add(sectionId);
				}
			}
		}
		return result;
	}

	public static void populateTakes(Connection conn, ArrayList<Integer> studentIds, ArrayList<Integer> sectionIds)
			throws SQLException {
		System.out.println("populating takes");
		for (Integer studentId : studentIds) {
			int nCourses = random.nextInt(30) + 6;
			for (int i = 0; i < nCourses; i++) {
				int sectionId = sectionIds.get(random.nextInt(sectionIds.size()));
				double grade = Math.round((random.nextDouble() * 4 + 0.7) * 10) / 10;
				insertTakes(studentId, sectionId, grade, conn);

			}
		}
	}

	public static void populateSectionTime(Connection conn, ArrayList<Integer> sections) throws SQLException {
		System.out.println("populating section time");
		int slot = 1;
		for (Integer sectionId : sections) {
			int nslots = random.nextInt(5) + 1;
			for (int i = 0; i < nslots; i++) {
				slot++;
				insertSectionTime(slot % 10000 + 1, sectionId, conn);
			}
		}
	}

	public static void insertSchema1(Connection connection) throws SQLException {
		deleteTable("section_time", connection);
		deleteTable("takes", connection);
		deleteTable("section", connection);
		deleteTable("pre_requiste", connection);
		deleteTable("course", connection);
		deleteTable("student", connection);
		deleteTable("time_slot", connection);
		deleteTable("classroom", connection);
		deleteTable("instructor", connection);
		deleteTable("department", connection);

		populateClassroom(connection);
		populateTimeSlot(connection);
		populatePrerequiste(connection);
		populateDepartment(connection);
	}

	public static void main(String[] argv) throws ClassNotFoundException, SQLException {

		System.out.println("-------- PostgreSQL JDBC Connection Testing ------------");
		Class.forName("org.postgresql.Driver");
		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/schema1", "postgres", "1111");

		insertSchema1(connection);

		System.out.println("You made it, take control your database now!");
	}
}
