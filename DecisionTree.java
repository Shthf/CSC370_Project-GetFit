import java.util.ArrayList;
import java.util.List;

public class DecisionTree {
    private DecisionTreeNode root;

    public void train(List<double[]> data, int numFeatures) {
        root = buildTree(data, numFeatures);
    }

    public double predict(double[] instance) {
        return root.predict(instance);
    }

    private DecisionTreeNode buildTree(List<double[]> data, int numFeatures) {
        if (data.isEmpty()) {
            // Create a leaf node with the mean value of the target variable
            return new DecisionTreeNode(-1, -1, getMeanValue(data));
        }

        double currentVariance = calculateVariance(data);
        double bestVarianceReduction = 0;
        int bestFeatureIndex = -1;
        double bestThreshold = -1;

        for (int featureIndex = 0; featureIndex < numFeatures; featureIndex++) {
            List<Double> featureValues = extractFeatureValues(data, featureIndex);
            for (double threshold : featureValues) {
                List<double[]> leftSubset = new ArrayList<>();
                List<double[]> rightSubset = new ArrayList<>();

                for (double[] instance : data) {
                    if (instance[featureIndex] <= threshold) {
                        leftSubset.add(instance);
                    } else {
                        rightSubset.add(instance);
                    }
                }

                double varianceReduction = currentVariance - calculateWeightedAverageVariance(leftSubset, rightSubset);
                if (varianceReduction > bestVarianceReduction) {
                    bestVarianceReduction = varianceReduction;
                    bestFeatureIndex = featureIndex;
                    bestThreshold = threshold;
                }
            }
        }

        if (bestVarianceReduction > 0) {
            DecisionTreeNode node = new DecisionTreeNode(bestFeatureIndex, bestThreshold, getMeanValue(data));
            node.setLeftChild(buildTree(extractSubset(data, bestFeatureIndex, bestThreshold, true), numFeatures));
            node.setRightChild(buildTree(extractSubset(data, bestFeatureIndex, bestThreshold, false), numFeatures));
            return node;
        } else {
            // Create a leaf node with the mean value of the target variable
            return new DecisionTreeNode(-1, -1, getMeanValue(data));
        }
    }

    private double calculateVariance(List<double[]> data) {
        double mean = getMeanValue(data);
        double sumSquaredDifferences = 0;

        for (double[] instance : data) {
            double difference = instance[instance.length - 1] - mean;
            sumSquaredDifferences += difference * difference;
        }

        return sumSquaredDifferences / data.size();
    }

    private double calculateWeightedAverageVariance(List<double[]> leftSubset, List<double[]> rightSubset) {
        double totalSize = leftSubset.size() + rightSubset.size();
        double leftWeight = leftSubset.size() / totalSize;
        double rightWeight = rightSubset.size() / totalSize;

        double leftVariance = calculateVariance(leftSubset);
        double rightVariance = calculateVariance(rightSubset);

        return leftWeight * leftVariance + rightWeight * rightVariance;
    }

    private List<Double> extractFeatureValues(List<double[]> data, int featureIndex) {
        List<Double> featureValues = new ArrayList<>();
        for (double[] instance : data) {
            featureValues.add(instance[featureIndex]);
        }
        return featureValues;
    }

    private List<double[]> extractSubset(List<double[]> data, int featureIndex, double threshold, boolean isLeft) {
        List<double[]> subset = new ArrayList<>();
        for (double[] instance : data) {
            if ((isLeft && instance[featureIndex] <= threshold) || (!isLeft && instance[featureIndex] > threshold)) {
                subset.add(instance);
            }
        }
        return subset;
    }

    private double getMeanValue(List<double[]> data) {
        double sum = 0;
        for (double[] instance : data) {
            sum += instance[instance.length - 1]; // Assuming the target variable is in the last column
        }
        return sum / data.size();
    }

}