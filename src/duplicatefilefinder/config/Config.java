package duplicatefilefinder.config;

import java.nio.file.Path;
import java.util.List;

public record Config(
        int minFilesFilter,
        Path searchFolder,
        List<String> extensions,
        String regex)
{
}
