package edu.olemiss.hpatel3.p4hpatel3;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Harsh on 4/30/2015.
 */
public class CustomGrid extends DialogFragment {

    Button cok;
    Button ccancel;
    SeekBar gridbar;
    SeekBar minebar;
    TextView gridtext;
    TextView minetext;
    int grid;
    int mines;

    public interface CustomSetting {
        void onFinishEditDialog(int grid, int mines);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_grid, container);

        getDialog().setTitle("Custom Game Board");

        cok = (Button) view.findViewById(R.id.cok);
        ccancel = (Button) view.findViewById(R.id.ccancel);
        gridbar = (SeekBar) view.findViewById(R.id.gridbar);
        minebar = (SeekBar) view.findViewById(R.id.minebar);
        gridtext = (TextView) view.findViewById(R.id.gridtext);
        minetext = (TextView) view.findViewById(R.id.minetext);

        gridbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gridtext.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        minebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                minetext.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        cok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomSetting activity = (CustomSetting) getActivity();
                activity.onFinishEditDialog(Integer.parseInt(gridtext.getText().toString()), Integer.parseInt(minetext.getText().toString()));
                getDialog().dismiss();
            }
        });

        ccancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }

}
