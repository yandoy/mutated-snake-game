package game.gui;

import game.Game;
import game.Piece;
import game.Worm;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class DrawingBoard extends JPanel implements Updatable {
  private Game game;
  private int pieceLength;

  //constructs a drawing board with a given game and pieceLength
  public DrawingBoard(Game game, int pieceLength){
    this.game = game;
    this.pieceLength = pieceLength;
  }

  //paints window with a background each worm in the generation that is
  // still alive and its given apple
  @Override
  public void paintComponent(Graphics graphics){
    super.paintComponent(graphics);
    super.setBackground(Color.black);
    super.setBorder(BorderFactory.createMatteBorder(1,1,1,1,
        Color.gray));

    for (Worm w: game.getWorms()){
      if (w.isAlive()) {
        for (Piece p : w.getPieces()) {
          graphics.setColor(new Color(100, 250, 100, 100));
          graphics.fill3DRect(p.getX() * pieceLength, p.getY() * pieceLength,
              pieceLength, pieceLength, true);
        }
        graphics.setColor(Color.red);
        graphics.fillOval(pieceLength * w.getApple().getX(), pieceLength *
            w.getApple().getY(), pieceLength, pieceLength);
      }
    }
  }

  //updates the drawing board
  public void update(){
    super.repaint();
  }
}
