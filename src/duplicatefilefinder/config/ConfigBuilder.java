package duplicatefilefinder.config;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

import static common.Utilities.getNewFile;
import static duplicatefilefinder.config.Config.App.*;

public class ConfigBuilder
{
    public Config.App app;

    // Duplicate file finder fields
    private OptionalInt quickHashSize;
    private Path outputFile;
    private int minFilesFilter = 2;
    private Path searchFolder;
    private final List<String> extensions = new ArrayList<>();
    private String regex = ".*";

    // Result processor fields
    private Path inputFile;

    public ConfigBuilder(final String[] args)
    {
        if (args.length > 0 && args[0].equalsIgnoreCase("resultProcessor"))
        {
            app = ResultProcessor;

            String inputFile = args[1];
            File newFile = new File(inputFile);
            if (!newFile.exists())
            {
                throw new RuntimeException("Please choose a input file. " + inputFile);
            }
            this.inputFile = newFile.toPath();
        }
        else
        {
            app = DuplicateFileFinder;

            // Defaults
            outputFile = getNewFile("dff", "json").toPath();
            quickHashSize = OptionalInt.empty();
            searchFolder = new File(System.getProperty("user.dir")).toPath();

            for (int i = 0; i < args.length; i++)
            {
                final String arg = args[i];
                switch (arg)
                {
                    case "-min", "--minFiles" -> minFilesFilter = Integer.parseInt(args[++i]);
                    case "-d", "--directory" -> searchFolder = getSearchFolder(args[++i]);
                    case "-i", "--include" -> extensions.add(args[++i]);
                    case "-r", "--regex" -> regex = args[++i];
                    case "-q", "--quickHashSize" -> quickHashSize = OptionalInt.of(Integer.parseInt(args[++i]));
                    case "-o", "--output-file" ->
                    {
                        File newFile = new File(args[++i]);
                        if (newFile.exists())
                        {
                            throw new RuntimeException("Please choose a new output file.");
                        }
                        outputFile = newFile.toPath();
                    }
                }
            }
        }
    }

    private String usage()
    {
        return """
Usage
  java -jar duplicate-file-finder.jar                           (Runs Duplicate File Finder in the current directory) 
  java -jar duplicate-file-finder.jar -d photos                 (Runs Duplicate File Finder in the given directory) 
  java -jar duplicate-file-finder.jar resultProcessor dff.json  (Runs the result processor on file dff.json)
                """;
    }

    private Path getSearchFolder(String input)
    {
        Path result = Path.of(input);
        if (!result.toFile().exists())
        {
            System.out.println(usage());
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
        return new Config(app, inputFile, outputFile, minFilesFilter, searchFolder, extensions, regex, quickHashSize);
    }
}
