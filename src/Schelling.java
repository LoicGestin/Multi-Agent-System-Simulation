import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
// Classe Schelling, qui étend la classe abstraite CellularGame et représente le jeu de Schelling.
public class Schelling extends CellularGame {
    // HashMaps pour suivre l'état des habitations vacantes
    private HashMap<String, Boolean> vacant;
    private HashMap<String, Boolean> init_vacant;
    // Paramètres du jeu
    private int k;
    private int nombre_famille;
    // Tableau de couleurs représentant les familles
    private Color[] colors;
    // Tableau de probabilités de répartition des familles
    private int[] repartition;
    // Générateur de nombres aléatoires
    private Random random;
    // Constructeur de la classe Schelling
    public Schelling(int n, int m, int k, int nombre_famille, Color[] c, int[] proba){
        // Appelle le constructeur de la classe parente (CellularGame) avec les dimensions spécifiées
        super(n, m);
        // Initialise les paramètres du jeu
        this.nombre_famille = nombre_famille;
        this.colors = c;
        this.k = k;
        this.vacant = new HashMap<>();
        this.init_vacant = new HashMap<>();
        this.repartition = proba;
        this.random = new Random();
        // Initialise la carte du jeu
        this.create_map();
    }
    // Méthode pour initialiser la carte du jeu avec des familles et des habitations vacantes
    public void create_map(){
        int totalCells = this.getN() * this.getM();

        // Place les familles sur la carte selon les probabilités de répartition
        for (int i = 0; i < this.nombre_famille; i++) {
            int familySize = (this.repartition[i] * totalCells) / 100;
            for (int j = 0; j < familySize; j++) {
                int row, col;
                do {
                    row = this.random.nextInt(this.getN());
                    col = this.random.nextInt(this.getM());
                } while (this.getGrid()[row][col] != 0);


                this.getGrid()[row][col] = i + 1; // Les familles sont numérotées à partir de 1
                this.getInitial_grid()[row][col] = i +1;

            }
        }

        // Initialise les habitations vacantes
        for (int i = 0; i < this.getN(); i++) {
            for (int j= 0; j < this.getM(); j++) {
                if (this.getGrid()[i][j] == 0) {
                    vacant.put(i + "," + j, true);
                    init_vacant.put(i + "," +j, true);
                }
                else{
                    vacant.put(i + "," + j, false);
                    init_vacant.put(i + "," +j, false);
                }
            }
        }
    }
    // Méthode de mise à jour du jeu selon les règles de Schelling
    @Override
    public void update() {
        // HashMap temporaire pour stocker l'état des habitations vacantes
        HashMap<String, Boolean> cache_vacant = new HashMap<>();
        // Grille temporaire pour stocker les nouveaux états des cellules
        int[][] cache_grid = new int[this.getN()][this.getM()];

        // Parcourt toutes les cellules de la grille
        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM(); j++) {
                int etat = this.getGrid()[i][j];
                // Si la cellule n'est pas vacante
                if(etat != 0) {
                    int voisin = calcul_voisin(i, j, etat);
                    // Si le nombre de voisins de la même famille est inférieur à k
                    if (voisin < k) {
                        // Trouve une habitation vacante aléatoire
                        String coordVacante = trouverHabitationVacanteAleatoire(cache_vacant);

                        // Si une habitation vacante est trouvée
                        if (coordVacante != null) {
                            String[] coord = coordVacante.split(",");
                            int newRow = Integer.parseInt(coord[0]);
                            int newCol = Integer.parseInt(coord[1]);
                            // Déplace la famille vers la nouvelle habitation
                            cache_grid[newRow][newCol] = etat;
                            cache_grid[i][j] = 0;

                            // Met à jour l'état des habitations vacantes
                            cache_vacant.put(coordVacante, false);
                            cache_vacant.put(i + "," + j, true);
                        }
                    } else {
                        // La cellule reste inchangée
                        cache_grid[i][j] = this.getGrid()[i][j];
                        cache_vacant.put(i + "," + j, false);
                    }
                }
            }
        }
        // Met à jour l'état des habitations vacantes
        for (String coord :cache_vacant.keySet()) {
            vacant.put(coord, cache_vacant.get(coord));
        }
        // Met à jour la grille principale avec les nouveaux états calculés
        this.setGrid(cache_grid);
    }

    // Méthode pour trouver une habitation vacante aléatoire parmi celles disponibles
    private String trouverHabitationVacanteAleatoire(HashMap<String, Boolean> cache_vacant) {
        ArrayList<String> habitationsVacantes = new ArrayList<>();

        // Parcourt les habitations vacantes
        for (String coord : vacant.keySet()) {
            if(cache_vacant.containsKey(coord)){

                if( cache_vacant.get(coord)){
                    habitationsVacantes.add(coord);
                }
            }
            else{
                if(vacant.get(coord)){
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
        return etat == 0 ? Color.white : colors[etat-1];
    }

    // Méthode pour réinitialiser le jeu
    @Override
    public void reInit() {
        // Réinitialise l'état des habitations vacantes et de la grille principale
        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM() ; j++) {
                this.vacant.put(i+","+j, this.init_vacant.get(i+","+j));
                this.getGrid()[i][j] = this.getInitial_grid()[i][j];
            }
        }
    }
    // Méthode pour calculer le prochain état d'une cellule à la position spécifiée
    @Override
    public int next_etat(int x, int y) {
        // Incrémente l'état actuel de la cellule modulo le nombre de familles, ou la réinitialise à 0
        return this.getGrid()[y][x] < this.nombre_famille ? this.getGrid()[y][x] +  1: 0;
    }
}
