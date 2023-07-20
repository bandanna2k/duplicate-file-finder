package duplicatefilefinder;

import duplicatefilefinder.config.Config;
import duplicatefilefinder.config.ConfigBuilder;

public class Main
{
    public static void main(String[] args) {
        Config config = new ConfigBuilder(args).build();
        new DuplicateFileFinder(config).start();
    }
}
