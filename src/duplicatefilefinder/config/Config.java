package duplicatefilefinder.config;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.OptionalInt;

public record Config(
        App app,
        Path inputFile,
        Path outputFile,
        int minFilesFilter,
        Path searchFolder,
        List<String> extensions,
        String regex,
        OptionalInt quickHashSize)
{
    public enum App
    {
        DuplicateFileFinder,
        ResultProcessor
    }

    @Override
    public String toString() {
        return "Config{" +
                "app=" + app +
                ", inputFile=" + inputFile +
                ", outputFile=" + outputFile +
                ", minFilesFilter=" + minFilesFilter +
                ", searchFolder=" + searchFolder +
                ", extensions=" + extensions +
                ", regex='" + regex + '\'' +
                ", quickHashSize=" + quickHashSize +
                '}';
    }

}
