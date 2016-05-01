package edu.olemiss.hpatel3.p4hpatel3;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Harsh on 4/30/2015.
 */
public class AddScore extends DialogFragment{

    TextView dscore;
    EditText name;
    Button ok;
    Button cancel;
    int score;
    String level;

    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.score_add, container);

        getDialog().setTitle("You Won!");

        dscore = (TextView) view.findViewById(R.id.dscore);
        ok = (Button) view.findViewById(R.id.ok);
        cancel = (Button) view.findViewById(R.id.cancel);
        name = (EditText) view.findViewById(R.id.name);

        score = getArguments().getInt("score");
        level = getArguments().getString("level");
        dscore.setText("" + score);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditNameDialogListener activity = (EditNameDialogListener) getActivity();
                activity.onFinishEditDialog(name.getText().toString());
                getDialog().dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }

}
