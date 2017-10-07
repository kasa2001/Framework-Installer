package JSON;

import java.util.ArrayList;
import java.util.List;

public class JSONObject {

    private List<JSONElement> JSONElements = new ArrayList<>();
    private List<JSONObject> JSONObjects = new ArrayList<>();
    private static JSONObject json;

    private JSONObject() {

    }

    public void addJSONElement(String[] item) {
        JSONElements.add(new JSONElement(item[0], item[1]));
    }

    public void addJSONElement(JSONElement element) {
        JSONElements.add(element);
    }


    public int getSizeElements () {
        return this.JSONElements.size();
    }
    public JSONElement getElement(int id) {
        return JSONElements.get(id);
    }


    public synchronized static JSONObject getInstance() {
        if (JSONObject.json == null) {
            JSONObject.json = new JSONObject();
        }
        return json;
    }
}
