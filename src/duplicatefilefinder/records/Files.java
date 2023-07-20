package duplicatefilefinder.records;

import com.google.gson.Gson;

import java.util.List;

public record Files(List<HashRecord> hashes)
{
    public String toJson() {
        return new Gson().toJson(this);
    }
}
