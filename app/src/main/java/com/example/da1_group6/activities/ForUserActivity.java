package com.example.da1_group6.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.da1_group6.R;
import com.example.da1_group6.dao.DAO_KhachHang;
import com.example.da1_group6.databinding.ActivityForUserBinding;
import com.example.da1_group6.model.KhachHang;
import com.example.da1_group6.ui_user.Fragment_Wallet_user;
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

public class ForUserActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityForUserBinding binding;
    DAO_KhachHang dao;
    ArrayList<KhachHang> list;
    KhachHang kh;
    TextView tv_hello, sodu;
    CircleImageView avatar;
    String email;

    public static final String CHANNEL_ID = "channel_id";
    public static final String CHANNEL_NAME = "channel_name";
    public static final String CHANNEL_DESC = "channel_desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarForUser.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dsve_user, R.id.nav_datve_user, R.id.nav_info_user, R.id.nav_wallet_user, R.id.nav_doimk_user)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_for_user);
        navigationView.setItemIconTintList(null);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (navDestination.getId() == R.id.nav_thoat) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ForUserActivity.this);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có chắc là muốn đăng xuất không?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ProgressDialog dialog1 = new ProgressDialog(ForUserActivity.this);
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
        tv_hello = (TextView) viewheader.findViewById(R.id.tv_hello_user);
        avatar = (CircleImageView) viewheader.findViewById(R.id.avatar_inheader_user);
        sodu = (TextView) viewheader.findViewById(R.id.sodu_in_header);

        SharedPreferences preferences = getSharedPreferences("TB", Context.MODE_PRIVATE);
        email = preferences.getString("User", "");

        reloadData(email);

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                reloadData(email);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                reloadData(email);
            }
        });

        dao = new DAO_KhachHang(this);
        list = dao.getUser(email);
        kh = list.get(0);

        if (kh.getTenkh() == null || kh.getDiachi() == null || kh.getSdt() == null) {

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    LayoutInflater inflater = LayoutInflater.from(ForUserActivity.this);
                    View custom = inflater.inflate(R.layout.layout_dialog_notify_noname, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ForUserActivity.this);
                    builder.setView(custom);
                    builder.setInverseBackgroundForced(false);
                    builder.setPositiveButton("OKEY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            };

            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(runnable, 1686);
        }

        SharedPreferences preferences1 = getSharedPreferences("NOTI_NAPTIEN", Context.MODE_PRIVATE);
        SharedPreferences preferences2 = getSharedPreferences("NOTI_STT_VMB", Context.MODE_PRIVATE);

        String makh = preferences1.getString("makh", "");
        String makh1 = preferences2.getString("makh", "");
        boolean check = preferences1.getBoolean("check", false);
        boolean check1 = preferences2.getBoolean("check", false);

        if(makh.equalsIgnoreCase(String.valueOf(kh.getMakh())) && check == true) {
            notification1();
            headNoti1();
            SharedPreferences.Editor editor = preferences1.edit();
            editor.putString("makh", String.valueOf(kh.getMakh()));
            editor.putBoolean("check", false);
            editor.commit();
        }

        if(makh1.equalsIgnoreCase(String.valueOf(kh.getMakh())) && check1 == true) {
            notification2();
            headNoti2();
            SharedPreferences.Editor editor = preferences2.edit();
            editor.putString("makh", String.valueOf(kh.getMakh()));
            editor.putBoolean("check", false);
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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_for_user);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void reloadData(String email) {
        dao = new DAO_KhachHang(this);
        list = dao.getUser(email);
        kh = list.get(0);


        tv_hello.setText("Xin chào, \n" + kh.getTenkh());
        avatar.setImageBitmap(kh.getImage());

        if (kh.getImage() == null) {
            avatar.setImageResource(R.drawable.img_avatar);
        }

        String sodustr = String.valueOf(kh.getSodu());
        StringBuilder str = new StringBuilder(sodustr);
        for (int i = str.length(); i > 0; i -= 3) {
            str.insert(i, " ");
        }
        sodu.setText("Số dư: " + str + " vnđ");
    }

    public void notification1() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notiChannel = new NotificationChannel("id", "name", NotificationManager.IMPORTANCE_HIGH);
            notiChannel.setDescription("desc");

            NotificationManager managerCompat = getSystemService(NotificationManager.class);
            managerCompat.createNotificationChannel(notiChannel);
        }
    }

    public void notification2() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notiChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notiChannel.setDescription(CHANNEL_DESC);

            NotificationManager managerCompat = getSystemService(NotificationManager.class);
            managerCompat.createNotificationChannel(notiChannel);
        }
    }

    public void headNoti1() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                "id")
                .setContentTitle("Bạn đã được admin xác nhận nạp tiền!")
                .setContentText("Vui lòng kiểm tra số dư!")
                .setSmallIcon(R.drawable.ic_avatar_app)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1001, builder.build());
    }

    public void headNoti2() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                CHANNEL_ID)
                .setContentTitle("Bạn có thông báo mới về trạng thái vé của bạn!")
                .setContentText("Vui lòng kiểm tra!")
                .setSmallIcon(R.drawable.ic_avatar_app)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1002, builder.build());
    }

}