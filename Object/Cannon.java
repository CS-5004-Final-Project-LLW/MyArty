package Object;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import Coordinate.CoordinateInt;
import Main.GUI;
import Main.Info;
import Main.Repo;
import Main.Tools;

/** A class for Cannon */
public class Cannon extends AbstractGameObject {
  /* A special number for gravity after a whole-night testing */
  // CoordinateInt size;
  private int cannonWidth;
  private int cannonHeight;
  private int cannonBaseWidth;
  private int cannonBaseHeight;

  double cannonScaledFactor;

  public Cannon(CoordinateInt coordinate, int cannonWidth, int cannonBaseWidth) {
    super(coordinate);
    BufferedImage cannonImage = Info.cannonImage.get();

    int cannonOriginalWidth = cannonImage.getWidth();
    int cannonOriginalHeight = cannonImage.getHeight();
    this.cannonScaledFactor = (double) cannonWidth / cannonOriginalWidth;

    this.cannonWidth = cannonWidth;
    this.cannonHeight = (int) (cannonOriginalHeight * cannonScaledFactor);

    BufferedImage cannonBaseImage = Info.cannonBaseImage.get();

    int cannonBaseOriginalWidth = cannonBaseImage.getWidth();
    int cannonBaseOriginalHeight = cannonBaseImage.getHeight();
    double cannonBaseScaledFactor = (double) cannonBaseWidth / cannonBaseOriginalWidth;

    this.cannonBaseWidth = cannonBaseWidth;
    this.cannonBaseHeight = (int) (cannonBaseOriginalHeight * cannonBaseScaledFactor);
  }

  @Override
  protected void createBoundary() {
    setBoundary_min(new CoordinateInt(cannonWidth, GUI.HEIGHT / 2 + cannonHeight));
    setBoundary_max(new CoordinateInt(GUI.WIDTH / 2 - cannonWidth, GUI.HEIGHT - cannonHeight));
  }

  @Override
  public boolean update() {

    int centerX = getX() + cannonHeight / 2;
    int centerY = getY() + cannonHeight / 2;

    /* Update angle */
    double dy = Info.cursorY.get() - centerY;
    double dx = Info.cursorX.get() - centerX;

    double radian = Math.atan2(dy, dx);
    // TODO: angleValue should be valid too
    Info.angleValue = (int) Tools.radianToDegree(radian);
    if (-Tools.radianToDegree(radian) > 90.0) {
      Info.setRotateDegree(Tools.degreeToRadian(-90));
    } else if (-Tools.radianToDegree(radian) < 0) {
      Info.setRotateDegree(0);
    } else {
      Info.setRotateDegree(radian);
    }

    /* Create bullets */
    int range = 400;
    if (Info.clicking.get() && Repo.isReadyForShot() && Math.sqrt(dx * dx + dy * dy) < range) {
      final int bulleHeight = 35;
      final int L = cannonWidth - cannonHeight / 2 + bulleHeight / 2;
      final double bulletX = centerX + L * Math.cos(Info.getRotateDegree()) - bulleHeight;
      final double bulletY = centerY + L * Math.sin(Info.getRotateDegree()) - bulleHeight;
      CoordinateInt bulletPoint = new CoordinateInt(bulletX, bulletY);
      Repo.bullets.add(new Bullet(bulletPoint, Info.powerValue, Info.angleValue, bulleHeight));
    }

    return true;
  }

  @Override
  public void draw(Graphics2D graph) {
    BufferedImage cannonImage = Info.cannonImage.get();
    BufferedImage cannonBaseImage = Info.cannonBaseImage.get();
    AffineTransform at = getTransformation();

    graph.drawImage(cannonImage, at, null);

    graph.drawImage(cannonBaseImage, getX() + cannonHeight / 2 - cannonBaseWidth / 2 - 1,
        getY() + cannonHeight / 2 - cannonBaseHeight / 2 + 4, cannonBaseWidth + 2,
        cannonBaseHeight + 2, null);
  }

  private AffineTransform getTransformation() {
    AffineTransform at = new AffineTransform();

    at.translate(getX(), getY());
    at.rotate(Info.getRotateDegree(), cannonHeight / 2, cannonHeight / 2);
    at.scale(cannonScaledFactor, cannonScaledFactor);

    return at;
  }

  public int getCannonHeight() {
    return cannonHeight;
  }

  public int getCannonWidth() {
    return cannonWidth;
  }
}
