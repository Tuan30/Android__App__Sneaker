package nhom8.android_coding.sneaker_app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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

public class AllProductActivity2 extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 0;
    int loai;
    AllProductadapter adapterProducts;
    List<SPMoi> spMoiList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product2);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        loai = getIntent().getIntExtra("loai", 1);
        AnhXa();
        ActionTooBar();
        getData(page);
        addEventLoad();
    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading == false){
                    if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == spMoiList.size()-1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //add null
                spMoiList.add(null);
                adapterProducts.notifyItemInserted(spMoiList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                spMoiList.remove(spMoiList.size()-1);
                adapterProducts.notifyItemRemoved(spMoiList.size());
                page = page+1;
                getData(page);
                adapterProducts.notifyDataSetChanged();
                isLoading = false;
            }
        },2000);
    }

    private void getData(int page) {
        compositeDisposable.add(apiBanHang.getProdcuts(page, loai)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                spMoiModel -> {
                    if (spMoiModel.isSuccess()){
                        if (adapterProducts == null){
                            spMoiList = spMoiModel.getResult();
                            adapterProducts = new AllProductadapter(getApplicationContext(), spMoiList);
                            recyclerView.setAdapter(adapterProducts);
                        }else {
                            int vitri = spMoiList.size()-1;
                            int soluongadd = spMoiModel.getResult().size();
                            for (int i=0; i<soluongadd; i++){
                                spMoiList.add(spMoiModel.getResult().get(i));
                            }
                            adapterProducts.notifyItemRangeInserted(vitri, soluongadd);
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Hết sản phẩm", Toast.LENGTH_LONG).show();
                        isLoading = true;
                    }
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Không kết nối được với server" , Toast.LENGTH_LONG).show();
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

    private void AnhXa() {
            toolbar = findViewById(R.id.toobar);
            recyclerView = findViewById(R.id.recycleview_allproduct);
            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            spMoiList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}