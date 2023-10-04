public class GameOfLife {
    private int[][] grid;
    private int[][] initial_grid;
    private final int n;
    private final int m;

    public GameOfLife(int n, int m){
        this.grid = new int[n][m];
        this.initial_grid = new int[n][m];
        this.n = n;
        this.m = m;
    }
    public GameOfLife(int[][]grid){
        this.grid = grid.clone();
        this.n = grid.length;
        this.m = grid[0].length;
    }
    public void make_alive(int y, int x){
        this.grid[y][x] = 1;
        this.initial_grid[y][x] = 1;
    }
    public void reInit(){
        for (int i = 0; i < this.n ; i++) {
            for (int j = 0; j < this.m ; j++) {
                grid[i][j] = initial_grid[i][j];
            }
        }
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public void update(){
        int[][] cache_grid = new int[n][m];
        for (int i = 0; i < this.n ; i++) {
            for (int j = 0; j < this.m ; j++) {
                cache_grid[i][j] = grid[i][j];
            }
        }
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                int voisin = 0;

                int v1 = i == 0 ? this.n -1 : i - 1;
                int v2 = i == this.n -1 ? 0 : i + 1;

                int v3 = j == 0 ? this.m -1 : j - 1;
                int v4 = j == this.m -1 ? 0 : j + 1;
                voisin += cache_grid[v1][j];
                voisin += cache_grid[v1][v3];
                voisin += cache_grid[v1][v4];

                voisin += cache_grid[v2][j];
                voisin += cache_grid[v2][v3];
                voisin += cache_grid[v2][v4];


                voisin += cache_grid[i][v3];
                voisin += cache_grid[i][v4];

                grid[i][j] = cache_grid[i][j] == 1 ? (voisin == 2 || voisin == 3) ? 1 : 0 : voisin == 3 ? 1 : 0;
            }
        }
    }

    public int[][] getGrid() {
        return grid;
    }

    public int[][] getInitial_grid() {
        return initial_grid;
    }

}
