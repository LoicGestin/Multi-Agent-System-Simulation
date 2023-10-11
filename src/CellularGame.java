import gui.GUISimulator;

import java.awt.*;

public abstract class CellularGame implements Game{
    private int[][] grid;
    private int[][] initial_grid;
    private final int n;
    private final int m;

    private int lastX = 0;
    private int lastY = 0;

    private boolean start = false;

    public CellularGame(int n, int m){
        this.grid = new int[n][m];
        this.initial_grid = new int[n][m];
        this.n = n;
        this.m = m;
    }
    public void make_alive(int y, int x, int etat){
        this.grid[y][x] = etat;
        if(!start) {
            this.initial_grid[y][x] = etat;
        }
    }
    public int calcul_voisin(int i, int j, int etat) {
        start = true;
        int voisin = 0;

        int v1 = i == 0 ? this.getN() -1 : i - 1;
        int v2 = i == this.getN() -1 ? 0 : i + 1;
        int v3 = j == 0 ? this.getM() -1 : j - 1;
        int v4 = j == this.getM() -1 ? 0 : j + 1;

        voisin += grid[v1][j] == etat ? 1 : 0;
        voisin += grid[v1][v3] == etat ? 1 : 0;
        voisin += grid[v1][v4] == etat ? 1 : 0;

        voisin += grid[v2][j] == etat ? 1 : 0;
        voisin += grid[v2][v3] == etat ? 1 : 0;
        voisin += grid[v2][v4] == etat ? 1 : 0;


        voisin += grid[i][v3] == etat ? 1 : 0;
        voisin += grid[i][v4] == etat ? 1 : 0;

        return voisin;
    }

    public void setGrid(int[][] grid) {this.grid = grid;}
    public void reInit(){
        start = false;
        for (int i = 0; i < this.n ; i++) {
            for (int j = 0; j < this.m; j++) {
                this.grid[i][j] = this.initial_grid[i][j];
            }
        }
    };
    public abstract void update();
    public abstract Color getColor(int i, int j);
    public int[][] getGrid() {
        return grid;
    }
    public int[][] getInitial_grid() {
        return initial_grid;
    }
    public int getN() {return n;}
    public int getM() {return m;}
    public void draw(GUISimulator gui){
        gui.reset();

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                gui.addGraphicalElement(new gui.Rectangle(
                        10 +j * (gui.getPanelWidth() / this.n),
                        10 + i * (gui.getPanelHeight() / this.m),
                        Color.GRAY,
                        this.getColor(i,j),
                        gui.getPanelHeight() / m));
            }
        }
    }

    @Override
    public void setUpEvent(EventManager eventManager) {
        eventManager.addEvent(new ActionEvent(0,eventManager,0,this));
    }

    @Override
    public void handleClick(int x, int y, int PanelX, int PanelY) {
        int block_width = (PanelX / this.n);
        int block_height = (PanelY / this.n);

        int gridX = x /block_width;
        int gridY = y / block_height;
        if(gridX < this.m && gridY < this.n){
            make_alive(gridY,gridX,next_etat(gridX,gridY));
        }

    }

    @Override
    public void handleMouseMotion(int x, int y, GUISimulator gui) {

        int block_width = (gui.getPanelWidth()/ this.n);
        int block_height = (gui.getPanelHeight() / this.n);

        int gridX = x /block_width;
        int gridY = y / block_height;
        if(gridX < this.m && gridY < this.n) {
            if (lastX != gridX || lastY != gridY) {
                gui.addGraphicalElement(new gui.Rectangle(
                        10 + lastX * (gui.getPanelWidth() / this.n),
                        10 + lastY * (gui.getPanelHeight() / this.m),
                        Color.GRAY,
                        this.getColor(lastY, lastX),
                        gui.getPanelHeight() / m));
                gui.addGraphicalElement(new gui.Rectangle(
                        10 + gridX * (gui.getPanelWidth() / this.n),
                        10 + gridY * (gui.getPanelHeight() / this.m),
                        Color.RED,
                        new Color(0.5f, 0f, 0f, 0),
                        gui.getPanelHeight() / m -1));

                lastX = gridX;
                lastY = gridY;
            }


        }
    }

    public abstract int next_etat(int x, int y);
}
