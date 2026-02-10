package pharmacy_webapp.constants;

public class OrderStatusConstants {
    public static final int PENDING = 0;
    public static final int CONFIRMED = 1;
    public static final int SHIPPING = 2;
    public static final int DELIVERED = 3;
    public static final int CANCELLED = 4;
    public static final int RETURNED = 5;

    public static String getDescription(int code) {
        switch (code) {
            case PENDING: return "Pending";
            case CONFIRMED: return "Confirmed";
            case SHIPPING: return "Shipping";
            case DELIVERED: return "Delivered";
            case CANCELLED: return "Cancelled";
            case RETURNED: return "Returned";
            default: return "Unknown";
        }
    }
}
