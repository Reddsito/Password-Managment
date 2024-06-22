package com.password_managment.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.password_managment.R;
import androidx.annotation.Nullable;

public class FormFieldComponent extends LinearLayout {
    private TextView labelTextView;
    private EditText inputEditText;


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

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_form_field, this);

        labelTextView = findViewById(R.id.tv_label);
        inputEditText = findViewById(R.id.et_input);
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
}
