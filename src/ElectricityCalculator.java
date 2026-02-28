/**
 * ElectricityCalculator — NEPRA Residential Tariff (2024-2025)
 * Source: National Electric Power Regulatory Authority, Pakistan
 */
public class ElectricityCalculator {

    // NEPRA 2024-2025 residential slab rates (PKR per unit / kWh)
    public static double calculateUnitPrice(double kwh) {
        if (kwh <= 100)  return 27.68;
        else if (kwh <= 200) return 32.47;
        else if (kwh <= 300) return 38.79;
        else if (kwh <= 400) return 45.00;
        else if (kwh <= 500) return 54.00;
        else if (kwh <= 600) return 59.00;
        else if (kwh <= 700) return 66.00;
        else                 return 71.00;
    }

    public static String getSlabLabel(double kwh) {
        if (kwh <= 100)  return "Slab 1 (≤100 units) — PKR 27.68/unit";
        else if (kwh <= 200) return "Slab 2 (101–200 units) — PKR 32.47/unit";
        else if (kwh <= 300) return "Slab 3 (201–300 units) — PKR 38.79/unit";
        else if (kwh <= 400) return "Slab 4 (301–400 units) — PKR 45.00/unit";
        else if (kwh <= 500) return "Slab 5 (401–500 units) — PKR 54.00/unit";
        else if (kwh <= 600) return "Slab 6 (501–600 units) — PKR 59.00/unit";
        else if (kwh <= 700) return "Slab 7 (601–700 units) — PKR 66.00/unit";
        else                 return "Slab 8 (700+ units) — PKR 71.00/unit";
    }

    public static double calculateBill(Appliance appliance) {
        double kwh = appliance.calculateKWH();
        double unitPrice = calculateUnitPrice(kwh);
        return kwh * unitPrice;
    }

    // Validate inputs and return full result data
    public static CalculationResult calculate(Appliance appliance) {
        if (!appliance.validateHours()) {
            return new CalculationResult(false, "Hours must be between 1 and 24.", 0, 0, "");
        }
        if (!appliance.validateWattage()) {
            return new CalculationResult(false, "Wattage must be between 1 and 50,000 watts.", 0, 0, "");
        }
        double kwh  = appliance.calculateKWH();
        double bill = calculateBill(appliance);
        String slab = getSlabLabel(kwh);
        return new CalculationResult(true, null, kwh, bill, slab);
    }

    // Simple data holder for a calculation result
    public static class CalculationResult {
        public final boolean success;
        public final String  errorMessage;
        public final double  kwh;
        public final double  bill;
        public final String  slabLabel;

        public CalculationResult(boolean success, String errorMessage,
                                 double kwh, double bill, String slabLabel) {
            this.success      = success;
            this.errorMessage = errorMessage;
            this.kwh          = kwh;
            this.bill         = bill;
            this.slabLabel    = slabLabel;
        }
    }
}
