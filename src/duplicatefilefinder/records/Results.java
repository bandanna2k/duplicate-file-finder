package duplicatefilefinder.records;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.*;

public class Results {
    private transient final Map<String, HashRecord> hashToRecord = new HashMap<>();
    private transient final Map<String, String> files = new HashMap<>();
    private final Set<HashRecord> hashes = new HashSet<>();

    public String toJson() {
        return new Gson().toJson(this);
    }

    public void add(String base64, Path file) {

        String absolutePath = file.toFile().getAbsolutePath();
        HashRecord hashRecord;
        if (hashToRecord.containsKey(base64)) {
            hashRecord = hashToRecord.get(base64);
            hashRecord.add(absolutePath);
        } else {
            hashRecord = new HashRecord(base64, file.toFile().length());
            hashRecord.add(absolutePath);
            hashToRecord.put(base64, hashRecord);
        }

        files.put(file.toAbsolutePath().toString(), base64);
        hashes.add(hashRecord);
    }

    public Collection<HashRecord> hashes() {
        return hashes;
    }

    public Map<String, String> files() {
        return files;
    }

    public void write(Writer writer) {
        JsonWriter jsonWriter = new JsonWriter(writer);
        try {
            jsonWriter.beginObject();
            jsonWriter.name("hashes");

            jsonWriter.beginArray();
            for (HashRecord v : hashes) {

                jsonWriter.beginObject();
                jsonWriter.name("md5").value(v.md5());
                jsonWriter.name("fileSize").value(v.fileSize());
                jsonWriter.name("files");

                jsonWriter.beginArray();
                for (String file : v.files()) {
                    jsonWriter.value(file);
                }
                jsonWriter.endArray();

                jsonWriter.endObject();
                jsonWriter.flush();
            }
            jsonWriter.endArray();

            jsonWriter.endObject();
            jsonWriter.flush();

            writer.write("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
