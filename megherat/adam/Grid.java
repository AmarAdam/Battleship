package megherat.adam;

import java.util.*;
import megherat.adam.*;

public class Grid {

	Scanner sc = new Scanner(System.in);
	private int shipLengths[] = { 5, 4, 3, 3, 2 };
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	private ArrayList<String> shots = new ArrayList<String>(); //tirs envoyes
	private ArrayList<String> stepsided = new ArrayList<String>(); // tirs encaisses
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";

	
	boolean inGame = true;
	private int NbrPlayer, size;

	Grid(int playerNbr, int size) {
		setPlayerNbr(playerNbr);
		setinGame(true);
		setSize(size);
		
		System.out.println("");
		System.out.println("The Grid of Amiral " + getPlayerNbr() + " is create !");
		System.out.println("");
		
		int i = 0;

		String startSquare = "";
		String endSquare = "";

		while (i < getShipLenghts().length) {
			System.out.println("Give me coordonates for a " + getShipLenghts()[i]
					+ " squares WarShip.");
			System.out.println("Give me coordonates of the starting position of your Ship, Amiral ! ( like this : A-J / 1-10 )");

			startSquare = this.askPosition();
			System.out.println("Give me coordonates of the ending position of your Ship, Amiral ! ( like this : A-J / 1-10 )");
			endSquare = this.askPosition();
			
			if ((endSquare.charAt(0) < startSquare.charAt(0)) || (Character.getNumericValue(endSquare.charAt(1)) < Character.getNumericValue(startSquare.charAt(1)))) {
				String switchString = endSquare;
				endSquare = startSquare;
				startSquare = switchString;
			}
		
			int len = LengthShip(startSquare, endSquare);
			if (getShipLenghts()[i] == len) {
				if (placeShip(startSquare, endSquare, len, i)) {
					i++;
				} else {
					System.out
							.println("\tWARNING You can't Amiral, one of our WarShip is already in position there...");
					System.out.println("\tWARNING Give me another position please !\n");
				}
			} else {
				System.out.println("\tWARNING You made a mistake on the LENGTH of your Ship Amiral !");
				System.out.println("\tWARNING Give me another position please !\n");
			}
		}

	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
	public void DisplayGrid() {
		
		ArrayList<String> usedCells = getUsed();
		System.out.println("");
		System.out.println(" \t Our WarShips :\t\t \t Our shots :\t\t");
		System.out.println("");
		System.out.println(" \t( A B C D E F G H I J\t)  \t( A B C D E F G H I J\t)");

		for (int i = 1; i < getSize() + 1; i++) {
			System.out.print((i) + "\t( ");
			for (int j = 0; j < getSize(); j++) {
				String testCell = Character.toString((char) (65 + j))
						+ String.valueOf((i));
				if (usedCells.indexOf(testCell) >= 0) {
					System.out.print("o");
				} else if (stepsided.indexOf(testCell) >= 0) {
					System.out.print("x");
				} else {
					System.out.print("-");
				}
				System.out.print(" ");
			}
			System.out.print("\t) " + i + "\t( ");
			for (int j = 0; j < getSize(); j++) {
				String testCell = Character.toString((char) (65 + j))
						+ String.valueOf((i));
				if (isHit(testCell)) {
					System.out.print("x");
				} else {
					System.out.print("-");
				}
				System.out.print(" ");
			}
			System.out.println("\t)");
		}
		System.out.println("");
	}

	// fonction de tir, check tous les ship
	// qui ont une case touchee
	public String Fire(String shootSquare) {

		// First of all, we got to save that this cell has been shot

		stepsided.add(shootSquare);
		String result = "miss";

		// la boucle prend les bateaux un par un
		for (Ship ship : getShips()) {
			// verifie si la case est touche pour chaque bateau
			result = ship.FireOpponent(shootSquare);
			// si un bateau a cet case, et aucune autre, on renvoi kill, le bateau est coule
			if (result == "kill") {
				getShips().remove(ship);
				System.out.println(getShips().size() + " length WarShip down ! for Amiral "
						+ getPlayerNbr());
				if (getShips().size() == 0) {
					setinGame(false);
				}
				return result;
			}
			// sinon, un bateau a cette case, on renvoi seulement hit 
			else if (result == "hit") {
				return result;
			}
		}
		// si aucun bateau n'a la case passee en parametre on renvoi miss
		return result;
	}

	public boolean inGame() {
		return inGame;
	}

	public int getPlayerNbr() {
		return NbrPlayer;
	}

	public int[] getShipLenghts() {
		return this.shipLengths;
	}

	public void setShipLengths(int[] shipLengths) {
		this.shipLengths = shipLengths;
	}

	public ArrayList<String> getShots() {
		return shots;
	}

	public void setShots(ArrayList<String> shots) {
		this.shots = shots;
	}

	public void addShot(String cell) {
		this.shots.add(cell);
	}

	// renvoi true si la case est libre
	private boolean isOK(String testSquare) {
		boolean result = true;
		for (Ship ship : getShips()) { // pour chaque ship
			// si un shi utilise deja cette square
			if (ship.testSquare(testSquare)) {
				result = false;
			}
		}
		return result;
	}

	// demande un position
	public String askPosition() {

		String square = "";
		ArrayList<String> usable = FreeSquares();

		square = sc.next();
		while (usable.indexOf(square) < 0) {
			System.out.println("Incorrect position Amiral, please give another one, valid if possible...");
			square = sc.next();
		}
		return square;
	}

	private boolean placeShip(String startCell, String endCell, int len, int i) {
		boolean result = false;
		result = true;
		ArrayList<String> array = getArray(startCell, endCell, len);
		for (String testBox : array) {
			if (!(isOK(testBox))) {
				result = false;
			}
		}
		if (result) {
			addShip(array);
		}
		return result;
	}

	private int LengthShip(String startCell, String endCell) {
		char xStart = startCell.charAt(0);

		int yStart = Character.getNumericValue(startCell.charAt(1));

		char xEnd = endCell.charAt(0);

		int yEnd = Character.getNumericValue(endCell.charAt(1));

		String tmp = " ";

		if (startCell.length() == 3) {
			tmp = Character.toString(startCell.charAt(1))
					+ Character.toString(startCell.charAt(2));
			yStart = Integer.parseInt(tmp);
		}

		if (endCell.length() == 3) {
			tmp = Character.toString(endCell.charAt(1))
					+ Character.toString(endCell.charAt(2));
			yEnd = Integer.parseInt(tmp);
		}

		int len = 0;
		
		if (xStart != xEnd && yStart != yEnd) {
			
			len = -1;
		
		} else if (xStart == xEnd && yStart == yEnd) {
			
			len = 0;
		
		} else if (xStart == xEnd) {
		
			return ((yEnd - yStart) + 1);
		
		} else if (yStart == yEnd) {
		
			return ((xEnd - xStart) + 1);
		}
		return len;
	}

	private ArrayList<String> getArray(String startCell, String endCell, int len) {
		char xStart = startCell.charAt(0);
		;
		int yStart = Character.getNumericValue(startCell.charAt(1));
		;
		char xEnd = endCell.charAt(0);
		;
		int yEnd = Character.getNumericValue(endCell.charAt(1));
		;
		String tmp = " ";

		if (startCell.length() == 3) {
			tmp = Character.toString(startCell.charAt(1))
					+ Character.toString(startCell.charAt(2));
			yStart = Integer.parseInt(tmp);
		}

		if (endCell.length() == 3) {
			tmp = Character.toString(endCell.charAt(1))
					+ Character.toString(endCell.charAt(2));
			yEnd = Integer.parseInt(tmp);
		}

		ArrayList<String> array = new ArrayList<String>();
		String builder;

		if (xStart == xEnd) {
			for (int i = 0; i < len; i++) {
				builder = String.valueOf(xStart) + String.valueOf(yStart + i);
				array.add(builder);
			}
		} else if (yStart == yEnd) {
			for (int i = 0; i < len; i++) {
				builder = String.valueOf(xStart) + String.valueOf(yStart);
				array.add(builder);
				xStart++;
			}
		}
		System.out.println("");
		return array;
	}

	private boolean isHit(String testCell) {
		boolean result = false;
		if (getShots().indexOf(testCell) >= 0) { // verifie si la case a la meme localisation
													
			result = true;
		}
		return result;
	}

	public ArrayList<String> FreeSquares() {
		ArrayList<String> freeSquares = new ArrayList<String>();
		for (int i = 1; i < getSize() + 1; i++) {
			for (int j = 0; j < getSize(); j++) {
				String freeSquare = Character.toString((char) (65 + j))
						+ String.valueOf((i));
				freeSquares.add(freeSquare);
			}
		}
		return freeSquares;
	}

	private ArrayList<String> getUsed() {
		ArrayList<String> usedSquares = new ArrayList<String>();
		for (Ship ship : getShips()) {
			for (String square : ship.getCells()) {
				usedSquares.add(square);
			}
		}
		return usedSquares;
	}

	private void setinGame(boolean inGame) {
		this.inGame = inGame;
	}

	private void setPlayerNbr(int NbrPlayer) {
		this.NbrPlayer = NbrPlayer;
	}

	private ArrayList<Ship> getShips() {
		return this.ships;
	}

	private void addShip(ArrayList<String> array) {
		this.ships.add(new Ship(array));
	}
}
