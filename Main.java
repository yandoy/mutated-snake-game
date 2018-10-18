import game.Game;
import game.gui.UserInterface;
import javax.swing.SwingUtilities;

public class Main {
  public static void main(String[] args){
    Game game = new Game(50, 50,1000);

    UserInterface ui = new UserInterface(game, 5);
    SwingUtilities.invokeLater(ui);

    while (ui.getUpdatable() == null) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException ex) {
        System.out.println("The drawing board hasn't been created yet.");
      }
    }
    game.setUpdatable(ui.getUpdatable());
    game.start();
  }

}
