//the entire class wiil be set to public and will change in the future
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
    public void setWeight(double we){
        this.weight = we;
    };
    public void setWaist(double wa){
        this.waist = wa;
    };
    public void setPulse(int pl){
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
