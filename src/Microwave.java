public class Microwave extends Appliance {
    public Microwave() { super("Microwave Oven", "\uD83C\uDF54"); }

    @Override
    public String getExtraInfo(double bill, double kwh) {
        return String.format("Daily cost: PKR %.2f | Per minute: PKR %.2f",
                bill / 30.0, bill / 30.0 / (getHoursUsed() * 60));
    }
}
