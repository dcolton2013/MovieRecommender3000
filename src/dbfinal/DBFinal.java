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
  private static BufferedReader br;


    public static void main(String[] args)throws SQLException, IOException, ClassNotFoundException {
//        String str = "hel'ooo";
//        int strc = str.indexOf("'");
//        System.out.println(str);
//        System.out.println(strc);
//         str = str.substring(0, strc) + "''"+str.substring(4);
//        System.out.println(str);
        // opening database connection to MySQL server
//        br = new BufferedReader(new FileReader("movies.dat"));
//        String[] arr = br.readLine().split("\t");
//        for (String a:arr)
//            System.out.println(a);
//        System.exit(0);

        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, password);
        System.out.println("Database connected successfully");
        System.out.println("Movie Recommender 3000");
        // Statement object to execute query
        stmt = conn.createStatement();
        MovieRecommender rec = new MovieRecommender();

        // executing query
        //rs = stmt.executeQuery(query);
        //close connection ,stmt and resultset here
        //rs.close();
        stmt.close();
        conn.close();
    }

    public static class MovieRecommender{

        public MovieRecommender() throws SQLException, IOException{
            createDB();
        }

        //Method constructs DB
        private void createDB() throws SQLException, IOException {
            System.out.println("Initializing DB:");
            createMoviesTable();
//            createMovieTagsTable();
            createMovieGenresTable();
            createMovieDirectorsTable();
            createMovieActorsTable();
//            createMovieCountriesTable();
            createMovieLocationsTable();
            createTagsTable();
            createUserRatedTimestampTable();
//            createRatedMoviesTable();
//            createTaggedTimestampTable();
//            createTaggedMoviesTable();
            System.out.println("DB created.");

        }

        private void createMoviesTable(){
            System.out.println("Creating Table: movies...");
            //init table
            String moviesTable = "create table movies ("
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
            } catch (SQLException ex) {
                //update movies
                //ex.printStackTrace();
            }

            try {
                br = new BufferedReader(new FileReader("movies.dat"));
            } catch (FileNotFoundException ex) {
            }
            populateDB("movies", br);
//            Query1
//            System.out.println("\n\n");
//            String sql = "select m.title, m.year, m.rtAudienceScore, m.rtPictureURL, m.imdbPictureURL "
//                        + "from movies m "
//                        + "order by rtAudienceScore desc limit " + 30;
//
//            ResultSet rs = stmt.executeQuery(sql);
//            System.out.println("Title" + "\t" + "Year" + "\t" + "RT Audience Score" + "\t" + "RT Pic URL" + "\t" + "Imdb Pic URL");
//            while (rs.next()) {
//                System.out.println(rs.getString("title") + "\t" + rs.getInt("year") + "\t" + rs.getDouble("rtAudienceScore") + "\t" + rs.getString("rtPictureURL") + "\t" + rs.getString("imdbPictureURL"));
//            }
        }

        private void createMovieGenresTable() {
            System.out.println("Creating Table: movie_genres.....");
            //init
            String movieGenresTable = "create table movie_genres ("
                                        + "movieID		int		not null,"
                                        + "genre 		varchar(70),"
                                        + "primary key(movieID, genre));";
            try {
                stmt.executeUpdate(movieGenresTable);
            } catch (SQLException ex) {
                //update movies
            }

            try {
                br = new BufferedReader(new FileReader("movie_genres.dat"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DBFinal.class.getName()).log(Level.SEVERE, null, ex);
            }
            populateDB("movie_genres",br);
        }

        private void createMovieDirectorsTable() {
            System.out.println("Creating Table: movie_directors....");
            String movieDirectorsTable = "create table movie_directors ("
                                            + "movieID          int		not null,"
                                            + "directorID       varchar(100),"
                                            + "directorName	varchar(100),"
                                            + "primary key (directorID, movieID));";

            try {
                stmt.executeUpdate(movieDirectorsTable);
                br = new BufferedReader(new FileReader("movie_directors.dat"));
            } catch (FileNotFoundException ex) {
            } catch (SQLException ex) {
            }

            populateDB("movie_directors",br);
        }

        private void createMovieActorsTable() {
            String movieActorsTable = "create table movie_actors ("
                                        + "movieID              int		not null,"
                                        + "actorID 		varchar(100),"
                                        + "actorName            varchar(100),"
                                        + "ranking		int,"
                                        + "primary key (actorID, movieID));";
            try {
                stmt.executeUpdate(movieActorsTable);
                br = new BufferedReader(new FileReader("movie_actors.dat"));
            } catch (FileNotFoundException ex) {
            } catch (SQLException ex) {
            }

            populateDB("movie_directors",br);
        }

        private void createTagsTable() {
            System.out.println("Creating Table: tags....");
            String tagsTable = "create table tags ("
                                + "id       int     not null,"
                                + "value    varchar(255),"
                                + "primary key (id));";
            try {
                stmt.executeUpdate(tagsTable);
                br = new BufferedReader(new FileReader("tags.dat"));
            } catch (FileNotFoundException ex) {
            } catch (SQLException ex) {
            }
            populateDB("tags",br);
        }

        private void createUserRatedTimestampTable() {
            System.out.println("Creating Table: user_ratedmovies_timestamps....");
            String userRatedMoviesTimestampTable = "create table user_ratedmovies_timestamps ("
                                                    + "userID           int	not null,"
                                                    + "movieID          int,"
                                                    + "rating  		double,"
                                                    + "timestamp        long,"
                                                    + "primary key (userID, movieID));";
            try {
                stmt.executeUpdate(userRatedMoviesTimestampTable);
                br = new BufferedReader(new FileReader("user_ratedmovies-timestamps.dat"));
            } catch (FileNotFoundException ex) {
            } catch (SQLException ex) {
            }
            populateDB("user_ratedmovies_timestamps",br);
        }

        private void createMovieCountriesTable() {
		System.out.println("Createing Table: moive_countries....");
		String movieCountriesTable = "create table move_countries ("
						+ "movieID	int	not null,"
						+ "country	varchar(50),"
						+ "primary key (movieID, country));";
		try{
			stmt.executeUpdate(movieCountriesTable);
			br = new BufferedReader(new FileReader("movie_countres.dat"));
		} catch (FileNotFoundException ex){
		} catch (SQLException ex){
		}
		populateDB("movie_countries", br);
        }

        private void createMovieLocationsTable() {
            System.out.println("Creating Table: movie_locations....");
            String movieLocationsTable = "create table movie_locations ("
                                            + "movieID		int		not null,"
                                            + "location1 	varchar(100),"
                                            + "location2	varchar(100),"
                                            + "location3	varchar(100),"
                                            + "location4	varchar(100),"
                                            + "primary key (movieID, location1, location2, location3, location4));";
            try {
                stmt.executeUpdate(movieLocationsTable);
                br = new BufferedReader(new FileReader("movie_locations.dat"));
            } catch (FileNotFoundException ex) {
            } catch (SQLException ex) {
            }
            populateDB("movie_locations",br);
        }

        private void createUserRatedMoviesTable() {
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

        private void populateDB(String table, BufferedReader br){
            try {
                //////////////////////////////////////////////////
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
                        //System.out.println(sql);
                        try {
                            stmt.executeUpdate(sql);
                        } catch (SQLException ex) {
                            ex.getLocalizedMessage();
                        }
                    }else
                        continue;
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
            }catch (Exception e){
                //Val is not int. cant be parsed
                return false;
            }
        }
    }
}
