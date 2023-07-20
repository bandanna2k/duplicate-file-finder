package duplicatefilefinder.records;

import java.util.List;

public record HashRecord(String md5, int fileSize, List<String> files)
{
}
