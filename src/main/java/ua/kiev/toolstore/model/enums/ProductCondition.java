package ua.kiev.toolstore.model.enums;

public enum ProductCondition {

    NEW("NEW"),
    USED("USED");


    public static final ProductCondition[] ALL = { NEW, USED };


    private final String name;


    public static ProductCondition forName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null for type");
        }
        if (name.toUpperCase().equals("NEW")) {
            return NEW;
        } else if (name.toUpperCase().equals("USED")) {
            return USED;
        }
        throw new IllegalArgumentException("Name \"" + name + "\" does not correspond to any ProductCondition");
    }


    private ProductCondition(final String name) {
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



/*




 */