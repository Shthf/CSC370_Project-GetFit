import java.util.List;

public class SplitResult {
    private int featureIndex;
    private double splitValue;
    private List<double[]> leftX;
    private List<Double> leftY;
    private List<double[]> rightX;
    private List<Double> rightY;

    public SplitResult(int featureIndex, double splitValue, List<double[]> leftX, List<Double> leftY,
                       List<double[]> rightX, List<Double> rightY) {
        this.featureIndex = featureIndex;
        this.splitValue = splitValue;
        this.leftX = leftX;
        this.leftY = leftY;
        this.rightX = rightX;
        this.rightY = rightY;
    }

    // Getters...

    public int getFeatureIndex() {
        return featureIndex;
    }

    public double getSplitValue() {
        return splitValue;
    }

    public List<double[]> getLeftX() {
        return leftX;
    }

    public List<Double> getLeftY() {
        return leftY;
    }

    public List<double[]> getRightX() {
        return rightX;
    }

    public List<Double> getRightY() {
        return rightY;
    }
}