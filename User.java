//the entire class wiil be set to public and will change in the future
public class User{
    public double weight;
    public double waist;
    public int pulse;
    public int max_chinups;
    public int max_situps;
    public int max_jumpjacks;

    //Constructor
    //may need to add error/bound checking
    public User(double we, double wa, int pl, int c, int s, int j){
        this.weight = we;
        this.waist = wa;
        this.pulse = pl;
        this.max_chinups = c;
        this.max_situps = s;
        this.max_jumpjacks = j;
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
    public void setMaxChinups(int c){
        this.max_chinups = c;
    };
    public void setMaxSitups(int s){
        this.max_situps = s;
    };
    public void setMaxJumpjacks(int j){
        this.max_jumpjacks = j;
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
    public int getMaxChinups(){
        return max_chinups;
    };
    public int getMaxSitups(){
        return max_situps;
    };
    public int getMaxJumpjacks(){
        return max_jumpjacks;
    };
}