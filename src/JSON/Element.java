package JSON;

public class Element {
    private String property;
    private String value;
    private Element element;

    public Element(String property, String value) {
        this.property = property;
        this.value = value;
    }

    public String getProperty() {
        return this.property;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.property + ":" + this.value;
    }
}
