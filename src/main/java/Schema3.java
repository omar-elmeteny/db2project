import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class Schema3 {

    static private Random random = new Random(1000);
    static private String[] colors = { "white", "red", "green", "blue", "black", "yellow", "orange" , "violet" , "brown" , "cyan"};

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

    // CREATE TABLE Sailors(sid INT PRIMARY KEY, sname CHAR(20), rating INT, age
    // REAL);

    public static int insertSailor(int ID, String Name, int rating, double age, Connection conn) throws SQLException {
        String SQL = "INSERT INTO Sailors(sid,sname,rating,age) "
                + "VALUES(?,?,?,?);";

        int id = 0;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, ID);
        pstmt.setString(2, Name);
        pstmt.setInt(3, rating);
        pstmt.setDouble(4, age);

        int affectedRows = pstmt.executeUpdate();
        // check the affected rows
        if (affectedRows > 0) {
            // get the ID back
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                // System.out.println(rs.next());
                if (rs.next()) {
                    id = rs.getInt(1);
                    pstmt.close();
                    conn.commit();
                }
            }
        }
        return id;
    }

    // CREATE TABLE Boat(bid INT PRIMARY KEY, bname CHAR(20), color CHAR(10));
    public static int insertBoat(int ID, String Name, String color, Connection conn) throws SQLException {
        String SQL = "INSERT INTO Boat(bid,bname,color) "
                + "VALUES(?,?,?);";

        int id = 0;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, ID);
        pstmt.setString(2, Name);
        pstmt.setString(3, color);

        int affectedRows = pstmt.executeUpdate();
        // check the affected rows
        if (affectedRows > 0) {
            // get the ID back
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                // System.out.println(rs.next());
                if (rs.next()) {
                    id = rs.getInt(1);
                    pstmt.close();
                    conn.commit();
                }
            }
        }
        return id;
    }

    // CREATE TABLE Reserves(sid INT REFERENCES Sailors, bid INT REFERENCES Boat,
    // day date, PRIMARY KEY(sid,bid));
    public static boolean insertReserves(int sID, int bID, Date day, Connection conn) throws SQLException {
        String SQL = "INSERT INTO Reserves(sid,bid,day) "
                + "VALUES(?,?,?);";

        boolean success = false;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, sID);
        pstmt.setInt(2, bID);
        pstmt.setDate(3, day);

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

    ///////////////////////////////////////////////////////// Data Population
    ///////////////////////////////////////////////////////// Methods
    ///////////////////////////////////////////////////////// //////////////////////////////////////////////////////////
    public static ArrayList<Integer> populateSailor(Connection conn) throws SQLException {
        System.out.println("Populating sailors");
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 1; i <= 19000; i++) {
            int age = random.nextInt(41) + 20;
            int rating = random.nextInt(10) + 1;
            int sailorID = insertSailor(i, "Sailor" + i, rating, age, conn);
            result.add(sailorID);
        }
        return result;
    }

    public static ArrayList<Integer> populateBoat(Connection conn) throws SQLException {
        System.out.println("Populating boats");
        ArrayList<Integer> result = new ArrayList<>();
        int div = 3000 / colors.length;
        for (int i = 1; i <= 3000; i++) {

            String color = colors[(i / div) % colors.length];
            int boatID = insertBoat(i, "Boat" + i, color, conn);
            result.add(boatID);
        }
        return result;
    }

    @SuppressWarnings("deprecation")
    public static void populateReserves(Connection conn, ArrayList<Integer> boats, ArrayList<Integer> sailors)
            throws SQLException {
        System.out.println("Populating reserves");
        for (int i = 1; i <= 35000; i++) {
            int boatID = i % 50 == 0 ? 103 : boats.get(random.nextInt(boats.size()));
            int sailorID = sailors.get(random.nextInt(sailors.size()));
            if (!insertReserves(sailorID, boatID, new Date(1, 1, 1999), conn)) {
                i--;
            }
            ;
        }
    }

    public static void insertSchema3(Connection connection) throws SQLException {
        deleteTable("reserves", connection);
        deleteTable("sailors", connection);
        deleteTable("boat", connection);
        ArrayList<Integer> sailors = populateSailor(connection);
        ArrayList<Integer> boats = populateBoat(connection);
        populateReserves(connection, boats, sailors);
    }

    public static void main(String[] argv) throws SQLException {

        Connection connection = null;

        connection = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/schema3", "postgres",
                "1");

        insertSchema3(connection);

        System.out.println("You made it, take control your database now!");
    }
}
