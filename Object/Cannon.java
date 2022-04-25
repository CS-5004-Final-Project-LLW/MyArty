package Object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import Coordinate.CoordinateInt;
import Main.GUI;
import Main.Info;
import Main.Repo;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/** A class for Cannon */
public class Cannon extends GameObject {
  /* A special number for gravity after a whole-night testing */
  CoordinateInt size;
  private int cannonWidth;
  private int cannonHeight;
  private int cannonBaseWidth;
  private int cannonBaseHeight;

  public Cannon(
      CoordinateInt coordinate,
      int cannonWidth,
      int cannonHeight,
      int cannonBaseWidth,
      int cannonBaseHeight) {
    super(coordinate);
    this.cannonWidth = cannonWidth;
    this.cannonHeight = cannonHeight;
    this.cannonBaseHeight = cannonBaseHeight;
    this.cannonBaseWidth = cannonBaseWidth;
  }

  /**
   * Convert degree angle to radian
   *
   * @param radian
   * @return double radian
   */
  private double RadianToDegree(double radian) {
    return radian / Math.PI * 180;
  }

  @Override
  protected void createBoundary() {
    setBoundary_min(new CoordinateInt(cannonWidth, GUI.HEIGHT / 2 + cannonHeight));
    setBoundary_max(new CoordinateInt(GUI.WIDTH / 2 - cannonWidth, GUI.HEIGHT - cannonHeight));
  }

  @Override
  public boolean update() {

    int centerX = getX() + cannonWidth / 2;
    int centerY = getY() + cannonHeight / 2;

    /* Update angle */
    double dy = Info.getCursorY() - centerY;
    double dx = Info.getCursorX() - centerX;

    double radian = Math.atan2(dy, dx);
    Info.angleValue = (int) RadianToDegree(radian);
    Info.setRotateDegree(radian);

    /* Create bullets */
    int range = 400;
    if (Info.isClicking() && Repo.isReadyForShot() && Math.sqrt(dx * dx + dy * dy) < range) {
      CoordinateInt bulletPoint = new CoordinateInt(centerX , centerY-100);
      Repo.bullets.add(new Bullet(bulletPoint, Info.powerValue, Info.angleValue, 25));
    }

    return true;
  }

  @Override
  public void draw(Graphics2D graph) {
    BufferedImage cannonImage = Info.getCannonImage();
    BufferedImage cannonBaseImage = Info.getCannonBaseImage();
    AffineTransform at = getTransformation();
    graph.drawImage(cannonImage,at, null);
    graph.drawImage(cannonBaseImage, getX(), getY()+20, cannonBaseWidth, cannonBaseHeight, null);

  }

  private AffineTransform getTransformation() {
    AffineTransform at = new AffineTransform();
    at.translate(getX() + 15, getY());
    at.rotate(Info.getRotateDegree());
    at.scale(3, 3);
    at.translate(-10, -10);
    return at;
  }

  public int getCannonHeight() {
    return cannonHeight;
  }

  public int getCannonWidth() {
    return cannonWidth;
  }
}
