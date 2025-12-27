package software.ulpgc.kata6.application.DB;

import software.ulpgc.kata6.application.Desktop;
import software.ulpgc.kata6.application.MovieDeserializer;
import software.ulpgc.kata6.application.Stream.RemoteStore;
import software.ulpgc.kata6.architecture.Model.Movie;
import software.ulpgc.kata6.architecture.viewmodel.Histogram;
import software.ulpgc.kata6.architecture.viewmodel.HistogramBuilder;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Stream;

public class Main {
    private static final File database = new File("movies.db");

    static void main(String[] args) throws SQLException{
        try (Connection connection = openConnection()) {
            importMoviesIfRequired(connection);
            Stream<Movie> movies = new DatabaseStore(connection)
                    .movies()
                    .filter(movie -> movie.year() >= 1900)
                    .filter(movie -> movie.year() <= 2000);

            Histogram histogram = HistogramBuilder
                    .with(movies)
                    .title("Movies")
                    .x("Years")
                    .y("Frequency")
                    .legend("Movies per year")
                    .use(Movie::year);
            Desktop.create().display(histogram).setVisible(true);
        }


    }

    private static void importMoviesIfRequired(Connection connection) throws SQLException {
        if(database.length() > 0) return;
        new DatabaseRecorder(connection).put(new RemoteStore(MovieDeserializer::fromTsv).movies());
    }

    private static Connection openConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + database.getAbsolutePath());
        connection.setAutoCommit(false);
        return connection;
    }
}
