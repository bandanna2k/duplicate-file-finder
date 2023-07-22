package dnt.duplicatefilefinder;

import duplicatefilefinder.DuplicateFileFinder;
import duplicatefilefinder.config.Config;
import duplicatefilefinder.progress.Progress;
import duplicatefilefinder.progress.ProgressEvents;
import duplicatefilefinder.records.ResultFiles;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class Tests {

    private ProgressEvents progressEvents = new ProgressEvents() {
        @Override
        public void onFileComplete(Progress progress) {
        }
    };
    private final Path path = getTempPath();
    private final DuplicateFileFinder duplicateFileFinder = new DuplicateFileFinder(progressEvents, new Config(2, path));

    private Path getTempPath() {
        try {
            return Files.createTempDirectory(this.getClass().getSimpleName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void shouldReturnResult() {
        ResultFiles duplicateFiles = duplicateFileFinder.findDuplicateFiles();
        assertThat(duplicateFiles.hashes()).isEmpty();
    }

    @Test
    public void shouldReturnJsonResult() {
        ResultFiles duplicateFiles = duplicateFileFinder.findDuplicateFiles();
        String json = duplicateFiles.toJson();
        assertThat(json).isEqualTo("{\"hashes\":[]}");
        assertThat(json).isEqualTo("""
                {"hashes":[]}""");
    }

}
