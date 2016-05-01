package edu.olemiss.hpatel3.p4hpatel3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;

/**
 * Created by Harsh on 4/18/2015.
 */
public class Levels extends ActionBarActivity implements CustomGrid.CustomSetting{

    int grid;
    int mines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onBeginner(View view){
        Intent i = new Intent("edu.olemiss.hpatel3.P4hpatel3.Game");
        i.putExtra("grid",8);
        i.putExtra("mines",10);
        i.putExtra("level", "beginner");
        startActivity(i);
    }

    public void onIntermediate(View view){
        Intent i = new Intent("edu.olemiss.hpatel3.P4hpatel3.Game");
        i.putExtra("grid",10);
        i.putExtra("mines",20);
        i.putExtra("level", "intermediate");
        startActivity(i);
    }

    public void onExpert(View view){
        Intent i = new Intent("edu.olemiss.hpatel3.P4hpatel3.Game");
        i.putExtra("grid",12);
        i.putExtra("mines",30);
        i.putExtra("level", "expert");
        startActivity(i);
    }

    public void onCustom(View view){
        CustomGrid customGrid = new CustomGrid();
        customGrid.show(getFragmentManager(), "score_add");

    }

    @Override
    public void onFinishEditDialog(int sgrid, int smines) {
        grid = sgrid;
        mines = smines;

        Intent i = new Intent("edu.olemiss.hpatel3.P4hpatel3.Game");
        i.putExtra("grid",grid);
        i.putExtra("mines",mines);
        i.putExtra("level", "custom");
        startActivity(i);
    }
}
