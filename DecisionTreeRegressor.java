import java.util.*;
import java.util.stream.Collectors;

class DecisionTreeRegressor {
    //change to private later
    public TreeNode root;

    public void fit(List<double[]> X, List<Double> y) {
        root = buildTree(X, y);
    }

    public double predict(double[] sample) {
        return predictRecursive(sample, root);
    }

    private TreeNode buildTree(List<double[]> X, List<Double> y) {
        // Create a new tree node
        TreeNode node = new TreeNode();

        // Stopping criteria - check if the node should be a leaf
        if (stoppingCriteriaMet(X, y)) {
            // If the stopping criterion is met, make the node a leaf   
            node.setPrediction(y.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
            return node;
        }

        // If the stopping criterion is not met, find the best split point
        SplitResult bestSplit = findBestSplit(X, y);
        node.setSplitFeatureIndex(bestSplit.getFeatureIndex());
        node.setSplitValue(bestSplit.getSplitValue());

        // Recursively build left and right subtrees
        List<double[]> leftX = bestSplit.getLeftX();
        List<Double> leftY = bestSplit.getLeftY();
        node.setLeftChild(buildTree(leftX, leftY));

        List<double[]> rightX = bestSplit.getRightX();
        List<Double> rightY = bestSplit.getRightY();
        node.setRightChild(buildTree(rightX, rightY));

        return node;
    }


    public void printTree(TreeNode n){
        if(n.isLeaf()){
            System.out.println(root.getPrediction());
        }else{
            printTree(n.getLeftChild());
            printTree(n.getRightChild());
        }   
    }


    private boolean stoppingCriteriaMet(List<double[]> X, List<Double> y) {
        // Calculate the variance before the split
        double varianceBeforeSplit = calculateVariance(y);
    
        // Perform a potential split and calculate the variance after the split
        SplitResult bestSplit = findBestSplit(X, y);
        List<Double> leftY = bestSplit.getLeftY();
        List<Double> rightY = bestSplit.getRightY();
        double varianceAfterSplit = calculateVariance(leftY) + calculateVariance(rightY);
    
        // Compare the reduction in variance with a threshold
        double reductionInVariance = varianceBeforeSplit - varianceAfterSplit;
        double varianceReductionThreshold = 0.01; // You can adjust this threshold based on your needs
    
        return reductionInVariance < varianceReductionThreshold;
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

    

    private SplitResult findBestSplit(List<double[]> X, List<Double> y) {
        if (X.isEmpty() || y.isEmpty()) {
            // Handle the case when the lists are empty
            return new SplitResult(-1, 0.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }
        
        
        int numFeatures = X.get(0).length;
        int dataSize = X.size();
    
        int bestFeatureIndex = -1;
        double bestSplitValue = 0.0;
        double bestVarianceReduction = Double.NEGATIVE_INFINITY;
        List<double[]> bestLeftX = new ArrayList<>();
        List<Double> bestLeftY = new ArrayList<>();
        List<double[]> bestRightX = new ArrayList<>();
        List<Double> bestRightY = new ArrayList<>();
    
        // Iterate over each feature
        for (int featureIndex = 0; featureIndex < numFeatures; featureIndex++) {
            // Sort data points based on the current feature
            List<DataPoint> sortedData = getSortedData(X, y, featureIndex);
    
            // Iterate over potential split points
            for (int i = 0; i < dataSize - 1; i++) {
                double splitValue = (sortedData.get(i).getValue() + sortedData.get(i + 1).getValue()) / 2.0;
    
                // Split the data into left and right subsets
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
    
                // Calculate variance reduction
                double varianceBeforeSplit = calculateVariance(y);
                double varianceAfterSplit = (calculateVariance(leftY) + calculateVariance(rightY)) / 2.0;
                double varianceReduction = varianceBeforeSplit - varianceAfterSplit;
    
                // Update the best split if the current split is better
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
    
        return new SplitResult(bestFeatureIndex, bestSplitValue, bestLeftX, bestLeftY, bestRightX, bestRightY);
    }

    public String printInfo(List<double[]> dl){
        String s = "";

        for(int i = 0; i < dl.size(); i++){
            for(int j = 0; j < dl.get(i).length; j++){
                s += dl.get(i)[j] + " ";
            }
            s += "\n";
        }
        
        return s;
    }

    public String printInfo2(List<Double> dl){
        String s = "";

        for(int i = 0; i < dl.size(); i++){
            
                s += dl.get(i).toString() + " ";
            
            s += "\n";
        }
        
        return s;
    }




    // private List<DataPoint> getSortedData(List<double[]> X, List<Double> y, int featureIndex) {
    //     // Helper method to sort data points based on a specific feature
    //     List<DataPoint> dataPoints = new ArrayList<>();
    //     for (int i = 0; i < X.size(); i++) {
    //         dataPoints.add(new DataPoint(X.get(i), y.get(i), featureIndex));
    //     }
    //     dataPoints.sort(null);
    //     return dataPoints;
    // }


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