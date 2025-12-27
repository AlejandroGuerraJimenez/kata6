package software.ulpgc.kata6.architecture.io;

import software.ulpgc.kata6.architecture.Model.Movie;

import java.util.stream.Stream;

public interface Recorder {
    public void put(Stream<Movie> movies);
}
