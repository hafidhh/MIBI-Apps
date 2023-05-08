package id.ac.ub.risite.mibi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import id.ac.ub.risite.mibi.ui.detaildata.DetailDataFragment;

public class DetailData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_data_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DetailDataFragment.newInstance())
                    .commitNow();
        }
    }
}
