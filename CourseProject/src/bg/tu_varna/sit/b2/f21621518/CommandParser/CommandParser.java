package bg.tu_varna.sit.b2.f21621518.CommandParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class CommandParser
{
    static public Path filePath;
    static public List<String> content = new ArrayList<>();

    public static void close()
    {
        //Setting FilePath to null
        filePath = null;
        //Clearing content
        content.clear();

        System.out.println("Content cleared.");
    }

    public static List<String> getContent()
    {
        return content;
    }
}
