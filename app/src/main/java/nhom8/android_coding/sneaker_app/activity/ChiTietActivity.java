package nhom8.android_coding.sneaker_app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

import nhom8.android_coding.sneaker_app.R;
import nhom8.android_coding.sneaker_app.model.GioHang;
import nhom8.android_coding.sneaker_app.model.SPMoi;
import nhom8.android_coding.sneaker_app.utils.Utils;

public class ChiTietActivity extends AppCompatActivity {
    TextView tensp, giasp, mota;
    Button btnaddSP;
    ImageView imghinhanh;
    Spinner spinner, spinnersize;
    Toolbar toolbar;
    SPMoi spMoi;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);

        Anhxa();
        ActionTooBar();
        InitData();
        initControl();
    }

    private void initControl() {
        btnaddSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
            }
        });
    }

    private void themGioHang() {
        if (Utils.manggiohang.size() > 0){
            boolean flag = false;
            int size = Integer.parseInt(spinnersize.getSelectedItem().toString());
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            for (int i = 0 ; i < Utils.manggiohang.size() ; i++){
                if(Utils.manggiohang.get(i).getIdsp() == spMoi.getId());{
                    Utils.manggiohang.get(i).setSizesp(size + Utils.manggiohang.get(i).getSizesp());
                    Utils.manggiohang.get(i).setSoluong(soluong + Utils.manggiohang.get(i).getSoluong());
                    long gia = Long.parseLong(spMoi.getGiasp()) * Utils.manggiohang.get(i).getSoluong();
                    Utils.manggiohang.get(i).setGiasp(gia);
                    Utils.manggiohang.get(i).setSizesp(size);
                    flag = true;
                }
            }
            if (flag == false){
                long gia = Long.parseLong(spMoi.getGiasp()) * soluong;
                GioHang gioHang = new GioHang();
                gioHang.setGiasp(gia);
                gioHang.setSizesp(spMoi.getSizesp());
                gioHang.setIdsp(spMoi.getId());
                gioHang.setTensp(spMoi.getTensp());
                gioHang.setHinhsp(spMoi.getHinhanh());
                Utils.manggiohang.add(gioHang);
            }
        }else{
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            long gia = Long.parseLong(spMoi.getGiasp()) * soluong;
            GioHang gioHang = new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setSizesp(spMoi.getSizesp());
            gioHang.setIdsp(spMoi.getId());
            gioHang.setTensp(spMoi.getTensp());
            gioHang.setHinhsp(spMoi.getHinhanh());
            Utils.manggiohang.add(gioHang);
        }
        int totalItem = 0;
        for(int i = 0; i < Utils.manggiohang.size(); i++){
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private void InitData() {
        spMoi = (SPMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(spMoi.getTensp());
        mota.setText(spMoi.getMota());
        Glide.with(getApplicationContext()).load(spMoi.getHinhanh()).into(imghinhanh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: " + decimalFormat.format(Double.parseDouble(spMoi.getGiasp())) + "đ");
        Integer[] so = new Integer[]{1,2,3,4,5,6,7};
        Integer[] size = new Integer[]{36,37,38,39,40,41,42,43,44};
        ArrayAdapter<Integer> adaptersize = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, size );
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, so);
        spinnersize.setAdapter(adaptersize);
        spinner.setAdapter(adapter);
    }

    private void ActionTooBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.manggiohang != null){
            int totalItem = 0;
            for(int i = 0; i < Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }

    private void Anhxa() {
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        imghinhanh = findViewById(R.id.imgchitiet_image);
        spinner = findViewById(R.id.sniper);
        btnaddSP = findViewById(R.id.btnaddgiohang);
        toolbar = findViewById(R.id.toobar);
        badge = findViewById(R.id.menu_sl);
        spinnersize = findViewById(R.id.spinner_size);
        FrameLayout frameLayoutgiohang = findViewById(R.id.framegiohang);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });
        if (Utils.manggiohang != null){
            int totalItem = 0;
            for(int i = 0; i < Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }
}