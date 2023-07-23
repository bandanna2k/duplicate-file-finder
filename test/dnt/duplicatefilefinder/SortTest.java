package dnt.duplicatefilefinder;

import duplicatefilefinder.DuplicateFileFinder;
import duplicatefilefinder.records.Results;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class SortTest extends TestBase {

    private final DuplicateFileFinder duplicateFileFinder = new DuplicateFileFinder(progressEvents,
            new TestConfigBuilder(path)
                    .minFilesFilter(1)
                    .build());

    @Test
    public void shouldSortOutput()
    {
        writeFiles(1, "1");
        writeFiles(3, "3");
        writeFiles(2, "2");

        Results results = duplicateFileFinder.findDuplicateFiles();

        assertThat(results.files().size()).isEqualTo(6);
        assertThat(results.hashes().size()).isEqualTo(3);

        System.out.println(results.toJson());
    }

    private void writeFiles(int count, String content)
    {
        for (int i = 0; i < count; i++) {
            String pathname = String.format("%s%sfile%d%d.jpg", path.toString(), File.separator, count, i);
            File file = new File(pathname);

            try {
                Files.writeString(file.toPath(), content);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
