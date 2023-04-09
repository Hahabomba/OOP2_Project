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
        try
        {
            boolean filePathIsNotOpened = filePath == null;

            if (filePathIsNotOpened)
            {
                throw new IllegalStateException("There is no opened file yet");
            }
            //Setting FilePath to null
            filePath = null;

            //Clearing content
            content.clear();

            System.out.println("Content from file cleared.");
        }
        catch (IllegalStateException ex)
        {
            System.out.println(ex);
        }
    }
    public static void exit()
    {
        boolean fileIsNotEmpty=!(content.isEmpty());

        if (fileIsNotEmpty)
        {

        }
        System.out.println("Exiting the program...");
        System.exit(1);

    }

    public static void saveAs(Path newPath) throws  IOException
    {
        Files.write(newPath,content);
        filePath=newPath;
        System.out.println("Successfully saved! "+filePath.getFileName());
    }

    public static void save() throws IOException
    {
        boolean fileNotOpened=filePath==null;

        if (fileNotOpened)
        {
            throw new IllegalStateException("File is not currently opened");
        }

        Files.write(filePath,content);
        System.out.println("Successfully saved! "+filePath.getFileName());

    }


    public static List<String> getContent()
    {
        return content;
    }
}
