package correcorre.game.scenario;

import correcorre.R;

public abstract class ScenarioFile {

        public static int map1 = R.drawable.map1;

        public static int getScenario(int x) {
            if (x == 1) return map1;
            return -1;
        }

    }
