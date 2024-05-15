package com.example.bookish.custom.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.bookish.R;

public class HalfPressableSentence extends LinearLayout {

    private TextView regularTextView;
    private TextView clickableTextView;

    public HalfPressableSentence(Context context) {
        super(context);
        init(context, null);
    }

    public HalfPressableSentence(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HalfPressableSentence(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_half_pressable_sentence, this, true);

        regularTextView = findViewById(R.id.textView);
        clickableTextView = findViewById(R.id.loginTextView);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HalfPressableSentence);
            String regularText = a.getString(R.styleable.HalfPressableSentence_regularText);
            String clickableText = a.getString(R.styleable.HalfPressableSentence_clickableText);
            int clickableTextColor = a.getColor(R.styleable.HalfPressableSentence_clickableTextColor, 0);

            setRegularText(regularText);
            setClickableText(clickableText, null);
            if (clickableTextColor != 0) {
                clickableTextView.setTextColor(clickableTextColor);
            }

            a.recycle();
        }
    }

    public void setRegularText(String text) {
        regularTextView.setText(text);
    }

    public void setClickableText(String text, OnClickListener listener) {
        clickableTextView.setText(text);
        if (listener != null) {
            clickableTextView.setOnClickListener(listener);
        }
    }
}
