import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Schelling extends CellularGame {

    private HashMap<String, Boolean> vacant;

    private HashMap<String, Boolean> init_vacant;
    private int k;
    private int nombre_famille;

    private Color[] colors;

    private int[] repartition;
    private Random random;
    public Schelling(int n, int m, int k, int nombre_famille, Color[] c, int[] proba){
        super(n, m);
        this.nombre_famille = nombre_famille;
        this.colors = c;
        this.k = k;
        this.vacant = new HashMap<>();
        this.init_vacant = new HashMap<>();
        this.repartition = proba;
        this.random = new Random();

        this.create_map();
    }

    public void create_map(){
        int totalCells = this.getN() * this.getM();
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

    @Override
    public void update() {
        HashMap<String, Boolean> cache_vacant = new HashMap<>();
        int[][] cache_grid = new int[this.getN()][this.getM()];

        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM(); j++) {
                int etat = this.getGrid()[i][j];
                if(etat != 0) {
                    int voisin = calcul_voisin(i, j, etat);
                    if (voisin < k) {
                        String coordVacante = trouverHabitationVacanteAleatoire(cache_vacant);
                        if (coordVacante != null) {
                            String[] coord = coordVacante.split(",");
                            int newRow = Integer.parseInt(coord[0]);
                            int newCol = Integer.parseInt(coord[1]);

                            cache_grid[newRow][newCol] = etat;
                            cache_grid[i][j] = 0;


                            cache_vacant.put(coordVacante, false);
                            cache_vacant.put(i + "," + j, true);
                        }
                    } else {
                        cache_grid[i][j] = this.getGrid()[i][j];
                        cache_vacant.put(i + "," + j, false);
                    }
                }
            }
        }

        for (String coord :cache_vacant.keySet()) {
            vacant.put(coord, cache_vacant.get(coord));
        }
        this.setGrid(cache_grid);
    }

    private String trouverHabitationVacanteAleatoire(HashMap<String, Boolean> cache_vacant) {
        ArrayList<String> habitationsVacantes = new ArrayList<>();


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
        if (!habitationsVacantes.isEmpty()) {
            int indexAleatoire = random.nextInt(habitationsVacantes.size());
            return habitationsVacantes.get(indexAleatoire);
        }

        return null;
    }
    @Override
    public Color getColor(int i, int j) {
        int etat = this.getGrid()[i][j];

        return etat == 0 ? Color.white : colors[etat-1];
    }

    @Override
    public void reInit() {
        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM() ; j++) {
                this.vacant.put(i+","+j, this.init_vacant.get(i+","+j));
                this.getGrid()[i][j] = this.getInitial_grid()[i][j];
            }
        }
    }

    @Override
    public int next_etat(int x, int y) {
        return this.getGrid()[y][x] == 0 ? 1 : 0;
    }
}
