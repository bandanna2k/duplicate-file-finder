package duplicatefilefinder;

import duplicatefilefinder.config.Config;
import duplicatefilefinder.records.Files;

import java.util.ArrayList;

public class DuplicateFileFinder
{
    private final Config config;

    public DuplicateFileFinder(Config config) {

        this.config = config;
    }

    public Files findDuplicateFiles()
    {
        return new Files(new ArrayList<>());
    }
}
