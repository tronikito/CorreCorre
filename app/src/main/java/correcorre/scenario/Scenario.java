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
    public volatile ArrayList<ArrayList<ArrayList>> scenario = new ArrayList<>();
    public volatile int[] start = {0,0};

    public Scenario(Main m,String path) {
        this.path = path;
        main = m;
    }

    public synchronized void createScenario() throws JSONException {

        String in = importJSON();

        JSONArray matrixXJSON;
        JSONArray columnJSON;
        JSONObject blockJSON;

        matrixXJSON = new JSONArray(in);
        ArrayList<ArrayList<ArrayList>> columns = new ArrayList<>();
        ArrayList column;

            for (int u = 0; u < matrixXJSON.length(); u++) {
                columnJSON = matrixXJSON.getJSONArray(u);
                column = new ArrayList();
                for (int y = 0; y < columnJSON.length(); y++) {
                    blockJSON = columnJSON.getJSONObject(y);
                    ArrayList newBlock = new ArrayList();
                    newBlock.add(blockJSON.getString("type"));//0
                    newBlock.add(blockJSON.getInt("position"));//1
                    newBlock.add(blockJSON.getInt("enemyType"));//2
                    newBlock.add(blockJSON.getInt("solid"));//3

                    column.add(newBlock);
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