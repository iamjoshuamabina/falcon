package sample;

@SuppressWarnings("ClassWithoutNoArgConstructor")
public class HedwigPacket {
    private String name;
    private String value;


    public HedwigPacket(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public final String getName() {
        return this.name;
    }

    public final String getValue() {
        return this.value;
    }
}
