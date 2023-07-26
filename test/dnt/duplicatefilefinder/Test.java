package dnt.duplicatefilefinder;

import duplicatefilefinder.DuplicateFileFinder;
import duplicatefilefinder.records.HashRecord;
import duplicatefilefinder.records.Results;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

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
    public void shouldReturnEmptyJsonResult() {
        Results duplicateFiles = duplicateFileFinder.findDuplicateFiles();
        String json = duplicateFiles.toJson();
        assertThat(json).startsWith("{\"hashes\":[]}");
        assertThat(json).startsWith("""
                {"hashes":[]}""");
    }


    @org.junit.Test
    public void shouldReturnJsonResult() throws IOException {

        File file1 = new File(path.toAbsolutePath() + File.separator + "file1.bin");
        Files.writeString(file1.toPath(), "image");

        Results duplicateFiles = duplicateFileFinder.findDuplicateFiles();
        String json = duplicateFiles.toJson();
        System.out.println(json);

        assertThat(json).startsWith("""
                {"hashes":[{"md5":"eIBaIhqYjnnvP0LXxb/UGA\\u003d\\u003d","fileSize":5,"files":["/tmp/""");
        assertThat(json).endsWith("""
                /file1.bin"]}]}""");
    }

    @org.junit.Test(timeout=1000)
    public void shouldReturnJsonResultUsingStream() throws IOException {

        File file1 = new File(path.toAbsolutePath() + File.separator + "file1.bin");
        Files.writeString(file1.toPath(), "image");

        Results duplicateFiles = duplicateFileFinder.findDuplicateFiles();

        PipedReader in = new PipedReader();
        PipedWriter out = new PipedWriter(in);

        duplicateFiles.write(out);
        BufferedReader bufferedReader = new BufferedReader(in);
        String json = bufferedReader.readLine();

        assertThat(json).startsWith("""
                {"hashes":[{"md5":"eIBaIhqYjnnvP0LXxb/UGA==","fileSize":5,"files":["/tmp/""");
        assertThat(json).endsWith("""
                /file1.bin"]}]}""");
    }

    @org.junit.Test
    public void testQuickMode() throws IOException {
        File file1001 = new File(path + File.separator + "file1001.speed");
        FileOutputStream fos = new FileOutputStream(file1001);
        for (int i = 0; i < 1001; i++) {
            fos.write(0);
        }
        fos.flush();
        fos.close();

        final DuplicateFileFinder dffFast = new DuplicateFileFinder(progressEvents,
                new TestConfigBuilder(path).extensions(".speed").quickHashSize(1000).build());
        final DuplicateFileFinder dffSlow = new DuplicateFileFinder(progressEvents,
                new TestConfigBuilder(path).extensions(".speed").build());

        Results resultsSlow = dffSlow.findDuplicateFiles();
        Results resultsFast = dffFast.findDuplicateFiles();
        String hashSlow = new ArrayList<>(resultsSlow.hashes()).get(0).md5();
        String hashFast = new ArrayList<>(resultsFast.hashes()).get(0).md5();
        assertThat(hashFast).isNotEqualTo(hashSlow);
    }
}
