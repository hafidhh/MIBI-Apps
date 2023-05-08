package id.ac.ub.risite.mibi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import id.ac.ub.risite.mibi.ui.info.InfoFragment;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, InfoFragment.newInstance())
                    .commitNow();
        }
    }
}
