package game;

public enum Direction {
  UP(0),
  RIGHT(1),
  DOWN(2),
  LEFT(3);
  public final int value;

  Direction(int value){
    this.value = value;
  }
}
