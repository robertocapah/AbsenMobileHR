package absenmobilehr.app.kalbenutritionals.absenmobilehr;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment.FragmentPushData;

/**
 * Created by Robert on 27/07/2017.
 */

public class PushData extends AppCompatActivity {
    private View v;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Push Data");
        setSupportActionBar(toolbar);

        Bundle bundle = new Bundle();
        String myMessage = "notMainMenu";
        bundle.putString("message", myMessage );

        FragmentPushData fragmentPushData = new FragmentPushData();
        fragmentPushData.setArguments(bundle);
        FragmentTransaction fragmentFragmentPushData = getSupportFragmentManager().beginTransaction();
        fragmentFragmentPushData.replace(R.id.frame, fragmentPushData);
        fragmentFragmentPushData.commit();
    }
}
