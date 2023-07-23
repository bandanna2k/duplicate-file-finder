package duplicatefilefinder.progress;

import duplicatefilefinder.records.HashRecord;

public interface ProgressEvents
{
    void onHashRecord(HashRecord hashRecord);
    void onMessage(String message);
}
