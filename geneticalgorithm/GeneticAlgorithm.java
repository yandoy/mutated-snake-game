package geneticalgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class GeneticAlgorithm {

  public double MUTATION_RATE;
  public double MUTATION_STRENGTH;
  public double AMOUNT_SURVIVORS;

  public Random random = new Random();

  //evolves the clients by passing the best clients into the next generation
  // and mutating the next generation
  public <T extends GeneticClient> void evolve(ArrayList<T> clients) {

    clients.sort(new Comparator<T>() {
      @Override
      public int compare(T o1, T o2) {

        if (o1.getScore() > o2.getScore())
          return -1;
        if (o2.getScore() > o1.getScore())
          return 1;
        return 0;

      }
    });

    ArrayList<T> selection = this.selection(clients);

    crossover(clients, selection);

    clients.addAll(0, selection);

    mutate(clients);
  }

  //returns the clients with the highest score
  private <T extends GeneticClient> ArrayList<T> selection(
      ArrayList<T> clients) {
    ArrayList<T> selection = new ArrayList<>();

    for (int i = 0; i < Math.min(AMOUNT_SURVIVORS, clients.size()); i++) {
      selection.add(clients.get(i));
    }

    for (T o : selection) {
      clients.remove(o);
    }
    return selection;
  }

  //sets the newborns of the generation to the most successful of the
  // previous generation
  private <T extends GeneticClient> void crossover(ArrayList<T> newborns,
      ArrayList<T> survivors) {

    for (T newborn : newborns) {
      T parentA = survivors.get((int)(random.nextFloat() * survivors.size()));

      for (int i = 1; i < newborn.getNetwork().NETWORK_SIZE; i++) {
        newborn.getNetwork().weights[i] = copyArray(
            parentA.getNetwork().weights[i]);
        newborn.getNetwork().bias[i] = copyArray(
            parentA.getNetwork().bias[i]);
      }
    }
  }

  //mutates the clients list weights and biases
  private <T extends GeneticClient> void mutate(ArrayList<T> clients) {
    for (T c : clients) {
      for (int i = 1; i < c.getNetwork().NETWORK_SIZE; i++) {
        mutateArray(c.getNetwork().weights[i], MUTATION_RATE,
            MUTATION_STRENGTH);
        mutateArray(c.getNetwork().bias[i], MUTATION_RATE, MUTATION_STRENGTH);
      }
    }
  }

  //changes the values of the given 2D doubles array at a given rate and a
  // given strength
  public void mutateArray(double[][] values, double rate,
      double strength) {
    for (int i = 0; i < values.length; i++) {
      for (int n = 0; n < values[0].length; n++) {
        if (random.nextFloat() < rate) {
          values[i][n] += random.nextGaussian() * strength;
        }
      }
    }
  }

  //changes the values of the given 1D doubles array at a given rate
  // and a given strength
  public void mutateArray(double[] values, double rate,
      double strength) {
    for (int i = 0; i < values.length; i++) {
      if (random.nextFloat() < rate) {
        values[i] += random.nextGaussian() * strength;
      }
    }
  }

  //makes a copy of a 2D doubles array
  public static double[][] copyArray(double[][] values) {
    double[][] out = new double[values.length][values[0].length];
    for (int i = 0; i < values.length; i++) {
      for (int n = 0; n < values[0].length; n++) {
        out[i][n] = values[i][n];
      }
    }
    return out;
  }

  //makes a copy of a 1D doubles array
  public static double[] copyArray(double[] values) {
    double[] out = new double[values.length];
    for (int i = 0; i < values.length; i++) {
      out[i] = values[i];
    }
    return out;
  }
}