package nhom8.android_coding.sneaker_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import nhom8.android_coding.sneaker_app.R;
import nhom8.android_coding.sneaker_app.adapter.LoaiSPadapter;
//import nhom8.android_coding.sneaker_app.adapter.SPMoiadapter;
import nhom8.android_coding.sneaker_app.adapter.SPMoiadapter;
import nhom8.android_coding.sneaker_app.model.DonHang;
import nhom8.android_coding.sneaker_app.model.LoaiSP;
//import nhom8.android_coding.sneaker_app.model.SPMoi;
//import nhom8.android_coding.sneaker_app.model.SPMoiModel;
import nhom8.android_coding.sneaker_app.model.SPMoi;
import nhom8.android_coding.sneaker_app.model.SPMoiModel;
import nhom8.android_coding.sneaker_app.model.User;
import nhom8.android_coding.sneaker_app.retrofit.ApiBanHang;
import nhom8.android_coding.sneaker_app.retrofit.RetrofitClient;
import nhom8.android_coding.sneaker_app.utils.Utils;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewHome;
    NavigationView navigationView;
    ListView listViewHome;
    DrawerLayout drawerLayout;
    LoaiSPadapter loaiSPadapter;
    List<LoaiSP> mangloaisp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<SPMoi> mangspMoi;
    SPMoiadapter spMoiadapter;
    NotificationBadge badge;
    FrameLayout frameLayout;
    ImageView imgsearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Paper.init(this);
        if(Paper.book().read("user") != null){
            User user = Paper.book().read("user");
            Utils.user_current = user;
        }else{

        }

        anhxa();
        ActionBar();
        ActionViewFlipper();
        if(isConnected(this)){
            Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
            ActionViewFlipper();
            LoaiSanPham();
//            SpMoi();
            getSpMoi();
            EventClick();
        }else{
            Toast.makeText(getApplicationContext(), "Không có internet, Vui lòng kết nối lại", Toast.LENGTH_LONG).show();
        }
    }

    private void EventClick() {
        listViewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 6:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 7: // Xem don hang
                        Intent donhang = new Intent(getApplicationContext(), XemDonActivity.class);
                        startActivity(donhang);
                        break;
                    case 8:
                        //xoa key user
                        Paper.book().delete("user");

                        Intent dangnhap = new Intent(getApplicationContext(), DangNhapActivity.class);
                        startActivity(dangnhap);
                        finish();
                        break;
                }
            }
        });
    }

    private void getSpMoi() {
        compositeDisposable.add(apiBanHang.getSPMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        SPMoiModel -> {
                            if (SPMoiModel.isSuccess()){
                                mangspMoi = SPMoiModel.getResult();
                                spMoiadapter = new SPMoiadapter(getApplicationContext(), mangspMoi);
                                recyclerViewHome.setAdapter(spMoiadapter);
                            }

                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                )
        );
    }

    private void LoaiSanPham() {
        compositeDisposable.add(apiBanHang.LoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSPModel -> {
                            if(loaiSPModel.isSuccess()){
                                mangloaisp = loaiSPModel.getResult();
                                mangloaisp.add(new LoaiSP("Đăng xuất"));
                                loaiSPadapter = new LoaiSPadapter(getApplicationContext(),mangloaisp);
                                listViewHome.setAdapter(loaiSPadapter);
                            }
                        }
                )
        );
    }

    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://streetstyle.vn/images/promo/22/Street_Style_x13z-66.png");
        mangquangcao.add("https://streetstyle.vn/images/promo/22/Street_Style_mpyc-p4.png");
        mangquangcao.add("https://streetstyle.vn/images/promo/23/Street_Style_hi%E1%BB%87n_ch%E1%BB%89_b%C3%A1n_ONLINE.png");
        for(int i = 0; i< mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slider_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_in_right);
        Animation slider_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_out_right);
        viewFlipper.setInAnimation(slider_in);
        viewFlipper.setOutAnimation(slider_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void anhxa() {
        toolbar = findViewById(R.id.toolbarhome);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewHome = findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this , 2);
        recyclerViewHome.setLayoutManager(layoutManager);
        recyclerViewHome.setHasFixedSize(true);
        navigationView = findViewById(R.id.navigationview);
        listViewHome = findViewById(R.id.listviewhome);
        drawerLayout = findViewById(R.id.drawerlayout);
        badge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.framegiohang);
        imgsearch = findViewById(R.id.imgsearch);
        //Khoi tao list
        mangloaisp = new ArrayList<>();
        mangspMoi = new ArrayList<>();
        if(Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else{
            int totalItem = 0;
            for(int i = 0; i < Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });

        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for(int i = 0; i < Utils.manggiohang.size(); i++){
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }


    private boolean isConnected (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); //THEM QUYEN
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifi != null && wifi.isConnected()) || (mobile != null && wifi.isConnected() )){
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}