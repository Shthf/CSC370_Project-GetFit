import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomForestRegressor {

    public List<DecisionTreeRegressor> trees;
    private int numTrees;
    private Random random;

    public RandomForestRegressor(int numTrees, long seed) {
        this.numTrees = numTrees;
        this.trees = new ArrayList<>();
        this.random = new Random(seed);
    }

    public void fit(List<double[]> X, List<Double> y) {
        for (int i = 0; i < numTrees; i++) {
            DecisionTreeRegressor tree = new DecisionTreeRegressor();
            List<double[]> bootstrapX = new ArrayList<>();
            List<Double> bootstrapY = new ArrayList<>();

            // Create a bootstrap sample
            for (int j = 0; j < X.size(); j++) {
                int randomIndex = random.nextInt(X.size());
                bootstrapX.add(X.get(randomIndex));
                bootstrapY.add(y.get(randomIndex));
            }

            // Train the tree on the bootstrap sample
            tree.fit(bootstrapX, bootstrapY);
            trees.add(tree);
        }
    }

    public double predict(double[] sample) {
        double sumPredictions = 0.0;

        for (DecisionTreeRegressor tree : trees) {
            sumPredictions += tree.predict(sample);
        }

        return sumPredictions / numTrees;
    }

    public void printForest(){
        for(DecisionTreeRegressor tree: trees){
            tree.printTreeBreadthFirst();
        }
    }
}
