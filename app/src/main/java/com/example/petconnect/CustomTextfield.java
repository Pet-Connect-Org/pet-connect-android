package com.example.petconnect;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomTextfield extends LinearLayout {

    private TextView labelTextView;
    private EditText inputEditText;
    private Drawable startIconDrawable;
    private Drawable endIconDrawable;
    private OnTextChangeListener textChangeListener;

    public interface OnTextChangeListener {
        void onTextChange(CharSequence text);
    }

    public CustomTextfield(Context context) {
        super(context);
        initializeViews(context, null);
    }

    public CustomTextfield(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    public CustomTextfield(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context, attrs);
    }

    private void initializeViews(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_textfield, this);

        labelTextView = findViewById(R.id.labelTextView);
        inputEditText = findViewById(R.id.inputEditText);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTextfield);
            String labelText = a.getString(R.styleable.CustomTextfield_labelText);
            String inputText = a.getString(R.styleable.CustomTextfield_inputText);
            String hint = a.getString(R.styleable.CustomTextfield_hint);
            startIconDrawable = a.getDrawable(R.styleable.CustomTextfield_startIcon);
            endIconDrawable = a.getDrawable(R.styleable.CustomTextfield_endIcon);

            a.recycle();

            if (inputText != null) {
                inputEditText.setText(inputText);
            }
            if (labelText != null) {
                labelTextView.setText(labelText);
            }
            if (hint != null) {
                inputEditText.setHint(hint);
            }
            inputEditText.setCompoundDrawablesWithIntrinsicBounds(startIconDrawable, null, endIconDrawable, null);
        }

        setupTextChangeListener();
    }

    public void setOnTextChangeListener(OnTextChangeListener listener) {
        this.textChangeListener = listener;
    }

    public void setText(String value) {
        inputEditText.setText(value);
    }

    public String getText() {
        return inputEditText.getText().toString();
    }

    private void setupTextChangeListener() {
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (textChangeListener != null) {
                    textChangeListener.onTextChange(charSequence);
                }
            }
        });
    }
}
