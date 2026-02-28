public class ElectricIron extends Appliance {
    public ElectricIron() { super("Electric Iron", "\uD83D\uDC55"); }

    @Override
    public String getExtraInfo(double bill, double kwh) {
        return String.format("Daily cost: PKR %.2f | Per session (~30 min): PKR %.2f",
                bill / 30.0, bill / 30.0 / (getHoursUsed() * 2));
    }
}
