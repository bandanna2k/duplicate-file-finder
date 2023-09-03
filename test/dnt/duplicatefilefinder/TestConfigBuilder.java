package dnt.duplicatefilefinder;

import duplicatefilefinder.config.Config;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;

import static duplicatefilefinder.config.Config.App.DuplicateFileFinder;

public class TestConfigBuilder
{
    private Path outputFile;
    private String regex = ".*";
    private final Path path;
    private List<String> extensions = Collections.emptyList();
    private int minFilesFilter = 1;
    private OptionalInt quickHashSize = OptionalInt.empty();

    public TestConfigBuilder(Path path)
    {
        this.path = path;
    }

    public Config build()
    {
        return new Config(DuplicateFileFinder, null, outputFile, minFilesFilter, path, extensions, regex, quickHashSize);
    }

    public TestConfigBuilder extensions(String... extensions)
    {
        this.extensions = List.of(extensions);
        return this;
    }

    public TestConfigBuilder regex(String regex) {
        this.regex = regex;
        return this;
    }

    public TestConfigBuilder outputFile(Path outputFile)
    {
        this.outputFile = outputFile;
        return this;
    }

    public TestConfigBuilder minFilesFilter(int minFilesFilter) {
        this.minFilesFilter = minFilesFilter;
        return this;
    }

    public TestConfigBuilder quickHashSize(int quickHashSize) {
        this.quickHashSize = OptionalInt.of(quickHashSize);
        return this;
    }
}
