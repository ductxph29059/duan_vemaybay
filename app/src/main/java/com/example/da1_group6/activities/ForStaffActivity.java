package com.example.da1_group6.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.da1_group6.R;
import com.example.da1_group6.dao.DAO_QLNV;
import com.example.da1_group6.databinding.ActivityForStaffBinding;
import com.example.da1_group6.model.NhanVien;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ForStaffActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityForStaffBinding binding;
    TextView tv_hello;
    CircleImageView avatar;
    String email;

    DAO_QLNV dao;
    NhanVien nv;
    ArrayList<NhanVien> list;

    public static final String CHANNEL_ID = "channel_id";
    public static final String CHANNEL_NAME = "channel_name";
    public static final String CHANNEL_DESC = "channel_desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForStaffBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarForStaff.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_qlyvemb_staff, R.id.nav_xnvemb_staff, R.id.nav_doanhthu_staff, R.id.nav_info_staff, R.id.nav_doimk_staff)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_for_staff);

        navigationView.setItemIconTintList(null);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if(navDestination.getId() == R.id.nav_thoat) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ForStaffActivity.this);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có chắc là muốn đăng xuất không?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ProgressDialog dialog1 = new ProgressDialog(ForStaffActivity.this);
                            dialog1.setMessage("Đang đăng xuất...");
                            dialog1.show();
                            Thread thread = new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    try {
                                        sleep(1444);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } finally {
                                        finish();
                                    }
                                }
                            };
                            thread.start();
                        }
                    });

                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View viewheader = navigationView.getHeaderView(0);
        tv_hello = viewheader.findViewById(R.id.tv_hello_staff);
        avatar = viewheader.findViewById(R.id.avatar_inheader_staff);

        SharedPreferences preferences = getSharedPreferences("TB", Context.MODE_PRIVATE);
        email = preferences.getString("User", "");

        dao = new DAO_QLNV(this);
        list = dao.getStaff(email);
        nv = list.get(0);

        tv_hello.setText("Xin chào, \n" + nv.getTennv());
        avatar.setImageBitmap(nv.getImage());

        if(nv.getImage() == null) {
            avatar.setImageResource(R.drawable.img_avatar);
        }
        reloadData();

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                reloadData();
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                reloadData();
            }
        });

        SharedPreferences preferences1 = getSharedPreferences("NOTI_STAFF", MODE_PRIVATE);
        String manv_noti = preferences1.getString("noti_manv", "");
        boolean check_noti = preferences1.getBoolean("noti_check", false);
        if(manv_noti.equalsIgnoreCase(email) && check_noti == true) {
            notification();
            headNoti();
            SharedPreferences.Editor editor = preferences1.edit();
            editor.putBoolean("noti_check", false);
            editor.putString("noti_manv", email);
            editor.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_for_staff);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void reloadData() {
        dao = new DAO_QLNV(this);
        list = dao.getStaff(email);
        nv = list.get(0);

        tv_hello.setText("Xin chào, \n" + nv.getTennv());
        avatar.setImageBitmap(nv.getImage());
        if(nv.getImage() == null) {
            avatar.setImageResource(R.drawable.img_avatar);
        }
    }

    public void notification() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notiChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notiChannel.setDescription(CHANNEL_DESC);

            NotificationManager managerCompat = getSystemService(NotificationManager.class);
            managerCompat.createNotificationChannel(notiChannel);
        }
    }

    public void headNoti() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                CHANNEL_ID)
                .setContentTitle("Notification")
                .setContentText("Bạn có yêu cầu xác nhận vé máy bay mới!")
                .setSmallIcon(R.drawable.ic_avatar_app)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1002, builder.build());
    }
}