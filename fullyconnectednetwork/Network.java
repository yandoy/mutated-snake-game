package fullyconnectednetwork;

public class Network {

  public double[][] output;
  public double[][][] weights;
  public double[][] bias;

  public final int[] NETWORK_LAYER_SIZES;
  public final int INPUT_SIZE;
  public final int OUTPUT_SIZE;
  public final int NETWORK_SIZE;

  //constructs a neural network with arbitrary layers and neurons initiating
  // the weights and biases randomly
  public Network(int... NETWORK_LAYER_SIZES) {
    this.NETWORK_LAYER_SIZES = NETWORK_LAYER_SIZES;
    this.INPUT_SIZE = NETWORK_LAYER_SIZES[0];
    this.NETWORK_SIZE = NETWORK_LAYER_SIZES.length;
    this.OUTPUT_SIZE = NETWORK_LAYER_SIZES[NETWORK_SIZE-1];

    this.output = new double[NETWORK_SIZE][];
    this.weights = new  double[NETWORK_SIZE][][];
    this.bias = new double[NETWORK_SIZE][];

    for (int i = 0; i < NETWORK_SIZE; i++ ){
      this.output[i] = new double[NETWORK_LAYER_SIZES[i]];
      if (i > 0){
        this.weights[i] = NetworkTools.createRandomArray
            (NETWORK_LAYER_SIZES[i], NETWORK_LAYER_SIZES[i-1], -2,
                2);
        this.bias[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i],
            -2, 2);
      }
    }
  }

  //calculates the value of each neuron and using those values returns the
  // values of the output layer neurons
  public double[] calculate(double... input){
    if (input.length != INPUT_SIZE) return null;
    this.output[0] = input;

    for (int  layer = 1; layer < NETWORK_SIZE; layer++ ){
      for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++){

        double sum = bias[layer][neuron];
        for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer-1];
            prevNeuron++){
          sum += output[layer-1][prevNeuron] *
              weights[layer][neuron][prevNeuron];
        }
        output[layer][neuron] = sigmoid(sum);
      }
    }
    return output[NETWORK_SIZE-1];
  }

  //actuator function we are using is the sigmoid function
  private double sigmoid(double x){
    return 1d/ (1 + Math.exp(-x));
  }

}
