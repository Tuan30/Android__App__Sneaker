package nhom8.android_coding.sneaker_app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

import nhom8.android_coding.sneaker_app.R;
import nhom8.android_coding.sneaker_app.model.SPMoi;

public class ChiTietActivity extends AppCompatActivity {
    TextView tensp, giasp, mota;
    Button btnaddSP;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);

        Anhxa();
        ActionTooBar();
        InitData();
    }

    private void InitData() {
        SPMoi spMoi = (SPMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(spMoi.getTensp());
        mota.setText(spMoi.getMota());
        Glide.with(getApplicationContext()).load(spMoi.getHinhanh()).into(imghinhanh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: " + decimalFormat.format(Double.parseDouble(spMoi.getGiasp())) + "đ");
        Integer[] so = new Integer[]{36,37,38,39,40,41,42,43,44};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, so);
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

    private void Anhxa() {
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        imghinhanh = findViewById(R.id.imgchitiet_image);
        spinner = findViewById(R.id.sniper);
        btnaddSP = findViewById(R.id.btnaddgiohang);
        toolbar = findViewById(R.id.toobar);
    }
}