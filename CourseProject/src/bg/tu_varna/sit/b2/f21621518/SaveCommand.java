package bg.tu_varna.sit.b2.f21621518;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SaveCommand implements Command
{
    private Path path;
    private List<String> content;

    @Override
    public void execute(String[] args)
    {
            try
            {
                if (path==null)
                {
                    throw new Exception("File is not currently opened");
                }
                Files.write(path,content);
                System.out.println("Successfully saved! "+path.getFileName());
            }
            catch (Exception ex)
            {
                System.out.println("Error ocured, while saving " + path.getFileName());
                System.out.println("Exception message: "+ex.getMessage());
                throw new RuntimeException("File was not saved!", ex);
            }
    }

    public void setContent(List<String> content)
    {
        this.content = content;
    }
    public void setPath(Path path)
    {
        this.path = path;
    }
}
