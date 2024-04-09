package com.example.petconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.petconnect.R;
import com.example.petconnect.fragment.EditPostFragment;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.services.post.UpdatePostResponse;
import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected Toolbar toolbar;


    @Override
    public void setContentView(View view) {
        UserManager userManager = new UserManager(getBaseContext());
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        toolbar = drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton barButton = toolbar.findViewById(R.id.barButton);

        View headerView = navigationView.getHeaderView(0);

        TextView headerTextView = headerView.findViewById(R.id.sidebar_username);

        headerTextView.setText(userManager.getUser().getName());

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrawerBaseActivity.this, ProfileActivity.class));
            }
        });

        barButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Toast.makeText(DrawerBaseActivity.this, "Clicked", Toast.LENGTH_SHORT).show();

        if (id == R.id.sidebar_home) {
            startActivity(new Intent(DrawerBaseActivity.this, MainActivity.class));
        }
        // Xử lý nhấn item ở đây

        overridePendingTransition(0, 0);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

}
