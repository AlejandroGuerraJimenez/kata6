package software.ulpgc.kata6.application;

import software.ulpgc.kata6.architecture.Model.Movie;

public class MovieDeserializer {

    public static Movie fromTsv(String movieString) {
        return fromTsv(movieString.split("\t"));
    }

    private static Movie fromTsv(String[] split) {
        return new Movie(split[2],toInt(split[5]), toInt(split[7]));
    }

    private static int toInt(String s) {
        if(isVoid(s)) return -1;
        return Integer.parseInt(s);
    }

    private static boolean isVoid(String s) {
        return s.equals("\\N");
    }
}
