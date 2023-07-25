package duplicatefilefinder.progress;

import duplicatefilefinder.records.HashRecord;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class ProgressEventsImpl implements ProgressEvents
{
    private final long startTime;
    int fileCount = 0;
    int duplicateFileCount = 0;

    int maxFileCount = 0;

    public ProgressEventsImpl() {
        startTime = System.currentTimeMillis();
    }

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

            long currentTime = System.currentTimeMillis();
            long durationMillis = currentTime - startTime;
            long durationSeconds = TimeUnit.SECONDS.convert(durationMillis, TimeUnit.MILLISECONDS);

            System.out.print(String.format("\rCount: %d, Time taken: %ds, Duplicates: %d, Max duplicates: %d",
                    fileCount, durationSeconds, duplicateFileCount, maxFileCount));
        }
    }

    @Override
    public void onMessage(String message)
    {
        System.out.println();
        System.out.println(message);
    }
}
