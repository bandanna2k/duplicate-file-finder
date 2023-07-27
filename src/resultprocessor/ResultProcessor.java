package resultprocessor;

import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ResultProcessor
{
    private final Events listener;

    public ResultProcessor(Events listener) {
        this.listener = listener;
    }

    public void process(Reader in) throws IOException {
        JsonReader reader = new JsonReader(in);
        reader.beginObject();
        String name = reader.nextName();
        switch (name)
        {
            case "hashes":
                processHashes(reader);
                break;
            default:
                return;
        }
        reader.endObject();
    }

    private void processHashes(JsonReader reader) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            String md5 = reader.nextName();
            String md5Value = reader.nextString();
            String fileSize = reader.nextName();
            int fileSizeValue = reader.nextInt();
            String files = reader.nextName();
            processFiles(reader);
            reader.endObject();

            listener.onHashRecord(md5Value);
        }
        reader.endArray();
    }

    private void processFiles(JsonReader reader) throws IOException {
        reader.beginArray();
        List<String> files = new ArrayList<>();
        while (reader.hasNext()) {
            String fileName = reader.nextString();
            files.add(fileName);
        }
        listener.onFilesEvent(files);
        reader.endArray();
    }

    interface Events
    {
        void onHashRecord(String md5);
        void onFilesEvent(List<String> files);
    }
}
