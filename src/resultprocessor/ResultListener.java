package resultprocessor;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultListener implements ResultProcessor.Events {

    private final InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    private final BufferedReader reader = new BufferedReader(inputStreamReader);
    private final List<FileChooser> fileChoosers = new ArrayList<>();

    public ResultListener(FileChooser... fileChoosers) {
        this.fileChoosers.addAll(Arrays.asList(fileChoosers));
    }

    @Override
    public void onHashRecord(String md5) {
        System.out.println("=== MD5 " + md5 + " ===");
    }

    @Override
    public void onFilesEvent(List<String> files) {
        if (files.isEmpty())
            return;
        try {
            int i = 1;
            for (String file : files) {
                System.out.println(i + ": " + file);
                i++;
            }
            System.out.print("Choose file: ");
            String choiceAsString = reader.readLine();
            int choice = Integer.parseInt(choiceAsString);
            if (choice == 0) {
                System.out.println("No file chosen");
                return;
            }
            int choiceIndex = choice - 1;
            String chosenFile = files.get(choiceIndex);
            System.out.println(String.format("You chose %d: %s", choice, chosenFile));

            List<String> otherFiles = new ArrayList<>();
            otherFiles.addAll(files);
            otherFiles.remove(choiceIndex);

            choseFile(chosenFile, files);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void choseFile(String chosenFile, List<String> otherFiles) {
        synchronized (fileChoosers)
        {
            this.fileChoosers.forEach(fileChooser -> fileChooser.onFileChosen(chosenFile, otherFiles));
        }
    }

    interface FileChooser
    {
        void onFileChosen(String chosenFile, List<String> otherFiles);
    }
}
