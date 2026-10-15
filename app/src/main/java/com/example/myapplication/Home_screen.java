package com.example.myapplication;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home_screen extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private int volumeSequenceStep = 0;
    private long lastSequenceStepTime = 0;

    private float startX = 0, startY = 0;
    private float maxY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
            if (bottomNavigationView != null) {
                bottomNavigationView.setSelectedItemId(R.id.nav_inicio);
            }
        }

        if (bottomNavigationView != null) {
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_inicio) {
                    return loadFragment(new HomeFragment());
                } else if (itemId == R.id.nav_mensajes) {
                    return loadFragment(new MessagesFragment());
                } else if (itemId == R.id.nav_solicitudes) {
                    return loadFragment(new SolicitudesFragment());
                } else if (itemId == R.id.nav_perfil) {
                    return loadFragment(new ProfileFragment());
                }

                return false;
            });
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            int keyCode = event.getKeyCode();

            if (System.currentTimeMillis() - lastSequenceStepTime > 6000) {
                volumeSequenceStep = 0;
            }

            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                volumeSequenceStep = 1;
                lastSequenceStepTime = System.currentTimeMillis();
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                if (volumeSequenceStep == 1) {
                    volumeSequenceStep = 2;
                    lastSequenceStepTime = System.currentTimeMillis();
                } else if (volumeSequenceStep == 2) {
                    volumeSequenceStep = 3;
                    lastSequenceStepTime = System.currentTimeMillis();
                } else if (volumeSequenceStep == 3) {
                    volumeSequenceStep = 4;
                    lastSequenceStepTime = System.currentTimeMillis();
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                maxY = startY;
                break;

            case MotionEvent.ACTION_MOVE:
                float currentY = event.getY();
                if (currentY > maxY) {
                    maxY = currentY;
                }
                break;

            case MotionEvent.ACTION_UP:
                float endX = event.getX();
                float endY = event.getY();

                float verticalDownDistance = maxY - startY;
                float horizontalRightDistance = endX - startX;
                float endVerticalDiff = Math.abs(endY - maxY);

                boolean isLGesture = (verticalDownDistance > 80) &&
                                     (horizontalRightDistance > 80) &&
                                     (endVerticalDiff < 160);

                boolean isSequenceActive = (volumeSequenceStep == 4) &&
                                           (System.currentTimeMillis() - lastSequenceStepTime < 10000);

                if (isLGesture && isSequenceActive) {
                    volumeSequenceStep = 0;
                    showGhostScreen();
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private void showGhostScreen() {
        if (!isFinishing() && !isDestroyed()) {
            GhostScreenDialog ghostDialog = GhostScreenDialog.newInstance();
            ghostDialog.show(getSupportFragmentManager(), "GhostScreenDialog");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBottomNavTint();
    }

    public void updateBottomNavTint() {
        if (bottomNavigationView == null) return;

        boolean isProvider = UserRoleManager.isProviderMode(this);
        int activeColor = isProvider ? Color.parseColor("#2563EB") : Color.parseColor("#059669");
        int inactiveColor = Color.parseColor("#9CA3AF");

        int[][] states = new int[][]{
                new int[]{android.R.attr.state_checked},
                new int[]{-android.R.attr.state_checked}
        };

        int[] colors = new int[]{
                activeColor,
                inactiveColor
        };

        ColorStateList colorStateList = new ColorStateList(states, colors);
        bottomNavigationView.setItemIconTintList(colorStateList);
        bottomNavigationView.setItemTextColor(colorStateList);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}