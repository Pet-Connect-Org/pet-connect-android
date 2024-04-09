package com.example.petconnect;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

/*
                    ===== BASIC USAGE =====

        dropdownDemo = findViewById(R.id.dropdown_demo);

        ArrayList<Item> demo = new ArrayList<>();

        demo.add(new Item("key", "Value"));

        dropdownDemo.setItems(demo);

        String key = dropdownDemo.getSelectedItemKey();
 */
public class CustomDropdown extends LinearLayout {
    Button dropdown_menu;
    TextView labelDropdown;

    public ArrayList<Item> getItems() {
        return items;
    }

    ArrayList<Item> items;
    String selectedItemKey;

    boolean canSetText = true;

    public CustomDropdown(Context context) {
        super(context);
        initializeViews(context, null);
    }

    public interface OnItemSelectedListener {
        void onItemSelected(String key);
    }

    public void customizeDropdown(int backgroundColor, int iconDrawable, boolean canSetText) {
        setBackground(backgroundColor);
        this.canSetText = canSetText;
        if (backgroundColor == android.R.color.transparent) {
            disabledPadding();
        }
        setIcon(iconDrawable);
    }


    private OnItemSelectedListener listener;

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public CustomDropdown(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    public void setIcon(int endIconDrawable) {
        dropdown_menu.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(endIconDrawable), null);
    }

    public void setBackground(Drawable backgroundDrawable) {
        dropdown_menu.setBackground(backgroundDrawable);
    }

    public void setBackground(int colorResourceId) {
        dropdown_menu.setBackgroundColor(getResources().getColor(colorResourceId));
    }

    public void disabledPadding() {
        dropdown_menu.setPadding(0, 0, 0, 0);
    }


    public String getSelectedItemKey() {
        return this.selectedItemKey;
    }

    public void setSelectedItemKey(String key) {
        this.selectedItemKey = key;
    }

    private void initializeViews(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_dropdown, this);

        dropdown_menu = findViewById(R.id.dropdown_menu);
        labelDropdown = findViewById(R.id.labelDropdown);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomDropdown);

        String dropdown_initial_text = a.getString(R.styleable.CustomDropdown_labelDropdown);

        dropdown_menu.setText(dropdown_initial_text);

        if (dropdown_initial_text != null) {
            labelDropdown.setText(dropdown_initial_text);
            labelDropdown.setVisibility(VISIBLE);
        } else {
            labelDropdown.setVisibility(GONE);
        }

        a.recycle();

        dropdown_menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, dropdown_menu);

                for (Item item : items) {
                    popup.getMenu().add(item.getValue());
                }

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        String selectedItemTitle = item.getTitle().toString();
                        int index = -1;
                        for (int i = 0; i < items.size(); i++) {
                            if (selectedItemTitle.equals(items.get(i).getValue())) {
                                index = i;
                                break;
                            }
                        }

                        if (index >= 0 && index < items.size()) {
                            setSelectedItemKey(items.get(index).getKey());
                            if (canSetText) {
                                dropdown_menu.setText(selectedItemTitle);
                            }
                            if (listener != null) {
                                listener.onItemSelected(items.get(index).getKey());
                            }
                        }
                        return true;
                    }
                });


                popup.show();
            }
        });
    }

    public void setDefaultItem(String defaultKey) {
        for (Item item : items) {
            if (item.getKey().equals(defaultKey)) {
                setSelectedItemKey(defaultKey);
                if (canSetText) {
                    dropdown_menu.setText(item.getValue());
                }
                if (listener != null) {
                    listener.onItemSelected(defaultKey);
                }
                return;
            }
        }
    }
}
