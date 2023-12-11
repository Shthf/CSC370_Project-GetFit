

public class Planner{
	static int CU = 0;
	static int SU = 1;
	static int JJ = 2;
	static int REST = 0;
    public int daily_chinups;
    public int daily_situps;
    public int daily_Jumpjacks;
    public int prediction_Chinups;
    public int prediction_Situps;
    public int prediction_Jumps;
    public int[][] fitnessPlan= new int[30][3];
    public int currentDay=0;
    
    public User thisUser;

    //constructor
    
    public Planner(){
    	
    }
    
    public Planner(User u){
    	thisUser = u;
    	DiagnosticSystem diagnosticSystem = new DiagnosticSystem(u);
        prediction_Chinups = diagnosticSystem.getPrediction_chinups();
        prediction_Situps = diagnosticSystem.getPrediction_Situps();
        prediction_Jumps = diagnosticSystem.getPrediction_Jumps();
	System.out.println();
	System.out.println("Predicted Chinups: " + prediction_Chinups);
	System.out.println("Predicted Situps: " + prediction_Situps);
	System.out.println("Predicted Jumps: " + prediction_Jumps);
	System.out.println();
        System.out.println("Initiate set goals.");
        this.setGoals();
        System.out.println("Goals set.");
        this.setDailyChinups(thisUser);
        this.setDailyJumpjacks(thisUser);
        this.setDailySitups(thisUser);
        this.setFitnessPlan();
        
    }
    
    public void setGoals(){				//Creating goals for each exercise based on user performance 
    	if(thisUser.getSitups() <10 ){  //and their expected performance.
    		thisUser.setGoalS(10);				//An extreme beginner's goal.
    	}
    	else if(thisUser.getSitups() < (int)(prediction_Situps*(.75))){
    		thisUser.setGoalS((int)(thisUser.getSitups()*(1.15)));		//A beginner's goal.
    	}
    	else if(thisUser.getSitups() < (int)(prediction_Situps*(1.25))){
    		thisUser.setGoalS((int)(thisUser.getSitups()*(1.3)));		//An intermediate goal.
    	}
    	else{
    		thisUser.setGoalS((int)(thisUser.getSitups()*(1.2)));		//A high performance goal.
    	}
    	
    	if(thisUser.getChinups() <2 ){
    		thisUser.setGoalC(3);				//An extreme beginner's goal.
    	}
    	else if(thisUser.getChinups() < (int)(prediction_Chinups*(.75))){
    		thisUser.setGoalC((int)(thisUser.getChinups()*(1.15)));		//A beginner's goal.
    	}
    	else if(thisUser.getChinups() < (int)(prediction_Chinups*(1.25))){
    		thisUser.setGoalC((int)(thisUser.getChinups()*(1.3)));		//An intermediate goal.
    	}
    	else{
    		thisUser.setGoalC((int)(thisUser.getChinups()*(1.2)));		//A high performance goal.
    	}
    	
    	if(thisUser.getJumpjacks() <25 ){
    		thisUser.setGoalJJ(30);				//An extreme beginner's goal.
    	}
    	else if(thisUser.getJumpjacks() < (int)(prediction_Jumps*(.75))){
    		thisUser.setGoalJJ((int)(thisUser.getJumpjacks()*(1.15)));		//A beginner's goal.
    	}
    	else if(thisUser.getJumpjacks() < (int)(prediction_Jumps*(1.25))){
    		thisUser.setGoalJJ((int)(thisUser.getJumpjacks()*(1.3)));		//An intermediate goal.
    	}
    	else{
    		thisUser.setGoalJJ((int)(thisUser.getJumpjacks()*(1.2)));		//A high performance goal.
    	}
    }

    //Data setter methods
    //Create daily amounts for each exercise as a percentage of the goal.
    public void setDailyChinups(User u){
        this.daily_chinups = (int)(u.getGoal_Chinups()*(.35));
    }
    
    public void setDailySitups(User u){
        this.daily_situps = (int)(u.getGoal_Situps()*(.4));
    }
    
    public void setDailyJumpjacks(User u){
        this.daily_Jumpjacks = (int)(u.getGoal_Jumpjacks()*(.5));
    }
    
    //Create the fitness plan for a 30 day span. Increasing the amount of daily exercise each week
    //by +10% of the initial daily amount.
    public void setFitnessPlan(){
    	for(int i = 0; i<30;i++){
    		if(i<5){
	    		this.fitnessPlan[i][CU] = this.daily_chinups;
	    		this.fitnessPlan[i][SU] = this.daily_situps;
	    		this.fitnessPlan[i][JJ] = this.daily_Jumpjacks;
    		}
    		else if(i<7){
    			this.fitnessPlan[i][CU] = REST;
	    		this.fitnessPlan[i][SU] = REST;
	    		this.fitnessPlan[i][JJ] = REST;
    		}
    		else if(i<12){
    			this.fitnessPlan[i][CU] = this.daily_chinups+(int)(this.daily_chinups*(.1));
	    		this.fitnessPlan[i][SU] = this.daily_situps+(int)(this.daily_situps*(.1));
	    		this.fitnessPlan[i][JJ] = this.daily_Jumpjacks+(int)(this.daily_Jumpjacks*(.1));
    		}
    		else if(i<14){
    			this.fitnessPlan[i][CU] = REST;
	    		this.fitnessPlan[i][SU] = REST;
	    		this.fitnessPlan[i][JJ] = REST;
    		}
    		else if(i<19){
    			this.fitnessPlan[i][CU] = this.daily_chinups+(int)(this.daily_chinups*(.2));
	    		this.fitnessPlan[i][SU] = this.daily_situps+(int)(this.daily_situps*(.2));
	    		this.fitnessPlan[i][JJ] = this.daily_Jumpjacks+(int)(this.daily_Jumpjacks*(.2));
    		}
    		else if(i<21){
    			this.fitnessPlan[i][CU] = REST;
	    		this.fitnessPlan[i][SU] = REST;
	    		this.fitnessPlan[i][JJ] = REST;
    		}
    		else if(i<27){
    			this.fitnessPlan[i][CU] = this.daily_chinups+(int)(this.daily_chinups*(.3));
	    		this.fitnessPlan[i][SU] = this.daily_situps+(int)(this.daily_situps*(.3));
	    		this.fitnessPlan[i][JJ] = this.daily_Jumpjacks+(int)(this.daily_Jumpjacks*(.3));
    		}
    		else{
    			
    		}
    		
    	}
    	System.out.println("Fitness Plan Created");
    }
    
    
    
}
