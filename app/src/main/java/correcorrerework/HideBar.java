package correcorrerework;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.View;

public abstract class HideBar {
    public static void toggleHideyBar(Activity a) {

        int uiOptions = a.getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("TAG", "Turning immersive mode mode off. ");
        } else {
            Log.i("TAG", "Turning immersive mode mode on.");
        }
        if (!isImmersiveModeEnabled) {
            // Navigation bar hiding:  Backwards compatible to ICS.
            if (Build.VERSION.SDK_INT >= 14) {
                newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            // Status bar hiding: Backwards compatible to Jellybean
            if (Build.VERSION.SDK_INT >= 16) {
                newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
            }
            if (Build.VERSION.SDK_INT >= 18) {
                newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            a.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        }

    }
}
