//the entire class will be set to public and will change in the future
public class User{
    private double weight;
    private double waist;
    private int pulse;
    private int chinups;
    private int situps;
    private int jumpjacks;
    private int goal_chinups;
    private int goal_situps;
    private int goal_jumpjacks;
    

    //Constructor
    public User(){
        
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
    public void setChinups(int cu){
        this.chinups = cu;
    };
    public void setSitups(int su){
        this.situps = su;
    };
    public void setJumpjacks(int jj){
        this.jumpjacks = jj;
    };
    public void setGoalC(int in){
    	this.goal_chinups = in;
    };
    public void setGoalS(int in){
    	this.goal_situps = in;
    };
    public void setGoalJJ(int in){
    	this.goal_jumpjacks = in;
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
    public int getSitups(){
    	return situps;
    };
    public int getChinups(){
    	return chinups;
    };
    public int getJumpjacks(){
    	return jumpjacks;
    };
    public int getGoal_Situps(){
    	return goal_situps;
    };
    public int getGoal_Chinups(){
    	return goal_chinups;
    };
    public int getGoal_Jumpjacks(){
    	return goal_jumpjacks;
    };
    

    
}
