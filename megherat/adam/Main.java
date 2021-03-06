package megherat.adam;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("Welcolme in BattleShip !");
			
			int size = 10;
			
			Game newGame = new Game(size);
			String square;
			
			while (newGame.getActiveGrid().inGame() && newGame.getPassiveGrid().inGame()) {
				System.out.println("Amiral " + newGame.getActiveGrid().getPlayerNbr() + ", it's your turn !");
			
				newGame.getActiveGrid().DisplayGrid();

				System.out.println("Amiral, give me coordonates to strike ?");
				
				square = newGame.getActiveGrid().askPosition();
				System.out.println(newGame.getPassiveGrid().Fire(square));
				
				newGame.getActiveGrid().addShot(square);
				newGame.switchGrid();
				
				Thread.sleep(1000);

			
				newGame.clearThat();
				System.out.println("Switching Grid ! Dont look at your opponent's Grid ! ");

				Thread.sleep(1000);
			}
		
		System.out.println("Amiral " + newGame.getPassiveGrid().getPlayerNbr() + ", you WIN ! it was a pleasure to serve at your side...");
		

	}
	
}
