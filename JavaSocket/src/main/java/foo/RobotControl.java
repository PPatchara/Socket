package foo;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;

/**
 * Created by Justwyne on 5/26/16 AD.
 */
public class RobotControl {

    private Robot robot;
    private Dimension screenSize;
    private final int PADDING = 10;

    public RobotControl() throws AWTException {
        robot = new Robot();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }

    public void mouseGlide(int x1, int y1, int x2, int y2, int t, int n) {
        try {
            double dx = (x2 - x1) / ((double) n);
            double dy = (y2 - y1) / ((double) n);
            double dt = t / ((double) n);
            for (int step = 1; step <= n; step++) {
                Thread.sleep((int) dt);
                robot.mouseMove((int) (x1 + dx * step), (int) (y1 + dy * step));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void move(int deltaX, int deltaY) {

        Point b = MouseInfo.getPointerInfo().getLocation();

        int cursor_x = (int) b.getX() + deltaX;
        int cursor_y = (int) b.getY() + deltaY;

        Point pos = cleanPosition(cursor_x, cursor_y);

        mouseGlide((int) b.getX(), (int) b.getY(), cursor_x, cursor_y, 1, 30);

//        System.out.println(String.format("Position: %d,%d", pos.x, pos.y));
//
//        robot.mouseMove(pos.x, pos.y);
    }

    public void click() {

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    private Point cleanPosition(int cursor_x, int cursor_y) {
        if ( cursor_x > screenSize.getWidth() - PADDING ) {
            cursor_x = (int) screenSize.getWidth() - PADDING;
        }
        if ( cursor_x < 0 ) {
            cursor_x = 0;
        }

        if ( cursor_y > screenSize.getHeight() - PADDING ) {
            cursor_y = (int) screenSize.getHeight() - PADDING;
        }
        if ( cursor_y < 0 ) {
            cursor_y = 0;
        }

        return new Point(cursor_x, cursor_y);
    }


}
