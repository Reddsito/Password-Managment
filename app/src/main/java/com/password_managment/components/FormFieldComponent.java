package com.password_managment.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.password_managment.R;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class FormFieldComponent extends LinearLayout {
    private TextView labelTextView;
    private EditText inputEditText;
    private OnClickListener onIconClickListener;


    public FormFieldComponent(Context context) {
        super(context);
        initializeViews(context);
    }

    public FormFieldComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public FormFieldComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_form_field, this);

        labelTextView = findViewById(R.id.tv_label);
        inputEditText = findViewById(R.id.et_input);

        inputEditText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Drawable drawableEnd = inputEditText.getCompoundDrawables()[2]; // Right drawable
                    if (drawableEnd != null && event.getRawX() >= (inputEditText.getRight() - drawableEnd.getBounds().width())) {
                        if (onIconClickListener != null) {
                            onIconClickListener.onClick(v);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void setInputText(String text) {
        inputEditText.setText(text);
    }

    public void setLabel(String label) {
        labelTextView.setText(label);
    }

    public String getText() {
        return inputEditText.getText().toString();
    }

    public void setHint(String hint) {
        inputEditText.setHint(hint);
    }

    public void setType(int type) {
        inputEditText.setInputType(type);
    }

    public void setIcon(int drawableId, Context context, int width, int height) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        drawable.setBounds(0,0, width, height);
        inputEditText.setCompoundDrawables(null, null, drawable, null);
    }

    public void setOnIconClickListener(OnClickListener listener) {
        this.onIconClickListener = listener;
    }

    public void setActive(Boolean isClickable, Context context) {
        inputEditText.setClickable(isClickable);
        inputEditText.setCursorVisible(isClickable);
        inputEditText.setFocusableInTouchMode(isClickable);

        int gray = ContextCompat.getColor(context, R.color.gray);
        int black = ContextCompat.getColor(context, R.color.black);

        inputEditText.setTextColor(isClickable ? black : gray);

    }
}
