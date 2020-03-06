package slogo.model.Commands.VCUCommands;
import slogo.model.CommandParser;
import slogo.model.CustomCommandMap;
import slogo.model.TreeNode;
import java.util.Arrays;
public class CustomCommand extends VCUCommand {
    public CustomCommand(String name)
    {
        super(name);
    }
    @Override
    public void doCommand(TreeNode commandNode) {
        CommandParser miniParser = new CommandParser(activatedTurtles,variables, language);
        String[] variables = CustomCommandMap.getVariables(commandNode.getName());
        String commands = CustomCommandMap.getCommands(commandNode.getName());
        //System.out.println("VARIABLE LENGTH: "  +variables[0]);
        for(int i = 1;i<variables.length;i++)
        {
            System.out.println(Arrays.toString(variables));
//            if(variables.length == 0 || variables[0].trim().equals(""))
//            {
//                continue;
//            }
            System.out.println("VARIABLE: " + i + " " + variables[i]);
            commands = commands.replaceAll(variables[i],getParamList().get(0));
        }
        miniParser.miniParse(commands);
        //.replaceFirst("\\[","")
    }
}