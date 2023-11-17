import gui.GUISimulator;

// Interface représentant un jeu
public interface Game {
    // Réinitialise l'état du jeu à son état initial
    void reInit();

    // Met à jour l'état du jeu (logique du jeu)
    void update();

    // Dessine l'état actuel du jeu sur l'interface graphique
    void draw(GUISimulator gui);

    // Initialise la gestion des événements pour le jeu en associant un gestionnaire d'événements
    void setUpEvent(EventManager eventManager);

    // Gère le clic de souris à la position spécifiée dans la fenêtre de jeu
    void handleClick(int x, int y, int panelX, int panelY);

    // Gère le mouvement de la souris à la position spécifiée sur l'interface graphique
    void handleMouseMotion(int x, int y, GUISimulator gui);
}