package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;

public class Triangle extends CenteredGraphicalElement {
    private Color drawColor;
    private Color fillColor;
    private int[] xPoints;
    private int[] yPoints;

    public Triangle(int[] xPoints, int[] yPoints, Color drawColor, Color fillColor) {
        super(computeCenterX(xPoints), computeCenterY(yPoints));
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.drawColor = drawColor;
        this.fillColor = fillColor;
    }

    private static int computeCenterX(int[] xPoints) {
        int sumX = 0;
        for (int x : xPoints) {
            sumX += x;
        }
        return sumX / xPoints.length;
    }

    private static int computeCenterY(int[] yPoints) {
        int sumY = 0;
        for (int y : yPoints) {
            sumY += y;
        }
        return sumY / yPoints.length;
    }

    public void paint(Graphics2D g2d) {
        Stroke originalStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(2.0F));
        Color originalColor = g2d.getColor();

        Polygon triangle = new Polygon(xPoints, yPoints, 3);

        if (fillColor != null) {
            g2d.setColor(fillColor);
            g2d.fill(triangle);
        }

        g2d.setColor(drawColor);
        g2d.draw(triangle);
        g2d.setColor(originalColor);
        g2d.setStroke(originalStroke);
    }

    public String toString() {
        return drawColor.toString() + " triangle";
    }
}