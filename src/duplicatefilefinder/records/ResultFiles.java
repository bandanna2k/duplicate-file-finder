package duplicatefilefinder.records;

import com.google.gson.Gson;

import java.util.List;

public record ResultFiles(List<HashRecord> hashes)
{
    public String toJson() {
        return new Gson().toJson(this);
    }
}
