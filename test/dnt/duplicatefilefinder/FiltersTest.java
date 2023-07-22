package dnt.duplicatefilefinder;

import duplicatefilefinder.DuplicateFileFinder;
import duplicatefilefinder.config.Config;
import duplicatefilefinder.records.Results;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class FiltersTest extends TestBase {

    @Before
    public void setUp() throws IOException
    {
        File dir1 = new File(path.toAbsolutePath() + File.separator + "dir1");
        File dir2 = new File(path.toAbsolutePath() + File.separator + "dir2");
        dir1.mkdir();
        dir2.mkdir();

        File[] files = new File[] {
            new File(dir1 + File.separator + "file1a.jpg"),
            new File(dir1 + File.separator + "file1b.png"),
            new File(dir1 + File.separator + "file1c.txt"),
            new File(dir1 + File.separator + "file1d.jpeg"),
            new File(dir1 + File.separator + "file1e.gif")
        };
        Arrays.stream(files).forEach(file -> {
            try {
                Files.writeString(file.toPath(), "image");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void noFilterNoFiles() {
        final DuplicateFileFinder duplicateFileFinder = new DuplicateFileFinder(progressEvents,
                new TestConfigBuilder(path).regex("noFilter").build());

        Results duplicateFiles = duplicateFileFinder.findDuplicateFiles();
        assertThat(duplicateFiles.files().size()).isEqualTo(0);
    }

    @Test
    public void test1Filter() {
        final DuplicateFileFinder duplicateFileFinder = new DuplicateFileFinder(progressEvents,
                new TestConfigBuilder(path).extensions(".jpg").build());

        Results duplicateFiles = duplicateFileFinder.findDuplicateFiles();
        assertThat(duplicateFiles.files().size()).isEqualTo(1);
    }

    @Test
    public void testMultipleFilters() {
        final DuplicateFileFinder duplicateFileFinder = new DuplicateFileFinder(progressEvents,
                new TestConfigBuilder(path).extensions(".png", ".jpg", ".bin").build());

        Results duplicateFiles = duplicateFileFinder.findDuplicateFiles();
        assertThat(duplicateFiles.files().size()).isEqualTo(2);
    }
}
