package ua.kiev.toolstore.model.enums;

public enum ProductCategory {

    ELECTRIC("ELECTRIC"),
    HYDRAULIC("HYDRAULIC"),
    PNEUMATIC("PNEUMATIC"),
    HAND_TOOL("HAND_TOOL"),
    OTHER("OTHER");


    public static final ProductCategory[] ALL = {ELECTRIC, HYDRAULIC, PNEUMATIC, HAND_TOOL, OTHER};

    private final String name;


    public static ProductCategory forName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null for type");
        }
        if (name.toUpperCase().equals("ELECTRIC")) {
            return ELECTRIC;
        } else if (name.toUpperCase().equals("HYDRAULIC")) {
            return HYDRAULIC;
        } else if (name.toUpperCase().equals("PNEUMATIC")) {
            return PNEUMATIC;
        } else if (name.toUpperCase().equals("HAND_TOOL")) {
            return HAND_TOOL;
        } else if (name.toUpperCase().equals("OTHER")) {
            return OTHER;
        }
        throw new IllegalArgumentException("Name \"" + name + "\" does not correspond to any ProductCategory");
    }


    private ProductCategory(final String name) {
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


