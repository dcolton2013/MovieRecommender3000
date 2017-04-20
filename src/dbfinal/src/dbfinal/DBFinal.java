package dbfinal;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBFinal {


  private static final String url = "jdbc:mysql://localhost:3306/movieRecommender3000?useSSL=false";    
  private static final String user = "root";
  private static final String password = "password";

  private static Connection conn;
  private static Statement stmt;
  private static ResultSet rs;
  private static String query;
  private static BufferedReader br;
  private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args)throws SQLException, IOException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, password);
        System.out.println("Database connected successfully");
        System.out.println("new_recommender");
        // Statement object to execute query
        stmt = conn.createStatement();
        MovieRecommender rec = new MovieRecommender();

        stmt.close();
        conn.close();
    }

    public static class MovieRecommender{

        public MovieRecommender() throws SQLException, IOException{
            createDB();
            System.out.println("\n\n------------------------------\n"
                    + "Database has been created and populated\n"
                    + "------------------------------");
        }

        //Method constructs DB
        private void createDB() throws SQLException, IOException {
            System.out.println("Initializing DB:");
            
            try {
                stmt.executeUpdate("create schema if not exists new_recommender;");
//                stmt.executeUpdate("use new_recommender;");
            } catch (SQLException ex) {
            }
            
            createMoviesTable();
            createMovieTagsTable();
            createMovieGenresTable();
            createMovieDirectorsTable();
            createMovieActorsTable();
            createMovieCountriesTable();
            createMovieLocationsTable();
            createTagsTable();
            createUserRatedTimestampTable();
            createUserRatedMoviesTable();
            createUserTaggedMoviesTimestampTable();
            createUserTaggedMoviesTable();
        }

        private void createMoviesTable(){
            System.out.println("Creating Table: movies...");
            //init table
            String moviesTable = "create table if not exists movies ("
                                    + "id               		int			not null,"
                                    + "title                    	varchar(250),"
                                    + "imdbID				int,"
                                    + "spanishTitle                     varchar(50),"
                                    + "imdbPictureURL                   varchar(250),"
                                    + "year                             int,"
                                    + "rtID				varchar(50),"
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
                                    + "rtPictureURL 			varchar(250),"
                                    + "primary key (id));";
            try {
                stmt.executeUpdate(moviesTable);
//                stmt.executeUpdate(
//                        "LOAD DATA LOCAL INFILE 'C:/Users/nicma/GitRepos/MovieRecommender3000-v1/src/dbfinal/movies.dat' INTO TABLE movies;"
//                );
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
            
            populateDB("movies.dat", "movies");
        }

        private void createMovieGenresTable() {
            System.out.println("Creating Table: movie_genres.....");
            //init
            String movieGenresTable = "create table if not exists movie_genres ( "
                                        + "movieID		int		not null, "
                                        + "genre 		varchar(70), "
                                        + "primary key(movieID, genre));";
            try {
                stmt.executeUpdate(movieGenresTable);
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
            
            populateDB("movie_genres.dat","movie_genres");
        }

        private void createMovieDirectorsTable() {
            System.out.println("Creating Table: movie_directors....");
            String movieDirectorsTable = "create table if not exists movie_directors ("
                                            + "movieID          int		not null,"
                                            + "directorID       varchar(100),"
                                            + "directorName	varchar(100),"
                                            + "primary key (directorID, movieID));";

            try {
                stmt.executeUpdate(movieDirectorsTable);
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            } 
            
            populateDB("movie_directors.dat","movie_directors");

        }

        private void createMovieActorsTable() {
            System.out.println("Creating Table: movie_actors.....");
            String movieActorsTable = "create table if not exists movie_actors ("
                                        + "movieID              int		not null,"
                                        + "actorID 		varchar(100),"
                                        + "actorName            varchar(100),"
                                        + "ranking		int,"
                                        + "primary key (actorID, movieID));";
            try {
                stmt.executeUpdate(movieActorsTable);
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }

            populateDB("movie_actors.dat","movie_actors");
        }

        private void createTagsTable() {
            System.out.println("Creating Table: tags....");
            String tagsTable = "create table if not exists tags ("
                                + "id       int     not null,"
                                + "value    varchar(255),"
                                + "primary key (id));";
            try {
                stmt.executeUpdate(tagsTable);
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
            
            populateDB("tags.dat","tags");
        }

        private void createUserRatedTimestampTable() {
            System.out.println("Creating Table: user_ratedmovies_timestamps....");
            String userRatedMoviesTimestampTable = "create table if not exists user_ratedmovies_timestamps ("
                                                    + "userID           int	not null,"
                                                    + "movieID          int,"
                                                    + "rating  		double,"
                                                    + "timestamp        long,"
                                                    + "primary key (userID, movieID));";
            try {
                stmt.executeUpdate(userRatedMoviesTimestampTable);
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
            
            populateDB("user_ratedmovies-timestamps.dat","user_ratedmovies_timestamps");
        }

        private void createMovieCountriesTable() {

            System.out.println("Creating Table: movie_countries....");
            String movieCountriesTable = "create table if not exists movie_countries ("
                                            + "movieID      int     not null,"
                                            + "country      varchar(50),"
                                            + "primary key (movieID));";
            try {
                stmt.executeUpdate(movieCountriesTable);
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
            
            populateDB("movie_countries.dat","movie_countries");
        }

        private void createMovieLocationsTable() {
            System.out.println("Creating Table: movie_locations....");
            String movieLocationsTable = "create table if not exists movie_locations ("
                                            + "movieID		int		not null,"
                                            + "location1 	varchar(100),"
                                            + "location2	varchar(100),"
                                            + "location3	varchar(100),"
                                            + "location4	varchar(100),"
                                            + "primary key (movieID, location1, location2, location3, location4));";
            try {
                stmt.executeUpdate(movieLocationsTable);
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
            
            populateDB("movie_locations.dat","movie_locations");
        }

        private void createUserRatedMoviesTable() {
            System.out.println("Creating Table: user_ratedmovies....");
            String userRatedMoviesTable = "create table if not exists user_ratedmovies ("
                                                + "userID		int         not null,"
                                                + "movieID		int,"
                                                + "rating		double,"
                                                + "date_day		int,"
                                                + "date_month		int,"
                                                + "date_year		int,"
                                                + "date_hour            int,"
                                                + "date_minute          int,"
                                                + "date_second		int,"
                                                + "primary key (userID, movieID));";
            try {
                stmt.executeUpdate(userRatedMoviesTable);
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
            
            populateDB("user_ratedmovies.dat","user_ratedmovies");
        }

        private void createUserTaggedMoviesTimestampTable() {
            System.out.println("Creating Table: user_taggedmovies_timestamps.... ");
            String userTaggedMoviesTimestampTable = "create table if not exists user_taggedmovies_timestamps ("
                                                + "userID           int	not null,"
                                                    + "movieID          int,"
                                                    + "rating  		double,"
                                                    + "timestamp        long,"
                                                    + "primary key (userID, movieID));";
            try {
                stmt.executeUpdate(userTaggedMoviesTimestampTable);
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
            
            populateDB("user_taggedmovies-timestamps.dat","user_taggedmovies_timestamps");
        }

        private void createUserTaggedMoviesTable() {
            System.out.println("Creating Table: user_taggedmovies.....");
            String userTaggedMoviesTable= "create table if not exists user_taggedmovies ("
                                                + "userID	int     not null,"
                                                + "movieID      int,"
                                                + "tagID        int,"
                                                + "date_day	int,"
                                                + "date_month   int,"
                                                + "date_year    int,"
                                                + "date_hour	int,"
                                                + "date_minute  int,"
                                                + "date_second	int,"
                                                + "primary key (userID, movieID, tagID));";
            try {
                stmt.executeUpdate(userTaggedMoviesTable);
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
            
            populateDB("user_taggedmovies.dat","user_taggedmovies");
        }

        private void createMovieTagsTable() {
            System.out.println("Creating table: movie_tags.....");
            String movieTagsTable = "create table if not exists movie_tags ("
                                        + "movieID      int     not null,"
                                        + "tagID        int,"
                                        + "tagWeight    int,"
                                        + "primary key (movieID, tagID));";
            try {
                stmt.executeUpdate(movieTagsTable);
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
            
            populateDB("movie_tags.dat","movie_tags");

        }
        
        private void populateDB(String fileName, String table) {
            
            String populate = "LOAD DATA LOCAL INFILE 'C:/Users/nicma/GitRepos/MovieRecommender3000-v1/src/dbfinal/" + fileName + "' INTO TABLE " + table + ";";
            
            try {
                stmt.executeUpdate(populate);
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }
}
