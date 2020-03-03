package slogo.model.Commands.DisplayCommands;

import slogo.model.TreeNode;

public class GetPenColor extends DisplayCommand {

  public GetPenColor(String name) {
    super(name);
  }

  @Override
  public void doCommand(TreeNode commandNode) {
    commandNode.setResult(displayOption.getPenIndex() + "");
  }
}
