import gui.GUISimulator;

import java.awt.*;

// Classe abstraite représentant un jeu basé sur une grille cellulaire
public abstract class CellularGame implements Game {
    // Grille représentant l'état initial du jeu
    private final int[][] initial_grid;
    // Dimensions de la grille
    private final int n;
    private final int m;
    // Grille représentant l'état actuel du jeu
    private int[][] grid;
    // Dernières coordonnées de la souris
    private int lastX = 0;
    private int lastY = 0;

    // Indique si le jeu a commencé (utilisé pour initialiser la grille initiale)
    private boolean start = false;

    // Constructeur de la classe CellularGame

    public CellularGame(int n, int m) {
        this.grid = new int[n][m];
        this.initial_grid = new int[n][m];
        this.n = n;
        this.m = m;
    }

    // Initialise une cellule avec un état spécifié
    public void make_alive(int y, int x, int etat) {
        this.grid[y][x] = etat;
        if (!start) {
            this.initial_grid[y][x] = etat;
        }
    }

    // Calcule le nombre de voisins d'une cellule avec un état spécifié
    public int calcul_voisin(int i, int j, int etat) {
        start = true;
        int voisin = 0;
        // Calcul des coordonnées des voisins
        int v1 = i == 0 ? this.getN() - 1 : i - 1;
        int v2 = i == this.getN() - 1 ? 0 : i + 1;
        int v3 = j == 0 ? this.getM() - 1 : j - 1;
        int v4 = j == this.getM() - 1 ? 0 : j + 1;

        // Vérification des états des voisins
        voisin += grid[v1][j] == etat ? 1 : 0;
        voisin += grid[v1][v3] == etat ? 1 : 0;
        voisin += grid[v1][v4] == etat ? 1 : 0;

        voisin += grid[v2][j] == etat ? 1 : 0;
        voisin += grid[v2][v3] == etat ? 1 : 0;
        voisin += grid[v2][v4] == etat ? 1 : 0;

        voisin += grid[i][v3] == etat ? 1 : 0;
        voisin += grid[i][v4] == etat ? 1 : 0;

        return voisin;
    }

    // Réinitialise l'état du jeu à son état initial
    public void reInit() {
        start = false;
        for (int i = 0; i < this.n; i++) {
            if (this.m >= 0) System.arraycopy(this.initial_grid[i], 0, this.grid[i], 0, this.m);
        }
    }

    // Méthode abstraite : met à jour l'état du jeu (doit être implémentée par les sous-classes)
    public abstract void update();

    // Méthode abstraite : retourne la couleur d'une cellule à la position spécifiée
    public abstract Color getColor(int i, int j);

    // Retourne la grille actuelle du jeu
    public int[][] getGrid() {
        return grid;
    }

    // Initialise la grille avec une nouvelle configuration
    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    // Retourne la grille initiale du jeu
    public int[][] getInitial_grid() {
        return initial_grid;
    }

    // Retourne la hauteur de la grille
    public int getN() {
        return n;
    }

    // Retourne la largeur de la grille
    public int getM() {
        return m;
    }

    // Dessine l'état actuel du jeu sur l'interface graphique
    public void draw(GUISimulator gui) {
        gui.reset();

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                gui.addGraphicalElement(new gui.Rectangle(
                        10 + j * (gui.getPanelWidth() / this.n),
                        10 + i * (gui.getPanelHeight() / this.m),
                        Color.GRAY,
                        this.getColor(i, j),
                        gui.getPanelHeight() / m));
            }
        }
    }

    // Méthode de l'interface Game : initialise un événement d'action pour le jeu
    @Override
    public void setUpEvent(EventManager eventManager) {
        eventManager.addEvent(new ActionEvent(0, eventManager, 0, this));
    }

    // Méthode de l'interface Game : gère le clic de souris à la position spécifiée
    @Override
    public void handleClick(int x, int y, int PanelX, int PanelY) {
        int block_width = (PanelX / this.n);
        int block_height = (PanelY / this.n);

        int gridX = x / block_width;
        int gridY = y / block_height;
        if (gridX < this.m && gridY < this.n) {
            make_alive(gridY, gridX, next_etat(gridX, gridY));
        }

    }

    // Méthode de l'interface Game : gère le mouvement de la souris à la position spécifiée
    @Override
    public void handleMouseMotion(int x, int y, GUISimulator gui) {

        int block_width = (gui.getPanelWidth() / this.n);
        int block_height = (gui.getPanelHeight() / this.n);

        int gridX = x / block_width;
        int gridY = y / block_height;
        if (gridX < this.m && gridY < this.n) {
            if (lastX != gridX || lastY != gridY) {
                gui.addGraphicalElement(new gui.Rectangle(
                        10 + lastX * (gui.getPanelWidth() / this.n),
                        10 + lastY * (gui.getPanelHeight() / this.m),
                        Color.GRAY,
                        this.getColor(lastY, lastX),
                        gui.getPanelHeight() / m));
                gui.addGraphicalElement(new gui.Rectangle(
                        10 + gridX * (gui.getPanelWidth() / this.n),
                        10 + gridY * (gui.getPanelHeight() / this.m),
                        Color.RED,
                        new Color(0.5f, 0f, 0f, 0),
                        gui.getPanelHeight() / m - 1));

                lastX = gridX;
                lastY = gridY;
            }


        }
    }

    // Méthode abstraite : retourne l'état suivant d'une case
    public abstract int next_etat(int x, int y);
}
