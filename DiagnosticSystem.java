import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiagnosticSystem {
    private List<double[]> X;
    private List<Double> y;
    private RandomForestRegressor randomForest;
    private double prediction;

    public DiagnosticSystem(User u, File f){
        X = getYourFeatures();  
        y = getYourLabels(f); 


        // Create and train the RandomForestRegressor
        randomForest = new RandomForestRegressor(5);
        
        // Set the number of folds
        int numFolds = 5;  

        // Perform cross-validation
        double totalMSE = 0.0;
        for (int fold = 0; fold < numFolds; fold++) {
            // Split the data into training and testing sets for this fold
            List<double[]> trainX = new ArrayList<>();
            List<Double> trainY = new ArrayList<>();
            List<double[]> testX = new ArrayList<>();
            List<Double> testY = new ArrayList<>();

            for (int i = 0; i < X.size(); i++) {
                if (i % numFolds == fold) {
                    testX.add(X.get(i));
                    testY.add(y.get(i));
                } else {
                    trainX.add(X.get(i));
                    trainY.add(y.get(i));
                }
            }

            // Train the model on the training set
            randomForest.fit(trainX, trainY);

            // Evaluate the model on the testing set
            double foldMSE = calculateMeanSquaredError(randomForest, testX, testY);
            //System.out.println("Fold " + (fold + 1) + " MSE: " + foldMSE);

            totalMSE += foldMSE;
        }

        // Calculate and print the average MSE across folds
        // double averageMSE = totalMSE / numFolds;
        // System.out.println("Average MSE: " + averageMSE);


        randomForest.fit(X, y);

        //randomForest.printForest();


        // Make predictions
        double[] newSample = {u.getWeight(), u.getWaist(), u.getPulse()};
        prediction = randomForest.predict(newSample);
    }

    public int getPrediction(){
        return (int)prediction + 1;
    }

    private static double calculateMeanSquaredError(RandomForestRegressor randomForest, List<double[]> X, List<Double> y) {
        // Calculate the mean squared error for the predictions on the given data
        int dataSize = X.size();
        double sumSquaredErrors = 0.0;

        for (int i = 0; i < dataSize; i++) {
            double[] sample = X.get(i);
            double actual = y.get(i);
            double predicted = randomForest.predict(sample);
            double squaredError = Math.pow(actual - predicted, 2);
            sumSquaredErrors += squaredError;
        }

        return sumSquaredErrors / dataSize;
    }

    private static List<double[]> getYourFeatures() {
        List <double[]> dal = new ArrayList<>();

        File physiologicalFile = new File("linnerud_physiological.txt");

        try{
            //reading physiological data
            Scanner sc = new Scanner(physiologicalFile);
            if(sc.nextLine() != null){
                double e1 = 0, e2 = 0, e3 = 0;
                while(sc.hasNext()) {
                    e1 = sc.nextDouble();
                    e2 = sc.nextDouble();
                    e3 = sc.nextDouble();
                    double[] sample = {e1, e2, e3};
                    dal.add(sample);
                } 
            }
            sc.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return dal;
    }

    private static List<Double> getYourLabels(File f) {
        List <Double> dl = new ArrayList<>();

        try{
            //reading physiological data
            Scanner sc = new Scanner(f);
            if(sc.nextLine() != null){
                double d = 0;
                while(sc.hasNext()) {
                    d = sc.nextDouble();
                    
                    dl.add(d);
                } 
            }

            sc.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return dl;
    }
}
