package resultprocessor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static common.Utilities.getNewFile;
import static common.Utilities.wrapFile;

public class FileOperationsFileChooser implements ResultListener.FileChooser
{
    private final BufferedWriter writer;
    private final List<String> chosenFiles = new ArrayList<>();

    public FileOperationsFileChooser()
    {
        try {
            File output = getNewFile("moves", "sh");
            this.writer = new BufferedWriter(new FileWriter(output));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<String> recommendation(List<String> files)
    {
        int maxScore = -1;
        String recommendation = null;

        for (String alreadyChosenFile : chosenFiles) {
            for (String file : files) {
                int score = score(alreadyChosenFile, file);
                if(score > maxScore)
                {
                    maxScore = score;
                    recommendation = file;
                }
            }
        }
        return Optional.ofNullable(recommendation);
    }

    static int score(String alreadyChosenFile, String file) {
        int score = 0;
        int letterLoopMax = Math.min(alreadyChosenFile.length(), file.length());
        // Forward score
        for (int i = 0; i < letterLoopMax; i++) {
            if(alreadyChosenFile.charAt(i) == file.charAt(i))
            {
                score++;
            }
        }
        // Backward score
        for (int i = 0; i < letterLoopMax; i++) {
            if(alreadyChosenFile.charAt(alreadyChosenFile.length() - i - 1) == file.charAt(file.length() - i - 1))
            {
                score++;
            }
        }
        return score;
    }

    @Override
    public void onFileChosen(String chosenFile, List<String> otherFiles)
    {
        chosenFiles.add(chosenFile);

        writeMovesToDisk(chosenFile, otherFiles);
    }

    void writeMovesToDisk(String chosenFile, List<String> otherFiles)
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
