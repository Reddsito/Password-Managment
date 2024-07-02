package com.password_managment.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.password_managment.R;

public class ButtonComponent extends LinearLayout {

    Button button;
    private OnClickListener onClickListener;

    public ButtonComponent(Context context) {
        super(context);
        initializeViews(context);
    }

    public ButtonComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public ButtonComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_button, this);

        button = findViewById(R.id.button);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
            }
        });

    }
    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    public void setButton(String label) {
        button.setText(label);
    }

    public String getText() {
        return button.getText().toString();
    }

    public void setBackgroudColor(Drawable color) {
        button.setBackground(color);
    }

}
