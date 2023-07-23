package dnt.duplicatefilefinder.acceptance;

import dnt.duplicatefilefinder.TestBase;
import dnt.duplicatefilefinder.TestConfigBuilder;
import duplicatefilefinder.Main;
import duplicatefilefinder.config.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceTest extends TestBase
{
    private final Path dff_json = Path.of(path.toAbsolutePath() + File.separator + "dff.json");
    private final Config config = new TestConfigBuilder(path)
            .outputFile(dff_json)
            .build();

    @Before
    public void setUp() throws Exception {
        writeTestFiles1();
    }

    @After
    public void tearDown() throws Exception {
        List<String> strings = Files.readAllLines(dff_json, StandardCharsets.UTF_8);
        strings.forEach(System.out::println);
    }

    @Test
    public void name()
    {
        Main main = new Main();
        main.start(config);
    }
}
