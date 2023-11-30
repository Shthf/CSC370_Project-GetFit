import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class RandomForestRegressor {
    private List<DecisionTreeRegressor> trees;

    public RandomForestRegressor(int numTrees) {
        trees = new ArrayList<>();
        for (int i = 0; i < numTrees; i++) {
            trees.add(new DecisionTreeRegressor());
        }
    }

    public void fit(List<double[]> X, List<Double> y) {
        for (DecisionTreeRegressor tree : trees) {
            List<double[]> bootstrapSamples = new ArrayList<>();
            List<Double> bootstrapLabels = new ArrayList<>();
            int dataSize = X.size();

            // Create bootstrap samples
            for (int i = 0; i < dataSize; i++) {
                int randomIndex = new Random().nextInt(dataSize);
                bootstrapSamples.add(X.get(randomIndex));
                bootstrapLabels.add(y.get(randomIndex));
            }

            tree.fit(bootstrapSamples, bootstrapLabels);
        }
    }

    public double predict(double[] sample) {
        double sum = 0.0;
        for (DecisionTreeRegressor tree : trees) {
            sum += tree.predict(sample);
        }
        return sum / trees.size(); // Average prediction
    }

    public void printForest(){
        for(int i = 0; i < trees.size(); i++){
            trees.get(i).printTree(trees.get(i).root);
        }
    }
}