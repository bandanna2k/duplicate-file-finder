package duplicatefilefinder.config;

import java.nio.file.Path;
import java.util.List;
import java.util.OptionalInt;

public record Config(
        Path outputFile,
        int minFilesFilter,
        Path searchFolder,
        List<String> extensions,
        String regex,
        OptionalInt quickHashSize)
{
    @Override
    public String toString() {
        return "Config{" +
                "outputFile=" + outputFile +
                ", minFilesFilter=" + minFilesFilter +
                ", searchFolder=" + searchFolder +
                ", extensions=" + extensions +
                ", regex='" + regex + '\'' +
                ", quickHashSize=" + quickHashSize +
                '}';
    }
}
