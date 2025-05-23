import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.showApplianceSelection();
    }

    public void showApplianceSelection() {
        String[] options = {"Refrigerator", "Ceiling Fan", "Light", "Air Conditioner"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Please choose the appliance you want to calculate the result for",
                "Appliance Choice",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (choice) {
            case 0:
                new RefrigiratorGUI(this);
                break;
            case 1:
                new FanGui(this);
                break;
            case 2:
                new LightGui(this);
                break;
            case 3:
                new AirConditioner(this);
                break;
            default:
                JOptionPane.showMessageDialog(
                        null,
                        "No Appliance Selected",
                        "Error",
                        JOptionPane.WARNING_MESSAGE
                );
                break;
        }
    }

}
