/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbfinal;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Donovan Colton
 *  
 * 
 * 
 * 
 * Program implements a movie recommender based on imdb and rotten tomatoes ratings
 */
public class DBFinal {

    /**
     * @param args the command line arguments
     */
        
    	private static final String url = "jdbc:mysql://localhost:3306/movieRecommender3000?useSSL=false";
	private static final String user = "root";
	private static final String password = "drc_DB2016";
        
        private static Connection conn;
	private static Statement stmt;
	private static ResultSet rs;
        private static String query;
        
    public static void main(String[] args)throws SQLException, IOException, ClassNotFoundException {
        // opening database connection to MySQL server
        Class.forName("com.mysql.jdbc.Driver");	
        conn = DriverManager.getConnection(url, user, password);
        System.out.println("Database connected successfully");
        
        System.out.println("Movie Recommender 3000");

        // Statement object to execute query
        stmt = conn.createStatement();
        //String query = "select count(*) from EMPLOYEE";
        
        MovieRecommender rec = new MovieRecommender();
        
        // executing SELECT query
//        rs = stmt.executeQuery(query);
//        while (rs.next()) {
//            int count = rs.getInt(1);
//            System.out.println("Total number of employees in the current DB : " + count);
//        }

        //close connection ,stmt and resultset here
        //rs.close();
        stmt.close();
        conn.close();
    }
    
    public static class MovieRecommender{
    
        public MovieRecommender() throws SQLException{
            initDB();
        }
        
        //Method constructs DB
        private void initDB() throws SQLException {
            createMoviesTable();
            createMovieTagsTable();
            createMovieGenresTable();
            createMovieDirectorsTable();
            createMovieActorsTable();
            createMovieCountriesTable();
            createMovieLocationsTable();
            createTagsTable();
            createRatedTimestampTable();
            createRatedMoviesTable();
            createTaggedTimestampTable();
            createTaggedMoviesTable();
            
        }

        private void createMoviesTable(){
            String moviesTable = "create table movies ("
                                    + "id               		int			not null,"
                                    + "title                    	varchar(255),"
                                    + "imdbID				int,"
                                    + "spanishTitle                     varchar(255),"
                                    + "imdbPictureURL                   varchar(255),"
                                    + "year                             int,"
                                    + "rtID				varchar(255),"
                                    + "rtAllCriticsRating		double,"
                                    + "rtAllCriticsNumReviews		int,"
                                    + "rtAllCriticsNumFresh		int,"
                                    + "rtAllCriticsNumRotten		int,"
                                    + "rtAllCriticsScore		int,"
                                    + "rtTopCriticsRating		double,"
                                    + "rtTopCriticsNumReviews		int,"
                                    + "rtTopCriticsNumFresh		int,"
                                    + "rtTopCriticsNumRotten		int,"
                                    + "rtTopCriticsScore		int,"
                                    + "rtAudienceRating			double,"
                                    + "rtAudienceNumRatings		int,"
                                    + "rtAudienceScore			double,"
                                    + "rtPictureURL 			varchar(255),"
                                    + "primary key (id));";
            try {
                stmt.executeUpdate(moviesTable);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        private void createMovieGenresTable() {
            String movieGenresTable = "create table movie_genres ("
                                        + "movieID		int		not null,"
                                        + "genre 		varchar(255),"
                                        + "primary key(movieID, genre));";
            try {
                stmt.executeUpdate(movieGenresTable);
            } catch (SQLException ex) {
            }
        }

        private void createMovieDirectorsTable() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private void createMovieActorsTable() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private void createTagsTable() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private void createRatedTimestampTable() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private void createMovieCountriesTable() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private void createMovieLocationsTable() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private void createRatedMoviesTable() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private void createTaggedTimestampTable() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private void createTaggedMoviesTable() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private void createMovieTagsTable() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        private void populateDB(String table,BufferedReader br) throws IOException, SQLException{
            br.readLine();
            String line = br.readLine();
            String sql;
            while(line != null){
                sql = "insert into"+table+" values (";
//                            + tokens[0] + ", \'" + tokens[1] + "\', " 
//                            + tokens[2] + ", \'" + tokens[3] + "\', \'" 
//                            + tokens[4] + "\', " + tokens[5] + ", \'" 
//                            + tokens[6] + "\', " + tokens[7] + ", " 
//                            + tokens[8] + ", " + tokens[9] + ", " 
//                            + tokens[10] + ", " + tokens[11] + ", " 
//                            + tokens[12] + ", " + tokens[13] + ", " 
//                            + tokens[14] + ", " + tokens[15] + ", " 
//                            + tokens[16] + ", " + tokens[17] + ", " 
//                            + tokens[18] + ", " + tokens[19] + ", \'" 
//                            + tokens[20] + "\');";
            }
                    stmt.executeUpdate(sql);
        }
        
    }
}

