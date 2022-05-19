package nhom8.android_coding.sneaker_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import nhom8.android_coding.sneaker_app.R;
import nhom8.android_coding.sneaker_app.adapter.AllProductadapter;
import nhom8.android_coding.sneaker_app.model.SPMoi;
import nhom8.android_coding.sneaker_app.retrofit.ApiBanHang;
import nhom8.android_coding.sneaker_app.retrofit.RetrofitClient;
import nhom8.android_coding.sneaker_app.utils.Utils;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtsearch;
    RecyclerView recyclerView;
    AllProductadapter adapterProducts;
    List<SPMoi> spMoiList;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Anhxa();
        ActionTooBar();
    }

    private void Anhxa() {
        spMoiList = new ArrayList<>();
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        edtsearch = findViewById(R.id.edtsearch);
        toolbar =findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recycleview_search);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() == 0){
                    spMoiList.clear();
                    adapterProducts = new AllProductadapter(getApplicationContext(), spMoiList);
                    recyclerView.setAdapter(adapterProducts);
                }else{
                    getDataSearch(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getDataSearch(String s) {
        spMoiList.clear();
        compositeDisposable.add(apiBanHang.search(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        spMoiModel -> {
                            if (spMoiModel.isSuccess()){
                                spMoiList = spMoiModel.getResult();
                                adapterProducts = new AllProductadapter(getApplicationContext(), spMoiList);
                                recyclerView.setAdapter(adapterProducts);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
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
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}