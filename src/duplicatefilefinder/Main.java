package duplicatefilefinder;

import duplicatefilefinder.config.Config;
import duplicatefilefinder.config.ConfigBuilder;
import duplicatefilefinder.progress.ProgressEventsImpl;
import duplicatefilefinder.records.Results;
import resultprocessor.FileOperationsFileChooser;
import resultprocessor.ResultListener;
import resultprocessor.ResultProcessor;

import java.io.File;
import java.io.FileReader;
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
        switch(config.app())
        {
            case DuplicateFileFinder:
                runDuplicateFileFinder(config);
                break;
            case ResultProcessor:
                runResultProcessor(config);
                break;
        }
    }

    private void runDuplicateFileFinder(final Config config)
    {
        final Results duplicateFiles = new DuplicateFileFinder(new ProgressEventsImpl(), config).findDuplicateFiles();

        try {
            File file = config.outputFile().toFile();
            final FileWriter writer = new FileWriter(file);
            duplicateFiles.write(writer);
        } catch (IOException e) {
            System.out.println("ERROR: Failed to write to file '" + config.outputFile() + "'");
        }
    }

    private void runResultProcessor(final Config config)
    {
        try {
            FileOperationsFileChooser fileOperationsFileChooser = new FileOperationsFileChooser();
            ResultListener listener = new ResultListener(fileOperationsFileChooser);
            ResultProcessor resultProcessor = new ResultProcessor(
                    listener);
            FileReader reader = new FileReader(config.inputFile().toFile());
            resultProcessor.process(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
