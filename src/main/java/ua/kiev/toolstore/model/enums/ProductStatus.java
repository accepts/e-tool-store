package ua.kiev.toolstore.model.enums;

public enum ProductStatus {

    ACTIVE("ACTIVE"),
    LOCKED("LOCKED"),
    SPECIAL("SPECIAL"),
    FRESH("FRESH"),
    OBSOLETE("OBSOLETE");

    public static final ProductStatus[] ALL = {ACTIVE, LOCKED, SPECIAL, FRESH, OBSOLETE};

    private final String name;

    public static ProductStatus forName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null for type");
        }
        if (name.toUpperCase().equals("ACTIVE")) {
            return ACTIVE;
        } else if (name.toUpperCase().equals("LOCKED")) {
            return LOCKED;
        } else if (name.toUpperCase().equals("SPECIAL")) {
            return SPECIAL;
        } else if (name.toUpperCase().equals("FRESH")) {
            return FRESH;
        } else if (name.toUpperCase().equals("OBSOLETE")) {
            return OBSOLETE;
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



