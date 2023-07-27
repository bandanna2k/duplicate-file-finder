package common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Utilities
{
    public static File getNewFile(String prefix, String extension)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        File file = new File(String.format(prefix + ".%s." + extension, sdf.format(new Date())));
        return file;
    }

    public static String wrapFile(final String file) {
        return "\"" + file + "\"";
    }
}
