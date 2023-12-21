package com.maanvith.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.maanvith.app.databinding.ActivityHomeScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Handler;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class HomeScreen extends AppCompatActivity {

    FirebaseAuth auth;

    private ActivityHomeScreenBinding binding;
    private ALoadingDialog aLoadingDialog;
    private static final long DOUBLE_BACK_TIME_INTERVAL = 2000;
    private long lastBackPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new SearchFragment());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));
        }
        auth = FirebaseAuth.getInstance();
        String user = auth.getUid();
        aLoadingDialog = new ALoadingDialog(this);

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.search) {
                    replaceFragment(new SearchFragment());
                } else if (item.getItemId() == R.id.publish) {
                    replaceFragment(new PublishFragment());
                } else if (item.getItemId() == R.id.rides) {
                    aLoadingDialog.show();
                    Handler handler  = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            aLoadingDialog.cancel();
                        }
                    };
                    handler.postDelayed(runnable,5000);
                    replaceFragment(new RidesFragment());
                } else if (item.getItemId() == R.id.inbox) {
                    aLoadingDialog.show();
                    Handler handler  = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            aLoadingDialog.cancel();
                        }
                    };
                    handler.postDelayed(runnable,5000);
                    replaceFragment(new InboxFragment());
                } else if (item.getItemId() == R.id.profile) {
                    aLoadingDialog.show();
                    Handler handler  = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            aLoadingDialog.cancel();
                        }
                    };
                    handler.postDelayed(runnable,5000);
                    ProfileFragment profileFragment = new ProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("user",user);
                    profileFragment.setArguments(bundle);
                    replaceFragment(profileFragment);
                }
                return true;
            }
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastBackPressedTime < DOUBLE_BACK_TIME_INTERVAL) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            lastBackPressedTime = currentTime;
        }
    }
}
