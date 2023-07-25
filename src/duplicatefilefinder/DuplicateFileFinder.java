package duplicatefilefinder;

import duplicatefilefinder.config.Config;
import duplicatefilefinder.progress.ProgressEvents;
import duplicatefilefinder.records.Results;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DuplicateFileFinder
{
    private final Config config;
    private final MessageDigest md5;
    private final Results results;

    public DuplicateFileFinder(ProgressEvents progressEvents, Config config)
    {
        this.config = config;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
        results = new Results(progressEvents);
    }

    public Results findDuplicateFiles()
    {
        Path root = config.searchFolder();
        FileVisitor<Path> fv = new SimpleFileVisitor<>()
        {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
            {
                if(shouldFilterOut(file))
                {
                    return FileVisitResult.CONTINUE;
                }

                //try { Thread.sleep(1000); } catch (InterruptedException e) { throw new RuntimeException(e); }

                try
                {
                    byte[] data = Files.readAllBytes(file.toAbsolutePath());
                    byte[] hash = md5.digest(data);
                    String base64 = Base64.getMimeEncoder().encodeToString(hash);

                    results.add(base64, file);

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

        results.sort();

        return results;
    }

    private boolean shouldFilterOut(Path file)
    {
        DosFileAttributes dfa;
        try {
            dfa = Files.readAttributes(file, DosFileAttributes.class);
            if(dfa.isDirectory() || dfa.isSystem())
            {
                return true;
            }
        } catch (IOException e) {
            // TODO: Could not read file
            return true;
        }

        if(config.regex() != null && !file.toString().matches(config.regex()))
        {
            return true;
        }

        if(config.extensions().isEmpty())
        {
            return false;
        }

        boolean shouldFilterOut = true;
        for (String extension : config.extensions())
        {
            if(file.toString().endsWith(extension))
            {
                shouldFilterOut = false;
                break;
            }
        }
        return shouldFilterOut;
    }
}
