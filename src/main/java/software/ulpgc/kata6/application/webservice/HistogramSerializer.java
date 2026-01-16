package software.ulpgc.kata6.application.webservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import software.ulpgc.kata6.architecture.Model.Movie;
import software.ulpgc.kata6.architecture.viewmodel.Histogram;

public class HistogramSerializer {
    private static final Gson gson = new Gson();
    public static String serialize(Histogram histogram){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Title", histogram.title());
        jsonObject.addProperty("yAxis", histogram.y());
        jsonObject.addProperty("xAxis", histogram.x());
        JsonObject dataNode = new JsonObject();
        for (int key : histogram) {
            dataNode.addProperty(String.valueOf(key), histogram.count(key));
        }
        jsonObject.add("value", dataNode);
        return gson.toJson(jsonObject);
    }
}
