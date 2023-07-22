package duplicatefilefinder.config;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfigBuilder
{
    private int minFilesFilter = 2;
    private Path searchFolder;
    private final List<String> extensions = new ArrayList<>();
    private String regex = ".*";

    public ConfigBuilder(final String[] args)
    {
        for (int i = 0; i < args.length; i++)
        {
            if (i == 0) {
                searchFolder = getSearchFolder(args[0]);
                continue;
            }

            final String arg = args[i];
            switch (arg) {
                case "-min", "--minFiles" -> minFilesFilter = Integer.parseInt(args[++i]);
                case "-i", "--include" -> extensions.add(args[++i]);
            }
        }
    }

    private static Path getSearchFolder(String input) {
        Path result = Path.of(input);
        if(!result.toFile().exists())
        {
            throw new RuntimeException("Search folder does not exist.");
        }
        return result;
    }

    public ConfigBuilder minFilesFilter(final int minFilesFilter)
    {
        this.minFilesFilter = minFilesFilter;
        return this;
    }

    public Config build()
    {
        return new Config(minFilesFilter, searchFolder, extensions, regex);
    }
}
