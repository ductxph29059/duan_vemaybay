package com.example.da1_group6.ui_staff;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_group6.R;
import com.example.da1_group6.dao.DAO_QLNV;
import com.example.da1_group6.model.NhanVien;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Info_staff extends Fragment {
    Spinner spin_sex, spin_quoctich;
    Button btnSave;
    CoordinatorLayout btn_Camera;
    TextView tv_manv, tv_chucvu;
    EditText edt_tennv, edt_email, edt_sdt;
    CircleImageView avatar;
    DAO_QLNV dao;
    NhanVien nv;
    ArrayList<NhanVien> list;
    String photopath;
    Bitmap imageBitmap;

    int index_sex = 0;
    int index_qt = 0;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PERMISSON_CODE_CAMERA = 2;
    static final int IMAGE_PICK_CODE = 10;
    static final int PERMISSON_CODE = 11;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_staff, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_Camera = view.findViewById(R.id.btn_camera_info);
        btnSave = view.findViewById(R.id.btnSave_Info_in_Staff);
        edt_tennv = view.findViewById(R.id.edt_name_staff);
        edt_email = view.findViewById(R.id.edt_email_staff);
        edt_sdt = view.findViewById(R.id.edt_sdt_staff);
        tv_manv = view.findViewById(R.id.tv_mnv_staff);
        tv_chucvu = view.findViewById(R.id.tv_chucvu);
        avatar = view.findViewById(R.id.avatar_staff);
        spin_quoctich = view.findViewById(R.id.spin_quoctich_staff);
        spin_sex = view.findViewById(R.id.spin_sex_staff);

        SharedPreferences preferences = getActivity().getSharedPreferences("TB", Context.MODE_PRIVATE);
        String user = preferences.getString("User", "");

        dao = new DAO_QLNV(getContext());
        list = dao.getStaff(user);
        nv = list.get(0);

        String[] list1 = {"Nam", "Nữ", "Khác"};
        String[] list2 = {"Việt Nam", "Hàn Quốc", "Nhật Bản"};

        ArrayAdapter adapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list1);
        spin_sex.setAdapter(adapter1);

        ArrayAdapter adapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list2);
        spin_quoctich.setAdapter(adapter2);

        tv_manv.setText(nv.getManv());
        edt_tennv.setText(nv.getTennv());
        edt_email.setText(nv.getEmail());
        edt_sdt.setText(nv.getSdt());
        tv_chucvu.setText(nv.getChucvu());
        avatar.setImageBitmap(nv.getImage());
        spin_sex.setSelection(nv.getGioitinh());
        spin_quoctich.setSelection(nv.getQuoctich());

        if (nv.getImage() == null) {
            avatar.setImageResource(R.drawable.img_avatar);
        }

        btn_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    String ten = edt_tennv.getText().toString().trim();
                    String email = edt_email.getText().toString().trim();
                    String sdt = edt_sdt.getText().toString().trim();
                    index_sex = spin_sex.getSelectedItemPosition();
                    index_qt = spin_quoctich.getSelectedItemPosition();
                    if (dao.update(new NhanVien(nv.getManv(), ten, email, sdt, index_sex, index_qt, nv.getChucvu(), nv.getMatkhau(), nv.getImage())) == true) {
                        Toast.makeText(getContext(), "Lưu thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void showDialog() {
        Dialog dialog = new Dialog(getContext());
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
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
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
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
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
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
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
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
        int targetW = avatar.getWidth();
        int targetH = avatar.getHeight();

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
        avatar.setImageBitmap(imageBitmap);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            setPic();
        }
        if (requestCode == IMAGE_PICK_CODE && resultCode == getActivity().RESULT_OK) {
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                avatar.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dao.updateImage(new NhanVien(nv.getManv(), nv.getTennv(), nv.getEmail(), nv.getSdt(), nv.getGioitinh(), nv.getQuoctich(), nv.getChucvu(), nv.getMatkhau(), imageBitmap));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSON_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage();
                } else {
                    Toast.makeText(getContext(), "Vui lòng cấp quyền", Toast.LENGTH_SHORT).show();
                }
            case PERMISSON_CODE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showCamera();
                } else {
                    Toast.makeText(getContext(), "Vui lòng cấp quyền", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public boolean check() {
        if (edt_tennv.getText().toString().trim().equals("") || edt_email.getText().toString().equals("")
                || edt_sdt.getText().toString().trim().equals("") || edt_sdt.getText().toString().trim().equals("")) {
            Toast.makeText(getContext(), "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edt_sdt.getText().toString().length() == 10) {
            if (!validateNumberPhone(edt_sdt.getText().toString().trim())) {
                Toast.makeText(getContext(), "Số điện thoại phải bắt đầu bằng 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(getContext(), "Số điện thoại phải có đủ 10 số", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean validateNumberPhone(String numberphone) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^0([1-9]{9})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(numberphone);
        return matcher.matches();
    }
}