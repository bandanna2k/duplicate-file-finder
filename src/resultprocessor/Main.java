package resultprocessor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main
{
    private final File inputFile;

    public Main(File inputFile) {

        this.inputFile = inputFile;
    }

    public static void main(String[] args) {
        File inputFile = new File(args[0]);
        new Main(inputFile).start();
    }

    public void start() {

        try {
            ResultProcessor resultProcessor = new ResultProcessor(
                    new ResultListener());
            FileReader reader = new FileReader(inputFile);
            resultProcessor.process(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
