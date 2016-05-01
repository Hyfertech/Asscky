package Model;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by AirUnknown on 16-04-30.
 */
public class Asscky extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
