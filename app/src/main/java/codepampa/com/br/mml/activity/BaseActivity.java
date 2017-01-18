package codepampa.com.br.mml.activity;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected static String TAG = "base_activity";


    protected void replaceFragment(int container, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(container,fragment).commit();
    }
}
