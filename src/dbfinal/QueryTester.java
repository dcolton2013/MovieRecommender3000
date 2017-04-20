package dbfinal;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class QueryTester {
    
    private static final String url = "jdbc:mysql://localhost:3306/movieRecommender3000?useSSL=false";
    private static final String user = "root";
    private static final String password = "password";

    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;
    private static String query;
    private static BufferedReader br;
    private static Scanner scan;
    
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException
    {        
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, password);
        System.out.println("Database connected successfully");
        System.out.println("Movie Recommender 3000");
        // Statement object to execute query
        stmt = conn.createStatement();
//        DBFinal.MovieRecommender rec = new DBFinal.MovieRecommender();

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
                      "\t1: Show the top k movies based on scores.\n"+
                      "\t2: Given movie title, show its info and all tags related to it.\n"+
                      "\t3\n"+
                      "\t4\n"+
                      "\t5\n"+
                      "\t6\n"+
                      "\t7\n"+
                      "\t8\n"+
                      "\t9\n"+
                      "\t10\n"+
                      "\tEnter 'quit' to exit\n"+
                      "********************************************";
            System.out.print(menu +" \n\t Selection:");
            selection = scan.nextLine();
            
            
           switch(selection) {
               case "1":    System.out.println("\n\nQuery 1");
                            System.out.println("How many top movies would you like to see?");
                            int k = scan.nextInt();
                            query1(k);
                            break;
               case "2":    System.out.println("\n\nQuery 2");
                            System.out.println("What movie title?");
                            String title = scan.nextLine();
                            query2(title);
                            break;
               case "3":    System.out.println("Query 3");
                            break;
               case "4":    System.out.println("Query 4");
                            break;
               case "5":    System.out.println("Query 5");
                            break;
               case "6":    System.out.println("Query 6");
                            break;
               case "7":    System.out.println("Query 7");
                            break;
               case "8":    System.out.println("Query 8");
                            break;
               case "9":    System.out.println("Query 9");
                            break;
               case "10":   System.out.println("Query 10");
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

        String sql = "SELECT m.title, m.year, m.rtAudienceScore, m.rtPictureURL, m.imdbPictureURL "
                        + "FROM movies m "
                        + "ORDER BY rtAudienceScore desc "
                        + "LIMIT " + n;
        
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
                    System.out.print(columnValue + " ");
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
                    System.out.print(columnValue + " ");
                }
                System.out.println();
            }

        } catch (SQLException ex){
            System.out.println("Whoops you suck!");
        }
        
        System.out.println("\n\n");
    }
    
    

}
