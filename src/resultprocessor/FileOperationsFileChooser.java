package resultprocessor;

import java.io.*;
import java.util.List;

import static common.Utilities.getNewFile;
import static common.Utilities.wrapFile;

public class FileOperationsFileChooser implements ResultListener.FileChooser
{
    private final File output = getNewFile("moves", "sh");
    private final BufferedWriter writer;

    public FileOperationsFileChooser()
    {
        try {
            this.writer = new BufferedWriter(new FileWriter(output));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onFileChosen(String chosenFile, List<String> otherFiles)
    {
        try {
            writer.write("chmod 0444 " + wrapFile(chosenFile));
            writer.newLine();
            writer.flush();

            for (String otherFile : otherFiles) {
                writer.write("mv " + wrapFile(otherFile) + " " + wrapFile("XXX" + otherFile));
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
