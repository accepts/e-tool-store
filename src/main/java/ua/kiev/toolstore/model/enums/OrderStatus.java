package ua.kiev.toolstore.model.enums;

public enum OrderStatus {

    ACTIVE("ACTIVE"),       //Current Customer's Order in process
    CONFIRMED("CONFIRMED"), // Customer confirm his Order
    CANCELED("CANCELED"),   // Customer canceled his Order

    PROCESSED("PROCESSED"), // Admin take Order in process
    DECLINED("DECLINED"),   // Admin Decline Order
    SHIPPED("SHIPPED"),     // Order is Shipping
    COMPLETED("COMPLETED"); // Completed


    public static final OrderStatus[] ALL = { ACTIVE, CONFIRMED, CANCELED, PROCESSED, DECLINED, SHIPPED, COMPLETED };

    private final String name;


    public static OrderStatus forName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null for type");
        }
        if (name.toUpperCase().equals("ACTIVE")) {
            return ACTIVE;
        } else if (name.toUpperCase().equals("CONFIRMED")) {
            return CONFIRMED;
        }else if (name.toUpperCase().equals("CANCELED")) {
            return CANCELED;
        }else if (name.toUpperCase().equals("PROCESSED")) {
            return PROCESSED;
        }else if (name.toUpperCase().equals("DECLINED")) {
            return DECLINED;
        }
        else if (name.toUpperCase().equals("SHIPPED")) {
            return SHIPPED;
        }
        else if (name.toUpperCase().equals("COMPLETED")) {
            return COMPLETED;
        }
        throw new IllegalArgumentException("Name \"" + name + "\" does not correspond to any OrderStatus");
    }


    private OrderStatus(final String name) {
        this.name = name;
    }


    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return getName();
    }



}
