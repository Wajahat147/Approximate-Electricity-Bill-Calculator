public class RoomCooler extends Appliance {
    public RoomCooler() { super("Room Cooler", "\uD83C\uDF2C\uFE0F"); }

    @Override
    public String getExtraInfo(double bill, double kwh) {
        return String.format("Daily cost: PKR %.2f | Seasonal (4mo): PKR %.2f",
                bill / 30.0, bill * 4.0);
    }
}