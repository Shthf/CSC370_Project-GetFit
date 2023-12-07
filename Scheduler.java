import java.util.Scanner;
import java.time.LocalDate;

public class Scheduler{
    public User printWelcomeGUI(){
        Scanner sc = new Scanner(System.in);

        System.out.println(" ---------------------------------------------");
        System.out.println("|                   GetFit                    |");
        System.out.println(" ---------------------------------------------");
        System.out.println();
        double weight = 0, waist = 0;
        int pulse = 0;
        do{
            System.out.println("Enter weight:");
            weight = Double.parseDouble(sc.nextLine());

            if(weight <= 0){
                System.out.println("Invalid input, please enter a number greater than zero.");
            }

        }while(weight <= 0);
        
        do{
            System.out.println("Enter waist:");
            waist = Double.parseDouble(sc.nextLine());

            if(waist < 15){
                System.out.println("Invalid input, please enter a number greater than zero.");
            }
            
        }while(waist < 15); //From google search, the minimum waist size in the world is 15 in

        do{
            System.out.println("Enter pulse:");
            pulse = Integer.parseInt(sc.nextLine());

            if(pulse < 27){
                System.out.println("Invalid input, please enter a number greater than zero.");
            }
            
        }while(pulse < 27); //From google search, the slowest bpm in the world record is 27 bpm

        sc.close();
        User user = new User(weight, waist, pulse);
        return user;
    }

    public void printScheduler(DiagnosticSystem d){
        System.out.println(" ---------------------------------------------");
        System.out.println("|                  Scheduler                  |");
        System.out.println(" ---------------------------------------------");
        System.out.println("Below is your planner for the next week:");

        LocalDate currentDate = LocalDate.now();
        // current date
        for(int i = 0; i < 7; i++){
            LocalDate nextDay = currentDate.plusDays(i);
            System.out.println(nextDay);
            System.out.println("Number of Chin-ups: " + d.getPrediction_chinups());
            System.out.println("Number of Sit-ups : " + d.getPrediction_Situps());
            System.out.println("Number of Jumps   : " + d.getPrediction_Jumps());
            System.out.println(" ---------------------------------------------");
        }

        // System.out.println("Testing Report:");
        // System.out.println("Report For Chinups: MSE = " + d.getMSE(0));
        // System.out.println("Report For Situps:  MSE = " + d.getMSE(1));
        // System.out.println("Report For Jumps:   MSE = " + d.getMSE(2));
        
        // System.out.println(" ---------------------------------------------");   
    }
}
