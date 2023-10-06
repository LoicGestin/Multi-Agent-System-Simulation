import java.awt.*;

public class GameOfLife extends CellularGame {

    public GameOfLife(int n, int m){
        super(n,m);
    }


    @Override
    public Color getColor(int i, int j) {
        return this.getGrid()[i][j] == 0 ? Color.white : Color.BLACK;
    }

    public void update(){
        int[][] cache_grid = new int[this.getN()][this.getM()];

        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM(); j++) {
                int voisin = calcul_voisin(i,j,1);
                cache_grid[i][j] = this.getGrid()[i][j] == 1 ? (voisin == 2 || voisin == 3) ? 1 : 0 : voisin == 3 ? 1 : 0;
            }
        }
        this.setGrid(cache_grid);
    }




}
