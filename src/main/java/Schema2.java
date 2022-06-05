import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Random;

public class Schema2 {
    static private Random rand = new Random(1000);
    static private String[] relationships = { "child", "parent", "cousin" };
    static private String[] gender = {"F" , "M"};

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

    // CREATE TABLE Employee(Fname CHAR(20), Minit CHAR(10), Lname CHAR(20), ssn INT
    // PRIMARY KEY, Bdate date, address CHAR(20), sex CHARACTER(1), salary INT,
    // Super_snn INT REFERENCES Employee(ssn), dno INT);

    public static long insertEmployee(String Fname, String Minit, String Lname, int ssn, Date Bdate, String address,
            String sex, int salary, Integer superSSN, int dno, Connection conn) {
        String SQL = "INSERT INTO Employee(Fname,Minit,Lname,ssn,Bdate,address,sex,salary,Super_snn,dno) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?);";

        long id = 0;
        try {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, Fname);
            pstmt.setString(2, Minit);
            pstmt.setString(3, Lname);
            pstmt.setInt(4, ssn);
            pstmt.setDate(5, Bdate);
            pstmt.setString(6, address);
            pstmt.setString(7, sex);
            pstmt.setInt(8, salary);
            if (superSSN == null) {
                pstmt.setNull(9, Types.INTEGER);
            } else {
                pstmt.setInt(9, superSSN);
            }
            pstmt.setInt(10, dno);

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    // System.out.println(rs.next());
                    if (rs.next()) {
                        id = rs.getLong(4);
                        pstmt.close();
                        conn.commit();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return id;
    }
    // CREATE TABLE Department(Dname CHAR(20), Dnumber INT PRIMARY KEY, Mgr_snn int
    // REFERENCES employee, Mgr_start_date date );

    public static long insertDepartment(String Dname, int Dnumber, int MgrSSN, Date startDate, Connection conn)
            throws SQLException {
        String SQL = "INSERT INTO Department(Dname,Dnumber,Mgr_snn,Mgr_start_date) "
                + "VALUES(?,?,?,?);";

        long id = 0;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, Dname);
        pstmt.setInt(2, Dnumber);
        pstmt.setInt(3, MgrSSN);
        pstmt.setDate(4, startDate);

        int affectedRows = pstmt.executeUpdate();
        // check the affected rows
        if (affectedRows > 0) {
            // get the ID back
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                // System.out.println(rs.next());
                if (rs.next()) {
                    id = rs.getLong(2);
                    pstmt.close();
                    conn.commit();
                }
            }
        }
        return id;
    }

    // CREATE TABLE Dept_locations(Dnumber integer REFERENCES Department, Dlocation
    // CHAR(20), PRIMARY KEY(Dnumber,Dlocation));
    public static long insertDeptLocations(int Dnumber, String Dlocation, Connection conn) throws SQLException {
        String SQL = "INSERT INTO Dept_locations(Dnumber,Dlocation) "
                + "VALUES(?,?);";

        long id = 0;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(2, Dlocation);
        pstmt.setInt(1, Dnumber);

        int affectedRows = pstmt.executeUpdate();
        // check the affected rows
        if (affectedRows > 0) {
            // get the ID back
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                // System.out.println(rs.next());
                if (rs.next()) {
                    id = rs.getLong(1);
                    pstmt.close();
                    conn.commit();
                }
            }
        }
        return id;
    }

    // CREATE TABLE Project(Pname CHAR(20), Pnumber INT PRIMARY KEY, Plocation
    // CHAR(50), Dnumber INT REFERENCES Department);
    public static long insertProject(String Pname, int Pnumber, String pLocation, int Dnumber, Connection conn)
            throws SQLException {
        String SQL = "INSERT INTO Project(Pname,Pnumber,Plocation,Dnumber) "
                + "VALUES(?,?,?,?);";

        long id = 0;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, Pname);
        pstmt.setInt(2, Pnumber);
        pstmt.setString(3, pLocation);
        pstmt.setInt(4, Dnumber);

        int affectedRows = pstmt.executeUpdate();
        // check the affected rows
        if (affectedRows > 0) {
            // get the ID back
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                // System.out.println(rs.next());
                if (rs.next()) {
                    id = rs.getLong(2);
                    pstmt.close();
                    conn.commit();
                }
            } 
        }
        return id;
    }
    // CREATE TABLE Works_on(Essn int REFERENCES Employee, Pno int REFERENCES
    // Project, Hours int, PRIMARY KEY(Essn,Pno));

    public static boolean insertWorksOn(int Essn, int pNo, int hours, Connection conn) throws SQLException {
        String SQL = "INSERT INTO Works_on(Essn,Pno,Hours) "
                + "VALUES(?,?,?);";

        boolean success = false;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(2, pNo);
        pstmt.setInt(1, Essn);
        pstmt.setInt(3, hours);
        try {
            pstmt.executeUpdate();
            success = true;
        } catch (SQLException e) {
            if (!e.getSQLState().equals("23505")) {
                throw e;
            }
        }
        pstmt.close();
        conn.commit();
        return success;
    }

    // CREATE TABLE Dependent(Essn INT REFERENCES Employee, Dependent_name CHAR(20),
    // sex CHARACTER(1), Bdate date, Relationship CHAR(20), PRIMARY KEY(Essn,
    // Dependent_name));
    public static long insertDependent(int Essn, String dependentName, String sex, Date Bdate, String relationship,
            Connection conn) throws SQLException {
        String SQL = "INSERT INTO Dependent(Essn,Dependent_name,sex,Bdate,Relationship) "
                + "VALUES(?,?,?,?,?);";

        long id = 0;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, Essn);
        pstmt.setString(2, dependentName);
        pstmt.setString(3, sex);
        pstmt.setDate(4, Bdate);
        pstmt.setString(5, relationship);

        int affectedRows = pstmt.executeUpdate();
        // check the affected rows
        if (affectedRows > 0) {
            // get the ID back
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                // System.out.println(rs.next());
                if (rs.next()) {
                    id = rs.getLong(1);
                    pstmt.close();
                    conn.commit();
                }
            } 
        }
        return id;
    }

    /////////////////////////////////////////////// Data Population Methods
    /////////////////////////////////////////////// //////////////////////////////////////////////////////////////
    @SuppressWarnings("deprecation")
    public static void populateEmployee(Connection conn) {
        System.out.println("Populating Employee");
        // inserting Supervisors
        for (int i = 1; i < 4000; i++) {
            String sex = gender[rand.nextInt(gender.length)];
            int random_supervisor_salary = rand.nextInt((60000 - 10000) + 1) + 10000;
            int random_department = rand.nextInt((150 - 1) + 1) + 1;
            insertEmployee("employee" + i, "M" + i, "employee" + i, i, new Date(22, 1, 1999), "address" + i, sex,
                    random_supervisor_salary, null, random_department, conn);
        }

        for (int i = 4000; i <= 16000; i++) {
            String sex = gender[rand.nextInt(gender.length)];
            int random_department = rand.nextInt((150 - 1) + 1) + 1;
            int random_salary;
            if(random_department == 5){
                random_salary = rand.nextInt(20000 + 1) + 10000;
            }
            else{
                random_salary = rand.nextInt((60000 - 10000) + 1) + 10000;
            }
            int superSNN = rand.nextInt(4000) + 1;
            insertEmployee("employee" + i, "M" + i, "employee" + i, i, new Date(22, 1, 1999), "address" + i, sex,
                    random_salary, superSNN, random_department, conn);
        }

    }

    @SuppressWarnings("deprecation")
    public static void populateDepartment(Connection conn) throws SQLException {
        System.out.println("Populating Department");
        for (int i = 1; i <= 150; i++) {
            int random_manager = rand.nextInt((4000 - 1) + 1) + 1;
            insertDepartment("Department" + i, i, random_manager, new Date(1, 1, 1990), conn);
        }
    }

    public static void populateDeptLocations(Connection conn) throws SQLException {
        System.out.println("Populating Dept_Locations");
        for (int i = 1; i <= 150; i++) {
            insertDeptLocations(i, "Location" + i, conn);
        }
    }

    public static void populateProject(Connection conn) throws SQLException {
        System.out.println("Populating Project");
        for (int i = 1; i <= 9200; i++) {
            int depts = rand.nextInt((150 - 1) + 1) + 1;
            insertProject("Project" + i, i, "Location" + depts, depts, conn);
        }
    }

    public static void populateWorksOn(Connection conn) throws SQLException {
        System.out.println("Populating Works_On");
        for(int j = 1; j <= 16000; j++){
            int projects;
            if(j == 1){
                projects = 600;
            }
            else{
                projects = 2;
            }
            for (int i = 1; i <= projects; i++) {
                int randomHours = rand.nextInt((9 - 1) + 1) + 1;
                int randomProject = rand.nextInt(9200) + 1;
                if(!insertWorksOn(j, randomProject, randomHours, conn)){
                    i--;
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static void populateDependent(Connection conn) throws SQLException {
        System.out.println("Populating Dependent");
        for (int i = 1; i <= 700; i++) {
            String sex = gender[rand.nextInt(gender.length)];
            String relation = relationships[rand.nextInt(relationships.length)];
            if(i <= 600){
                insertDependent(i, "employee" + i, sex, new Date(1, 1, 1999), relation, conn);
            }
            else{
                int random_employee = rand.nextInt((16000 - 1) + 1) + 1;
                insertDependent(i, "employee" + random_employee, sex, new Date(1, 1, 1999), relation, conn);
            }
        }
    }

    public static void insertSchema2(Connection connection) throws SQLException {
        deleteTable("employee", connection);
        deleteTable("department", connection);
        deleteTable("dept_locations", connection);
        deleteTable("project", connection);
        deleteTable("works_on", connection);
        deleteTable("dependent", connection);

        populateEmployee(connection);
        populateDepartment(connection);
        populateDeptLocations(connection);
        populateProject(connection);
        populateWorksOn(connection);
        populateDependent(connection);
    }

    public static void main(String[] argv) {

        System.out.println("-------- PostgreSQL "
                + "JDBC Connection Testing ------------");

        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
            return;

        }

        System.out.println("PostgreSQL JDBC Driver Registered!");

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/schema2", "postgres",
                    "1111");
            insertSchema2(connection);

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }
}
