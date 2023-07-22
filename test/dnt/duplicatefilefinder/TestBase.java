package dnt.duplicatefilefinder;

import duplicatefilefinder.progress.Progress;
import duplicatefilefinder.progress.ProgressEvents;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestBase
{
    protected static ProgressEvents progressEvents = new ProgressEvents() {
        @Override
        public void onFileComplete(Progress progress) {
            System.out.println(progress);
        }
    };
    protected final Path path = getTempPath();

    protected static Path getTempPath() {
        try {
            return Files.createTempDirectory(AllFilesTest.class.getSimpleName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
