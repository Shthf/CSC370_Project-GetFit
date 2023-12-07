//the User class is for user's input data
public class User{
    private double weight;
    private double waist;
    private int pulse;
    

    //Constructor
    public User(double we, double wa, int pl){
        this.weight = we;
        this.waist = wa;
        this.pulse = pl;
        
    }

    //setter methods
    private void setWeight(double we){
        this.weight = we;
    };
    private void setWaist(double wa){
        this.waist = wa;
    };
    private void setPulse(int pl){
        this.pulse = pl;
    };

    //getter methods
    public double getWeight(){
        return weight;
    };
    public double getWaist(){
        return waist;
    };
    public double getPulse(){
        return pulse;
    };
}
