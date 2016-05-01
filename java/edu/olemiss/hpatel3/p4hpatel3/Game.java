package edu.olemiss.hpatel3.p4hpatel3;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import pl.polidea.view.ZoomView;

/**
 * Created by Harsh on 4/18/2015.
 */

public class Game extends FragmentActivity implements AddScore.EditNameDialogListener{

    int grid;
    int[] mines;
    //TableLayout gcontainer;
    Button b;
    Button[] blist;
    int[] open;
    int m=0;
    boolean redFlag=false;
    Button red_Flag;
    Button gameState;
    int[] btn_redFlag;
    String gstate="newgame";
    int openCount=0;
    TextView mine_count;
    TextView timer;
    int cmines;
    boolean timeFlag = false;
    int tcount = 0;
    Timer T;
    ZoomView zoomview;
    LinearLayout mainContainer;
    int gcmines;
    String level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        red_Flag = (Button) findViewById(R.id.flag);
        gameState = (Button) findViewById(R.id.gamestate);
       // gcontainer = (TableLayout) this.findViewById(R.id.gridContainer);
        mine_count = (TextView) findViewById(R.id.mines);
        timer = (TextView) findViewById(R.id.time);
        mainContainer = (LinearLayout) findViewById(R.id.main_container);

        grid = getIntent().getIntExtra("grid",8);
        cmines = getIntent().getIntExtra("mines",10);
        level = getIntent().getStringExtra("level");
        mines = new int[cmines];
        open = new int[grid * grid];
        btn_redFlag = new int[grid * grid];
        mine_count.setText("" + cmines);
        gcmines = cmines;
        newGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    public int getMineCount(int x, int y){

        int count = 0;

        for (int i=-1 ; i<2 ; i++) {
            for (int j = -1; j < 2; j++) {
                if(i!=0 || j!=0) {

                    int rx = x + i;
                    int ry = y + j;

                    if (rx >= 0 && ry >= 0 && rx < grid && ry < grid) {
                        int no = (rx * grid) + ry;

                        for (int k : mines) {
                            if (k == no) {
                                count = count + 1;
                            }
                        }
                    }
                }
            }
        }


        return  count;
    }

    public void openMultiple(int tid){
        int x = tid / grid;
        int y = tid % grid;

        for (int i=-1 ; i<2 ; i++) {
            for (int j = -1; j < 2; j++) {
                if(i!=0 || j!=0) {

                    int rx = x + i;
                    int ry = y + j;

                    if (rx >= 0 && ry >= 0 && rx < grid && ry < grid) {
                        int no = (rx * grid) + ry;

                        boolean flag = false;

                        for (int k : mines) {
                            if (k == no) {
                                flag = true;
                            }
                        }

                        if(open[no] == 0 && flag == false && btn_redFlag[no] == 0) {
                            open[no] = 1;
                            int mineCount = getMineCount(rx, ry);

                            GradientDrawable gd = new GradientDrawable();
                            gd.setColor(getResources().getColor(R.color.blueHeader)); // Changes this drawbale to use a single color instead of a gradient
                            gd.setCornerRadius(5);
                            gd.setStroke(1, 0xFF000000);
                            blist[no].setBackground(gd);
                            openCount += 1;

                            if (openCount == (grid * grid) - mines.length ){
                                gstate = "won";
                                gameState.setBackgroundResource(R.drawable.smile_winer);
                                red_Flag.setEnabled(false);

                                for(int temp=0;temp<(grid*grid);temp++){
                                    blist[temp].setEnabled(false);
                                }

                                AddScore scoreDialog = new AddScore();
                                Bundle args = new Bundle();
                                args.putInt("score", Integer.parseInt(timer.getText().toString()));
                                args.putCharSequence("level", level);
                                scoreDialog.setArguments(args);
                                scoreDialog.show(getFragmentManager(),"score_add");

                            }

                            if (mineCount != 0) {
                                blist[no].setText("" + mineCount);
                            } else {
                                blist[no].setText("");
                                openMultiple(no);
                            }
                        }
                    }
                }
            }
        }
    }

    public void onRedFlag(View view){
        if(redFlag == false) {
            redFlag = true;
            red_Flag.setBackgroundResource(R.drawable.flag);
        }
        else{
            redFlag = false;
            red_Flag.setBackgroundResource(R.drawable.flag_transparent);

        }
    }

    public void newGame(){

        if(gstate == "lost" || gstate == "won"){
            gstate = "newgame";
            gameState.setBackgroundResource(R.drawable.smile);
            mainContainer.removeView(zoomview);
            //gcontainer.removeAllViews();
            /*redFlag = false;
            red_Flag.setEnabled(true);
            red_Flag.setBackgroundResource(R.drawable.flag_transparent);
            openCount = 0;
            timeFlag = false;
            tcount = 0;*/
        }

        redFlag = false;
        red_Flag.setEnabled(true);
        red_Flag.setBackgroundResource(R.drawable.flag_transparent);
        openCount = 0;
        timeFlag = false;
        tcount = 0;
        timer.setText("00");
        m = 0;
        gcmines = cmines;
        mine_count.setText("" + gcmines);

        for(int k=0;k < (grid * grid); k++){
            open[k] = 0;
            btn_redFlag[k] = 0;
        }

        Random rm = new Random();
        for(int n=0;n<cmines;n++){
            boolean check = false;

            while(check == false) {
                check = true;
                mines[n] = rm.nextInt(grid * grid);
                for (int p = 0; p < n; p++) {
                    if (mines[n] == mines[p]) {
                        check = false;
                    }
                }
            }
        }

        View v = ((LayoutInflater)  getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoombleview, null, false);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        blist = new Button[grid * grid];


        int i,j;
        for(i=0; i<grid; i++){
            TableRow grow = new TableRow(this);
            TableLayout.LayoutParams row_params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,40);

            for(j=0;j<grid;j++) {
                b = new Button(this);
                TableRow.LayoutParams cell_params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);
                cell_params.weight = 1.0f;


                b.setBackgroundResource(R.drawable.cell);
                b.setId(i + j);
                b.setTextColor(getResources().getColor(R.color.white));

                final int x = i;
                final int y = j;

                blist[m] = b;

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int no = (x * grid) + y ;

                        if(timeFlag == false) {
                            timeFlag = true;
                            T = new Timer();
                            T.scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            timer.setText("" + tcount);
                                            tcount++;
                                        }
                                    });
                                }
                            }, 1000, 1000);
                        }

                        if(redFlag == false && btn_redFlag[no] == 0 && open[no] == 0)  {

                            boolean flag = false;

                            for (int k : mines) {
                                if (k == no) {
                                    gstate = "lost";
                                    blist[no].setBackgroundResource(R.drawable.mine_exploded);
                                    flag = true;
                                    for (int l : mines) {
                                        if (l != no) {
                                            blist[l].setBackgroundResource(R.drawable.mine);
                                        }
                                    }

                                    gameState.setBackgroundResource(R.drawable.smile_loser);
                                    red_Flag.setEnabled(false);
                                    T.cancel();

                                    for(int temp=0;temp<(grid*grid);temp++){
                                        blist[temp].setEnabled(false);
                                    }
                                    break;
                                }

                            }


                            if (flag == false ) {
                                open[no] = 1;

                                int mineCount = getMineCount(x, y);

                                GradientDrawable gd = new GradientDrawable();
                                gd.setColor(getResources().getColor(R.color.blueHeader)); // Changes this drawbale to use a single color instead of a gradient
                                gd.setCornerRadius(5);
                                gd.setStroke(1, 0xFF000000);
                                blist[no].setBackground(gd);
                                openCount += 1;

                                if (openCount == (grid * grid) - mines.length ){
                                    gstate = "won";
                                    gameState.setBackgroundResource(R.drawable.smile_winer);
                                    red_Flag.setEnabled(false);
                                    T.cancel();

                                    for(int temp=0;temp<(grid*grid);temp++){
                                        blist[temp].setEnabled(false);
                                    }

                                    AddScore scoreDialog = new AddScore();
                                    Bundle args = new Bundle();
                                    args.putInt("score", Integer.parseInt(timer.getText().toString()));
                                    args.putCharSequence("level", level);
                                    scoreDialog.setArguments(args);
                                    scoreDialog.show(getFragmentManager(),"score_add");

                                }

                                if (mineCount != 0) {
                                    blist[no].setText("" + mineCount);
                                } else {
                                    blist[no].setText("");
                                    openMultiple(no);
                                }
                            }
                        }
                        else if (redFlag == true && open[no] == 0)
                        {
                            if(btn_redFlag[no] == 0 && gcmines > 0){
                                btn_redFlag[no] = 1;
                                blist[no].setBackgroundResource(R.drawable.flag);
                                gcmines = gcmines - 1;
                                mine_count.setText("" + gcmines);
                            }
                            else if(btn_redFlag[no] == 1){
                                btn_redFlag[no] = 0;
                                blist[no].setBackgroundResource(R.drawable.cell);
                                gcmines = gcmines + 1;
                                mine_count.setText("" + gcmines);
                            }
                        }
                    }
                });

                grow.addView(b,cell_params);
                m++;
            }
            //gcontainer.addView(grow,row_params);
            TableLayout gcontainer = (TableLayout) v.findViewById(R.id.gcontainer);
            gcontainer.addView(grow,row_params);
        }

        zoomview = new ZoomView(this);
        zoomview.addView(v);
        mainContainer.addView(zoomview);

    }

    public void onNewGame(View view){

        if(gstate == "lost" || gstate == "won"){
            newGame();
        }


    }

    @Override
    public void onFinishEditDialog(String inputText) {
        String name = inputText;
        int score = Integer.parseInt(timer.getText().toString());

        DBAdapter db = new DBAdapter(this);
        db.open();
        long result = db.insertScore(name, score, level);
        if (result != 0) {
            Toast.makeText(this, "Score Added Successfully!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Problem adding Score", Toast.LENGTH_SHORT).show();
        }
    }
}
