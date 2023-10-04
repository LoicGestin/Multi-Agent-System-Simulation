import java.awt.*;

public abstract class BasicGame {
    private int[][] grid;
    private int[][] initial_grid;
    private final int n;
    private final int m;

    public BasicGame(int n, int m){
        this.grid = new int[n][m];
        this.initial_grid = new int[n][m];
        this.n = n;
        this.m = m;
    }
    public void make_alive(int y, int x, int etat){
        this.grid[y][x] = etat;
        this.initial_grid[y][x] = etat;
    }
    public int calcul_voisin(int i, int j, int etat) {
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
    public void setInitial_grid(int[][] initial_grid) {this.initial_grid = initial_grid;}
    public void reInit(){
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
}
