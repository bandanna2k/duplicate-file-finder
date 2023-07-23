package dnt.duplicatefilefinder;

import duplicatefilefinder.progress.Progress;
import duplicatefilefinder.progress.ProgressEvents;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    protected void writeTestFiles1() throws IOException {
        File dir1 = new File(path.toAbsolutePath() + File.separator + "dir1");
        File dir2 = new File(path.toAbsolutePath() + File.separator + "dir2");
        dir1.mkdir();
        dir2.mkdir();

        File file1a = new File(dir1 + File.separator + "file1a.txt");
        File file1b = new File(dir1 + File.separator + "file1b.txt");

        Files.write(file1a.toPath(), "data1a".getBytes(StandardCharsets.UTF_8));
        Files.write(file1b.toPath(), "data1b".getBytes(StandardCharsets.UTF_8));
    }
}
