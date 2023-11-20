class DecisionTreeNode {
    private int featureIndex;
    private double threshold;
    private double predictedValue; // For regression
    private DecisionTreeNode leftChild;
    private DecisionTreeNode rightChild;

    public DecisionTreeNode(int featureIndex, double threshold, double predictedValue) {
        this.featureIndex = featureIndex;
        this.threshold = threshold;
        this.predictedValue = predictedValue;
    }

    public void setLeftChild(DecisionTreeNode left){
        this.leftChild = left;
    }

    public void setRightChild(DecisionTreeNode right){
        this.rightChild = right;
    }

    public DecisionTreeNode getLeftChild() {
        return leftChild;
    }

    public DecisionTreeNode getRightChild() {
        return rightChild;
    }

    public double predict(double[] instance) {
        if (leftChild == null && rightChild == null) {
            return predictedValue;
        }
        if (instance[featureIndex] <= threshold) {
            return leftChild.predict(instance);
        } else {
            return rightChild.predict(instance);
        }
    }
}