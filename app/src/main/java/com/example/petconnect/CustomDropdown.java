package com.example.petconnect;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

/*
                    ===== BASIC USAGE =====

        dropdownDemo = findViewById(R.id.dropdown_demo);

        Item[] demo = new Item[] {
            new Item("Key1", "Value1"),
            new Item("Key2", "Value2")
        };

        dropdownDemo.setItems(demo);

        String key = dropdownDemo.getSelectedItemKey();
 */
public class CustomDropdown extends LinearLayout {
    Button dropdown_menu;

    TextView labelDropdown;
    Item[] items;
    String selectedItemKey;

    public CustomDropdown(Context context) {
        super(context);
        initializeViews(context, null);
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public CustomDropdown(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
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

        int itemsId = a.getResourceId(R.styleable.CustomDropdown_options, 0);

        String dropdown_initial_text = a.getString(R.styleable.CustomDropdown_labelDropdown);

        dropdown_menu.setText(dropdown_initial_text);
        labelDropdown.setText(dropdown_initial_text);

        if (itemsId != 0) {
            String[] itemStrings = context.getResources().getStringArray(itemsId);
            items = new Item[itemStrings.length];
            for (int i = 0; i < itemStrings.length; i++) {
                String[] keyValue = itemStrings[i].split("=");
                if (keyValue.length == 2) {
                    items[i] = new Item(keyValue[0], keyValue[1]);
                }
            }
        }

        a.recycle();

        dropdown_menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, dropdown_menu);

                if (items != null) {
                    for (Item item : items) {
                        popup.getMenu().add(item.getValue());
                    }
                }

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        setSelectedItemKey(items[item.getItemId()].getKey());
                        dropdown_menu.setText(item.getTitle());
                        return true;
                    }
                });

                popup.show();
            }
        });
    }
}
