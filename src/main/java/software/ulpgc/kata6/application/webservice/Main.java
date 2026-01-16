package software.ulpgc.kata6.application.webservice;

import software.ulpgc.kata6.application.DB.DatabaseRecorder;
import software.ulpgc.kata6.application.DB.DatabaseStore;
import software.ulpgc.kata6.application.MovieDeserializer;
import software.ulpgc.kata6.application.Stream.RemoteStore;
import software.ulpgc.kata6.architecture.Model.Movie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:movies.db");
        checkDatabase(connection);
        System.out.println("Starting Webservice on https://localhost:8080/histogram");
        new MovieWebService(new DatabaseStore(connection)).execute();
    }

    private static void checkDatabase(Connection connection) throws SQLException {
        boolean exists = connection.getMetaData()
                .getTables(null, null, "movies", null)
                .next();
        if(!exists) {
            System.out.println("No database found");
            importIfNeededInto(connection);
        }
    }

    private static void importIfNeededInto(Connection connection) throws SQLException {
        Stream<Movie> movies = new RemoteStore(MovieDeserializer::fromTsv).movies();
        connection.setAutoCommit(false);
        new DatabaseRecorder(connection).put(movies);
        connection.commit();
    }
}
