package g1.aplicaciones.com.anywall_gr1;


import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class Application extends android.app.Application {
    public static final boolean APPDEBUG = false;
    public static final String APPTAG = "AnyWall";

    public Application() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(AnywallPost.class);
        Parse.initialize(this, "irauSku745xgYfCdpB1eOkg9YjeEQZ6k7GG2wiGN", "VBbcEDDJLG24Za5LrNleFb7AGvKK6GWgl3DpwLZJ");
        ParseUser.enableAutomaticUser();
        ParseACL defauAcl = new ParseACL();
        defauAcl.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defauAcl, true);
    }
}

