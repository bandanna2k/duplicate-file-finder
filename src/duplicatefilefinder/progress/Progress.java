package duplicatefilefinder.progress;

public record Progress(
        int fileCount,
        String mostFrequentFile
)
{
    @Override
    public String toString() {
        return String.format("Count: %d, Avg process time: %.2f, MFF: %s", fileCount, 1.2, mostFrequentFile);
    }
}
