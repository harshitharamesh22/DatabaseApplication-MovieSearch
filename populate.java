
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author harshitha
 */
public class populate {

    public static void main(String args[]) throws IOException, Exception {
        populate populateDB = new populate();
        populateDB.start();

    }

    private void start() throws Exception {
        Connection connectDB = null;
        connectDB = startDBConnection();
        System.out.println("Connection Successful-" + connectDB);
//    Movies(connectDB);
//    Movie_genres(connectDB);
//    Movie_Directors(connectDB);
//Movie_Actors(connectDB);
//Movie_Countries(connectDB);
//Movie_Locations(connectDB);
//Tags(connectDB);
//Movie_Tags(connectDB);
//User_taggedmovies(connectDB);
//User_taggedmovies_timestamp(connectDB);
//User_ratedmovies(connectDB);
        User_ratedmovies_timestamp(connectDB);
    }

    public static void drop(Connection connectDB) {
        try {
            System.out.println("Start dropping tables");
            String insert_Stm = "";
            List<String> tables = Arrays.asList("Movies", "Movie_genres", "Movie_directors", "Movie_actors", "Movie_countries", "Movie_locations", "Tags", "Movie_tags",
                    "User_taggedmovies", "user_taggedmovies_timestamp", "User_ratedmovies", "user_ratedmovies_timestamp");

            for (String table : tables) {
                insert_Stm = "DROP TABLE " + table;
                PreparedStatement preStm = null;
                try {
                    preStm = connectDB.prepareStatement(insert_Stm);
                    System.out.println("dropping table " + table);
                    preStm.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Error dropping tables");
                    e.printStackTrace();
                    return;
                } finally {
                    if (preStm != null) {
                        try {
                            preStm.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(populate.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

            System.out.println("DROPPED TABLES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection startDBConnection() throws SQLException, ClassNotFoundException {
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String host = "localhost";
        String port = "1521";
        String dbName = "XE";
        String userName = "SYSTEM";
        String password = "asdf1234";
        String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + "/" + dbName;
        return DriverManager.getConnection(dbURL, userName, password);
    }

    private void closeConnection(Connection connectDB) {
        try {
            connectDB.close();
        } catch (SQLException e) {
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }

    /*   Populate movies  */
    public static void Movies(Connection connectDB) {
        FileReader readFileMovies = null;
        BufferedReader bufReadMovies = null;

        String fileNameMovies = "/home/vinaya/Desktop/hw3/movies.dat";
        System.out.println("Filename_1:" + fileNameMovies);
//        /home/vinaya/Downloads/ojdbc7.jar:
        try {
            readFileMovies = new FileReader(fileNameMovies);
            bufReadMovies = new BufferedReader(readFileMovies);
            String currentRow;
            String id;
            String title;
            String imdbID;
            String spanishTitle;
            String imdbPictureURL;
            int year;
            String rtID;
            float rtAllCriticsRating;
            float rtAllCriticsNumReviews;
            float rtAllCriticsNumFresh;
            float rtAllCriticsNumRotten;
            float rtAllCriticsScore;
            float rtTopCriticsRating;
            float rtTopCriticsNumReviews;
            float rtTopCriticsNumFresh;
            float rtTopCriticsNumRotten;
            float rtTopCriticsScore;
            float rtAudienceRating;
            float rtAudienceNumRatings;
            float rtAudienceScore;
            String rtPictureURL;
            String insert_Stm;
            PreparedStatement preStm = null;
            bufReadMovies.readLine();
            while ((currentRow = bufReadMovies.readLine()) != null) {
                String column[] = currentRow.split("\t");
                if (column.length == 21) {
                    id = column[0];
                    title = column[1];
                    imdbID = column[2];
                    spanishTitle = column[3];
                    imdbPictureURL = column[4];
                    year = Integer.parseInt(column[5]);
                    rtID = column[6];
                    rtAllCriticsRating = column[7].equals("\\N") ? 0 : Float.parseFloat(column[7]);
                    rtAllCriticsNumReviews = column[8].equals("\\N") ? 0 : Float.parseFloat(column[8]);
                    rtAllCriticsNumFresh = column[9].equals("\\N") ? 0 : Float.parseFloat(column[9]);
                    rtAllCriticsNumRotten = column[10].equals("\\N") ? 0 : Float.parseFloat(column[10]);
                    rtAllCriticsScore = column[11].equals("\\N") ? 0 : Float.parseFloat(column[11]);
                    rtTopCriticsRating = column[12].equals("\\N") ? 0 : Float.parseFloat(column[12]);
                    rtTopCriticsNumReviews = column[13].equals("\\N") ? 0 : Float.parseFloat(column[13]);
                    rtTopCriticsNumFresh = column[14].equals("\\N") ? 0 : Float.parseFloat(column[14]);
                    rtTopCriticsNumRotten = column[15].equals("\\N") ? 0 : Float.parseFloat(column[15]);
                    rtTopCriticsScore = column[16].equals("\\N") ? 0 : Float.parseFloat(column[16]);
                    rtAudienceRating = column[17].equals("\\N") ? 0 : Float.parseFloat(column[17]);
                    rtAudienceNumRatings = column[18].equals("\\N") ? 0 : Float.parseFloat(column[18]);
                    rtAudienceScore = column[19].equals("\\N") ? 0 : Float.parseFloat(column[19]);
                    rtPictureURL = column[20];
                    insert_Stm = "INSERT INTO Movies (MOVIEID, TITLE, IMDBMOVIEID, SPANISHTITLE, IMDBPICTUREURL, YEAR, "
                            + "ROTMOVIEID, ROTALLCRITICSRATING, ROTALLCRITICSREVIEWSCOUNT, ROTALLCRITICSFRESHSCORE, ROTALLCRITICSROTSCORE,"
                            + "ROTALLCRITICSAVGSCORE, ROTTOPCRITICSRATING, ROTTOPCRITICSREVIEWSCOUNT, ROTTOPCRITICSFRESHSCORE, "
                            + "ROTTOPCRITICSROTSCORE, ROTTOPCRITICSSCORE, ROTAUDIENCERATING, ROTAUDIENCERATINGCOUNT, "
                            + "ROTAUDIENCEAVGSCORE, ROTPICTUREURL) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    try {
                        preStm = connectDB.prepareStatement(insert_Stm);
                        preStm.setString(1, id);
                        preStm.setString(2, title);
                        preStm.setString(3, imdbID);
                        preStm.setString(4, spanishTitle);
                        preStm.setString(5, imdbPictureURL);
                        preStm.setInt(6, year);
                        preStm.setString(7, rtID);
                        preStm.setDouble(8, rtAllCriticsRating);
                        preStm.setDouble(9, rtAllCriticsNumReviews);
                        preStm.setDouble(10, rtAllCriticsNumFresh);
                        preStm.setDouble(11, rtAllCriticsNumRotten);
                        preStm.setDouble(12, rtAllCriticsScore);
                        preStm.setDouble(13, rtTopCriticsRating);
                        preStm.setDouble(14, rtTopCriticsNumReviews);
                        preStm.setDouble(15, rtTopCriticsNumFresh);
                        preStm.setDouble(16, rtTopCriticsNumRotten);
                        preStm.setDouble(17, rtTopCriticsScore);
                        preStm.setDouble(18, rtAudienceRating);
                        preStm.setDouble(19, rtAudienceNumRatings);
                        preStm.setDouble(20, rtAudienceScore);
                        preStm.setString(21, rtPictureURL);
                        preStm.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println("1:TABLE MOVIES: Prepared Statement failed");
                        e.printStackTrace();
                        return;
                    } finally {
                        try {
                            preStm.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(populate.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    System.out.println("1:TABLE MOVIES: Error with columns");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (bufReadMovies != null) {
                    bufReadMovies.close();
                }
                ;
                if (readFileMovies != null) {
                    readFileMovies.close();
                }
            } catch (IOException ex) {
                System.out.println("1:TABLE MOVIES: Insert table failed");

                ex.printStackTrace();
            }
        }

        System.out.println("1:TABLE MOVIES: Successfully Inserted");
    }

// =============================================================================================================================
    public static void Movie_genres(Connection connectDB) {
        BufferedReader bufReadGenres = null;
        FileReader readFileMovieGenres = null;
        String fileNameMovieGenres = "/home/vinaya/Desktop/hw3/movie_genres.dat";

        try {
            readFileMovieGenres = new FileReader(fileNameMovieGenres);
            bufReadGenres = new BufferedReader(readFileMovieGenres);
            String currentRow;
            String id;
            String genres;
            String insert_Stm;
            PreparedStatement preStm = null;
            bufReadGenres.readLine();
            while ((currentRow = bufReadGenres.readLine()) != null) {
                String column[] = currentRow.split("\t");

                id = (column.length > 0) ? column[0] : "";
                genres = (column.length > 1) ? column[1] : "";
                insert_Stm = "INSERT INTO Movie_genres " + "VALUES (" + "'" + id + "','" + genres + "')";
                try {
                    preStm = connectDB.prepareStatement(insert_Stm);
                    preStm.executeUpdate();

                } catch (SQLException e) {
                    System.out.println(" 2:TABLE MOVIE_GENRES: Prepared Statement failed");
                    e.printStackTrace();
                    return;
                } finally {
                    try {
                        preStm.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(populate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (bufReadGenres != null) {
                    bufReadGenres.close();
                }
                ;
                if (readFileMovieGenres != null) {
                    readFileMovieGenres.close();
                }
            } catch (IOException ex) {
                System.out.println("2:TABLE MOVIE_GENRES: Insert table failed");

                ex.printStackTrace();
            }
        }

        System.out.println("2:TABLE MOVIE_GENRES: Successfully Inserted");
    }

// =============================================================================================================================
    public static void Movie_Directors(Connection connectDB) {
        BufferedReader bufReadDirectors = null;
        FileReader readFileDirectors = null;
        String fileNameMovieDirectors = "/home/vinaya/Desktop/hw3/movie_directors.dat";
        String movieID;
        String directorID;
        String directorName;
        try {
            readFileDirectors = new FileReader(fileNameMovieDirectors);
            bufReadDirectors = new BufferedReader(readFileDirectors);
            String currentRow;
            String insert_Stm;
            PreparedStatement preStm = null;
            bufReadDirectors.readLine();
            while ((currentRow = bufReadDirectors.readLine()) != null) {
                String column[] = currentRow.split("\t");

                movieID = (column.length > 0) ? column[0] : "";
                directorID = (column.length > 1) ? column[1] : "";
                directorName = (column.length > 1) ? column[2] : "";

                insert_Stm = "INSERT INTO Movie_directors " + "VALUES (?, ?, ?)";
                try {
                    preStm = connectDB.prepareStatement(insert_Stm);
                    preStm.setString(1, movieID);
                    preStm.setString(2, directorID);
                    preStm.setString(3, directorName);
                    preStm.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("3:TABLE MOVIE_DIRECTORS: Insert table failed");
                    e.printStackTrace();
                    return;
                } finally {
                    if (preStm != null) {
                        try {
                            preStm.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(populate.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReadDirectors != null) {
                    bufReadDirectors.close();
                }
                if (readFileDirectors != null) {
                    readFileDirectors.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("3:TABLE MOVIE_DIRECTORS: Successfully Inserted");

    }

    // =============================================================================================================================
    public static void Movie_Actors(Connection connectDB) {
        BufferedReader bufReadActors = null;
        FileReader readFileActors = null;
        String fileNameMovieActors = "/home/vinaya/Desktop/hw3/movie_actors.dat";
        String movieID;
        String actorID;
        String actorName;
        int ranking;
        try {
            readFileActors = new FileReader(fileNameMovieActors);
            bufReadActors = new BufferedReader(readFileActors);
            String currentRow;
            String insert_Stm;
            PreparedStatement preStm = null;
            bufReadActors.readLine();
            while ((currentRow = bufReadActors.readLine()) != null) {
                String column[] = currentRow.split("\t");

                movieID = (column.length > 0) ? column[0] : "";
                actorID = (column.length > 1) ? column[1] : "";
                actorName = (column.length > 2) ? column[2] : "";
                ranking = Integer.parseInt(column[3]);
                insert_Stm = "INSERT INTO Movie_actors " + "VALUES (?, ?, ?, ?)";
                try {
                    preStm = connectDB.prepareStatement(insert_Stm);
                    preStm.setString(1, movieID);
                    preStm.setString(2, actorID);
                    preStm.setString(3, actorName);
                    preStm.setInt(4, ranking);
                    preStm.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("4:TABLE MOVIE_ACTORS: Insert table failed");
                    e.printStackTrace();
                    return;
                } finally {
                    if (preStm != null) {
                        try {
                            preStm.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(populate.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReadActors != null) {
                    bufReadActors.close();
                }
                if (readFileActors != null) {
                    readFileActors.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("4:TABLE MOVIE_ACTORS: Successfully Inserted");

    }
    // =============================================================================================================================

    public static void Movie_Countries(Connection connectDB) {
        BufferedReader bufReadCountries = null;
        FileReader readFileCountries = null;
        String fileNameCountries = "/home/vinaya/Desktop/hw3/movie_countries.dat";
        String movieID;
        String country;

        try {
            readFileCountries = new FileReader(fileNameCountries);
            bufReadCountries = new BufferedReader(readFileCountries);
            String currentRow;
            String insert_Stm;
            PreparedStatement preStm = null;
            bufReadCountries.readLine();
            while ((currentRow = bufReadCountries.readLine()) != null) {
                String column[] = currentRow.split("\t");

                movieID = (column.length > 0) ? column[0] : "";
                country = (column.length > 1) ? column[1] : "";
                insert_Stm = "INSERT INTO Movie_countries " + "VALUES (?, ?)";
                try {
                    preStm = connectDB.prepareStatement(insert_Stm);
                    preStm.setString(1, movieID);
                    preStm.setString(2, country);
                    preStm.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("5:TABLE MOVIE_COUNTRIES: Insert table failed");
                    e.printStackTrace();
                    return;
                } finally {
                    if (preStm != null) {
                        try {
                            preStm.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(populate.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReadCountries != null) {
                    bufReadCountries.close();
                }
                if (readFileCountries != null) {
                    readFileCountries.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("5:TABLE MOVIE_COUNTRIES: Successfully Inserted");

    }
    // =============================================================================================================================

    public static void Movie_Locations(Connection connectDB) {
        BufferedReader bufReadLocations = null;
        FileReader readFileLocations = null;
        String fileNameLocations = "/home/vinaya/Desktop/hw3/movie_locations.dat";
        String movieID;
        String location1_country;
        String location2_state;
        String location3_city;
        String location4_street;

        try {
            readFileLocations = new FileReader(fileNameLocations);
            bufReadLocations = new BufferedReader(readFileLocations);
            String currentRow;
            String insert_Stm;
            PreparedStatement preStm = null;
            bufReadLocations.readLine();
            while ((currentRow = bufReadLocations.readLine()) != null) {
                String column[] = currentRow.split("\t");

                movieID = (column.length > 0) ? column[0] : "";
                location1_country = (column.length > 1) ? column[1] : "";
                location2_state = (column.length > 2) ? column[2] : "";
                location3_city = (column.length > 3) ? column[3] : "";
                location4_street = (column.length > 4) ? column[4] : "";

                insert_Stm = "INSERT INTO Movie_locations " + "VALUES (?, ?, ?, ?, ?)";
                try {
                    preStm = connectDB.prepareStatement(insert_Stm);
                    preStm.setString(1, movieID);
                    preStm.setString(2, location1_country);
                    preStm.setString(3, location2_state);
                    preStm.setString(4, location3_city);
                    preStm.setString(5, location4_street);

                    preStm.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("6:TABLE MOVIE_lOCATIONS: Insert table failed");
                    e.printStackTrace();
                    return;
                } finally {
                    if (preStm != null) {
                        try {
                            preStm.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(populate.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReadLocations != null) {
                    readFileLocations.close();
                }
                if (bufReadLocations != null) {
                    readFileLocations.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("6:TABLE MOVIE_LOCATIONS: Successfully Inserted");

    }
    // =============================================================================================================================

    public static void Tags(Connection connectDB) {
        BufferedReader bufReadTags = null;
        FileReader readFileTags = null;
        String fileNameTags = "/home/vinaya/Desktop/hw3/tags.dat";
        String tagID;
        String tagText;

        try {
            readFileTags = new FileReader(fileNameTags);
            bufReadTags = new BufferedReader(readFileTags);
            String currentRow;
            String insert_Stm;
            PreparedStatement preStm = null;
            bufReadTags.readLine();
            while ((currentRow = bufReadTags.readLine()) != null) {
                String column[] = currentRow.split("\t");

                tagID = (column.length > 0) ? column[0] : "";
                tagText = (column.length > 1) ? column[1] : "";
                insert_Stm = "INSERT INTO Tags " + "VALUES (?, ?)";
                try {
                    preStm = connectDB.prepareStatement(insert_Stm);
                    preStm.setString(1, tagID);
                    preStm.setString(2, tagText);
                    preStm.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("7:TABLE TAGS: Insert table failed");
                    e.printStackTrace();
                    return;
                } finally {
                    if (preStm != null) {
                        try {
                            preStm.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(populate.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReadTags != null) {
                    bufReadTags.close();
                }
                if (readFileTags != null) {
                    readFileTags.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("7:TABLE TAGS: Successfully Inserted");

    }
// =============================================================================================================================

    public static void Movie_Tags(Connection connectDB) {
        BufferedReader bufReadMovieTags = null;
        FileReader readFileMovieTags = null;
        String fileNameMovieTags = "/home/vinaya/Desktop/hw3/movie_tags.dat";
        String movieID;
        String tagID;
        int tagWeight;

        try {
            readFileMovieTags = new FileReader(fileNameMovieTags);
            bufReadMovieTags = new BufferedReader(readFileMovieTags);
            String currentRow;
            String insert_Stm;
            PreparedStatement preStm = null;
            bufReadMovieTags.readLine();
            while ((currentRow = bufReadMovieTags.readLine()) != null) {
                String column[] = currentRow.split("\t");

                movieID = (column.length > 0) ? column[0] : "";
                tagID = (column.length > 1) ? column[1] : "";
                tagWeight = (column.length > 0) ? Integer.parseInt(column[2]) : 0;

                insert_Stm = "INSERT INTO Movie_tags " + "VALUES (?, ?, ?)";
                try {
                    preStm = connectDB.prepareStatement(insert_Stm);
                    preStm.setString(1, movieID);
                    preStm.setString(2, tagID);
                    preStm.setInt(3, tagWeight);

                    preStm.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("8:TABLE MOVIE_TAGS: Insert table failed");
                    e.printStackTrace();
                    return;
                } finally {
                    if (preStm != null) {
                        try {
                            preStm.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(populate.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReadMovieTags != null) {
                    readFileMovieTags.close();
                }
                if (bufReadMovieTags != null) {
                    readFileMovieTags.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("8:TABLE MOVIE_TAGS: Successfully Inserted");

    }
// =============================================================================================================================

    public static void User_taggedmovies(Connection connectDB) {
        BufferedReader bufReadUserTaggedMovies = null;
        FileReader readFileUserTaggedMovies = null;
        String fileNameUserTaggedMovies = "/home/vinaya/Desktop/hw3/user_taggedmovies.dat";
        String userID;
        String movieID;
        String tagID;
        int date_day;
        int date_month;
        int date_year;
        int date_hour;
        int date_minute;
        int date_second;
        try {
            readFileUserTaggedMovies = new FileReader(fileNameUserTaggedMovies);
            bufReadUserTaggedMovies = new BufferedReader(readFileUserTaggedMovies);
            String currentRow;
            String insert_Stm;
            PreparedStatement preStm = null;
            bufReadUserTaggedMovies.readLine();
            while ((currentRow = bufReadUserTaggedMovies.readLine()) != null) {
                String column[] = currentRow.split("\t");

                userID = (column.length > 0) ? column[0] : "";
                movieID = (column.length > 0) ? column[1] : "";
                tagID = (column.length > 0) ? column[2] : "";
                date_day = (column.length > 0) ? Integer.parseInt(column[3]) : 0;
                date_month = (column.length > 0) ? Integer.parseInt(column[4]) : 0;
                date_year = (column.length > 0) ? Integer.parseInt(column[5]) : 0;
                date_hour = (column.length > 0) ? Integer.parseInt(column[6]) : 0;
                date_minute = (column.length > 0) ? Integer.parseInt(column[7]) : 0;
                date_second = (column.length > 0) ? Integer.parseInt(column[8]) : 0;

                insert_Stm = "INSERT INTO User_taggedmovies " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try {
                    preStm = connectDB.prepareStatement(insert_Stm);
                    preStm.setString(1, userID);
                    preStm.setString(2, movieID);
                    preStm.setString(3, tagID);
                    preStm.setString(4, Integer.toString(date_day));
                    preStm.setString(5, Integer.toString(date_month));
                    preStm.setString(6, Integer.toString(date_year));
                    preStm.setString(7, Integer.toString(date_hour));
                    preStm.setString(8, Integer.toString(date_minute));
                    preStm.setString(9, Integer.toString(date_second));
                    preStm.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("9:TABLE User_taggedmovies: Insert table failed");
                    e.printStackTrace();
                    return;
                } finally {
                    if (preStm != null) {
                        try {
                            preStm.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(populate.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReadUserTaggedMovies != null) {
                    bufReadUserTaggedMovies.close();
                }
                if (readFileUserTaggedMovies != null) {
                    readFileUserTaggedMovies.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("9:TABLE User_taggedmovies: Successfully Inserted");

    }
// =============================================================================================================================

    public static void User_taggedmovies_timestamp(Connection connectDB) {
        BufferedReader bufReadUserTaggedMoviesTimestamp = null;
        FileReader readFileUserTaggedMoviesTImestamp = null;
        String fileNameUserTaggedMoviesTimestamp = "/home/vinaya/Desktop/hw3/user_taggedmovies-timestamps.dat";
        String userID;
        String movieID;
        String tagID;
        float rating;
        String timeStamp;
        try {
            readFileUserTaggedMoviesTImestamp = new FileReader(fileNameUserTaggedMoviesTimestamp);
            bufReadUserTaggedMoviesTimestamp = new BufferedReader(readFileUserTaggedMoviesTImestamp);
            String currentRow;
            String insert_Stm;
            PreparedStatement preStm = null;
            bufReadUserTaggedMoviesTimestamp.readLine();
            while ((currentRow = bufReadUserTaggedMoviesTimestamp.readLine()) != null) {
                String column[] = currentRow.split("\t");

                userID = (column.length > 0) ? column[0] : "";
                movieID = (column.length > 0) ? column[1] : "";
                tagID = (column.length > 0) ? column[2] : "";
                timeStamp = (column.length > 0) ? column[3] : "";

                insert_Stm = "INSERT INTO user_taggedmovies_timestamp " + "VALUES (?, ?, ?, ?)";
                try {
                    preStm = connectDB.prepareStatement(insert_Stm);
                    preStm.setString(1, userID);
                    preStm.setString(2, movieID);
                    preStm.setString(3, tagID);
                    preStm.setString(4, timeStamp);
                    preStm.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("10:TABLE user_taggedmovies_timestamp: Insert table failed");
                    e.printStackTrace();
                    return;
                } finally {
                    if (preStm != null) {
                        try {
                            preStm.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(populate.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReadUserTaggedMoviesTimestamp != null) {
                    bufReadUserTaggedMoviesTimestamp.close();
                }
                if (readFileUserTaggedMoviesTImestamp != null) {
                    readFileUserTaggedMoviesTImestamp.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("10:TABLE user_taggedmovies_timestamp: Successfully Inserted");

    }
    // =============================================================================================================================

    public static void User_ratedmovies(Connection connectDB) {
        BufferedReader bufReadUserRatedMovies = null;
        FileReader readFileUserRatedMovies = null;
        String fileNameUserRatedMovies = "/home/vinaya/Desktop/hw3/user_ratedmovies.dat";
        String userID;
        String movieID;
        float rating;
        int date_day;
        int date_month;
        int date_year;
        int date_hour;
        int date_minute;
        int date_second;
        try {
            readFileUserRatedMovies = new FileReader(fileNameUserRatedMovies);
            bufReadUserRatedMovies = new BufferedReader(readFileUserRatedMovies);
            String currentRow;
            String insert_Stm;
            PreparedStatement ps = null;
            bufReadUserRatedMovies.readLine();
            while ((currentRow = bufReadUserRatedMovies.readLine()) != null) {
                String column[] = currentRow.split("\t");

                userID = (column.length > 0) ? column[0] : "";
                movieID = (column.length > 0) ? column[1] : "";
                rating = (column.length > 0) ? Float.parseFloat(column[2]) : 0;
                date_day = (column.length > 0) ? Integer.parseInt(column[3]) : 0;
                date_month = (column.length > 0) ? Integer.parseInt(column[4]) : 0;
                date_year = (column.length > 0) ? Integer.parseInt(column[5]) : 0;
                date_hour = (column.length > 0) ? Integer.parseInt(column[6]) : 0;
                date_minute = (column.length > 0) ? Integer.parseInt(column[7]) : 0;
                date_second = (column.length > 0) ? Integer.parseInt(column[8]) : 0;

                insert_Stm = "INSERT INTO User_ratedmovies " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try {
                    ps = connectDB.prepareStatement(insert_Stm);
                    ps.setString(1, userID);
                    ps.setString(2, movieID);
                    ps.setString(3, Float.toString(rating));
                    ps.setString(4, Integer.toString(date_day));
                    ps.setString(5, Integer.toString(date_month));
                    ps.setString(6, Integer.toString(date_year));
                    ps.setString(7, Integer.toString(date_hour));
                    ps.setString(8, Integer.toString(date_minute));
                    ps.setString(9, Integer.toString(date_second));
                    ps.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("11:TABLE user_ratedmovies: Insert table failed");
                    e.printStackTrace();
                    return;
                } finally {
                    if (ps != null) {
                        try {
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(populate.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReadUserRatedMovies != null) {
                    bufReadUserRatedMovies.close();
                }
                if (readFileUserRatedMovies != null) {
                    readFileUserRatedMovies.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("11:TABLE user_ratedmovies: Successfully Inserted");

    }
    // =============================================================================================================================

    public static void User_ratedmovies_timestamp(Connection connectDB) {
        BufferedReader bufReadUserRatedMoviesTimestamp = null;
        FileReader readFileUserRatedMoviesTimestamp = null;
        String fileNameUserRatedMoviesTimestamp = "/home/vinaya/Desktop/hw3/user_ratedmovies-timestamps.dat";
        String userID;
        String movieID;
        float rating;
        String timeStamp;
        try {
            readFileUserRatedMoviesTimestamp = new FileReader(fileNameUserRatedMoviesTimestamp);
            bufReadUserRatedMoviesTimestamp = new BufferedReader(readFileUserRatedMoviesTimestamp);
            String currentRow;
            String insert_Stm;
            PreparedStatement ps = null;
            bufReadUserRatedMoviesTimestamp.readLine();
            while ((currentRow = bufReadUserRatedMoviesTimestamp.readLine()) != null) {
                String column[] = currentRow.split("\t");

                userID = (column.length > 0) ? column[0] : "";
                movieID = (column.length > 0) ? column[1] : "";
                rating = (column.length > 0) ? Float.parseFloat(column[2]) : 0;
                timeStamp = (column.length > 0) ? column[3] : "";

                insert_Stm = "INSERT INTO user_ratedmovies_timestamp " + "VALUES (?, ?, ?, ?)";
                try {
                    ps = connectDB.prepareStatement(insert_Stm);
                    ps.setString(1, userID);
                    ps.setString(2, movieID);
                    ps.setString(3, Float.toString(rating));
                    ps.setString(4, timeStamp);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("12:TABLE user_ratedmovies_timestamp: Insert table failed");
                    e.printStackTrace();
                    return;
                } finally {
                    if (ps != null) {
                        try {
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(populate.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReadUserRatedMoviesTimestamp != null) {
                    bufReadUserRatedMoviesTimestamp.close();
                }
                if (readFileUserRatedMoviesTimestamp != null) {
                    readFileUserRatedMoviesTimestamp.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("12:TABLE user_ratedmovies_timestamp: Successfully Inserted");
    }
    // =============================================================================================================================

}
