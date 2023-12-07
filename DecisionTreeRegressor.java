import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

class DecisionTreeRegressor {

    private TreeNode root;

    public DecisionTreeRegressor() {
        this.root = null;
    }

    public void fit(List<double[]> X, List<Double> y) {
        this.root = buildTree(X, y, 0);  // Start building the tree recursively
    }

    public double predict(double[] sample) {
        if (root != null) {
            return predictRecursive(sample, root);
        }
        throw new IllegalStateException("Decision tree has not been trained. Call fit() first.");
    }

    private TreeNode buildTree(List<double[]> X, List<Double> y, int depth) {
        if (stoppingCriteriaMet(X, y, depth)) {
            // Create a leaf node with the mean of target values as the prediction
            TreeNode leafNode = new TreeNode();
            leafNode.setLeftChild(null);
            leafNode.setRightChild(null);
            leafNode.setPrediction(calculateMean(y));
            return leafNode;
        }

        // Find the best split
        SplitResult bestSplit = findBestSplit(X, y);

        if (bestSplit.getVarianceReduction() <= 0.0) {
            // If no improvement in variance reduction, create a leaf node
            TreeNode leafNode = new TreeNode();
            leafNode.setLeftChild(null);
            leafNode.setRightChild(null);
            leafNode.setPrediction(calculateMean(y));
            return leafNode;
        }

        // Split the data
        List<double[]> leftX = bestSplit.getLeftX();
        List<Double> leftY = bestSplit.getLeftY();
        List<double[]> rightX = bestSplit.getRightX();
        List<Double> rightY = bestSplit.getRightY();

        // Recursively build left and right subtrees
        TreeNode node = new TreeNode();
        node.setSplitFeatureIndex(bestSplit.getFeatureIndex());
        node.setSplitValue(bestSplit.getSplitValue());
        node.setLeftChild(buildTree(leftX, leftY, depth + 1));
        node.setRightChild(buildTree(rightX, rightY, depth + 1));

        return node;
    }

    private double predictRecursive(double[] sample, TreeNode node) {
        if (node.isLeaf()) {
            return node.getPrediction();
        } else {
            int splitFeatureIndex = node.getSplitFeatureIndex();
            double splitValue = node.getSplitValue();

            if (sample[splitFeatureIndex] <= splitValue) {
                return predictRecursive(sample, node.getLeftChild());
            } else {
                return predictRecursive(sample, node.getRightChild());
            }
        }
    }

    private boolean stoppingCriteriaMet(List<double[]> X, List<Double> y, int depth) {
        // Implement your stopping criteria here, e.g., based on depth or minimum samples in a node.
        return depth >= 5 || X.size() <= 1;
    }

    private double calculateMean(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    private SplitResult findBestSplit(List<double[]> X, List<Double> y) {
        int numFeatures = X.get(0).length;
        int dataSize = X.size();

        int bestFeatureIndex = -1;
        double bestSplitValue = 0.0;
        double bestVarianceReduction = Double.NEGATIVE_INFINITY;
        List<double[]> bestLeftX = new ArrayList<>();
        List<Double> bestLeftY = new ArrayList<>();
        List<double[]> bestRightX = new ArrayList<>();
        List<Double> bestRightY = new ArrayList<>();

        for (int featureIndex = 0; featureIndex < numFeatures; featureIndex++) {
            List<DataPoint> sortedData = getSortedData(X, y, featureIndex);

            for (int i = 0; i < dataSize - 1; i++) {
                double splitValue = (sortedData.get(i).getValue() + sortedData.get(i + 1).getValue()) / 2.0;

                List<double[]> leftX = sortedData.subList(0, i + 1)
                        .stream()
                        .map(DataPoint::getX)
                        .collect(Collectors.toList());

                List<Double> leftY = sortedData.subList(0, i + 1)
                        .stream()
                        .map(DataPoint::getY)
                        .collect(Collectors.toList());

                List<double[]> rightX = sortedData.subList(i + 1, dataSize)
                        .stream()
                        .map(DataPoint::getX)
                        .collect(Collectors.toList());

                List<Double> rightY = sortedData.subList(i + 1, dataSize)
                        .stream()
                        .map(DataPoint::getY)
                        .collect(Collectors.toList());

                double varianceReduction = calculateVarianceReduction(y, leftY, rightY);

                if (varianceReduction > bestVarianceReduction) {
                    bestVarianceReduction = varianceReduction;
                    bestFeatureIndex = featureIndex;
                    bestSplitValue = splitValue;
                    bestLeftX = leftX;
                    bestLeftY = leftY;
                    bestRightX = rightX;
                    bestRightY = rightY;
                }
            }
        }

        return new SplitResult(bestFeatureIndex, bestSplitValue, bestLeftX, bestLeftY, bestRightX, bestRightY, bestVarianceReduction);
    }

    private double calculateVarianceReduction(List<Double> parentY, List<Double> leftY, List<Double> rightY) {
        double parentVariance = calculateVariance(parentY);
        double leftVariance = calculateVariance(leftY);
        double rightVariance = calculateVariance(rightY);

        int parentSize = parentY.size();
        int leftSize = leftY.size();
        int rightSize = rightY.size();

        double weightedAverageVariance = (leftSize * leftVariance + rightSize * rightVariance) / parentSize;

        return parentVariance - weightedAverageVariance;
    }


    private List<DataPoint> getSortedData(List<double[]> X, List<Double> y, int featureIndex) {
        List<DataPoint> data = new ArrayList<>();

        // Combine features and labels into DataPoint objects
        for (int i = 0; i < X.size(); i++) {
            double[] features = X.get(i);
            double label = y.get(i);
            data.add(new DataPoint(features, label, featureIndex));
        }

        // Sort DataPoint objects based on the specified feature index
        data.sort(Comparator.comparingDouble(dp -> dp.getX()[featureIndex]));

        return data;
    }

    private double calculateVariance(List<Double> values) {
        int size = values.size();

        if (size == 0) {
            return 0.0;  // Return 0 variance for an empty list
        }

        double mean = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        // Calculate the sum of squared differences from the mean
        double sumSquaredDifferences = values.stream()
                .mapToDouble(value -> Math.pow(value - mean, 2))
                .sum();

        // Calculate the variance
        double variance = sumSquaredDifferences / size;

        return variance;
    }

    //Print the tree in breadthfirst order for inspection
    public void printTreeBreadthFirst() {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                if (node.isLeaf()) {
                    System.out.print("(Leaf: " + node.getPrediction() + ")  ");
                } else {
                    System.out.print("(" + node.getSplitFeatureIndex() +
                            " -> " + node.getSplitValue() + " ) ");

                    queue.add(node.getLeftChild());
                    queue.add(node.getRightChild());
                }
            }
            System.out.println(); // Move to the next level in the output
        }
    }

    private static class DataPoint implements Comparable<DataPoint> {
        private final double[] x;
        private final double y;
        private final int featureIndex;

        public DataPoint(double[] x, double y, int featureIndex) {
            this.x = x;
            this.y = y;
            this.featureIndex = featureIndex;
        }

        public double getValue() {
            return x[featureIndex];
        }

        public double[] getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        @Override
        public int compareTo(DataPoint other) {
            // Compare data points based on the feature value
            return Double.compare(this.getValue(), other.getValue());
        }
    }
}
