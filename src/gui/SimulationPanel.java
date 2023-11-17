package gui;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.Collection;
import java.util.LinkedList;

public class SimulationPanel extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int width;
    private final int height;
    private final Color bgColor;
    private Collection<GraphicalElement> shapes;

    protected SimulationPanel(int var1, int var2, Color var3) {
        this.bgColor = var3;
        this.width = var1;
        this.height = var2;
        this.setPreferredSize(new Dimension(var1, var2));
        this.reset();


    }

    protected void reset() {
        this.shapes = new LinkedList<>();
        this.repaint();
    }

    protected void addGraphicalElement(GraphicalElement var1) {
        synchronized (this.shapes) {
            this.shapes.add(var1);
        }

        this.repaint();
    }

    public void paintComponent(Graphics var1) {
        super.paintComponent(var1);
        Graphics2D var2 = (Graphics2D) var1;
        var2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        var2.setColor(this.bgColor);
        var2.fillRect(0, 0, this.width, this.height);
        synchronized (this.shapes) {

            for (GraphicalElement var5 : this.shapes) {

                var5.paint(var2);
            }
        }

        var2.dispose();
    }
}
