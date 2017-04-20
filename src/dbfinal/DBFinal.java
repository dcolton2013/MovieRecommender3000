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
        System.out.println("Movie Recommender 3000");
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
                br = new BufferedReader(new FileReader("movies.dat"));
            } catch (FileNotFoundException | SQLException ex) {
            }
            
            populateDB("movies", br);
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
                br = new BufferedReader(new FileReader("movie_genres.dat"));
            } catch (SQLException | FileNotFoundException ex) {
//                Logger.getLogger(DBFinal.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            populateDB("movie_genres",br);
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
                br = new BufferedReader(new FileReader("movie_directors.dat"));
            } catch (FileNotFoundException | SQLException ex) {
            } 
            
            populateDB("movie_directors", br);

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
                br = new BufferedReader(new FileReader("movie_actors.dat"));
            } catch (FileNotFoundException | SQLException ex) {
            }

            populateDB("movie_actors",br);
        }

        private void createTagsTable() {
            System.out.println("Creating Table: tags....");
            String tagsTable = "create table if not exists tags ("
                                + "id       int     not null,"
                                + "value    varchar(255),"
                                + "primary key (id));";
            try {
                stmt.executeUpdate(tagsTable);
                br = new BufferedReader(new FileReader("tags.dat"));
            } catch (FileNotFoundException | SQLException ex) {
            }
            
            populateDB("tags",br);
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
                br = new BufferedReader(new FileReader("user_ratedmovies-timestamps.dat"));
            } catch (FileNotFoundException | SQLException ex) {
            }
            
            populateDB("user_ratedmovies_timestamps",br);
        }

        private void createMovieCountriesTable() {

            System.out.println("Creating Table: movie_countries....");
            String movieCountriesTable = "create table if not exists movie_countries ("
                                            + "movieID      int     not null,"
                                            + "country      varchar(50),"
                                            + "primary key (movieID));";
            try {
                stmt.executeUpdate(movieCountriesTable);
                br = new BufferedReader(new FileReader("movie_countries.dat"));
            } catch (FileNotFoundException | SQLException ex) {
            }
            
            populateDB("movie_countries",br);
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
                br = new BufferedReader(new FileReader("movie_locations.dat"));
            } catch (FileNotFoundException | SQLException ex) {
            }
            
            populateDB("movie_locations",br);
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
                br = new BufferedReader(new FileReader("user_ratedmovies.dat"));
            } catch (FileNotFoundException | SQLException ex) {
            }
            
            populateDB("user_ratedmovies",br);
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
                br = new BufferedReader(new FileReader("user_taggedmovies-timestamps.dat"));
            } catch (FileNotFoundException | SQLException ex) {
                System.out.println("\n\nNot happening bro: 254");
            }
            
            populateDB("user_taggedmovies_timestamps",br);
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
                br = new BufferedReader(new FileReader("user_taggedmovies.dat"));
            } catch (FileNotFoundException | SQLException ex) {
            }
            
            populateDB("user_taggedmovies",br);
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
                br = new BufferedReader(new FileReader("movie_tags.dat"));
            } catch (FileNotFoundException | SQLException ex) {
            }
            
            populateDB("movie_tags",br);

        }
        
        private void populateDB(String table, BufferedReader br){
            try {
                //holds the current line in br
                String line;
                //holds the sql statement
                String sql;
                //number of attributess for each tuple (splits on any whitespace chars)
                int count = br.readLine().split("\\s+").length;

                //for loop will loop until br line is null
                while ((line = br.readLine()) != null) {
                    //pick up tuple values
                    String[] tuple = line.split("\t");
                    //System.out.println(""+count+ ""+tuple.length);
                    //verify this tuple can be inserted in DB table
                    if (tuple.length == count) {
                        //build sql stmt
                        sql = "insert into " + table +" values (";
                        for (int i=0;i<tuple.length; i++){
                            String val = tuple[i];
                            int index = val.indexOf("'");

                            if (index >= 0)
                                val = val.substring(0,index) + "''" + val.substring(index+1);

                            if (isInt(val))
                                sql += Integer.parseInt(val);
                            else
                                sql += "\'"+val+"\'";

                            if (i != tuple.length - 1)
                                sql += ",";
                        }
                        //finish sql statement
                        sql += ");";
                        System.out.println(sql);
                        try {
                            stmt.executeUpdate(sql);
                        } catch (SQLException ex) {
                            ex.getLocalizedMessage();
                        }
                    }else {
                    }
                }
                br.close();
            //////////////////////////////////////////
            } catch (IOException ex) {
            }
        }

        //method checks whether val is int
        //if false val should be surronded with
        //single quotes when inserted into db
        private boolean isInt(String val) {
            int x;
            try{
                //val is int
                x = Integer.parseInt(val);
                return true;
            }catch (NumberFormatException e){
                //Val is not int. cant be parsed
                return false;
            }
        }
    }
}
