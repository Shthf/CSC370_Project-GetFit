import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiagnosticSystem {
    private List<double[]> X;
    private List<Double> y_chinups;
    private List<Double> y_situps;
    private List<Double> y_jumps;
    private RandomForestRegressor randomForest_Chinups;
    private RandomForestRegressor randomForest_Situps;
    private RandomForestRegressor randomForest_Jumps;
    private double prediction_chinups;
    private double prediction_situps;
    private double prediction_jumps;

    public DiagnosticSystem(User u){
        X = new ArrayList<>();
        y_chinups = new ArrayList<>();
        y_situps = new ArrayList<>();
        y_jumps = new ArrayList<>();
        
        setPhysiologicalDataset_X();
        setOutputDataset_y();

        //Train the forest using k fold validation and output the MSE
        System.out.println(" ---------------------------------------------");
        System.out.println("Testing Report:");
        getMSE(0);
        System.out.println(" ---------------------------------------------");
        getMSE(1);
        System.out.println(" ---------------------------------------------");
        getMSE(2);
        System.out.println(" ---------------------------------------------");
        
        randomForest_Chinups = new RandomForestRegressor(30, 10);
        randomForest_Chinups.fit(this.X, this.y_chinups);
        randomForest_Situps = new RandomForestRegressor(100, 11);
        randomForest_Situps.fit(this.X, this.y_situps);
        randomForest_Jumps = new RandomForestRegressor(100, 12);
        randomForest_Jumps.fit(this.X, this.y_jumps);
        
        
        // Make predictions
        double[] newSample = {u.getWeight(), u.getWaist(), u.getPulse()};
        prediction_chinups = randomForest_Chinups.predict(newSample);
        prediction_situps = randomForest_Situps.predict(newSample);
        prediction_jumps = randomForest_Jumps.predict(newSample);

        // System.out.println("Printing Trees:");
        // randomForest.printForest();
        // System.out.println("MSE for chinup_forest:");
        // getMSE(y_chinups);
        // System.out.println("MSE for situp_forest:");
        // getMSE(y_situps);
        // System.out.println("MSE for jump_forest:");
        // getMSE(y_jumps);
    }

    public double getMSE(int index){
        
        List<Double> y = new ArrayList<>();

        if(index == 0){
            y = y_chinups;
        }else if(index == 1){
            y = y_situps;
        }else{
            y = y_jumps;
        }
    
        // Number of folds for cross-validation
        int numFolds = 10;

        // Calculate the size of each fold
        int foldSize = X.size() / numFolds;

        double totalMSE = 0.0;

        for (int fold = 0; fold < numFolds; fold++) {
            // Calculate start and end indices for the current fold
            int startIdx = fold * foldSize;
            int endIdx = (fold == numFolds - 1) ? X.size() : (fold + 1) * foldSize - 1;

            // Split the dataset into training and validation sets
            List<double[]> X_train = new ArrayList<>();
            List<Double> y_train = new ArrayList<>();
            List<double[]> X_valid = new ArrayList<>();
            List<Double> y_valid = new ArrayList<>();

            for (int i = 0; i < X.size(); i++) {
                if (i >= startIdx && i < endIdx) {
                    X_valid.add(X.get(i));
                    y_valid.add(y.get(i));
                } else {
                    X_train.add(X.get(i));
                    y_train.add(y.get(i));
                }
            }

            //train
            RandomForestRegressor forest = new RandomForestRegressor(30, 12);
            forest.fit(X_train, y_train);

            // Evaluate the model on the validation set
            double mse = calculateMeanSquaredError(forest, X_valid, y_valid);
            totalMSE += mse;

            System.out.println("Fold " + (fold + 1) + " MSE: " + mse);
        }
        // Calculate the average MSE across all folds
        double averageMSE = totalMSE / numFolds;
        System.out.println("Average MSE: " + averageMSE);
        return averageMSE;
    }

    public int getPrediction_chinups(){
        return (int)prediction_chinups + 1;
    }

    public int getPrediction_Situps(){
        return (int)prediction_situps + 1;
    }

    public int getPrediction_Jumps(){
        return (int)prediction_jumps + 1;
    }

    private double calculateMeanSquaredError(RandomForestRegressor forest, List<double[]> X, List<Double> y) {
        int numSamples = X.size();
        double sumSquaredErrors = 0.0;
    
        for (int i = 0; i < numSamples; i++) {
            double[] sample = X.get(i);
            double trueValue = y.get(i);
            double predictedValue = forest.predict(sample);
    
            // Calculate squared error for this sample
            double squaredError = Math.pow(trueValue - predictedValue, 2);
            sumSquaredErrors += squaredError;
        }
    
        // Calculate Mean Squared Error
        return sumSquaredErrors / numSamples;
    }


    private void setPhysiologicalDataset_X(){
        File dataset1 = new File("linnerud_physiological.txt");
        File dataset2 = new File("makeup_physiological.txt");

        try{
            Scanner sc = new Scanner(dataset1);
            sc.nextLine();

            int weight = 0;
            int waist = 0;
            int pulse = 0;

            //read data from original dataset and store into result
            while(sc.hasNext()){
                weight = sc.nextInt();
                waist = sc.nextInt();
                pulse = sc.nextInt();
                X.add(new double[]{weight, waist, pulse});
            }
            sc.close();

            sc = new Scanner(dataset2);
            sc.nextLine();

            //read data from makeup dataset and store into result
            while(sc.hasNext()){
                weight = sc.nextInt();
                waist = sc.nextInt();
                pulse = sc.nextInt();
                X.add(new double[]{weight, waist, pulse});
            }
            sc.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void setOutputDataset_y(){

        File file = new File("linnerud_exercise.txt");
        File file2 = new File("makeup_exercise.txt");

        double data1 = 0;
        double data2 = 0;
        double data3 = 0;
        try{
            Scanner sc = new Scanner(file);
            sc.nextLine();

            while(sc.hasNext()){
                data1 = sc.nextInt();
                y_chinups.add(data1);
                data2 = sc.nextInt();
                y_situps.add(data2);
                data3 = sc.nextInt();
                y_jumps.add(data3);
            }
            sc.close();
            sc = new Scanner(file2);
            sc.nextLine();

            while(sc.hasNext()){
                data1 = sc.nextInt();
                y_chinups.add(data1);
                data2 = sc.nextInt();
                y_situps.add(data2);
                data3 = sc.nextInt();
                y_jumps.add(data3);
            }
            sc.close();                
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
