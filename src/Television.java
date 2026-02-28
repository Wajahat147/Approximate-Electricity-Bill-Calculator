public class Television extends Appliance {
    public Television() { super("Television", "\uD83D\uDCFA"); }

    @Override
    public String getExtraInfo(double bill, double kwh) {
        return String.format("Usage: %.1f hrs/day | Daily cost: PKR %.2f",
                hoursUsed, bill / 30.0);
    }
}