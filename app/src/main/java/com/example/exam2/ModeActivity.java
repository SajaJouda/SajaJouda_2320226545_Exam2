package com.example.exam2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.exam2.databinding.ActivityModeBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class ModeActivity extends AppCompatActivity {

    private ActivityModeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityModeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.viewPager.setAdapter(new PagerAdapter(this));

        ArrayList<String> tabs = new ArrayList<>();
        tabs.add(getString(R.string.first));
        tabs.add(getString(R.string.second));
        tabs.add(getString(R.string.third));

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            tab.setText(tabs.get(position));
        }).attach();

        binding.setModeButton.setOnClickListener(view -> {
            showPopupMenu();
        });
    }

    public void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(ModeActivity.this, binding.setModeButton);
        popupMenu.getMenuInflater().inflate(R.menu.menu_mode, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.item_light) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                return true;
            } else if (menuItem.getItemId() == R.id.item_night) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                return true;
            } else if (menuItem.getItemId() == R.id.item_system) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (binding.viewPager.getCurrentItem() != 0) {
            binding.viewPager.setCurrentItem(0);
        } else {
            finish();
        }
    }

    public class PagerAdapter extends FragmentStateAdapter {
        public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 1: return new SecondFragment();
                case 2: return new ThirdFragment();
                default: return new FirstFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}