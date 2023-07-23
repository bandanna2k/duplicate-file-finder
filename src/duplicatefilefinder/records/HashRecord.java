package duplicatefilefinder.records;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class HashRecord
{
    private final String md5;
    private final long fileSize;
    private final List<String> files;

    public HashRecord(
            String md5,
            long fileSize
    ) {
        this.md5 = md5;
        this.fileSize = fileSize;
        this.files = new ArrayList<>();
    }

    public void add(String absolutePath) {
        files().add(absolutePath);
    }

    public String md5() {
        return md5;
    }

    public long fileSize() {
        return fileSize;
    }

    public List<String> files() {
        return files;
    }

    @Override
    public String toString() {
        return "HashRecord[" +
                "md5=" + md5 + ", " +
                "fileSize=" + fileSize + ", " +
                "files=" + files + ']';
    }

}
