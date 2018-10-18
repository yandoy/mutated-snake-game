package game.gui;

import game.Game;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class UserInterface implements Runnable {

  private JFrame frame;
  private Game game;
  private DrawingBoard drawingBoard;
  private int sideLength;

  //constructs the user interface with a give game and sideLength
  public UserInterface(Game game, int sideLength) {
    this.game = game;
    this.sideLength = sideLength;
  }

  //sets up the window so its visible to user
  @Override
  public void run() {
    this.frame = new JFrame("Worm Game");
    int width = (game.getWidth() + 1) * sideLength + 10;
    int height = (game.getHeight() + 2) * sideLength + 10;
    frame.setPreferredSize(new Dimension(width, height));
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    createComponents(frame.getContentPane());

    frame.pack();
    frame.setVisible(true);
  }

  //adds the drawing board to the given container
  public void createComponents(Container container) {
    drawingBoard = new DrawingBoard(game, sideLength);
    container.add(drawingBoard);
  }

  //returns the drawing board
  public Updatable getUpdatable() {
    return drawingBoard;
  }
}
