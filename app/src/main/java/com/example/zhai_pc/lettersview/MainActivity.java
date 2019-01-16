package com.example.zhai_pc.lettersview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView mTv;
    private LetterView mLerrer;
    private ArrayList<String> letters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = (TextView) findViewById(R.id.tv);
        mLerrer = (LetterView) findViewById(R.id.lerrer);
        initList();
        mLerrer.setOnShowLetter(new LetterView.onShowLetterListener() {
            @Override
            public void showLatter(int letter) {
                if (letter >= 0) {
                    mTv.setText(letters.get(letter));
                    mTv.setVisibility(View.VISIBLE);
                } else {
                    mTv.setVisibility(View.GONE);
                }

            }
        });
    }

    private void initList() {
        letters = new ArrayList<>();
        letters.add("A");
        letters.add("B");
        letters.add("C");
        letters.add("D");
        letters.add("E");
        letters.add("F");
        letters.add("G");
        letters.add("H");
        letters.add("I");
        letters.add("J");
        letters.add("K");
        letters.add("L");
        letters.add("M");
        letters.add("N");
        letters.add("O");
        letters.add("P");
        letters.add("Q");
        letters.add("R");
        letters.add("S");
        letters.add("T");
        letters.add("U");
        letters.add("V");
        letters.add("W");
        letters.add("X");
        letters.add("Y");
        letters.add("Z");
    }
}
