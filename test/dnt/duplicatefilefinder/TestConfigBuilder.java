package dnt.duplicatefilefinder;

import duplicatefilefinder.config.Config;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class TestConfigBuilder
{
    private Path outputFile;
    private String regex;
    private final Path path;
    private List<String> extensions = Collections.emptyList();
    private int minFilesFilter;

    public TestConfigBuilder(Path path)
    {
        this.path = path;
    }

    public Config build()
    {
        minFilesFilter = 2;
        return new Config(outputFile, minFilesFilter, path, extensions, regex);
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
}
