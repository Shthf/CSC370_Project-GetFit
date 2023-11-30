import java.io.File;

public class WenS_370Project {
    public static void main(String args[]){
        
        Scheduler s = new Scheduler();
        User u = s.printWelcomeGUI();

        File c = new File("linnerud_exercise_Chins.txt");
        File sit = new File("linnerud_exercise_Situps.txt");
        File j = new File("linnerud_exercise_Jumps.txt");


        s.printScheduler(u, c, sit, j);


    }
}
