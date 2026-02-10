package pharmacy_webapp.constants;

public class PaymentMethodConstants {
    public static final int CASH_ON_DELIVERY = 1;
    public static final int BANK_TRANSFER = 2;
    public static final int MOMO = 3;

    public static String getDescription(int paymentMethod) {
        switch (paymentMethod) {
            case CASH_ON_DELIVERY: return "Cash On Delivery";
            case BANK_TRANSFER: return "Bank Transfer";
            case MOMO: return "Momo";
            default: return "Unknown PaymentMethod";
        }
    }
}
