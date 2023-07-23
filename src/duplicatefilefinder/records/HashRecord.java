package duplicatefilefinder.records;

import java.util.List;

public record HashRecord(
        String md5,
        long fileSize,
        List<String> files
)
{
    public void add(String absolutePath)
    {
        files().add(absolutePath);
    }
}
