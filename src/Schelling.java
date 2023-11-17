import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

// Classe Schelling, qui étend la classe abstraite CellularGame et représente le jeu de Schelling.
public class Schelling extends CellularGame {
    // HashMaps pour suivre l'état des habitations vacantes
    private final HashMap<String, Boolean> vacant;
    private final HashMap<String, Boolean> initVacant;
    // Paramètres du jeu
    private final int k;
    private final int nombreFamille;
    // Tableau de couleurs représentant les familles
    private final Color[] colors;
    // Tableau de probabilités de répartition des familles
    private final int[] repartition;
    // Générateur de nombres aléatoires
    private final Random random;

    // Constructeur de la classe Schelling
    public Schelling(int n, int m, int k, int nombreFamille, Color[] c, int[] proba) {
        // Appelle le constructeur de la classe parente (CellularGame) avec les dimensions spécifiées
        super(n, m);
        // Initialise les paramètres du jeu
        this.nombreFamille = nombreFamille;
        this.colors = c;
        this.k = k;
        this.vacant = new HashMap<>();
        this.initVacant = new HashMap<>();
        this.repartition = proba;
        this.random = new Random();
        if(Arrays.stream(this.repartition).sum() > 100 ){
            throw new IllegalStateException("La somme de repartition ne doit pas dépasser 100");
        }
        if(this.nombreFamille != this.repartition.length || this.nombreFamille != this.colors.length){
            throw new IllegalStateException("Le nombre de famille spécifié n'est pas le même que le nombre de couleurs ou de répartition");
        }

        // Initialise la carte du jeu
        this.createMap();
    }

    // Méthode pour initialiser la carte du jeu avec des familles et des habitations vacantes
    public void createMap() {
        int totalCells = this.getN() * this.getM();

        // Place les familles sur la carte selon les probabilités de répartition
        for (int i = 0; i < this.nombreFamille; i++) {
            int familySize = (this.repartition[i] * totalCells) / 100;
            for (int j = 0; j < familySize; j++) {
                int row, col;
                do {
                    row = this.random.nextInt(this.getN());
                    col = this.random.nextInt(this.getM());
                } while (this.getGrid()[row][col] != 0);


                this.getGrid()[row][col] = i + 1; // Les familles sont numérotées à partir de 1
                this.getInitialGrid()[row][col] = i + 1;

            }
        }

        // Initialise les habitations vacantes
        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM(); j++) {
                if (this.getGrid()[i][j] == 0) {
                    vacant.put(i + "," + j, true);
                    initVacant.put(i + "," + j, true);
                } else {
                    vacant.put(i + "," + j, false);
                    initVacant.put(i + "," + j, false);
                }
            }
        }
    }

    // Méthode de mise à jour du jeu selon les règles de Schelling
    @Override
    public void update() {
        // HashMap temporaire pour stocker l'état des habitations vacantes
        HashMap<String, Boolean> cacheVacant = new HashMap<>();
        // Grille temporaire pour stocker les nouveaux états des cellules
        int[][] cacheGrid = new int[this.getN()][this.getM()];

        // Parcourt toutes les cellules de la grille
        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM(); j++) {
                int etat = this.getGrid()[i][j];
                // Si la cellule n'est pas vacante
                if (etat != 0) {
                    int voisin = calculVoisin(i, j, etat);
                    // Si le nombre de voisins de la même famille est inférieur à k
                    if (voisin < k) {
                        // Trouve une habitation vacante aléatoire
                        String coordVacante = trouverHabitationVacanteAleatoire(cacheVacant);

                        // Si une habitation vacante est trouvée
                        if (coordVacante != null) {
                            String[] coord = coordVacante.split(",");
                            int newRow = Integer.parseInt(coord[0]);
                            int newCol = Integer.parseInt(coord[1]);
                            // Déplace la famille vers la nouvelle habitation
                            cacheGrid[newRow][newCol] = etat;
                            cacheGrid[i][j] = 0;

                            // Met à jour l'état des habitations vacantes
                            cacheVacant.put(coordVacante, false);
                            cacheVacant.put(i + "," + j, true);
                        }
                    } else {
                        // La cellule reste inchangée
                        cacheGrid[i][j] = this.getGrid()[i][j];
                        cacheVacant.put(i + "," + j, false);
                    }
                }
            }
        }
        // Met à jour l'état des habitations vacantes
        for (String coord : cacheVacant.keySet()) {
            vacant.put(coord, cacheVacant.get(coord));
        }
        // Met à jour la grille principale avec les nouveaux états calculés
        this.setGrid(cacheGrid);
    }

    // Méthode pour trouver une habitation vacante aléatoire parmi celles disponibles
    private String trouverHabitationVacanteAleatoire(HashMap<String, Boolean> cacheVacant) {
        ArrayList<String> habitationsVacantes = new ArrayList<>();

        // Parcourt les habitations vacantes
        for (String coord : vacant.keySet()) {
            if (cacheVacant.containsKey(coord)) {

                if (cacheVacant.get(coord)) {
                    habitationsVacantes.add(coord);
                }
            } else {
                if (vacant.get(coord)) {
                    habitationsVacantes.add(coord);
                }
            }

        }
        // Si des habitations vacantes sont disponibles, en choisit une aléatoirement
        if (!habitationsVacantes.isEmpty()) {
            int indexAleatoire = random.nextInt(habitationsVacantes.size());
            return habitationsVacantes.get(indexAleatoire);
        }

        return null;
    }

    // Méthode pour obtenir la couleur d'une cellule à la position spécifiée
    @Override
    public Color getColor(int i, int j) {
        int etat = this.getGrid()[i][j];
        // Retourne la couleur correspondant à l'état de la cellule
        return etat == 0 ? Color.white : colors[etat - 1];
    }

    // Méthode pour réinitialiser le jeu
    @Override
    public void reInit() {
        // Réinitialise l'état des habitations vacantes et de la grille principale
        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM(); j++) {
                this.vacant.put(i + "," + j, this.initVacant.get(i + "," + j));
                this.getGrid()[i][j] = this.getInitialGrid()[i][j];
            }
        }
    }

    // Méthode pour calculer le prochain état d'une cellule à la position spécifiée
    @Override
    public int nextEtat(int x, int y) {
        // Incrémente l'état actuel de la cellule modulo le nombre de familles, ou la réinitialise à 0
        return this.getGrid()[y][x] < this.nombreFamille ? this.getGrid()[y][x] + 1 : 0;
    }
}
