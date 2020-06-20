package wordGame;

/**  @author Pooja Renganathan, Sasha Devi-Bangar, Shivani Mohan
  	 @version 12/10/19
*/

public class StartGame {
	
	public static void main(String[] args) {
		Controller con = new ControllerImpl();
		TUI myTUI = new TUI(con);
	}

}
