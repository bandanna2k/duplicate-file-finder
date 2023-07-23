package duplicatefilefinder.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfigBuilder
{
    private Path outputFile;
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
                case "-r", "--regex" -> regex = args[++i];
                case "-o", "--output-file" -> {
                    File newFile = new File(args[++i]);
                    outputFile = newFile.toPath();
                }
            }
        }

        if(outputFile == null)
        {
            throw new IllegalArgumentException("Output file must be set.");
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
        return new Config(outputFile, minFilesFilter, searchFolder, extensions, regex);
    }
}
