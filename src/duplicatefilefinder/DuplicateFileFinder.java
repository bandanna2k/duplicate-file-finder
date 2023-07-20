package duplicatefilefinder;

import duplicatefilefinder.config.Config;

public class DuplicateFileFinder
{
    private final Config config;

    public DuplicateFileFinder(Config config) {

        this.config = config;
    }

    public void start()
    {
        System.out.println("{ hashes: [] }");
    }
}
