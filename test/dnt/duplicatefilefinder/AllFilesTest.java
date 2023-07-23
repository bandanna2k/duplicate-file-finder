package dnt.duplicatefilefinder;

import duplicatefilefinder.DuplicateFileFinder;
import duplicatefilefinder.config.Config;
import duplicatefilefinder.records.Results;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class AllFilesTest extends TestBase {

    private final DuplicateFileFinder duplicateFileFinder = new DuplicateFileFinder(progressEvents,
            new TestConfigBuilder(path).build());

    @Before
    public void setUp() throws IOException
    {
        writeTestFiles1();
    }

    @Test
    public void shouldReturnResult() {
        Results duplicateFiles = duplicateFileFinder.findDuplicateFiles();
        assertThat(duplicateFiles.files()).isNotEmpty();
        assertThat(duplicateFiles.files().size()).isEqualTo(2);
    }
}
