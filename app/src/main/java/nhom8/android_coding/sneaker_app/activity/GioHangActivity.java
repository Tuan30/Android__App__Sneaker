package nhom8.android_coding.sneaker_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

import nhom8.android_coding.sneaker_app.R;
import nhom8.android_coding.sneaker_app.adapter.GioHangadapter;
import nhom8.android_coding.sneaker_app.model.EventBus.TongEvent;
import nhom8.android_coding.sneaker_app.utils.Utils;

public class GioHangActivity extends AppCompatActivity {

    TextView giohangtrong, tongtien;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnmuahang;
    GioHangadapter gioHangadapter;
    long tongtiensp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Anhxa();
        totalTongTien();
        initControl();
    }

    private void totalTongTien() {
        tongtiensp = 0;
        for (int i = 0; i < Utils.mangmuahang.size(); i ++) {
            tongtiensp = tongtiensp + Utils.mangmuahang.get(i).getGiasp() * Utils.mangmuahang.get(i).getSoluong();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(tongtiensp));
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (Utils.manggiohang.size() == 0){
            giohangtrong.setVisibility(View.VISIBLE);
        }else{
            gioHangadapter = new GioHangadapter(getApplicationContext(), Utils.manggiohang);
            recyclerView.setAdapter(gioHangadapter);
        }
        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThanhToanActivity.class);
                intent.putExtra("tongtien", tongtiensp);
                Utils.manggiohang.clear();
                startActivity(intent);
            }
        });
    }

    private void Anhxa() {

        giohangtrong = findViewById(R.id.txtgiohangtrong);
        toolbar = findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recycleviewgiohang);
        tongtien = findViewById(R.id.txttongtien);
        btnmuahang = findViewById(R.id.btnmuahang);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventTong(TongEvent event){
        if(event != null){
            totalTongTien();
        }
    }
}