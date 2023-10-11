package gui;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import gui.GraphicalElement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JPanel;

public class SimulationPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Collection<GraphicalElement> shapes;
    private int width;
    private int height;
    private Color bgColor;

    protected SimulationPanel(int var1, int var2, Color var3) {
        this.bgColor = var3;
        this.width = var1;
        this.height = var2;
        this.setPreferredSize(new Dimension(var1, var2));
        this.reset();


    }
    protected void reset() {
        this.shapes = new LinkedList();
        this.repaint();
    }

    protected void addGraphicalElement(GraphicalElement var1) {
        synchronized(this.shapes) {
            this.shapes.add(var1);
        }

        this.repaint();
    }

    public void paintComponent(Graphics var1) {
        super.paintComponent(var1);
        Graphics2D var2 = (Graphics2D)var1;
        var2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        var2.setColor(this.bgColor);
        var2.fillRect(0, 0, this.width, this.height);
        synchronized(this.shapes) {
            Iterator var4 = this.shapes.iterator();

            while(true) {
                if (!var4.hasNext()) {
                    break;
                }

                GraphicalElement var5 = (GraphicalElement)var4.next();
                var5.paint(var2);
            }
        }

        var2.dispose();
    }
}
