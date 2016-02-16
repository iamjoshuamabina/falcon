package sample;

public class HedwigPacket {
    private final String name;
    private final String value;
    private Route route;

    public HedwigPacket(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }
}
