package pharmacy_webapp.constants;

public class PaymentStatusConstants {
    public static final int PENDING = 0;
    public static final int PAID = 1;
    public static final int FAILED = 2;
    public static final int REFUNDED = 3;
    public static final int CANCELLED = 4;

    public static String getDescription(int code) {
        switch (code) {
            case PENDING: return "Pending";
            case PAID: return "Paid";
            case FAILED: return "Failed";
            case REFUNDED: return "Refunded";
            case CANCELLED: return "Cancelled";
            default: return "Unknown";
        }
    }
}
