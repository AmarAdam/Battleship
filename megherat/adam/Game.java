package megherat.adam;

import java.util.Scanner;

public class Game {

	Scanner sc = new Scanner(System.in);
	private Grid activeGrid;
	private Grid passiveGrid;

	Game(int size) {
		// when a new game is created, 2 Board are created for each player
		setActiveGrid(new Grid(1, size)); 	// A Board only take player number in
										// arguments
		setPassiveGrid(new Grid(2, size));
	}

	public void clearThat() {
		for (int i = 0; i < 50; i++) {
			System.out.println("");
		}
		System.out.println("_____________________________________");
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

	// This function will ask user for a valid position
}
