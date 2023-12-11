import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.LocalDate;

public class Scheduler extends Planner{
	
	public int stage = 0;
	
	public class mainWindow extends Frame {
	    private TextField inputs;
	    double output;

	    public mainWindow(User u) {
	        setTitle("GetFit");
	        setSize(800, 600);

	       

	        addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(WindowEvent e) {
	                System.exit(0);
	            }
	        });

	        centerWindow();
	        setVisible(true);
	        initUI(u);
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
	        Label dialogueComponent = new Label("Welcome to GetFit! Please enter your weight in lbs to begin.");
	        dialogue.add(dialogueComponent);
	        inputs = new TextField();
	        inputs.setFont(new Font("Arial", Font.PLAIN, 14));

	        Button inputButton = new Button("Enter");
	        inputButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	try{
	            		//System.out.println("button press");
	            		output = Double.parseDouble(inputs.getText());
	            		//System.out.println(output);
	            		intake(output, u, dialogueComponent);
	            		//System.out.println(dialogueComponent);
	            		if(stage == 6){
	            			printScheduler(u);
	            			
	            		}
	            		}
	            	catch(NumberFormatException ef){
	            		
	            	}
	            }
	        });
	        //inputPanel.setSize(180, 26);
	        centerPanel.add(inputPanel, BorderLayout.CENTER);
	        centerPanel.add(dialogue, BorderLayout.SOUTH);
	        centerPanel.add(inputs, BorderLayout.CENTER);
	        centerPanel.add(inputButton, BorderLayout.EAST);
	        

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
    				di.setText("Please enter your weight in lbs.");
    			}
    		}
    		else if(stage ==1){
    			if(in>15 && in<80){
    				u.setWaist(in);
    				//System.out.println(in);
    				stage++;
    				di.setText("Please enter your heart rate in beats per minute.");
    			}
    		}else if(stage ==2){
    			if(in>27 && in<100){
    				u.setPulse((int)in);
    				stage++;
    				di.setText("Please enter the maximum amount of chin-ups you can complete consecutively.");
    			}
    		}else if(stage ==3){
    			if(in>-1){
    				u.setChinups((int)in);
    				stage++;
    				di.setText("Please enter the maximum amount of sit-ups you can complete consecutively.");
    			}	
    		}else if(stage ==4){
    			if(in>-1){
    				u.setSitups((int)in);
    				stage++;
    				di.setText("Please enter the maximum amount of jumping jacks you can complete consecutively.");
    			}	
    		}else if(stage ==5){
    			if(in>-1){
    				u.setJumpjacks((int)in);
    				stage++;
    			}	
    		}
	    }
	    

	    private void centerWindow() {
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        int screenWidth = screenSize.width;
	        int screenHeight = screenSize.height;

	        int windowWidth = getSize().width;
	        int windowHeight = getSize().height;

	        int x = (screenWidth - windowWidth) / 2;
	        int y = (screenHeight - windowHeight) / 2;

	        setLocation(x, y);
	    }

	}


	   


    public User printWelcomeGUI(){
        User user = new User();
        mainWindow window = new mainWindow(user);
        return user;
    }

    public void printScheduler(User u){
    	Planner userPlan = new Planner(u);
        LocalDate currentDate = LocalDate.now();
        
    }
}
