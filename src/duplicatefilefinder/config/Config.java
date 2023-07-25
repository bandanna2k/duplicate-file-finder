package duplicatefilefinder.config;

import java.nio.file.Path;
import java.util.List;

public record Config(
        Path outputFile,
        int minFilesFilter,
        Path searchFolder,
        List<String> extensions,
        String regex, boolean quick)
{
    @Override
    public String toString() {
        return "Config{" +
                "outputFile=" + outputFile +
                ", minFilesFilter=" + minFilesFilter +
                ", searchFolder=" + searchFolder +
                ", extensions=" + extensions +
                ", regex='" + regex + '\'' +
                ", quick=" + quick +
                '}';
    }
}
