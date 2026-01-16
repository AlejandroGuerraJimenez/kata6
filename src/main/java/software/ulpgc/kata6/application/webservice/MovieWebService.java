package software.ulpgc.kata6.application.webservice;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import software.ulpgc.kata6.architecture.Model.Movie;
import software.ulpgc.kata6.architecture.io.Store;
import software.ulpgc.kata6.architecture.viewmodel.HistogramBuilder;

import java.sql.SQLException;
import java.util.function.Function;

public class MovieWebService {
    private final Store store;

    public MovieWebService(Store store) {
        this.store = store;
    }

    public void execute() {
        Javalin.create()
                .get("/histogram", this::handleHistogramRequest)
                .start(8080);
    }

    private void handleHistogramRequest(@NotNull Context context) throws SQLException {
        String type = context.queryParam("type");
        if  (type == null) type = "year";

        var histogram = HistogramBuilder.with(store.movies())
                .title("Movies by " + type)
                .x(type)
                .y("Count")
                .use(selectExtract(type));

        context.result(HistogramSerializer.serialize(histogram));
    }

    private Function<Movie, Integer> selectExtract(String type) {
        return switch (type.toLowerCase()) {
            case "duration" -> Movie::duration;
            default -> Movie::year;
        };
    }
}
