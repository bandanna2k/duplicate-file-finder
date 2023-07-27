package dnt.duplicatefilefinder;

import duplicatefilefinder.DuplicateFileFinder;
import duplicatefilefinder.records.Results;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MinCountTest extends TestBase {

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
        int i = 1;
        for (File file : files) {
            try {
                i *= 10;
                Files.writeString(file.toPath(), "image" + i);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void testFilterHigherThanCount() {
        final DuplicateFileFinder duplicateFileFinder = new DuplicateFileFinder(progressEvents,
                new TestConfigBuilder(path)
                        .minFilesFilter(2).build());

        Results duplicateFiles = duplicateFileFinder.findDuplicateFiles();
        assertThat(duplicateFiles.hashes().size()).isEqualTo(0);

        OutputStreamWriter osw = new OutputStreamWriter(System.out);
        duplicateFiles.write(osw);
    }

    @Test
    public void testFilterLowerOrEqualThanCount() {
        final DuplicateFileFinder duplicateFileFinder = new DuplicateFileFinder(progressEvents,
                new TestConfigBuilder(path)
                        .minFilesFilter(1).build());

        Results duplicateFiles = duplicateFileFinder.findDuplicateFiles();
        assertThat(duplicateFiles.hashes().size()).isEqualTo(5);
    }
}
