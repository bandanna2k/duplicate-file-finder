package dnt.duplicatefilefinder;

import duplicatefilefinder.DuplicateFileFinder;
import duplicatefilefinder.config.Config;
import duplicatefilefinder.records.Files;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Tests
{

    private final DuplicateFileFinder duplicateFileFinder = new DuplicateFileFinder(new Config(2));

    @Test
    public void shouldReturnResult()
    {
        Files duplicateFiles = duplicateFileFinder.findDuplicateFiles();
        assertThat(duplicateFiles.hashes()).isEmpty();
    }

    @Test
    public void shouldReturnJsonResult()
    {
        Files duplicateFiles = duplicateFileFinder.findDuplicateFiles();
        String json = duplicateFiles.toJson();
        assertThat(json).isEqualTo("{\"hashes\":[]}");
        assertThat(json).isEqualTo("""
                {"hashes":[]}""");
    }
}
