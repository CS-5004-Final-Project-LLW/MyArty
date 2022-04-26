package TextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import Coordinate.CoordinateInt;
import Main.Info;
import Main.Tools;
import Object.AbstractGameObject;

public class AngleTextField extends AbstractGameObject {
  private int width;
  private int height;

  public AngleTextField(CoordinateInt coordinate, int width, int height) {
    super(coordinate);
    this.width = width;
    this.height = height;

  }

  @Override
  protected void createBoundary() {
    createNullBoundary();
  }

  @Override
  public boolean update() {
    return false;
  }

  @Override
  public void draw(Graphics2D graph) {
    Font pf = new Font("Calibri", Font.LAYOUT_LEFT_TO_RIGHT, 40);
    Tools.drawStringWithOutline("Angle: ", getX(), getY(), pf, 15, Color.WHITE, Color.BLACK, graph);
    String angle = String.format("%.1f", Tools.radianToDegree(Math.abs(Info.getRotateDegree())));
    Tools.drawStringWithOutline(angle + "°", getX() + 130, getY(), pf, 15, Color.WHITE, Color.RED,
        graph);
  }
}
