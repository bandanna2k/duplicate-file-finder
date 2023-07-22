package duplicatefilefinder.records;

import com.google.gson.Gson;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Results
{
    private final Map<String, List<String>> hashes = new HashMap<>();
    private transient final Map<String, String> files = new HashMap<>();

    public String toJson() {
        return new Gson().toJson(this);
    }

    public void add(String base64, Path file) {

        String absolutePath = file.toFile().getAbsolutePath();
        if(hashes.containsKey(base64))
        {
            List<String> files = hashes.get(base64);
            files.add(absolutePath);
        }
        else
        {
            ArrayList<String> list = new ArrayList<>();
            list.add(absolutePath);
            hashes.put(base64, list);
        }

        files.put(file.toAbsolutePath().toString(), base64);
    }

    public Map<String, List<String>> hashes() {
        return hashes;
    }

    public Map<String, String> files() {
        return files;
    }
}
