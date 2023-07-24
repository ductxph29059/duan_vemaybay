package com.example.da1_group6.ui_user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.da1_group6.R;
import com.example.da1_group6.activities.ForUserActivity;
import com.example.da1_group6.dao.DAO_HoaDonNapTien;
import com.example.da1_group6.model.HoaDonNapTien;
import com.example.da1_group6.model.KhachHang;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity_UpBill extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PERMISSON_CODE_CAMERA = 2;
    static final int IMAGE_PICK_CODE = 10;
    static final int PERMISSON_CODE = 11;

    String photopath;
    Bitmap imageBitmap;

    Button btn_naptien;
    ImageView img_upbill, ic_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bill_naptien);

        img_upbill = findViewById(R.id.img_pick_bill);
        btn_naptien = findViewById(R.id.btn_naptien_bill);
        ic_add = findViewById(R.id.ic_add_in_bill);

        img_upbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        btn_naptien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageBitmap == null) {
                    Toast.makeText(Activity_UpBill.this, "Vui lòng tải lên biên lai chuyển tiền", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    SharedPreferences preferences = getSharedPreferences("INFO_USER_BANKING", Context.MODE_PRIVATE);
                    int makh = preferences.getInt("makh", 0);
                    int sotiennap = preferences.getInt("sotiennap", 0);
                    String time = preferences.getString("time", "");

                    HoaDonNapTien hd = new HoaDonNapTien();
                    DAO_HoaDonNapTien dao = new DAO_HoaDonNapTien(Activity_UpBill.this);

                    dao.addHD(new HoaDonNapTien(hd.getId(), makh, sotiennap, time, 0, imageBitmap));
                    Toast.makeText(Activity_UpBill.this, "Nạp tiền thành công. Vui lòng chờ admin xác nhận!!!", Toast.LENGTH_SHORT).show();

                    SharedPreferences preferences1 = getSharedPreferences("NOTI", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences1.edit();
                    editor.putBoolean("noti", true);
                    editor.commit();

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    };

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(runnable, 1000);
                }
            }
        });
    }


    public void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_select_image_from_camera);
        dialog.show();

        LinearLayout select1 = dialog.findViewById(R.id.select1);
        LinearLayout select2 = dialog.findViewById(R.id.select2);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        select1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermisson_Open_Camera();
                dialog.dismiss();
            }
        });

        select2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermisson_SelectImage_FromLirbrary();
                dialog.dismiss();
            }
        });

    }

    public void checkPermisson_Open_Camera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSON_CODE_CAMERA);
            } else {
                showCamera();
            }
        } else {
            showCamera();
        }
    }

    public void checkPermisson_SelectImage_FromLirbrary() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSON_CODE);
            } else {
                selectImage();
            }
        } else {
            selectImage();
        }
    }

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    public void showCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.da1_group6.ui_staff.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        photopath = image.getAbsolutePath();
        return image;
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = img_upbill.getWidth();
        int targetH = img_upbill.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photopath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        imageBitmap = BitmapFactory.decodeFile(photopath, bmOptions);
        img_upbill.setImageBitmap(imageBitmap);
        ic_add.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPic();
        }
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK) {
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                img_upbill.setImageBitmap(imageBitmap);
                ic_add.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSON_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage();
                } else {
                    Toast.makeText(this, "Vui lòng cấp quyền", Toast.LENGTH_SHORT).show();
                }
            case PERMISSON_CODE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showCamera();
                } else {
                    Toast.makeText(this, "Vui lòng cấp quyền", Toast.LENGTH_SHORT).show();
                }
        }
    }
}