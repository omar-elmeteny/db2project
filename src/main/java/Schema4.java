import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Schema4 {
    static private Random rand = new Random(1000);
    static private String[] languages = { "EN", "AR", "FR", "DE", "ES" };
    static private String[] countries = { "US", "EG", "FR", "DE", "GB" };
    static private String[] genres = { "Action", "Comedy", "Drama", "Fantasy", "Horror", "Mystery", "Romance",
            "Thriller", "Western" };
    static private String[] genders = { "M", "F" };

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

    // CREATE TABLE Movie(mov_id INT PRIMARY KEY, mov_title CHAR(50), mov_year INT,
    // mov_time INT, mov_lang CHAR(50), mov_dt_rel date, mov_rel_country CHAR(5));

    public static long insertMovie(int ID, String title, int year, int time, String lang, Date releaseDate,
            String movieCountry, Connection conn) throws SQLException {
        String SQL = "INSERT INTO Movie(mov_id,mov_title,mov_year,mov_time,mov_lang,mov_dt_rel,mov_rel_country) "
                + "VALUES(?,?,?,?,?,?,?);";

        long id = 0;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, ID);
        pstmt.setString(2, title);
        pstmt.setInt(3, year);
        pstmt.setInt(4, time);
        pstmt.setString(5, lang);
        pstmt.setDate(6, releaseDate);
        pstmt.setString(7, movieCountry);

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

    // CREATE TABLE Reviewer(rev_id INT PRIMARY KEY, rev_name CHAR(30));

    public static long insertReviewer(int ID, String name, Connection conn) throws SQLException {
        String SQL = "INSERT INTO Reviewer(rev_id,rev_name) "
                + "VALUES(?,?);";

        long id = 0;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, ID);
        pstmt.setString(2, name);

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

    // CREATE TABLE Genres(gen_id INT PRIMARY KEY, gen_title CHAR(20));
    public static long insertGenres(int ID, String title, Connection conn) throws SQLException {
        String SQL = "INSERT INTO Genres(gen_id,gen_title) "
                + "VALUES(?,?);";

        long id = 0;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, ID);
        pstmt.setString(2, title);

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

    // CREATE TABLE Actor(act_id INT PRIMARY KEY, act_fname CHAR(20), act_lname
    // CHAR(20), act_gender CHAR(1));
    public static long insertActor(int ID, String fName, String lName, String gender, Connection conn)
            throws SQLException {
        String SQL = "INSERT INTO Actor(act_id,act_fname,act_lname,act_gender) "
                + "VALUES(?,?,?,?);";

        long id = 0;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, ID);
        pstmt.setString(2, fName);
        pstmt.setString(3, lName);
        pstmt.setString(4, gender);

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

    // CREATE TABLE Director(dir_id INT PRIMARY KEY, dir_fname CHAR(20), dir_lname
    // CHAR(20));
    public static long insertDirector(int ID, String fName, String lName, Connection conn) throws SQLException {
        String SQL = "INSERT INTO Director(dir_id,dir_fname,dir_lname) "
                + "VALUES(?,?,?);";

        long id = 0;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, ID);
        pstmt.setString(2, fName);
        pstmt.setString(3, lName);

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

    // CREATE TABLE movie_direction(dir_id INT REFERENCES Director, mov_id INT
    // REFERENCES Movie, PRIMARY KEY(dir_id,mov_id));
    public static long insertMovieDirection(int ID, int movieID, Connection conn) throws SQLException {
        String SQL = "INSERT INTO movie_direction(dir_id,mov_id) "
                + "VALUES(?,?);";

        long id = 0;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, ID);
        pstmt.setInt(2, movieID);

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
    // CREATE TABLE movie_cast(act_id INT REFERENCES Actor, mov_id INT REFERENCES
    // Movie, role CHAR(30), PRIMARY KEY(act_id, mov_id));

    public static boolean insertMovieCast(int actorID, int movieID, String role, Connection conn) throws SQLException {
        String SQL = "INSERT INTO movie_cast(act_id,mov_id,role) "
                + "VALUES(?,?,?);";

        boolean success = false;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, actorID);
        pstmt.setInt(2, movieID);
        pstmt.setString(3, role);

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

    // CREATE TABLE movie_genres(mov_id INT REFERENCES Movie, gen_id INT REFERENCES
    // genres, PRIMARY KEY(mov_id,gen_id));
    public static long insertMovieGenres(int movieID, int genreID, Connection conn) throws SQLException {
        String SQL = "INSERT INTO movie_genres(mov_id,gen_id) "
                + "VALUES(?,?);";

        long id = 0;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, movieID);
        pstmt.setInt(2, genreID);

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

    // CREATE TABLE Rating(mov_id INT REFERENCES Movie, rev_id INT REFERENCES
    // Reviewer, rev_stars INT, num_o_ratings INT, PRIMARY KEY(mov_id,rev_id));
    public static long insertRating(int movieID, int reviewID, int stars, int rating, Connection conn)
            throws SQLException {
        String SQL = "INSERT INTO Rating(mov_id,rev_id,rev_stars,num_o_ratings) "
                + "VALUES(?,?,?,?);";

        long id = 0;
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, movieID);
        pstmt.setInt(2, reviewID);
        pstmt.setInt(3, stars);
        pstmt.setInt(4, rating);

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

    ////////////////////////////////////////////////////////// Data Population
    ////////////////////////////////////////////////////////// Methods
    ////////////////////////////////////////////////////////// //////////////////////////////////////////////////////////
    @SuppressWarnings("deprecation")
    public static void populateMovie(Connection conn) throws SQLException {
        System.out.println("Populating Movie");
        for (int i = 1; i <= 20000; i++) {
            int random_time = rand.nextInt((12)) + 1;
            String language = languages[rand.nextInt(languages.length)];
            String country = countries[rand.nextInt(countries.length)];
            insertMovie(i, "Movie" + i, 2000 + i, random_time, language, new Date(22, 1, 1999), country, conn);
        }

        // specified movie #1

        insertMovie(20001, "Annie Hall", 1977, 4, "EN", new Date(20, 4, 1977), "US", conn);

        for (int i = 20002; i <= 80000; i++) {
            int random_time = rand.nextInt((12)) + 1;
            String language = languages[rand.nextInt(languages.length)];
            String country = countries[rand.nextInt(countries.length)];
            insertMovie(i, "Movie" + i, 2000 + i, random_time, language, new Date(22, 1, 1999), country, conn);
        }

        // specified movie #2

        insertMovie(80001, "Eyes Wide Shut", 1999, 7, "EN", new Date(16, 7, 1999), "US", conn);

        for (int i = 80002; i <= 100000; i++) {
            int random_time = rand.nextInt((12)) + 1;
            String language = languages[rand.nextInt(languages.length)];
            String country = countries[rand.nextInt(countries.length)];
            insertMovie(i, "Movie" + i, 2000 + i, random_time, language, new Date(22, 1, 1999), country, conn);
        }
    }

    public static void populateReviewer(Connection conn) throws SQLException {
        System.out.println("Populating Reviewer");
        for (int i = 1; i <= 10000; i++) {
            insertReviewer(i, "Name" + i, conn);
        }
    }

    public static void populateGenres(Connection conn) throws SQLException {
        System.out.println("Populating Genres");
        for (int i = 1; i <= genres.length; i++) {
            insertGenres(i, genres[i - 1], conn);
        }
    }

    public static void populateActor(Connection conn) throws SQLException {
        System.out.println("Populating Actor");
        for (int i = 1; i <= 120000; i++) {
            String gender = genders[rand.nextInt(genders.length)];
            insertActor(i, "Actor" + i, "Actor" + i, gender, conn);
        }
    }

    public static void populateDirector(Connection conn) throws SQLException {
        System.out.println("Populating Director");
        for (int i = 1; i <= 6000; i++) {
            if (i == 3000) {
                insertDirector(i, "Woddy", "Allen", conn); // insert woddy allen in between
            } else {
                insertDirector(i, "Director" + i, "Director" + i, conn);
            }
        }
    }

    public static void populateMovieDirection(Connection conn) throws SQLException {
        System.out.println("Populating Movie Direction");
        for (int i = 1; i <= 350; i++) {
            insertMovieDirection(3000, i, conn);
        }
        for (int i = 351; i <= 5999; i++) {
            insertMovieDirection(i, i, conn);
        }
        insertMovieDirection(6000, 80001, conn);
        insertMovieDirection(5999, 80002, conn);
        insertMovieDirection(5998, 80005, conn);
        insertMovieDirection(5997, 20001, conn);
        insertMovieDirection(5996, 50000, conn);

    }

    public static void populateMovieCast(Connection conn) throws SQLException {
        System.out.println("Populating Movie Cast");
        List<Integer> Movies_Shuffled = new ArrayList<>();
        for (int i = 1; i <= 100000; i++) {
            Movies_Shuffled.add(i); // before shuffle
        }
        Collections.shuffle(Movies_Shuffled, rand); // after shuffle

        for (int j = 1; j <= 1000; j++) {

            for (int i = 1; i <= 100; i++) {
                if (j % 5 == 0) {
                    insertMovieCast(j, 20001, "Role" + i, conn);
                    break;
                } else {
                    insertMovieCast(j, Movies_Shuffled.get(i), "Role" + i, conn);
                }
            }
        }
        for (int j = 1000; j <= 1150; j++) {
            insertMovieCast(j, 80001, "Role" + j, conn);
            insertMovieCast(j, 20001, "Role" + j, conn);
            insertMovieCast(j, 50000, "Role" + j, conn);
        }

        for (int j = 1151; j <= 1174; j++) {
            insertMovieCast(j, 20001, "Role" + j, conn);
        }

        for (int j = 1024; j <= 1124; j++) {
            insertMovieCast(j, 80002, "Role" + j, conn);
            insertMovieCast(j, 80005, "Role" + j, conn);
        }
    }

    public static void populateMovieGenres(Connection conn) throws SQLException {
        System.out.println("Populating Movie Genres");
        for (int i = 1; i <= 100000; i++) {
            int random_genre_id = rand.nextInt(genres.length) + 1;
            insertMovieGenres(i, random_genre_id, conn);
        }
    }

    public static void populateRating(Connection conn) throws SQLException {
        System.out.println("Populating Rating");
        for (int i = 1; i <= 10000; i++) {
            for (int j = 1; j <= 10; j++) {
                int rating_Stars = rand.nextInt(5) + 1;
                int zero_rate = rand.nextInt(10) + 1;
                insertRating(j, i, rating_Stars, zero_rate, conn);
            }
        }
    }

    public static void insertSchema4(Connection connection) throws SQLException {
        deleteTable("movie", connection);
        deleteTable("reviewer", connection);
        deleteTable("genres", connection);
        deleteTable("actor", connection);
        deleteTable("director", connection);
        deleteTable("movie_direction", connection);
        deleteTable("movie_cast", connection);
        deleteTable("movie_genres", connection);
        deleteTable("rating", connection);


        populateMovie(connection);
        populateReviewer(connection);
        populateGenres(connection);
        populateActor(connection);
        populateDirector(connection);
        populateMovieDirection(connection);
        populateMovieCast(connection);
        populateMovieGenres(connection);
        populateRating(connection);
    }

    public static void main(String[] argv) throws SQLException {

        Connection connection = null;

        connection = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/schema4", "postgres",
                "1111");
        insertSchema4(connection);

        System.out.println("You made it, take control your database now!");
    }
}
