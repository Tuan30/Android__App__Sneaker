package nhom8.android_coding.sneaker_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import nhom8.android_coding.sneaker_app.R;
import nhom8.android_coding.sneaker_app.adapter.LoaiSPadapter;
import nhom8.android_coding.sneaker_app.model.LoaiSP;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewHome;
    NavigationView navigationView;
    ListView listViewHome;
    DrawerLayout drawerLayout;
    LoaiSPadapter loaiSPadapter;
    List<LoaiSP> mangloaisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        anhxa();
        ActionBar();
        ActionViewFlipper();
    }

    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://streetstyle.vn/images/promo/22/Street_Style_x13z-66.png");
        mangquangcao.add("https://streetstyle.vn/images/promo/22/Street_Style_mpyc-p4.png");
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
        navigationView = findViewById(R.id.navigationview);
        listViewHome = findViewById(R.id.listviewhome);
        drawerLayout = findViewById(R.id.drawerlayout);

        //Khoi tao list
        mangloaisp = new ArrayList<>();
        //Khoi tao adapter
        loaiSPadapter = new LoaiSPadapter(getApplicationContext(),mangloaisp);
        listViewHome.setAdapter(loaiSPadapter);
    }
}