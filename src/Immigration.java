import java.awt.*;
import java.util.Random;

// Classe Immigration, qui étend la classe abstraite CellularGame et représente le jeu d'Immigration.
public class Immigration extends CellularGame {
    // Constructeur de la classe Immigration
    private final int nombreEtat;

    public Immigration(int n, int m, int nombreEtat) {
        super(n, m);
        this.nombreEtat = nombreEtat;
    }

    // Méthode pour remplir la grille avec des états aléatoires
    public void fillRandomStates() {
        Random random = new Random();

        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM(); j++) {
                int randomState = random.nextInt(nombreEtat);
                this.getGrid()[i][j] = randomState;
                this.getInitialGrid()[i][j] = randomState;
            }
        }
    }

    // Méthode de la classe : calcule le prochain état d'une cellule à la position spécifiée
    @Override
    public int nextEtat(int x, int y) {
        return (this.getGrid()[y][x] + 1) % nombreEtat;
    }

    // Méthode de la classe : met à jour l'état du jeu selon les règles spécifiques du jeu d'Immigration
    @Override
    public void update() {
        // Crée une grille temporaire pour stocker les nouveaux états des cellules
        int[][] cacheGrid = new int[this.getN()][this.getM()];
        // Parcourt toutes les cellules de la grille
        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM(); j++) {
                // Calcule le prochain état de la cellule en cours
                int nextEtat = (this.getGrid()[i][j] + 1) % nombreEtat;
                // Calcule le nombre de voisins ayant le prochain état
                int voisin = this.calculVoisin(i, j, nextEtat);
                // Applique la règle du jeu d'Immigration pour mettre à jour l'état de la cellule
                cacheGrid[i][j] = voisin >= 3 ? (this.getGrid()[i][j] + 1) % nombreEtat : this.getGrid()[i][j];
            }
        }
        // Met à jour la grille principale avec les nouveaux états calculés
        this.setGrid(cacheGrid);
    }

    // Méthode de la classe : retourne la couleur d'une cellule à la position spécifiée
    @Override
    public Color getColor(int i, int j) {
        int etat = this.getGrid()[i][j];
        // Calcule la valeur de dégradé entre le blanc et le noir en fonction du nombre d'états
        int gradation = 255 - ((int) ((1.0 * etat / (nombreEtat - 1)) * 255));
        return new Color(gradation, gradation, gradation);
    }
}
