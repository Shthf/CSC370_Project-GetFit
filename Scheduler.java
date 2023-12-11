import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class Scheduler extends Planner{
	
	public mainWindow getFitWindow;
	public int stage = 0;
	
	public class mainWindow extends Frame {
	    private TextField inputs;
	    double output;
	    
	    public mainWindow(){}
	    public mainWindow(User u) {
	        setTitle("GetFit");
	        setSize(800, 600);

	       

	        addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(WindowEvent e) {
	                System.exit(0);
	            }
	        });

	        centerWindow(0);
	        setVisible(true);
	    }
	    

	    private void initUI(User u) {
	        setLayout(new BorderLayout());

	        // North Panel
	        Panel northPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
	        northPanel.setBackground(new Color(255, 255, 255));

	        Label getfitLabel = new Label("GetFit");
	        getfitLabel.setFont(new Font("Arial", Font.PLAIN, 20));

	        northPanel.add(getfitLabel);
	        

	        // Center Panel
	        Panel centerPanel = new Panel(new BorderLayout());
	        centerPanel.setBackground(new Color(255, 255, 255));
	        Panel inputPanel = new Panel();
	        Panel dialogue = new Panel();
	        Label errorMsg = new Label("Invalid input please try again.");
	        Label dialogueComponent = new Label("Welcome to GetFit! Please enter your weight in lbs to begin.");
	        dialogue.add(dialogueComponent);
	        inputs = new TextField();
	        inputs.setColumns(19);
	        inputs.setFont(new Font("Arial", Font.PLAIN, 14));

	        Button inputButton = new Button("Enter");
	        inputButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	try{
	            		//System.out.println("button press");
	            		inputPanel.remove(errorMsg);
	            		output = Double.parseDouble(inputs.getText());
	            		//System.out.println(output);
	            		intake(output, u, dialogueComponent);
	            		dialogue.validate();
	            		//System.out.println(dialogueComponent);
	            		if(stage == 6){
	            			removeAll();
	            			printScheduler(u);
	            		}
	            		}
	            	catch(NumberFormatException ef){
	            		inputPanel.add(errorMsg, BorderLayout.SOUTH); 
	            	}
	            }
	        });
	        //inputPanel.setSize(180, 26);
	        Panel space1 = new Panel();
	        space1.add(new Label(""));
	        Panel space2 = new Panel();
	        space2.add(new Label(""));
	        inputPanel.add(space1, BorderLayout.SOUTH);
	        inputPanel.add(space2, BorderLayout.NORTH);
	        inputPanel.add(inputs, BorderLayout.CENTER);
	        inputPanel.add(inputButton, BorderLayout.EAST);
	        centerPanel.add(inputPanel, BorderLayout.CENTER);
	        centerPanel.add(dialogue, BorderLayout.SOUTH);
	        
	        
	        

	        // Adding Panels to Frame
	        add(northPanel, BorderLayout.NORTH);
	        add(centerPanel, BorderLayout.CENTER);
	    }
	    
	    private void intake(double in, User u, Label di){
	    	//System.out.println("intake");
	    	//System.out.println(stage);
	    	if(stage ==0){
    			if(in>-1){
    				u.setWeight(in);
    				//System.out.println(in);
    				stage++;
    				di.setText("Please enter your waist size in inches.");
    			}
    			else{
    				di.setText("Invalid input. Please enter your weight in lbs.");
    			}
    		}
    		else if(stage ==1){
    			if(in>15 && in<80){
    				u.setWaist(in);
    				//System.out.println(in);
    				stage++;
    				di.setText("Please enter your heart rate in beats per minute.");
    			}
    			else{
    				di.setText("Invalid input. Please enter your waist size in inches.");
    			}
    		}else if(stage ==2){
    			if(in>27 && in<104){
    				u.setPulse((int)in);
    				stage++;
    				di.setText("Please enter the maximum amount of chin-ups you can complete consecutively.");
    			}
    			else{
    				di.setText("Invalid input. Please enter your heart rate in beats per minute.");
    			}
    		}else if(stage ==3){
    			if(in>-1){
    				u.setChinups((int)in);
    				stage++;
    				di.setText("Please enter the maximum amount of sit-ups you can complete consecutively.");
    			}
    			else{
    				di.setText("Invalid input. Please enter the maximum amount of chin-ups you can complete consecutively.");
    			}
    		}else if(stage ==4){
    			if(in>-1){
    				u.setSitups((int)in);
    				stage++;
    				di.setText("Please enter the maximum amount of jumping jacks you can complete consecutively.");
    			}
    			else{
    				di.setText("Invalid input. the maximum amount of sit-ups you can complete consecutively.");
    			}
    		}else if(stage ==5){
    			if(in>-1){
    				u.setJumpjacks((int)in);
    				stage++;
    			}
    			else{
    				di.setText("Invalid input. Please enter the maximum amount of jumping jacks you can complete consecutively.");
    			}
    		}
	    }
	    
	    
	    private void dailySchedule(Planner plan, LocalDate today){
	    	//getFitWindow.removeAll();
	    	System.out.println("Daily Schedule");
	    	setLayout(new BorderLayout());
	    	
	    	// North Panel
	        Panel northPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
	        northPanel.setBackground(new Color(255, 255, 255));

	        Label getfitLabel = new Label("GetFit");
	        getfitLabel.setFont(new Font("Arial", Font.PLAIN, 20));

	        northPanel.add(getfitLabel);
	        Label todaysDate = new Label("Fitness plan for "+today.getDayOfWeek()+" "+today.getMonth()+" "+today.getDayOfMonth());
	        todaysDate.setFont(new Font("Arial", Font.PLAIN, 16));
	        Panel header = new Panel(new BorderLayout());
	        header.add(todaysDate);
	        
	        
	        Panel centerPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
	        Label todaysSchedule = new Label();
	        todaysSchedule.setFont(new Font("Arial", Font.PLAIN, 16));
	        todaysSchedule.setText("Situps: "+Integer.toString(plan.fitnessPlan[0][1])+"\n Chinups: "+Integer.toString(plan.fitnessPlan[0][0])+"\n Jumping Jacks: "+Integer.toString(plan.fitnessPlan[0][2]));
	        centerPanel.add(header, BorderLayout.NORTH);	        
	        Panel workPanel = new Panel(new BorderLayout());
	        workPanel.add(todaysSchedule);
	        workPanel.add(todaysSchedule, BorderLayout.CENTER);
	        centerPanel.add(workPanel, BorderLayout.CENTER);
	        Button nextButton = new Button("Next Day");
	        nextButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	if(plan.currentDay ==29){
	            		todaysSchedule.setText("Congratulations on completing your training regime!");
	            		todaysDate.setText("");
	            		todaysSchedule.validate();
		            	todaysDate.validate();
		            	workPanel.validate();
		            	header.validate();
		            	centerPanel.validate();
	            	}
	            	else{
		            	plan.currentDay++;
		            	nextDay(todaysSchedule, todaysDate, today, plan);
		            	todaysSchedule.validate();
		            	todaysDate.validate();
		            	workPanel.validate();
		            	header.validate();
		            	centerPanel.validate();
	            	}
	            }
	        });
	        add(northPanel, BorderLayout.NORTH);
	        add(centerPanel, BorderLayout.CENTER);
	        centerPanel.add(nextButton, BorderLayout.SOUTH);
	        this.validate();
	    }
	    
	    private void nextDay(Label todaysSchedule, Label todaysDate, LocalDate today, Planner plan){
	    	LocalDate currDay=today.plusDays(plan.currentDay);
	    	todaysSchedule.setText("Situps: "+Integer.toString(plan.fitnessPlan[plan.currentDay][1])+"\n Chinups: "+Integer.toString(plan.fitnessPlan[plan.currentDay][0])+"\n Jumping Jacks: "+Integer.toString(plan.fitnessPlan[plan.currentDay][2]));
	    	todaysDate.setText("Fitness plan for "+currDay.getDayOfWeek()+" "+currDay.getMonth()+" "+currDay.getDayOfMonth());
	    }
	    

	    private void centerWindow(int i) {
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        int screenWidth = screenSize.width;
	        int screenHeight = screenSize.height;

	        int windowWidth = getSize().width;
	        int windowHeight = getSize().height;

	        int x = (screenWidth - windowWidth+i) / 2;
	        int y = (screenHeight - windowHeight+i) / 2;

	        setLocation(x, y);
	    }

	}
	
	public void dailyTask(Planner plan, LocalDate today){
		getFitWindow.dailySchedule(plan,today);
	}


    public User printWelcomeGUI(){
        User user = new User();
        getFitWindow = new mainWindow(user);
        getFitWindow.initUI(user);
        return user;
    }

    public void printScheduler(User u){
    	Planner userPlan = new Planner(u);
        LocalDate currentDate = LocalDate.now();
        dailyTask(userPlan, currentDate);
        
    }
}
