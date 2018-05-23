package megherat.adam;

import java.util.Scanner;

public class Game {

	Scanner sc = new Scanner(System.in);
	private Grid activeGrid;
	private Grid passiveGrid;

	Game(int size) {
		// quand le nouveau jeu est cree, deux grilles sont crees pour chaque joueur
		setActiveGrid(new Grid(1, size)); 	// la grille prend le numero d'un joueur en parametre
										
		setPassiveGrid(new Grid(2, size));
	}

	public void clearThat() {
		for (int i = 0; i < 50; i++) {
			System.out.println("");
		}
		System.out.println("");
	}

	public Grid getActiveGrid() {
		return this.activeGrid;
	}

	public void setActiveGrid(Grid grid) {
		this.activeGrid = grid;
	}

	public Grid getPassiveGrid() {
		return this.passiveGrid;
	}

	public void setPassiveGrid(Grid grid) {
		this.passiveGrid = grid;
	}

	public void switchGrid() {
		Grid switchGrid = this.getActiveGrid();
		this.setActiveGrid(this.getPassiveGrid());
		this.setPassiveGrid(switchGrid);
	}


}

