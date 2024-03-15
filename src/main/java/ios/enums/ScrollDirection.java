package ios.enums;

public enum ScrollDirection {

    UP ("up"),
    DOWN ("down");

    public final String value;

    ScrollDirection(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
