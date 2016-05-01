package edu.olemiss.hpatel3.p4hpatel3;

import android.app.ActionBar;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.TextView;

/**
 * Created by Harsh on 4/30/2015.
 */
public class HighScore extends ActionBarActivity {

    TextView hbname;
    TextView hbscore;
    TextView hiname;
    TextView hiscore;
    TextView hename;
    TextView hescore;
    TextView hcname;
    TextView hcscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score);

        hbname = (TextView) findViewById(R.id.hbname);
        hbscore = (TextView) findViewById(R.id.hbscore);
        hiname = (TextView) findViewById(R.id.hiname);
        hiscore = (TextView) findViewById(R.id.hiscore);
        hename = (TextView) findViewById(R.id.hename);
        hescore = (TextView) findViewById(R.id.hescore);
        hcname = (TextView) findViewById(R.id.hcname);
        hcscore = (TextView) findViewById(R.id.hcscore);

        DBAdapter db = new DBAdapter(this);
        db.open();

        Cursor c = db.bmin();

        if(c != null){
            hbname.setText(c.getString(0));
            hbscore.setText("" + c.getInt(1));
        }

        c = db.imin();

        if(c != null){
            hiname.setText(c.getString(0));
            hiscore.setText("" + c.getInt(1));
        }

        c = db.emin();

        if(c != null){
            hename.setText(c.getString(0));
            hescore.setText("" + c.getInt(1));
        }

        c = db.cmin();

        if(c != null){
            hcname.setText(c.getString(0));
            hcscore.setText("" + c.getInt(1));
        }

        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
