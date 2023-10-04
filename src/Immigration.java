import java.awt.*;

public class Immigration extends BasicGame{
    public Immigration(int n, int m) {
        super(n, m);
    }

    @Override
    public void update() {
        int[][] cache_grid = new int[this.getN()][this.getM()];

        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM(); j++) {
                int next_etat = (this.getGrid()[i][j] + 1) % 4;
                int voisin = this.calcul_voisin(i,j, next_etat);
                if(i == 5 && j == 7 ){
                    System.out.println(voisin);
                }

                cache_grid[i][j] = voisin >= 3 ? (this.getGrid()[i][j] + 1 )% 4 : this.getGrid()[i][j];
            }
        }

        this.setGrid(cache_grid);
    }

    @Override
    public Color getColor(int i, int j) {
        int etat = this.getGrid()[i][j];
        return etat == 0 ? Color.white : etat == 1 ? Color.LIGHT_GRAY : etat == 2 ? Color.DARK_GRAY : Color.black;
    }
}
