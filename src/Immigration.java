import java.awt.*;
// Classe Immigration, qui étend la classe abstraite CellularGame et représente le jeu d'Immigration.
public class Immigration extends CellularGame{
    // Constructeur de la classe Immigration
    public Immigration(int n, int m) {
        super(n, m);
    }
    // Méthode de la classe : calcule le prochain état d'une cellule à la position spécifiée
    @Override
    public int next_etat(int x, int y) {
        return (this.getGrid()[y][x] + 1) % 4;
    }
    // Méthode de la classe : met à jour l'état du jeu selon les règles spécifiques du jeu d'Immigration
    @Override
    public void update() {
        // Crée une grille temporaire pour stocker les nouveaux états des cellules
        int[][] cache_grid = new int[this.getN()][this.getM()];
        // Parcourt toutes les cellules de la grille
        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM(); j++) {
                // Calcule le prochain état de la cellule en cours
                int next_etat = (this.getGrid()[i][j] + 1) % 4;
                // Calcule le nombre de voisins ayant le prochain état
                int voisin = this.calcul_voisin(i,j, next_etat);
                // Applique la règle du jeu d'Immigration pour mettre à jour l'état de la cellule
                cache_grid[i][j] = voisin >= 3 ? (this.getGrid()[i][j] + 1 )% 4 : this.getGrid()[i][j];
            }
        }
        // Met à jour la grille principale avec les nouveaux états calculés
        this.setGrid(cache_grid);
    }
    // Méthode de la classe : retourne la couleur d'une cellule à la position spécifiée
    @Override
    public Color getColor(int i, int j) {
        int etat = this.getGrid()[i][j];
        // Associe chaque état à une couleur spécifique
        return etat == 0 ? Color.white : etat == 1 ? Color.LIGHT_GRAY : etat == 2 ? Color.DARK_GRAY : Color.black;
    }
}
