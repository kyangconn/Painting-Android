package com.example.androidpainting;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidpainting.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    DrawingView drawingView;
    private LinearLayout colorPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.androidpainting.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");//set toolbar to none for the title.

        drawingView = findViewById(R.id.drawing_view);
        colorPopup = findViewById(R.id.popup_color);
        FloatingActionButton color = findViewById(R.id.fab_color);
        FloatingActionButton erase = findViewById(R.id.fab_erase);
        Button clear = findViewById(R.id.func_clear);
        Slider redSlider = findViewById(R.id.red_slider);
        Slider greenSlider = findViewById(R.id.green_slider);
        Slider blueSlider = findViewById(R.id.blue_slider);
        Slider sizeSlider = findViewById(R.id.size_slider);//set the control to variables.

        Slider.OnChangeListener listener = (slider, value, fromUser) -> {
            int buttonRed = (int) redSlider.getValue();
            int buttonGreen = (int) greenSlider.getValue();
            int buttonBlue = (int) blueSlider.getValue();
            int buttonColor = Color.rgb(buttonRed, buttonGreen, buttonBlue);

            drawingView.setPaintColor(buttonColor);
            color.setBackgroundTintList(ColorStateList.valueOf(buttonColor));
        };//set the listener for color sliders.

        sizeSlider.addOnChangeListener((slider, value, fromUser) -> {
            int paintSize = (int) sizeSlider.getValue();
            drawingView.setPaintSize(paintSize);
        });//set the listener for the size changer.

        redSlider.addOnChangeListener(listener);
        greenSlider.addOnChangeListener(listener);
        blueSlider.addOnChangeListener(listener);

        color.setOnClickListener(view -> popupColor());
        erase.setOnClickListener(view -> drawingView.setErase());
        clear.setOnClickListener(view -> drawingView.clear());
    }

    private void popupColor() {//with no popup dialog to show and dismiss the color sliders and size sliders
        if (colorPopup.getVisibility() == View.GONE)
            colorPopup.setVisibility(View.VISIBLE);
        else
            colorPopup.setVisibility(View.GONE);
    }
}
