package duplicatefilefinder.progress;

import duplicatefilefinder.records.HashRecord;

public class ProgressEventsImpl implements ProgressEvents
{
    int fileCount = 0;

    int maxFileCount = 0;

    @Override
    public void onHashRecord(HashRecord hashRecord)
    {
        fileCount++;

        if(hashRecord.fileSize() != 0) {
            int size = hashRecord.files().size();
            if (size > maxFileCount) {
                maxFileCount = size;
            }

            System.out.print(String.format("\rCount: %d, Max duplicates: %d", fileCount, maxFileCount));
        }
    }

    @Override
    public void onMessage(String message)
    {
        System.out.println();
        System.out.println(message);
    }
}
