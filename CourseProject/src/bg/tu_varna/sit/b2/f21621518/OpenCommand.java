package bg.tu_varna.sit.b2.f21621518;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class OpenCommand implements Command
{
    private Path path;
    private List<String> content;

    @Override
    public void execute(String[] args)
    {
        if (args.length<2)
        {
            throw new IllegalArgumentException("Invalid Operation!\nopen <filePath>");
        }

        var inputPath=args[1];
        this.path= Paths.get(inputPath);

        try
        {
            this.content= Files.readAllLines(path);
            System.out.println("Opened successfully file "+path.getFileName());

        }
        catch(IOException exception)
        {
            System.out.println("Can not open "+path.getFileName());
            System.out.println("Exception Message: "+exception.getMessage());
        }
    }

    public List<String> getContent()
    {
        return this.content;
    }
}
