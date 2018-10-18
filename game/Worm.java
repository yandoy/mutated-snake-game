package game;

import fullyconnectednetwork.Network;
import geneticalgorithm.GeneticClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worm implements GeneticClient, Cloneable {

  private int x;
  private int y;
  private Direction direction;
  private List<Piece> worm;
  private boolean grow;
  private Random random;

  private double score;
  private boolean alive;
  private int timeAlive;
  private Apple apple;
  private Network network;

  //constructs a worm with a given initial position and direction also gives
  // it a neural network
  public Worm(int initialX, int initialY, Direction initialDir){
    this.x = initialX;
    this.y = initialY;
    this.alive = true;
    this.direction = initialDir;
    this.worm = new ArrayList<>();
    worm.add(new Piece(initialX,initialY));

    this.random = new Random();

    this.network = new Network(8, 10, 10, 4);
  }

  //kills the worm and gives it its final score dependent on its length and
  // time alive
  public void kill(){
    this.alive = false;
    this.score = Math.pow(getLength(),2) + timeAlive/5;
  }

  //increases the score of the worm
  public void increaseScore(){
    this.timeAlive++;
  }

  //returns true if worm is alive
  public boolean isAlive() {
    return alive;
  }

  //resets all the parameters of the worm except its neural network
  public void resetWorm(int x, int y, Direction dir){
    this.alive = true;
    this.worm = new ArrayList<>();
    worm.add(new Piece(x,y));
    this.direction = dir;
    this.score = 0;
    this.timeAlive = 0;
  }

  //resets the position of the apple
  public void resetApple(int width, int height){
    int randX = random.nextInt(width);
    int randY = random.nextInt(height);
    this.apple = new Apple(randX,randY);
  }

  //sets the direction of the worm
  public void setDirection(Direction dir) {
    this.direction = dir;
  }

  //moves the worm one piece in the direction the worm is currently in
  public void move() {
    int newX = wormHead().getX();
    int newY = wormHead().getY();

    if (direction == Direction.UP){
      newY--;
    }
    else if (direction == Direction.DOWN){
      newY++;
    }
    else if (direction == Direction.LEFT){
      newX--;
    }
    else if (direction == Direction.RIGHT){
      newX++;
    }

    if (getLength()>2 && !grow){
      worm.remove(0);
    }

    if (grow == true){
      grow = false;
    }
    worm.add(new Piece(newX,newY));
  }

  //sets grow to true
  public void grow(){
    grow = true;
  }

  //returns true if the worms head has ran into the apple
  public boolean runsIntoApple(){
    for (Piece p: worm){
      if (p.runsInto(apple)){
        return true;
      }
    }
    return false;
  }

  //returns true if a piece if the worm has ran into itself
  public boolean runsIntoItself(){
    for (int i = 0; i < getLength() - 1; i++) {
      if (wormHead().getX() == worm.get(i).getX() && wormHead().getY() == worm.get
          (i).getY()) {
        return true;
      }
    }
    return false;
  }

  //returns true if moving in the given direction will cause the worm to run
  // into itself
  public boolean runsIntoItselfFuture(Direction dir){

    for (int i = 0; i < getLength() - 1; i++) {
      if (dir == Direction.UP && wormHead().getX() == worm.get(i).getX()
            && wormHead().getY() - 4 == worm.get(i).getY()) {
          return true;
      }
      if (dir == Direction.DOWN && wormHead().getX() == worm.get(i).getX()
          && wormHead().getY() + 4 == worm.get(i).getY()) {
        return true;
      }
      if (dir == Direction.LEFT && wormHead().getX() - 4 == worm.get(i).getX()
          && wormHead().getY() == worm.get(i).getY()) {
        return true;
      }
      if (dir == Direction.RIGHT && wormHead().getX() + 4 == worm.get(i).getX()
          && wormHead().getY() == worm.get(i).getY()) {
        return true;
      }
    }
    return false;
  }

  //returns the head of the worm
  public Piece wormHead(){
    return worm.get(worm.size()-1);
  }


  public Network getNetwork() {
    return network;
  }

  public List<Piece> getPieces(){
    return worm;
  }

  public int getLength(){
    return worm.size();
  }

  public int getTimeAlive() {
    return timeAlive;
  }

  public double getScore() {
    return score;
  }

  public Apple getApple(){return apple;}

}
