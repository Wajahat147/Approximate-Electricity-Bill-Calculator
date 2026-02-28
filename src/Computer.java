public class Computer extends Appliance {
    public Computer() { super("Desktop PC", "\uD83D\uDCBB"); }

    @Override
    public String getExtraInfo(double bill, double kwh) {
        return String.format("Daily cost: PKR %.2f | Yearly: PKR %.2f | Tip: Switch to laptop to save ~70%%",
                bill / 30.0, bill * 12.0);
    }
}
