package br.com.prototipoat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


public class SettingActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Intent itMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.incMenu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return false;
            }
        });
        mToolbar.inflateMenu(R.menu.menu_main);
    }
}
