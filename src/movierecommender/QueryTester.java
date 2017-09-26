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
        

        stmt.close();
        conn.close();
    }

    public static void open() throws SQLException{
        
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
    }
    
    public static void close() throws SQLException{
    	//rs.close();
        stmt.close();
        conn.close();
    }
    
    // Query 1
    static String query1(String n)
    {
    	try{
           	open();
   	   	}
   	   	catch(Exception E){
       		E.printStackTrace();
    	}
    	String retval = "\t";

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
                    retval += rsmd.getColumnName(i) + "\t";
                }
            retval += "\n";

            // print out each tuple
            int j = 1;
            while (rs.next()) {
            	retval += " " + j + ".\t";
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) retval += ",  ";
                    String columnValue = rs.getString(i);
                    retval += columnValue;
                }
                retval += "\n";
                j++;
            }

        } catch (SQLException ex){
            System.out.println(ex);
            return retval;
        }

 
        System.out.println("worked");
        try{
        	close();
    	}
    	catch(SQLException E){
    		System.out.println(E);
    	}
        return retval;
    }

    // Query 2
    static String query2(String str)
   {
    	try{
        	open();
    	}
    	catch(Exception E){
    		System.out.println("didnt open");
    	}
   	   String retval = "\t";

       String sql =    "SELECT m.title, m.year, m.rtaudiencescore, rtpictureurl, m.imdbpictureurl, t.value " +
                       "FROM movies m, tags t, user_taggedmovies ut " +
                       "WHERE (m.title LIKE '%" + str + "%') AND (ut.movieid = m.id) AND (ut.tagid = t.id) ";
       try {
           ResultSet rs = stmt.executeQuery(sql);
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnsNumber = rsmd.getColumnCount();

           // print out the column titles separated by tab
           for (int i = 1; i <= columnsNumber; i++) {
                   retval += rsmd.getColumnName(i) + "\t";
               }
           retval += "\n";

           // print out each tuple
           int j = 1;
           while (rs.next()) {
        	   retval += " " + j + ".\t";

               for (int i = 1; i <= columnsNumber; i++) {
                   if (i > 1) retval += ",  ";
                   String columnValue = rs.getString(i);
                   retval += columnValue;
               }
               retval += "\n";
               j++;
           }

       } catch (SQLException ex){
           retval = "Whoops you suck!";
           return retval;
       }
       try{
       	close();
	   }
       catch(Exception E){
    	   System.out.println("didnt close");
	   }
       return retval;
      
   }

   // Query 3
   static String query3(String str, int limit)
   {
	   try{
       	open();
	   	}
	   	catch(Exception E){
	   		System.out.println("didnt open");
	   	}
       String retval = "\t";

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
                   retval += rsmd.getColumnName(i) + "\t";
               }
           retval += "\n";

           // print out each tuple
           int j = 1;
           while (rs.next()) {
        	   retval += " " + j + ".\t";

               for (int i = 1; i <= columnsNumber; i++) {
                   if (i > 1) retval += ",  ";
                   String columnValue = rs.getString(i);
                   retval += columnValue;
               }
               retval += "\n";
               j++;
           }

       } catch (SQLException ex){
           retval = "Whoops you suck!";
           return retval;
       }
       try{
          	close();
   	   }
          catch(Exception E){
       	   System.out.println("didnt close");
   	   }
       return retval;
       
   }

   // Query 4
   static String query4(String str)
   {
	   try{
          	open();
  	   	}
  	   	catch(Exception E){
      		System.out.println("didnt open");
  	   	}	
       String retval = "\t";

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
                   retval += rsmd.getColumnName(i) + "\t";
               }
           retval += "\n";

           // print out each tuple
           int j = 1;
           while (rs.next()) {
        	   retval += " " + j + ".\t";

               for (int i = 1; i <= columnsNumber; i++) {
                   if (i > 1) retval += ",  ";
                   String columnValue = rs.getString(i);
                   retval += columnValue;
               }
               retval += "\n";
               j++;
           }

       } catch (SQLException ex){
           retval = "Whoops you suck!";
           return retval;
       }
       try{
          	close();
   	   }
          catch(Exception E){
       	   System.out.println("didnt close");
   	   }
       return retval;
   }

   // Query 5
   static String query5(String str)
   {
	   try{
          	open();
  	   	}
  	   	catch(Exception E){
      		System.out.println("didnt open");
  	   	}
       String retval = "\t";

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
                   retval += rsmd.getColumnName(i) + "\t";
               }
           retval += "\n";

           // print out each tuple
           int j = 1;
           while (rs.next()) {
        	   retval += " " + j + ".\t";

               for (int i = 1; i <= columnsNumber; i++) {
                   if (i > 1) retval += ",  ";
                   String columnValue = rs.getString(i);
                   retval += columnValue;
               }
               retval += "\n";
               j++;
           }

       } catch (SQLException ex){
           retval = "Whoops you suck!";
           return retval;
       }
       try{
          	close();
   	   }
          catch(Exception E){
       	   System.out.println("didnt close");
   	   }
       return retval;
   }

   // Query 6
   static String query6(String str)
   {
	   try{
          	open();
  	   	}
  	   	catch(Exception E){
      		System.out.println("didnt open");
  	   	}
       String retval = "\t";

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
                   retval += rsmd.getColumnName(i) + "\t";
               }
           retval += "\n";

           // print out each tuple
           int j = 1;
           while (rs.next()) {
        	   retval += " " + j + ".\t";

               for (int i = 1; i <= columnsNumber; i++) {
                   if (i > 1) retval += ",  ";
                   String columnValue = rs.getString(i);
                   retval += columnValue;
               }
               retval += "\n";
               j++;
           }

       } catch (SQLException ex){
           retval = "Whoops you suck!";
           return retval;
       }
       try{
          	close();
   	   }
          catch(Exception E){
       	   System.out.println("didnt close");
   	   }
       return retval;
   }

   // Query 7
   static String query7(String k)
   {
	   try{
          	open();
  	   	}
  	   	catch(Exception E){
      		System.out.println("didnt open");
  	   	}
       String retval = "\t";

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
                   retval += rsmd.getColumnName(i) + "\t";
               }
           retval += "\n";

           // print out each tuple
           int j = 1;
           while (rs.next()) {
        	   retval += " " + j + ".\t";

               for (int i = 1; i <= columnsNumber; i++) {
                   if (i > 1) retval += ",  ";
                   String columnValue = rs.getString(i);
                   retval += columnValue;
               }
               retval += "\n";
               j++;
           }

       } catch (SQLException ex){
           retval = "Whoops you suck!";
           return retval;
       }
       try{
          	close();
   	   }
          catch(Exception E){
       	   System.out.println("didnt close");
   	   }
       return retval;
   }

   // Query 8
   static String query8(String k)
   {
	   try{
          	open();
  	   	}
  	   	catch(Exception E){
      		System.out.println("didnt open");
  	   	}
       String retval = "\t";

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
                   retval += rsmd.getColumnName(i) + "\t";
               }
           retval += "\n";

           // print out each tuple
           int j = 1;
           while (rs.next()) {
        	   retval += " " + j + ".\t";

               for (int i = 1; i <= columnsNumber; i++) {
                   if (i > 1) retval += ",  ";
                   String columnValue = rs.getString(i);
                   retval += columnValue;
               }
               retval += "\n";
               j++;
           }

       } catch (SQLException ex){
           retval = "Whoops you suck!";
           return retval;
       }
       try{
          	close();
   	   }
          catch(Exception E){
       	   System.out.println("didnt close");
   	   }
       return retval;
   }

   // Query 9
   static String query9(String k)
   {
	   try{
          	open();
  	   	}
  	   	catch(Exception E){
      		System.out.println("didnt open");
  	   	}
       String retval = "\t";

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
                   retval += rsmd.getColumnName(i) + "\t";
               }
           retval += "\n";

           // print out each tuple
           int j = 1;
           while (rs.next()) {
        	   retval += " " + j + ".\t";

               for (int i = 1; i <= columnsNumber; i++) {
                   if (i > 1) retval += ",  ";
                   String columnValue = rs.getString(i);
                   retval += columnValue;
               }
               retval += "\n";
               j++;
           }

       } catch (SQLException ex){
           retval = "Whoops you suck!";
           return retval;
       }
       try{
          	close();
   	   }
          catch(Exception E){
       	   System.out.println("didnt close");
   	   }
       return retval;
   }

   // Query 10
   static String query10(String str)
   {
	   try{
          	open();
  	   	}
  	   	catch(Exception E){
      		System.out.println("didnt open");
  	   	}
       String retval = "\t";

       String sql =    "SELECT m.title, t.value " +
                       "FROM movies m, tags t, movie_tags mt " +
                       "WHERE (m.title LIKE '%" + str + "%') AND (mt.movieid = m.id) AND (mt.tagid = t.id) ";

       try {
           ResultSet rs = stmt.executeQuery(sql);
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnsNumber = rsmd.getColumnCount();

           // print out the column titles separated by tab
           for (int i = 1; i <= columnsNumber; i++) {
                   retval += rsmd.getColumnName(i) + "\t";
               }
           retval += "\n";

           // print out each tuple
           int j = 1;
           while (rs.next()) {
        	   retval += " " + j + ".\t";

               for (int i = 1; i <= columnsNumber; i++) {
                   if (i > 1) retval += ",  ";
                   String columnValue = rs.getString(i);
                   retval += columnValue;
               }
               retval += "\n";
               j++;
           }

       } catch (SQLException ex){
           retval = "Whoops you suck!";
           return retval;
       }
       try{
          	close();
   	   }
          catch(Exception E){
       	   System.out.println("didnt close");
   	   }
       return retval;
   }
   
   // Query 11
   static String query11(String str)
   {
	   try{
          	open();
  	   	}
  	   	catch(Exception E){
      		System.out.println("didnt open");
  	   	}
       String retval = "\t";

       String sql =     "SELECT m.title, m.year, m.rtAudienceScore, m.rtPictureURL, m.imdbPictureURL " +
    		   			"FROM movies m, movie_genres g " +
    		   			"WHERE m.id = g.movieID AND g.genre LIKE '%" + str + "%' " +
    		   			"ORDER BY m.rtAudienceScore DESC " +
    		   			"LIMIT 5";
       
       try {
           ResultSet rs = stmt.executeQuery(sql);
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnsNumber = rsmd.getColumnCount();

           // print out the column titles separated by tab
           for (int i = 1; i <= columnsNumber; i++) {
                   retval += rsmd.getColumnName(i) + "\t";
               }
           retval += "\n";

           // print out each tuple
           int j = 1;
           while (rs.next()) {
        	   retval += " " + j + ".\t";

               for (int i = 1; i <= columnsNumber; i++) {
                   if (i > 1) retval += ",  ";
                   String columnValue = rs.getString(i);
                   retval += columnValue;
               }
               retval += "\n";
               j++;
           }

       } catch (SQLException ex){
           retval = "Whoops you suck!";
           return retval;
       }
       try{
          	close();
   	   }
          catch(Exception E){
       	   System.out.println("didnt close");
   	   }
       return retval;
   }
   
   // Query 12
   static String query12(String str)
   {
	   try{
          	open();
  	   	}
  	   	catch(Exception E){
      		System.out.println("didnt open");
  	   	}
       String retval = "\t";

       String sql =     "SELECT m.title, m.year, m.rtAudienceScore, m.rtPictureURL, m.imdbPictureURL " +
    		   			"FROM movies m, movie_directors d " +
    		   			"WHERE m.id = d.movieID AND d.directorName LIKE '%" + str + "%' " +
    		   			"ORDER BY m.rtAudienceScore DESC " +
    		   			"LIMIT 5";
       
       try {
           ResultSet rs = stmt.executeQuery(sql);
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnsNumber = rsmd.getColumnCount();

           // print out the column titles separated by tab
           for (int i = 1; i <= columnsNumber; i++) {
                   retval += rsmd.getColumnName(i) + "\t";
               }
           retval += "\n";

           // print out each tuple
           int j = 1;
           while (rs.next()) {
        	   retval += " " + j + ".\t";

               for (int i = 1; i <= columnsNumber; i++) {
                   if (i > 1) retval += ",  ";
                   String columnValue = rs.getString(i);
                   retval += columnValue;
               }
               retval += "\n";
               j++;
           }

       } catch (SQLException ex){
           retval = "Whoops you suck!";
           return retval;
       }
       try{
          	close();
   	   }
          catch(Exception E){
       	   System.out.println("didnt close");
   	   }
       return retval;
   }
}