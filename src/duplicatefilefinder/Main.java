package duplicatefilefinder;

import duplicatefilefinder.config.Config;
import duplicatefilefinder.config.ConfigBuilder;
import duplicatefilefinder.records.Files;

public class Main
{
    public static void main(String[] args) {
        Config config = new ConfigBuilder(args).build();
        Files duplicateFiles = new DuplicateFileFinder(config).findDuplicateFiles();
        System.out.println(duplicateFiles.toJson());
    }
}
