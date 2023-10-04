import java.awt.*;

public class Immigration extends GameOfLife{
    public Immigration(int n, int m) {
        super(n, m);
    }


    @Override
    public int calcul_voisin(int[][] cache_grid, int i, int j) {
        int voisin = 0;
        int etat = cache_grid[i][j];
        int next_etat = (etat + 1) % 4;
        int v1 = i == 0 ? this.getN() -1 : i - 1;
        int v2 = i == this.getN() -1 ? 0 : i + 1;

        int v3 = j == 0 ? this.getM() -1 : j - 1;
        int v4 = j == this.getM() -1 ? 0 : j + 1;
        voisin += cache_grid[v1][j] == next_etat ? 1 : 0;
        voisin += cache_grid[v1][v3] == next_etat ? 1 : 0;
        voisin += cache_grid[v1][v4] == next_etat ? 1 : 0;

        voisin += cache_grid[v2][j] == next_etat ? 1 : 0;
        voisin += cache_grid[v2][v3] == next_etat ? 1 : 0;
        voisin += cache_grid[v2][v4] == next_etat ? 1 : 0;


        voisin += cache_grid[i][v3] == next_etat ? 1 : 0;
        voisin += cache_grid[i][v4] == next_etat ? 1 : 0;

        return voisin;
    }

    @Override
    public void update() {
        int[][] cache_grid = new int[this.getN()][this.getM()];
        for (int i = 0; i < this.getN() ; i++) {
            for (int j = 0; j < this.getM() ; j++) {
                cache_grid[i][j] = this.getGrid()[i][j];
            }
        }
        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM(); j++) {
                int voisin = calcul_voisin(cache_grid,i,j);
                this.getGrid()[i][j] = voisin >= 3 ? (this.getGrid()[i][j] + 1 )% 4 : this.getGrid()[i][j];
            }
        }
    }

    @Override
    public Color getColor(int i, int j) {
        int etat = this.getGrid()[i][j];
        return etat == 0 ? Color.white : etat == 1 ? Color.LIGHT_GRAY : etat == 2 ? Color.DARK_GRAY : Color.black;
    }
}
