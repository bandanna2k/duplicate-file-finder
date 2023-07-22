package dnt.duplicatefilefinder;

import duplicatefilefinder.DuplicateFileFinder;
import duplicatefilefinder.records.Results;

import static org.assertj.core.api.Assertions.assertThat;

public class Test extends TestBase
{
    private final DuplicateFileFinder duplicateFileFinder = new DuplicateFileFinder(progressEvents,
            new TestConfigBuilder(path).build());

    @org.junit.Test
    public void shouldReturnResult() {
        Results duplicateFiles = duplicateFileFinder.findDuplicateFiles();
        assertThat(duplicateFiles.hashes()).isEmpty();
    }

    @org.junit.Test
    public void shouldReturnJsonResult() {
        Results duplicateFiles = duplicateFileFinder.findDuplicateFiles();
        String json = duplicateFiles.toJson();
        assertThat(json).startsWith("{\"hashes\":{}}");
        assertThat(json).startsWith("""
                {"hashes":{}}""");
    }
}
