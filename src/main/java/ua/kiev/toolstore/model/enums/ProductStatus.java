package ua.kiev.toolstore.model.enums;

public enum ProductStatus {

    IN_STOCK("IN_STOCK"),
    OUT_OF_STOCK("OUT_OF_STOCK"),
    SEVERAL_LEFT("SEVERAL_LEFT");

    public static final ProductStatus[] ALL = {IN_STOCK, OUT_OF_STOCK, SEVERAL_LEFT};

    private final String name;

    public static ProductStatus forName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null for type");
        }
        if (name.toUpperCase().equals("IN_STOCK")) {
            return IN_STOCK;
        } else if (name.toUpperCase().equals("OUT_OF_STOCK")) {
            return OUT_OF_STOCK;
        }else if (name.toUpperCase().equals("SEVERAL_LEFT")) {
            return SEVERAL_LEFT;
        }
        throw new IllegalArgumentException("Name \"" + name + "\" does not correspond to any ProductStatus");
    }

    private ProductStatus(final String name) {
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



