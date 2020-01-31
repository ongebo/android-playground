package com.example.transitions;

import android.os.Bundle;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ViewGroup sceneRoot;
    private Scene firstScene;
    private Scene secondScene;
    private Scene thirdScene;
    private TransitionManager transitionManager;

    private TextView firstTextView;
    private String firstInput;
    private TextView secondTextView;
    private String secondInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        transitionToSceneOne();
    }

    private void init() {
        sceneRoot = findViewById(R.id.scene_root);
        firstScene = Scene.getSceneForLayout(sceneRoot, R.layout.scene_one, this);
        secondScene = Scene.getSceneForLayout(sceneRoot, R.layout.scene_two, this);
        thirdScene = Scene.getSceneForLayout(sceneRoot, R.layout.scene_three, this);
        transitionManager = TransitionInflater.from(this).inflateTransitionManager(
                R.transition.manager, sceneRoot
        );
    }

    private void transitionToSceneOne() {
        transitionManager.transitionTo(firstScene);
        firstTextView = findViewById(R.id.user_input);
        firstTextView.setText(firstInput);
        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {
            firstInput = firstTextView.getText().toString();
            transitionToSceneTwo();
        });
    }

    private void transitionToSceneTwo() {
        transitionManager.transitionTo(secondScene);
        secondTextView = findViewById(R.id.user_input);
        secondTextView.setText(secondInput);
        Button nextButton = findViewById(R.id.next_button);
        Button prevButton = findViewById(R.id.prev_button);

        nextButton.setOnClickListener(v -> {
            secondInput = secondTextView.getText().toString();
            transitionToSceneThree();
        });
        prevButton.setOnClickListener(v -> {
            secondInput = secondTextView.getText().toString();
            transitionToSceneOne();
        });
    }

    private void transitionToSceneThree() {
        transitionManager.transitionTo(thirdScene);
        TextView firstEntryTextView = findViewById(R.id.first_entry);
        TextView secondEntryTextView = findViewById(R.id.second_entry);
        Button startOverButton = findViewById(R.id.startover_button);

        firstEntryTextView.setText(firstInput);
        secondEntryTextView.setText(secondInput);
        startOverButton.setOnClickListener(v -> {
            firstInput = null;
            secondInput = null;
            transitionToSceneOne();
        });
    }
}
