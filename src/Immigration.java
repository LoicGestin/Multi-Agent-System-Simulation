import java.awt.*;
import java.util.Random;

// Classe Immigration, qui étend la classe abstraite CellularGame et représente le jeu d'Immigration.
public class Immigration extends CellularGame{
    // Constructeur de la classe Immigration
    private int nombre_etat;
    public Immigration(int n, int m, int nombre_etat) {
        super(n, m);
        this.nombre_etat = nombre_etat;
    }

    // Méthode pour remplir la grille avec des états aléatoires
    public void fillRandomStates() {
        Random random = new Random();

        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM(); j++) {
                int randomState = random.nextInt(nombre_etat);
                this.getGrid()[i][j] = randomState;
                this.getInitial_grid()[i][j] = randomState;
            }
        }
    }
    // Méthode de la classe : calcule le prochain état d'une cellule à la position spécifiée
    @Override
    public int next_etat(int x, int y) {
        return (this.getGrid()[y][x] + 1) % nombre_etat;
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
                int next_etat = (this.getGrid()[i][j] + 1) % nombre_etat;
                // Calcule le nombre de voisins ayant le prochain état
                int voisin = this.calcul_voisin(i,j, next_etat);
                // Applique la règle du jeu d'Immigration pour mettre à jour l'état de la cellule
                cache_grid[i][j] = voisin >= 3 ? (this.getGrid()[i][j] + 1 )% nombre_etat : this.getGrid()[i][j];
            }
        }
        // Met à jour la grille principale avec les nouveaux états calculés
        this.setGrid(cache_grid);
    }
    // Méthode de la classe : retourne la couleur d'une cellule à la position spécifiée
    @Override
    public Color getColor(int i, int j) {
        int etat = this.getGrid()[i][j];
        // Calcule la valeur de dégradé entre le blanc et le noir en fonction du nombre d'états
        int gradation = 255 - ((int) ((1.0 * etat / (nombre_etat - 1)) * 255));
        return new Color(gradation, gradation, gradation);
    }
}
