public class Laptop extends Appliance {
    public Laptop() { super("Laptop", "\uD83D\uDCF9"); }

    @Override
    public String getExtraInfo(double bill, double kwh) {
        return String.format("Daily cost: PKR %.2f | Yearly: PKR %.2f | Very energy efficient!",
                bill / 30.0, bill * 12.0);
    }
}
