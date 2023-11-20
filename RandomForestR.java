import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomForestR {
    private List<DecisionTree> trees;
    private int numTrees;

    public RandomForestR(int num){
        this.numTrees = num;
        this.trees = new ArrayList<>();
    }

    public void train(List<double[]> data, int numFeatures) {
        for (int i = 0; i < numTrees; i++) {
            List<double[]> subset = createSubset(data);
            DecisionTree tree = new DecisionTree();  // Instantiate your decision tree class
            // Build the decision tree on the subset
            // You need to implement a method in the DecisionTree class for training
            // and adjust the method parameters based on your implementation
            tree.train(subset, numFeatures);
            trees.add(tree);
        }
    }

    public double predict(double[] instance) {
        double sum = 0.0;
        for (DecisionTree tree : trees) {
            sum += tree.predict(instance);
        }
        return sum / numTrees;  // Average the predictions
    }

    private List<double[]> createSubset(List<double[]> data) {
        List<double[]> subset = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < data.size(); i++) {
            int randomIndex = rand.nextInt(data.size());
            subset.add(data.get(randomIndex));
        }
        return subset;
    }
}
