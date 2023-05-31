package bg.tu_varna.sit.b2.f21621518.Commands;

import bg.tu_varna.sit.b2.f21621518.CommandParser.CommandParser;

import java.io.IOException;

public class CloseCommand implements Command
{
    @Override
    public void execute(String[] args)
    {

        try {
            CommandParser.getInstance().close();
        }
        catch (IOException ex)
        {
            System.out.println("Error occured while trying to exit: "+ex.getMessage());
        }
    }
}
