package com.lyp.facedetect.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lyp.facedetect.R;

/**
 * Created by demon.li on 2016/4/10.
 */
public abstract  class BaseActicity extends AppCompatActivity {



    public abstract String getClassName();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                this.finish();
                break;
            default:
                break;
        }
        return true;
    }

}
