package resultprocessor;

import java.io.*;
import java.util.List;

import static common.Utilities.getNewFile;
import static common.Utilities.wrapFile;

public class ResultListener implements ResultProcessor.Events {
    private final File output = getNewFile("moves", "sh");
    private final InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    private final BufferedReader reader = new BufferedReader(inputStreamReader);
    private final BufferedWriter writer;

    public ResultListener() {
        try {
            this.writer = new BufferedWriter(new FileWriter(output));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

            for (int j = 0; j < files.size(); j++) {
                String file = files.get(j);
                if (j == choiceIndex) {
                    writer.write("chmod 0444 " + wrapFile(file));
                    writer.newLine();
                    writer.flush();
                } else {
                    writer.write("mv " + wrapFile(file) + " " + wrapFile("XXX" + file));
                    writer.newLine();
                    writer.flush();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
