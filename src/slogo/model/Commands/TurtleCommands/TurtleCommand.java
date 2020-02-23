package slogo.model.Commands.TurtleCommands;

import slogo.model.Commands.Command;
import slogo.model.Turtle;

public abstract class TurtleCommand extends Command {
  protected int facingRight = 90;
  protected int facingUp = 360;
  protected int facingLeft = 270;
  protected int facingDown = 180;

  public TurtleCommand(String name) {
    super(name);
  }

  protected double angleToRadians(int angle) {
    return angle * Math.PI / 180;
  }

  protected void moveTurtleTo(int x, int y) {
    turtle.setX(x);
    turtle.setY(y);
  }

  protected boolean facingTopRight(int angle) {
    return angle >= 0 && angle < facingRight;
  }

  protected boolean facingTopLeft(int angle) {
    return angle >= facingLeft && angle < facingUp;
  }

  protected boolean facingBottomRight(int angle) {
    return angle >= facingRight && angle < facingDown;
  }

  protected boolean facingBottomLeft(int angle) {
    return angle >= facingDown && angle < facingLeft;
  }

  protected int getAdjustedAngle(int angle) {
    if (facingTopRight(angle))
      return angle;
    else if (facingTopLeft(angle))
      return facingUp - angle;
    else if (facingBottomRight(angle))
      return facingDown - angle;
    else
      return angle - facingDown;
  }

  protected int getXMultiplier(int angle) {
    if (angle >= 0 && angle < facingDown)
      return 1;
    else
      return -1;
  }

  protected int getYMultiplier(int angle) {
    if (angle >= 90 && angle < 270)
      return -1;
    else
      return 1;
  }

}
