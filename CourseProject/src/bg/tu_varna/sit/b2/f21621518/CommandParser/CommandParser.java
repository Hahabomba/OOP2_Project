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

    private static  CommandParser commandParserInstance;

    public static  CommandParser getInstance()
    {
        if (commandParserInstance==null)
        {
            commandParserInstance=new CommandParser();
        }

        return commandParserInstance;
    }



    public static void close() throws IOException
    {
            boolean filePathIsNotOpened = (filePath == null);

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
    public static void exit() throws IOException
    {
        boolean fileIsNotEmpty=!(content.isEmpty());

        if (fileIsNotEmpty)
        {
            save();
            close();
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
        boolean fileNotOpened=(filePath==null);

        if (fileNotOpened)
        {
            throw new IllegalStateException("File is not currently opened");
        }

        Files.write(filePath,content);
        System.out.println("Successfully saved! "+filePath.getFileName());

    }

    public static void open(Path newFilePath) throws  IOException
    {
        boolean fileExists=Files.exists(newFilePath);

        //It does not exist
        if (!fileExists)
        {
            throw new IOException("File does not exist!");
        }

        content=Files.readAllLines(newFilePath);
        filePath=newFilePath;
        System.out.println("Succesfully opened "+filePath.getFileName());
    }



    public static List<String> getContent()
    {
        return content;
    }
}
