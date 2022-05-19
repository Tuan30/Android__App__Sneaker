package nhom8.android_coding.sneaker_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import nhom8.android_coding.sneaker_app.R;
import nhom8.android_coding.sneaker_app.retrofit.ApiBanHang;
import nhom8.android_coding.sneaker_app.retrofit.RetrofitClient;
import nhom8.android_coding.sneaker_app.utils.Utils;

public class ThanhToanActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txttongtien, txtsdt, txtmail;
    EditText edtlocation;
    AppCompatButton button;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    long tongtien;
    int totalItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        Anhxa();
        countItem();
        initControl();
    }

    private void countItem() {
        totalItem = 0;
        for(int i = 0; i < Utils.mangmuahang.size(); i++){
            totalItem = totalItem + Utils.mangmuahang.get(i).getSoluong();
        }
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
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien = getIntent().getLongExtra("tongtien" , 0);

        txttongtien.setText(decimalFormat.format(tongtien));
        txtmail.setText(Utils.user_current.getEmail());
        txtsdt.setText(Utils.user_current.getMobile());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_diachi = edtlocation.getText().toString().trim();
                if(TextUtils.isEmpty(str_diachi)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                }else {
                    //pos data
                    String str_email = Utils.user_current.getEmail();
                    String str_sdt = Utils.user_current.getMobile();
                    int id = Utils.user_current.getId();
                    Log.d("test", new Gson().toJson(Utils.mangmuahang));
                    compositeDisposable.add(apiBanHang.createOder(str_email, str_sdt, String.valueOf(tongtien), id, str_diachi, totalItem, new Gson().toJson(Utils.mangmuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        Toast.makeText(getApplicationContext(), "Thanh cong", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        Utils.mangmuahang.clear();
                                        finish();
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private void Anhxa() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = findViewById(R.id.toobardathang);
        txttongtien = findViewById(R.id.txttoaldathang);
        txtsdt = findViewById(R.id.txtphonedathang);
        txtmail = findViewById(R.id.txtemaildathang);
        edtlocation = findViewById(R.id.edtlocation);
        button = findViewById(R.id.btndathang);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}