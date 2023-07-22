package duplicatefilefinder.config;

import java.nio.file.Path;

public record Config(
        int minFilesFilter,
        Path searchFolder
)
{
}
