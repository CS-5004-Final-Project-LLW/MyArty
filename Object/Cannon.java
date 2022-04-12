package Object;

import java.util.ArrayList;

public class Cannon extends GameObject {
    private static double GRAVITY = 0.5; // TODO: find a constant for gravity

    public Cannon(CoordinateInt coordinate, CoordinateInt size) {
        super(coordinate, size);
    }

    public Cannon(CoordinateInt coordinate) {
        super(coordinate, new CoordinateInt(3, 3));
    }

    private double degreeToRadian(double degree) {
        return degree * Math.PI / 180;
    }


    /**
     * @param time in millisecond
     * @param angleDegree between 0 and 90
     * @param powerPercentage between 0 and 1
     * @return CoordinateDouble between 0 and 1
     */
    private CoordinateDouble getCurrentCoordinate(double time, double angleDegree,
            double powerPercentage, CoordinateInt screenSize) {
        /* h, x, y are all percentages (from 0 to 1) */
        int screenSizeX = screenSize.x;
        int screenSizeY = screenSize.y;
        double v = (double) powerPercentage / 100; // TODO: find a constant for velocity
        double h = (double) getY() / screenSize.y;
        double t = (double) time / 1000;
        double x0 = (double) getX() / screenSizeX;
        double y0 = (double) getY() / screenSizeY;
        double theta = degreeToRadian(angleDegree);
        double x = x0 + v * Math.cos(theta) * t;
        double y = y0 + h + v * Math.sin(theta) * t - GRAVITY * t * t / 2;
        return new CoordinateDouble(x, y);
    }

    public ArrayList<CoordinateInt> getShootTrace(double angleDegree, double powerPercentage,
            GameObject target, CoordinateInt screenSize) {
        ArrayList<CoordinateInt> result = new ArrayList<CoordinateInt>();
        int screenSizeX = screenSize.x;
        int screenSizeY = screenSize.y;
        double time = 0;
        double x = getX();
        double y = getY();
        double xPercent = x / screenSizeX;
        double yPercent = y / screenSizeY;
        CoordinateDouble currentCoordinate = new CoordinateDouble(getCoordinate());

        while (time <= 5000 && yPercent >= target.getY() / screenSizeY && 0 <= xPercent
                && xPercent <= 1 && 0 <= yPercent && yPercent <= 1.2) {
            currentCoordinate =
                    getCurrentCoordinate(time, angleDegree, powerPercentage, screenSize);
            xPercent = currentCoordinate.x;
            yPercent = currentCoordinate.y;
            int resultX = (int) (xPercent * screenSizeX);
            int resultY = (int) (yPercent * screenSizeY);
            result.add(new CoordinateInt(resultX, resultY));
            time += 100; // Magic Number
        }

        // System.out.println(result.toString());
        return result;
    }

    public boolean getShootResult(double angleDegree, double powerPercentage, GameObject target,
            CoordinateInt screenSize) {
        double v = powerPercentage / 100;
        double h = (double) getY() / screenSize.y;
        double x0 = (double) getX() / screenSize.x;
        double y0 = (double) getY() / screenSize.y;
        double theta = degreeToRadian(angleDegree);
        double x = (double) target.getX() / screenSize.x;
        double t = (x - x0) / v / Math.cos(theta);
        double y = y0 + h + v * Math.sin(theta) * t - GRAVITY * t * t / 2;
        // System.out.println("x0:%.5f, y0:%.5f, x:%.5f, y:%.5f, t: %.5f".formatted(x0, y0, x, y, t));
        // System.out.println("expected:%.5f, actual:%d".formatted(y * screenSize.y, target.getY()));
        return Math.abs(y * screenSize.y - target.getY()) <= target.getSize().y;
    }

}
