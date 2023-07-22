package duplicatefilefinder;

import duplicatefilefinder.config.Config;
import duplicatefilefinder.progress.Progress;
import duplicatefilefinder.progress.ProgressEvents;
import duplicatefilefinder.records.ResultFiles;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class DuplicateFileFinder
{
    private final ProgressEvents progressEvents;
    private final Config config;

    public DuplicateFileFinder(ProgressEvents progressEvents, Config config)
    {
        this.progressEvents = progressEvents;
        this.config = config;
    }

    public ResultFiles findDuplicateFiles()
    {
        Path root = config.searchFolder();
        FileVisitor<Path> fv = new SimpleFileVisitor<>() {
            int fileCount = 0;
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
            {
                progressEvents.onFileComplete(new Progress(++fileCount, file.toString()));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return FileVisitResult.CONTINUE;
            }
        };

        try
        {
            Files.walkFileTree(root, fv);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return new ResultFiles(new ArrayList<>());
    }
}
