package JSON;

import java.util.ArrayList;
import java.util.List;

public class Object {

    private List<Element> elements = new ArrayList<>();
    private List<Object> objects = new ArrayList<>();

    public Object () {

    }

    public void addJSONElement(String[] item) {
        elements.add(new Element(item[0],item[1]));
    }

    public Element getElement(int id) {
        return elements.get(id);
    }

}
