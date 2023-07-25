package duplicatefilefinder.config;

import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.OptionalInt;

public class ConfigBuilder
{
    private OptionalInt quickHashSize;
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
                case "-q", "--quickHashSize" -> quickHashSize = OptionalInt.of(Integer.parseInt(args[++i]));
                case "-o", "--output-file" -> {
                    File newFile = new File(args[++i]);
                    if(newFile.exists())
                    {
                        throw new RuntimeException("Please choose a new output file.");
                    }
                    outputFile = newFile.toPath();
                }
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
        Path outputFile = this.outputFile == null ? getNewFile().toPath() : this.outputFile;
        return new Config(outputFile, minFilesFilter, searchFolder, extensions, regex, quickHashSize);
    }

    private File getNewFile()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        File file = new File(String.format("dff.%s.json", sdf.format(new Date())));
        return file;
    }
}
