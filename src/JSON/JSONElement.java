package JSON;

public class JSONElement {
    private String property;
    private String value;
    private JSONElement JSONElement;

    public JSONElement(String property, String value) {
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
