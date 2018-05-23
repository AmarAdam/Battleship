package megherat.adam;

import java.util.ArrayList;

public class Ship {
	
	private int length;
	private ArrayList<String> squares = new ArrayList<String>();

	Ship(ArrayList<String> cells) {

		for (String cell : cells) {
			addSquare(cell);
		}

		System.out.println("\nBattleShip created\n");

	}

	// Renvoi true si la case testé est occupée
	public boolean testSquare(String square) {
		boolean result = false;
		for (String locs : squares) {
	
			if (locs.indexOf(square) >= 0) {
				result = true;
			}
		}
		// Sinon la case n'est pas occupé donc 
		return result;
	}

	// Tell this ship a cell have been hit
	public String FireOpponent(String testSquare) {
		// vérifie si la case est occupé
		int index = getSquares().indexOf(testSquare);
		String result = "miss";
		// si la case est utilisé
		if (index >= 0) {
			// on supprime la case, elle est touchée, donc détruite
			getSquares().remove(index);
			// si le bateau n'a plus de case, il est donc détruit
			if (getSquares().size() == 0) {
				result = "kill";
			}
			// sinon il est touché
			else {
				result = "hit";
			}
		}
		// sinon on return miss
		return result;
	}
	
	public ArrayList<String> getSquares() {
		return this.squares;
	}
	
	public ArrayList<String> getCells() {
		return this.squares;
	}

	public void setSquares(ArrayList<String> squares) {
		this.squares = squares;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	private void addSquare(String square) {
		this.squares.add(square);
	}

}
