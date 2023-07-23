package duplicatefilefinder;

import duplicatefilefinder.config.Config;
import duplicatefilefinder.progress.Progress;
import duplicatefilefinder.progress.ProgressEvents;
import duplicatefilefinder.records.Results;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class DuplicateFileFinder
{
    private final ProgressEvents progressEvents;
    private final Config config;
    private final MessageDigest md5;
    private final Results results;

    public DuplicateFileFinder(ProgressEvents progressEvents, Config config)
    {
        this.progressEvents = progressEvents;
        this.config = config;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
        results = new Results();
    }

    public Results findDuplicateFiles()
    {
        Path root = config.searchFolder();
        FileVisitor<Path> fv = new SimpleFileVisitor<>()
        {
            int fileCount = 0;
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
            {
                if(!canContinue(file))
                {
                    // Filtered out.
                    return FileVisitResult.CONTINUE;
                }

                try
                {
                    byte[] data = Files.readAllBytes(file.toAbsolutePath());
                    byte[] hash = md5.digest(data);
                    String base64 = Base64.getMimeEncoder().encodeToString(hash);

                    results.add(base64, file);

                    progressEvents.onFileComplete(new Progress(++fileCount, file.toString()));

                    return FileVisitResult.CONTINUE;
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        };

        try
        {
            Files.walkFileTree(root, fv);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }

        return results;
    }

    private boolean canContinue(Path file)
    {
        if(config.regex() != null && !file.toString().matches(config.regex()))
        {
            return false;
        }

        if(config.extensions().isEmpty())
        {
            return true;
        }

        boolean canContinue = false;
        for (String extension : config.extensions())
        {
            if(file.toString().endsWith(extension))
            {
                canContinue = true;
                break;
            }
        }
        return canContinue;
    }
}
