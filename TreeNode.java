class TreeNode {
    //private boolean isLeaf;
    private double prediction;
    private int splitFeatureIndex;
    private double splitValue;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(){}

    //public TreeNode(){}
    
    // Setter methods
    public void setPrediction(double prediction) {
        this.prediction = prediction;
    }

    public void setSplitFeatureIndex(int splitFeatureIndex) {
        this.splitFeatureIndex = splitFeatureIndex;
    }

    public void setSplitValue(double splitValue) {
        this.splitValue = splitValue;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.left = leftChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.right = rightChild;
    }

    //Getter methods
    public double getPrediction() {
        return prediction;
    }

    public int getSplitFeatureIndex() {
        return splitFeatureIndex;
    }

    public double getSplitValue() {
        return splitValue;
    }

    public TreeNode getLeftChild() {
        return left;
    }

    public TreeNode getRightChild() {
        return right;
    }

    //true if the node is leaf, false otherwise
    public boolean isLeaf() {
        return left == null && right == null;
    }
}