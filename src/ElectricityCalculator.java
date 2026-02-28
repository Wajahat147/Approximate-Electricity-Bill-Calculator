/**
 * ElectricityCalculator — NEPRA Residential Tariff (2025)
 * Source: National Electric Power Regulatory Authority, Pakistan
 * Updated: February 2025
 *
 * Tariff Categories:
 *  - Lifeline: ≤50 units @ PKR 4.78/unit
 *  - Protected (≤200 units):
 *      ≤100 units  → PKR 8.52/unit
 *      101–200     → PKR 11.51/unit
 *  - Non-Protected (>200 units):
 *      ≤300 units  → PKR 34.03/unit
 *      >300 units  → PKR 48.46/unit (TOU rate)
 *  Fixed charge: PKR 75/month (single-phase)
 */
public class ElectricityCalculator {

    // Fixed monthly customer charge (single-phase connection)
    public static final double FIXED_CHARGE = 75.0;

    /**
     * Returns per-unit rate based on NEPRA 2025 residential slabs.
     */
    public static double calculateUnitPrice(double kwh) {
        if (kwh <= 50)  return 4.78;    // Lifeline consumers
        else if (kwh <= 100)  return 8.52;   // Protected ≤100 units
        else if (kwh <= 200)  return 11.51;  // Protected 101–200 units
        else if (kwh <= 300)  return 34.03;  // Non-Protected 201–300 units
        else                  return 48.46;  // Non-Protected 300+ units (TOU)
    }

    public static String getSlabLabel(double kwh) {
        if (kwh <= 50)        return "Lifeline (≤50 units) — PKR 4.78/unit";
        else if (kwh <= 100)  return "Protected Slab 1 (≤100 units) — PKR 8.52/unit";
        else if (kwh <= 200)  return "Protected Slab 2 (101–200 units) — PKR 11.51/unit";
        else if (kwh <= 300)  return "Non-Protected (201–300 units) — PKR 34.03/unit";
        else                  return "Non-Protected TOU (300+ units) — PKR 48.46/unit";
    }

    public static double calculateBill(Appliance appliance) {
        double kwh       = appliance.calculateKWH();
        double unitPrice = calculateUnitPrice(kwh);
        return kwh * unitPrice + FIXED_CHARGE;
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
