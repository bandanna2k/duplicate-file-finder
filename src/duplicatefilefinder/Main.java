package duplicatefilefinder;

import duplicatefilefinder.config.Config;
import duplicatefilefinder.config.ConfigBuilder;
import duplicatefilefinder.progress.ProgressEventsImpl;
import duplicatefilefinder.records.Results;

import java.io.FileWriter;
import java.io.IOException;

public class Main
{
    public static void main(String[] args)
    {
        Config config = new ConfigBuilder(args).build();
        new Main().start(config);
    }

    public void start(Config config)
    {
        System.out.printf("Searching '%s' for files.%n", config.searchFolder());

        Results duplicateFiles = new DuplicateFileFinder(new ProgressEventsImpl(), config).findDuplicateFiles();

        try {
            final FileWriter writer = new FileWriter(config.outputFile().toFile());
            duplicateFiles.write(writer);
        } catch (IOException e) {
            System.out.println("ERROR: Failed to write to file '" + config.outputFile() + "'");
        }
    }
}
