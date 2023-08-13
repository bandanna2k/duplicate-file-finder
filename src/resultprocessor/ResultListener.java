package resultprocessor;

import java.io.*;
import java.util.*;

public class ResultListener implements ResultProcessor.Events {

    private final InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    private final BufferedReader reader = new BufferedReader(inputStreamReader);
    private final FileChooser fileChooser;

    public ResultListener(FileChooser fileChooser) {

        this.fileChooser = fileChooser;
    }

    @Override
    public void onHashRecord(String md5) {
        System.out.println("=== MD5 " + md5 + " ===");
    }

    @Override
    public void onFilesEvent(List<String> files)
    {
        Optional<String> recommendation = fileChooser.recommendation(files);

        if (files.isEmpty())
            return;
        try {
            int i = 1;
            OptionalInt recommendationChoice = OptionalInt.empty();
            for (String file : files) {

                boolean recommended = recommendation.isPresent() && recommendation.get().contentEquals(file);
                if(recommended)
                {
                    System.out.println("*** " + i + ": " + file);
                    recommendationChoice = OptionalInt.of(i);
                }
                else
                {
                    System.out.println(i + ": " + file);
                }
                i++;
            }
            System.out.print("Choose file: ");
            String choiceAsString = reader.readLine();

            if(choiceAsString.isEmpty() && recommendationChoice.isPresent())
            {
                int choice = recommendationChoice.getAsInt();
                String chosenFile = files.get(choice - 1);
                System.out.println(String.format("Going with recommendation '%d', '%s'", choice, chosenFile));

                fileChooser.onFileChosen(chosenFile, files);
                return;
            }

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

            fileChooser.onFileChosen(chosenFile, files);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    interface FileChooser
    {
        void onFileChosen(String chosenFile, List<String> otherFiles);

        Optional<String> recommendation(List<String> files);
    }
}
