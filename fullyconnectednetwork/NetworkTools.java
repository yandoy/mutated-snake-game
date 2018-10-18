package fullyconnectednetwork;

public class NetworkTools {

  //returns a doubles 1D array with random inputs
  public static double[] createRandomArray(int size, double lower_bound,
      double upper_bound) {
    if (size < 1) {
      return null;
    }
    double[] ar = new double[size];
    for (int i = 0; i < size; i++) {
      ar[i] = randomValue(lower_bound, upper_bound);
    }
    return ar;
  }

  //returns a doubles 2D array with random inputs
  public static double[][] createRandomArray(int sizeX, int sizeY,
      double lower_bound, double upper_bound) {
    if (sizeX < 1 || sizeY < 1) {
      return null;
    }
    double[][] ar = new double[sizeX][sizeY];
    for (int i = 0; i < sizeX; i++) {
      ar[i] = createRandomArray(sizeY, lower_bound, upper_bound);
    }
    return ar;
  }

  //returns a random value with a given upper and lower bound
  public static double randomValue(double lower_bound, double upper_bound) {
    return Math.random() * (upper_bound - lower_bound) + lower_bound;
  }
}
