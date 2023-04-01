package bg.tu_varna.sit.b2.f21621518;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner=new Scanner(System.in);

        boolean closeProgram=false;

        while (!closeProgram)
        {
            //Reading Input Line (Command)
            System.out.print("> ");
            String inputLine=scanner.nextLine();

            //Input line being split by delimiter to get input arguments
            String[] inputArgs=inputLine.split("\\s+");

            String currentCommand=inputArgs[0];
            System.out.println(currentCommand);
            //Switch case for different commands from CLI
            switch (currentCommand)
            {

            }
        }
    }
}