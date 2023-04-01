package bg.tu_varna.sit.b2.f21621518.Commands;

import bg.tu_varna.sit.b2.f21621518.Commands.Command;

public class HelpCommand implements Command
{
    //Help Command -> Only prints information about available commands
    @Override
    public void execute(String[] args)
    {
        System.out.println("The following commands are supported:");
        System.out.println("open <file>     opens <file>");
        System.out.println("close           closes currently opened file");
        System.out.println("save            saves the currently opened file");
        System.out.println("saveas <file>   saves the currently opened file in <file>");
        System.out.println("help            prints this information");
        System.out.println("exit            exits the program");
    }
}
