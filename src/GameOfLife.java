import java.awt.*;

// Classe GameOfLife, qui étend la classe abstraite CellularGame, représentant le jeu de la vie (Game of Life).
public class GameOfLife extends CellularGame {
    // Constructeur de la classe GameOfLife
    public GameOfLife(int n, int m) {
        super(n, m);
    }

    // Méthode abstraite de la classe parente : retourne la couleur d'une cellule à la position spécifiée
    @Override
    public Color getColor(int i, int j) {
        return this.getGrid()[i][j] == 0 ? Color.white : Color.BLACK;
    }

    // Méthode abstraite de la classe parente : calcule le prochain état d'une cellule à la position spécifiée
    @Override
    public int nextEtat(int x, int y) {
        return this.getGrid()[y][x] == 0 ? 1 : 0;
    }

    // Méthode de la classe : met à jour l'état du jeu selon les règles du jeu de la vie
    public void update() {
        // Crée une grille temporaire pour stocker les nouveaux états des cellules
        int[][] cacheGrid = new int[this.getN()][this.getM()];

        // Parcourt toutes les cellules de la grille
        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM(); j++) {
                // Calcule le nombre de voisins vivants pour la cellule en cours
                int voisin = calculVoisin(i, j, 1);
                // Applique les règles du jeu de la vie pour mettre à jour l'état de la cellule
                cacheGrid[i][j] = this.getGrid()[i][j] == 1 ? (voisin == 2 || voisin == 3) ? 1 : 0 : voisin == 3 ? 1 : 0;
            }
        }
        // Met à jour la grille principale avec les nouveaux états calculés
        this.setGrid(cacheGrid);
    }


}
