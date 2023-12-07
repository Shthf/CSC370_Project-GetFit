public class TreeNode {

    private double prediction;
    private int splitFeatureIndex;
    private double splitValue;
    private TreeNode leftChild;
    private TreeNode rightChild;

    public TreeNode() {
        this.prediction = 0.0;
        this.splitFeatureIndex = -1;
        this.splitValue = 0.0;
        this.leftChild = null;
        this.rightChild = null;
    }

    // Getters and setters

    public boolean isLeaf() {
        return this.leftChild == null && this.rightChild == null;
    }

    public double getPrediction() {
        return prediction;
    }

    public void setPrediction(double prediction) {
        this.prediction = prediction;
    }

    public int getSplitFeatureIndex() {
        return splitFeatureIndex;
    }

    public void setSplitFeatureIndex(int splitFeatureIndex) {
        this.splitFeatureIndex = splitFeatureIndex;
    }

    public double getSplitValue() {
        return splitValue;
    }

    public void setSplitValue(double splitValue) {
        this.splitValue = splitValue;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }
}
