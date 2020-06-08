package correcorre.scenario;

import android.content.res.AssetManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import correcorre.Main;

public class Scenario {

    private static Main main;
    private String path;
    public volatile ArrayList<ArrayList<String>> scenario = new ArrayList<>();
    public volatile int[] start = {0,0};

    public Scenario(Main m,String path) {
        this.path = path;
        main = m;
    }

    public synchronized void createScenario() throws JSONException {

        String in = importJSON();
        JSONObject obj;
        JSONArray columnsJSON;
        JSONObject blockJSON;
        ArrayList<String> column;
        String type;
        obj = new JSONObject(in);
        ArrayList<ArrayList<String>> columns = new ArrayList<>();

        for (int i = 0; i < obj.length(); i++) {//columnas
                columnsJSON = obj.getJSONArray("" + i);
                column = new ArrayList<>();
                for (int u = 0; u < columnsJSON.length(); u++) {
                    blockJSON = columnsJSON.getJSONObject(u);
                    type = blockJSON.getString("type");
                    column.add(type);
                }
                columns.add(column);
        }
        this.scenario = columns;
    }

    private synchronized String importJSON() {

        String line;
        String json = "";
        AssetManager manager = main.getContext().getAssets();
        try {
            InputStream open = manager.open(this.path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(open));
            while((line = reader.readLine()) != null) {
                json += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    return json;
    }
}