package software.ulpgc.kata6.architecture.viewmodel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Histogram implements Iterable<Integer>{
    private final Map<Integer,Integer> values;
    private final Map<String, String> labels;

    public Histogram(Map<String, String> labels) {
        this.values = new HashMap<>();
        this.labels = labels;
    }


    public void add(Integer bin) {
        values.put(bin, count(bin));
    }

    public int count(Integer bin) {
        return values.getOrDefault(bin, 0) + 1;
    }

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public String title(){
        return labels.getOrDefault("title", "");
    }

    public String x(){
        return labels.getOrDefault("x", "");
    }

    public String y(){
        return labels.getOrDefault("y", "");
    }

    public String legend(){
        return labels.getOrDefault("legend", "");
    }
    @Override
    public Iterator<Integer> iterator() {
        return values.keySet().iterator();
    }
}
