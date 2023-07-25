package duplicatefilefinder.progress;

import duplicatefilefinder.records.HashRecord;

public class ProgressEventsImpl implements ProgressEvents
{
    int fileCount = 0;
    int duplicateFileCount = 0;

    int maxFileCount = 0;

    @Override
    public void onHashRecord(HashRecord hashRecord)
    {
        fileCount++;

        boolean isEmptyFile = hashRecord.fileSize() == 0;
        if(!isEmptyFile) {

            boolean hasDuplicate = hashRecord.files().size() > 1;
            if(hasDuplicate)
            {
               duplicateFileCount++;
            }

            int size = hashRecord.files().size();
            if (size > maxFileCount) {
                maxFileCount = size;
            }

            System.out.print(String.format("\rCount: %d, Duplicates: %d, Max duplicates: %d", fileCount, duplicateFileCount, maxFileCount));
        }
    }

    @Override
    public void onMessage(String message)
    {
        System.out.println();
        System.out.println(message);
    }
}
