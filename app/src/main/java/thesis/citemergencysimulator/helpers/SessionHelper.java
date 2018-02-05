package thesis.citemergencysimulator.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Dave Tolentin on 1/14/2018.
 */
public class SessionHelper {

    public static final String PREF_NAME = "SCAPE_ROUTES";
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionHelper(Context context) {
        this.context = context;
        editor = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
    }

    public void setRoomNum(int roomNum) {
        editor.putInt("roomNum", roomNum);
        editor.apply();
    }

    public void clearSession() {
        SharedPreferences  sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
