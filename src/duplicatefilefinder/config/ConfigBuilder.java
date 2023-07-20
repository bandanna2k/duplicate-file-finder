package duplicatefilefinder.config;

public class ConfigBuilder
{
    private int minFilesFilter = 2;

    public ConfigBuilder(final String[] args)
    {
        for (int i = 0; i < args.length; i++)
        {
            final String arg = args[i];
            switch (arg) {
                case "-min", "--minFiles" -> minFilesFilter = Integer.parseInt(args[++i]);
            }
        }
    }

    public ConfigBuilder minFilesFilter(final int minFilesFilter)
    {
        this.minFilesFilter = minFilesFilter;
        return this;
    }

    public Config build()
    {
        return new Config(minFilesFilter);
    }
}
