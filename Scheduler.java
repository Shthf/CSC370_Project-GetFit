import java.util.Scanner;
import java.io.File;
import java.time.LocalDate;

public class Scheduler extends Planner{
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

            if(waist <= 0){
                System.out.println("Invalid input, please enter a number greater than zero.");
            }
            
        }while(waist <= 0);

        do{
            System.out.println("Enter pulse:");
            pulse = Integer.parseInt(sc.nextLine());

            if(pulse <= 0){
                System.out.println("Invalid input, please enter a number greater than zero.");
            }
            
        }while(pulse <= 0);

        sc.close();
        User user = new User(weight, waist, pulse);
        return user;
    }

    public void printScheduler(User u, File file_chinups, File file_Situps, File file_Jumps){
        System.out.println(" ---------------------------------------------");
        System.out.println("|                  Scheduler                  |");
        System.out.println(" ---------------------------------------------");
        System.out.println("Below is your planner for the next week:");

        DiagnosticSystem diagnosticSystem_Chinups = new DiagnosticSystem(u, file_chinups);
        DiagnosticSystem diagnosticSystem_Situps = new DiagnosticSystem(u, file_Situps);
        DiagnosticSystem diagnosticSystem_Jumps = new DiagnosticSystem(u, file_Jumps);

        int prediction_Chinups = diagnosticSystem_Chinups.getPrediction();
        int prediction_Situps = diagnosticSystem_Situps.getPrediction();
        int prediction_Jumps = diagnosticSystem_Jumps.getPrediction();

        
    
        LocalDate currentDate = LocalDate.now();
        // current date
        for(int i = 0; i < 7; i++){
            LocalDate nextDay = currentDate.plusDays(i);
            System.out.println(nextDay);
            System.out.println("Number of Chin-ups: " + prediction_Chinups);
            System.out.println("Number of Sit-ups : " + prediction_Situps);
            System.out.println("Number of Jumps   : " + prediction_Jumps);
            System.out.println(" ---------------------------------------------");
        }
        
    }
}
