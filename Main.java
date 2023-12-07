public class Main {
    public static void main(String[] args) {
        Scheduler sd = new Scheduler();
        User u = sd.printWelcomeGUI();
        DiagnosticSystem ds = new DiagnosticSystem(u);
        sd.printScheduler(ds);
    }
}
