package movierecommender;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class QueryTester {

    private static final String url = "jdbc:mysql://localhost:3306/movieRecommender3000?useSSL=false";
    private static final String user = "root";
    private static final String password = "drc_DB2016";

    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;
    private static String query;
    private static BufferedReader br;
    private static Scanner scan;

    public static void main(String[] args) throws SQLException{
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver error. Exiting....");
            System.exit(0);
        }
        
        conn = DriverManager.getConnection(url, user, password);
        System.out.println("Database connected successfully");
        System.out.println("Schema: movieRecommender3000");
        // Statement object to execute query
        stmt = conn.createStatement();
        

        displayMenu();

        rs.close();
        stmt.close();
        conn.close();
    }

    private static void displayMenu()
    {
        scan = new Scanner(System.in);
        String selection = "0";
        System.out.println();
        while (!selection.equals("quit")){
            String menu = "********** Movie Recommender 3000 **********\n" +
                      "\t1:  Show the top k movies based on scores.\n"+
                      "\t2:  Given movie title, show its info and all tags related to it.\n"+
                      "\t3:  Given genre and k, show top k movies of given genre based on RT scores. \n"+
                      "\t4:  For any director name specified by user, show all the movies directed. \n"+
                      "\t5:  Given an actor, show his movies. \n"+
                      "\t6:  For given tag, show all movies relating to that tag. \n"+
                      "\t7:  Input a limit to see top directors. Must have at least k movies. \n"+
                      "\t8:  Returns the top 10 actors with the highest average scores. Must have at least k movies. \n"+
                      "\t9:  Show timeline of user rating by genre. \n"+
                      "\t10: See all tags for specified movie. \n"+
                      "\tEnter 'quit' to exit\n"+
                      "********************************************";
            System.out.print(menu + "\n\t Selection:");
            selection = scan.nextLine();

           switch(selection) {
               case "1":    System.out.println("\n\nQuery 1");
                            System.out.println("How many top movies would you like to see?");
                            int k = scan.nextInt();
                            query1(k);
                            scan.nextLine();
                            break;
               case "2":    System.out.println("\n\nQuery 2");
                            System.out.println("What movie title?");
                            String str = scan.nextLine();
                            query2(str);
                            break;
               case "3":    System.out.println("Query 3");
                            System.out.println("How many top movies would you like to see?");
                            k = scan.nextInt();
                            scan.nextLine();
                            System.out.println("What genre?");
                            str = scan.nextLine();
                            System.out.println(str);
                            query3(str, k);
                            break;
               case "4":    System.out.println("Query 4");
                            System.out.println("What director?");
                            str = scan.nextLine();
                            query4(str);
                            break;
               case "5":    System.out.println("Query 5");
                            System.out.println("What actor?");
                            str = scan.nextLine();
                            query5(str);
                            break;
               case "6":    System.out.println("Query 6");
                            System.out.println("What movie tag?");
                            str = scan.nextLine();
                            query6(str);
                            break;
               case "7":    System.out.println("Query 7");
                            System.out.println("How many movies do they need?");
                            k = scan.nextInt();
                            scan.nextLine();
                            query7(k);
                            break;
               case "8":    System.out.println("Query 8");
                            System.out.println("How many movies do they need?");
                            k = scan.nextInt();
                            scan.nextLine();
                            query8(k);
                            break;
               case "9":    System.out.println("Query 9");
                            System.out.println("What user id?");
                            k = scan.nextInt();
                            scan.nextLine();
                            query9(k);
                            break;
               case "10":   System.out.println("Query 10");
                            System.out.println("What movie title?");
                            str = scan.nextLine();
                            query10(str);
                            break;
               case "quit": System.out.println("Goodbye!");
                            break;
               default:     System.out.println("Please enter a valid option");
                            break;
           }
        }
    }

    // Query 1
    private static void query1(int n)
    {
        System.out.println("\n\n");

        String sql =    "SELECT m.title, m.year, m.rtAudienceScore, m.rtPictureURL, m.imdbPictureURL " +
                        "FROM movies m " +
                        "ORDER BY rtAudienceScore desc " +
                        "LIMIT " + n;

        try {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            // print out the column titles separated by tab
            for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rsmd.getColumnName(i) + "\t");
                }
            System.out.println();

            // print out each tuple
            while (rs.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println();
            }

        } catch (SQLException ex){
            System.out.println("Whoops you suck!");
        }

        System.out.println("\n\n");
    }

    // Query 2
    private static void query2(String str)
    {
        System.out.println("\n\n");

        String sql =    "SELECT m.title, m.year, m.rtaudiencescore, rtpictureurl, m.imdbpictureurl, t.value " +
                        "FROM movies m, tags t, user_taggedmovies ut " +
                        "WHERE (m.title LIKE '%" + str + "%') AND (ut.movieid = m.id) AND (ut.tagid = t.id) ";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            // print out the column titles separated by tab
            for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rsmd.getColumnName(i) + "\t");
                }
            System.out.println();

            // print out each tuple
            while (rs.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println();
            }

        } catch (SQLException ex){
            System.out.println("Whoops you suck!");
        }
        System.out.println("\n\n");
    }

    // Query 3
    private static void query3(String str, int limit)
    {
        System.out.println("\n\n");

        String sql =    "SELECT m.title, m.year, m.rtaudiencescore, rtpictureurl, m.imdbpictureurl, g.genre " +
                        "FROM movies m, movie_genres g " +
                        "WHERE g.movieid = m.id AND (g.genre LIKE '%" + str + "%') " +
                        "ORDER BY m.rtaudiencescore desc " +
                        "LIMIT " + limit;

        try {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            // print out the column titles separated by tab
            for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rsmd.getColumnName(i) + "\t");
                }
            System.out.println();

            // print out each tuple
            while (rs.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println();
            }

        } catch (SQLException ex){
            System.out.println("Whoops you suck!");
        }

        System.out.println("\n\n");
    }

    // Query 4
    private static void query4(String str)
    {
        System.out.println("\n\n");

        String sql =    "SELECT DISTINCT m.title, m.year, m.rtaudiencescore, rtpictureurl, m.imdbpictureurl, d.directorname " +
                        "FROM movies m, movie_directors d " +
                        "WHERE (d.movieid = m.id) AND (d.directorname LIKE '%" + str + "%') " +
                        "ORDER BY m.title";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            // print out the column titles separated by tab
            for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rsmd.getColumnName(i) + "\t");
                }
            System.out.println();

            // print out each tuple
            while (rs.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println();
            }

        } catch (SQLException ex){
            System.out.println("Whoops you suck!");
        }

        System.out.println("\n\n");
    }

    // Query 5
    private static void query5(String str)
    {
        System.out.println("\n\n");

        String sql =    "SELECT DISTINCT m.title, m.year, m.rtaudiencescore, rtpictureurl, m.imdbpictureurl, a.actorname " +
                        "FROM movies m, movie_actors a " +
                        "WHERE (m.id = a.movieid) AND (a.actorname LIKE '%" + str + "%') " +
                        "ORDER BY m.title";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            // print out the column titles separated by tab
            for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rsmd.getColumnName(i) + "\t");
                }
            System.out.println();

            // print out each tuple
            while (rs.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println();
            }

        } catch (SQLException ex){
            System.out.println("Whoops you suck!");
        }

        System.out.println("\n\n");
    }

    // Query 6
    private static void query6(String str)
    {
        System.out.println("\n\n");

        String sql =    "SELECT DISTINCT m.title, m.year, m.rtaudiencescore, rtpictureurl, m.imdbpictureurl, t.value " +
                        "FROM movies m, movie_tags mt, tags t " +
                        "WHERE (m.id = mt.movieid) AND (mt.tagid = t.id) AND (t.value LIKE '%" + str + "%') " +
                        "ORDER BY m.rtaudiencescore DESC " +
                        "LIMIT 10000";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            // print out the column titles separated by tab
            for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rsmd.getColumnName(i) + "\t");
                }
            System.out.println();

            // print out each tuple
            while (rs.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println();
            }

        } catch (SQLException ex){
            System.out.println("Whoops you suck!");
        }

        System.out.println("\n\n");
    }

    // Query 7
    private static void query7(int k)
    {
        System.out.println("\n\n");

        String sql =    "SELECT DISTINCT directorname, AVG(rtaudiencescore) as average " +
                        "FROM movies, movie_directors " +
                        "WHERE movie_directors.movieid = movies.id " +
                        "GROUP BY directorname " +
                        "HAVING COUNT(*) >= " + k + " " +
                        "ORDER BY average DESC, directorname " +
                        "LIMIT 10";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            // print out the column titles separated by tab
            for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rsmd.getColumnName(i) + "\t");
                }
            System.out.println();

            // print out each tuple
            while (rs.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println();
            }

        } catch (SQLException ex){
            System.out.println("Whoops you suck!");
        }

        System.out.println("\n\n");
    }

    // Query 8
    private static void query8(int k)
    {
        System.out.println("\n\n");

        String sql =    "SELECT DISTINCT actorname, AVG(rtaudiencescore) as average " +
                        "FROM movies, movie_actors " +
                        "WHERE movie_actors.movieid = movies.id " +
                        "GROUP BY actorname " +
                        "HAVING COUNT(*) >= " + k + " " +
                        "ORDER BY average DESC, actorname " +
                        "LIMIT 10";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            // print out the column titles separated by tab
            for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rsmd.getColumnName(i) + "\t");
                }
            System.out.println();

            // print out each tuple
            while (rs.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println();
            }

        } catch (SQLException ex){
            System.out.println("Whoops you suck!");
        }

        System.out.println("\n\n");
    }

    // Query 9
    private static void query9(int k)
    {
        System.out.println("\n\n");

        String sql =    "SELECT u.userid, m.title, u.rating, g.genre " +
                        "FROM user_ratedmovies u, movies m, movie_genres g " +
                        "WHERE (m.id = u.movieid) AND (u.userid = " + k + ") AND (g.movieid = m.id) " +
                        "ORDER BY g.genre, m.title ";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            // print out the column titles separated by tab
            for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rsmd.getColumnName(i) + "\t");
                }
            System.out.println();

            // print out each tuple
            while (rs.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println();
            }

        } catch (SQLException ex){
            System.out.println("Whoops you suck!");
        }

        System.out.println("\n\n");
    }

    // Query 10
    private static void query10(String str)
    {
        System.out.println("\n\n");

        String sql =    "SELECT m.title, t.value " +
                        "FROM movies m, tags t, movie_tags mt " +
                        "WHERE (m.title LIKE '%" + str + "%') AND (mt.movieid = m.id) AND (mt.tagid = t.id) ";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            // print out the column titles separated by tab
            for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rsmd.getColumnName(i) + "\t");
                }
            System.out.println();

            // print out each tuple
            while (rs.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println();
            }

        } catch (SQLException ex){
            System.out.println("Whoops you suck!");
        }
        System.out.println("\n\n");
    }
}
