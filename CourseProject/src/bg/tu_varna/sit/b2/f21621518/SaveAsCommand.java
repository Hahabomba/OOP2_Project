package bg.tu_varna.sit.b2.f21621518;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SaveAsCommand implements Command
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

        Path newPath= Paths.get(args[1]);
        try
        {
            Files.write(newPath,content);
            this.path=newPath;
            System.out.println("Successfully saved! "+path.getFileName());
        }
        catch (Exception ex)
        {
            System.out.println("Error saving as " + newPath.getFileName());
            System.out.println("Exception message: "+ex.getMessage());
            throw new RuntimeException("File was not saved!", ex);
        }

    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
