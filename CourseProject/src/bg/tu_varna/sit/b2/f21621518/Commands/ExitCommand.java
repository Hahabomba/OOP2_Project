package bg.tu_varna.sit.b2.f21621518.Commands;

import bg.tu_varna.sit.b2.f21621518.Commands.Command;

public class ExitCommand implements Command
{
    @Override
    public void execute(String[] args)
    {
        System.out.println("Exiting the program...");
        System.exit(1);
    }
}
