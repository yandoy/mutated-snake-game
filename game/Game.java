package game;

import game.gui.Updatable;
import geneticalgorithm.GeneticAlgorithm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;

public class Game extends Timer implements ActionListener {
  private int games = 0;
  private int width;
  private int height;
  private int SPEED;
  private Updatable updatable;
  private ArrayList<Worm> worms;
  private int wormsAlive;
  private GeneticAlgorithm algorithm;
  private Random random;

  //constructs a game, here the settings of the game can be altered
  // the speed, nutation rate and number of survivors)
  public Game(int height, int width, int numOfWorms) {
    super(10, null);

    this.height = height;
    this.width = width;
    this.algorithm = new GeneticAlgorithm();
    this.algorithm.MUTATION_RATE = 0.2;
    this.algorithm.MUTATION_STRENGTH = 0.3;
    this.algorithm.AMOUNT_SURVIVORS = 70;
    this.SPEED = 50;
    this.random = new Random();

    this.worms = new ArrayList<>();
    for (int i = 0; i < numOfWorms; i++) {
      worms.add(new Worm(width / 2, height / 2,
          Direction.DOWN));
    }
    restarting();
    addActionListener(this);
  }

  //resets all the worms and the apples of the game
  public void restarting() {
    this.wormsAlive = worms.size();
    for (Worm w : worms) {
      w.resetWorm(width / 2, height / 2, Direction.values()[random.nextInt(3)]);
      w.resetApple(width, height);
    }
  }

  //depending on the speed set this method will run, here the physics of the
  // game are implemented
  @Override
  public void actionPerformed(ActionEvent e) {
    for (Worm w : worms) {
      if (w.isAlive() == true) {
        w.move();
        w.increaseScore();

        //neuron output
        int neuronFired = 0;
        for (int i = 0; i < w.getNetwork().calculate(getInput(w)).length; i++) {
          neuronFired = w.getNetwork().calculate(getInput(w))[i] >
              w.getNetwork()
                  .calculate(getInput(w))[neuronFired] ? i : neuronFired;
        }

        //depending on the neuron output select a direction
        w.setDirection(Direction.values()[neuronFired]);

        //physics of the game
        if (w.runsIntoApple()) {
          w.grow();
          while (w.runsIntoApple()) {
            w.resetApple(width, height);
          }
        } else if (w.runsIntoItself() || w.wormHead().getX() == this.width
            || w.wormHead().getX() <= 0 || w.wormHead().getY() == this.height
            || w.wormHead().getY() <= 0
            || Math.pow(w.getLength(),4) < w.getTimeAlive()) {

          w.kill();
          wormsAlive--;
        }
        setDelay(SPEED);
        updatable.update();
      }
    }
    resetGame();
  }

  //if no worms are alive it resets the game
  public void resetGame(){
    if (wormsAlive <= 0) {
      System.out.println("GAME: " + games++ +"\n Best Snake Length: "
          + lengthOfBestWorm());

      algorithm.evolve(worms);
      restarting();
    }
  }

  //returns the longest worm
  public int lengthOfBestWorm(){
    worms.sort(new Comparator<Worm>() {
      @Override
      public int compare(Worm o1, Worm o2) {
        if (o1.getLength() > o2.getLength()){
          return -1;
        }
        if (o1.getLength() < o2.getLength()){
          return 1;
        }
        return 0;
      }
    });
    return worms.get(0).getLength();
  }

  //returns the inputs to the first layer of the NN
  public double[] getInput(Worm worm) {
    double[] input = new double[8];
    //check if it will run into itself or the walls
    if (worm.runsIntoItselfFuture(Direction.UP) || worm.wormHead().getY() - 1
        < 0){
      input[0] = 1;
    }
    else {
      input[0] = 0;
    }

    if (worm.runsIntoItselfFuture(Direction.DOWN) || worm.wormHead().getY() + 1
        == this.height){
      input[1] = 1;
    }
    else {
      input[1] = 0;
    }

    if (worm.runsIntoItselfFuture(Direction.LEFT) || worm.wormHead().getX() - 1
        < 0){
      input[2] = 1;
    }
    else {
      input[2] = 0;
    }

    if (worm.runsIntoItselfFuture(Direction.RIGHT) || worm.wormHead().getX() + 1
        == this.width){
      input[3] = 1;
    }
    else {
      input[3] = 0;
    }

    //checks if the snake is going the best direction possible
    if (bestDirection(worm) != Direction.UP){
      input[4] = 1;
    }
    else {
      input[4] = 0;
    }
    if (bestDirection(worm) != Direction.DOWN){
      input[5] = 1;
    }
    else {
      input[5] = 0;
    }
    if (bestDirection(worm) != Direction.LEFT){
      input[6] = 1;
    }
    else {
      input[6] = 0;
    }
    if (bestDirection(worm) != Direction.RIGHT){
      input[7] = 1;
    }
    else {
      input[7] = 0;
    }
    return input;
  }

  //returns the direction closest to the apple
  public Direction bestDirection(Worm worm){
    if (worm.wormHead().getX() - worm.getApple().getX() > 0){
      return Direction.LEFT;
    }
    else if (worm.wormHead().getX() - worm.getApple().getX() < 0) {
      return Direction.RIGHT;
    }
    else if (worm.wormHead().getY() - worm.getApple().getY() > 0) {
      return Direction.UP;
    }
    return Direction.DOWN;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public List<Worm> getWorms(){
    return worms;
  }

  public void setUpdatable(Updatable updatable) {
    this.updatable = updatable;
  }
}
