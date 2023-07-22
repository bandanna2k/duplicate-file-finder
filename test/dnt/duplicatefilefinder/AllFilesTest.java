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
        File dir1 = new File(path.toAbsolutePath() + File.separator + "dir1");
        File dir2 = new File(path.toAbsolutePath() + File.separator + "dir2");
        dir1.mkdir();
        dir2.mkdir();

        File file1a = new File(dir1 + File.separator + "file1a.txt");
        File file1b = new File(dir1 + File.separator + "file1b.txt");

        Files.write(file1a.toPath(), "data1a".getBytes(StandardCharsets.UTF_8));
        Files.write(file1b.toPath(), "data1b".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void shouldReturnResult() {
        Results duplicateFiles = duplicateFileFinder.findDuplicateFiles();
        assertThat(duplicateFiles.files()).isNotEmpty();
        assertThat(duplicateFiles.files().size()).isEqualTo(2);
    }
}
