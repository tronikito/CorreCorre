package correcorre.game.scenario;

import android.content.res.AssetManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static correcorre.ResourcesClass.*;

public class Scenario {

    private String path;
    public volatile int[] start = {-15,-15};//nofuca

    public Scenario(String path) {
        this.path = path;
        createScenario();
    }

    public synchronized void createScenario() {

        try {
            String in = importJSON();

            JSONArray matrixXJSON;
            JSONArray columnJSON;
            JSONObject blockJSON;

            matrixXJSON = new JSONArray(in);
            matrixScenario = new ArrayList<>();
            ArrayList column;

            for (int u = 0; u < matrixXJSON.length(); u++) {
                columnJSON = matrixXJSON.getJSONArray(u);
                column = new ArrayList();
                for (int y = 0; y < columnJSON.length(); y++) {
                    blockJSON = columnJSON.getJSONObject(y);
                    ArrayList newBlock = new ArrayList();
                    newBlock.add(blockJSON.getString("type"));//0
                    newBlock.add(blockJSON.getInt("position"));//1
                    newBlock.add(blockJSON.getString("enemyType"));//2
                    newBlock.add(blockJSON.getInt("solid"));//3
                    newBlock.add(blockJSON.getString("weaponType"));//4
                    newBlock.add(blockJSON.getInt("sprite"));//5
                    newBlock.add(blockJSON.getString("blockType"));//5

                    column.add(newBlock);
                }
                matrixScenario.add(column);
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }

    }

    private synchronized String importJSON() {

        String line;
        String json = "";
        AssetManager manager = mactivity.getAssets();
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