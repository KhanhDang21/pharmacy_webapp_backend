package pharmacy_webapp.constants;

public class PaymentMethodConstants {
    public static final int CASH_ON_DELIVERY = 1;
    public static final int VNPAY = 2;

    public static String getDescription(int paymentMethod) {
        switch (paymentMethod) {
            case CASH_ON_DELIVERY: return "Cash On Delivery";
            case VNPAY: return "Vnpay";
            default: return "Unknown PaymentMethod";
        }
    }
}
