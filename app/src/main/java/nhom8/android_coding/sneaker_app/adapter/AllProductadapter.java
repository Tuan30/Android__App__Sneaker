package nhom8.android_coding.sneaker_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

import nhom8.android_coding.sneaker_app.InterFace.ItemClickListner;
import nhom8.android_coding.sneaker_app.R;
import nhom8.android_coding.sneaker_app.activity.ChiTietActivity;
import nhom8.android_coding.sneaker_app.model.SPMoi;

public class AllProductadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<SPMoi> array;
    private  static final int VIEW_TYPE_DATA = 0;
    private  static final int VIEW_TYPE_LOADDING = 1;

    public AllProductadapter(Context context, List<SPMoi> array) {
        this.context = context;
        this.array = array;
    }

    public  class LoaddingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;

        public LoaddingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allproduct,parent, false);
            return new MyViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loadding, parent, false);
            return new LoaddingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            SPMoi sanPham = array.get(position);
            myViewHolder.tensp.setText(sanPham.getTensp().trim());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHolder.giasp.setText("Giá: " + decimalFormat.format(Double.parseDouble(sanPham.getGiasp())) + "đ");
            myViewHolder.mota.setText(sanPham.getMota());
            myViewHolder.idsp.setText("ID: " + sanPham.getId() + "");
            Glide.with(context).load(sanPham.getHinhanh()).into(myViewHolder.hinhanh);

            myViewHolder.setItemClickListner(new ItemClickListner() {
                @Override
                public void onClick(View view, int pos, boolean isLongClick) {
                    Intent intent = new Intent(context, ChiTietActivity.class);
                    intent.putExtra("chitiet", sanPham);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivities(new Intent[]{intent});
                }
            });
        }else {
            LoaddingViewHolder loaddingViewHolder = (LoaddingViewHolder) holder;
            loaddingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? VIEW_TYPE_LOADDING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tensp, giasp, mota, idsp;
        ImageView hinhanh;

        private ItemClickListner itemClickListner;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idsp = itemView.findViewById(R.id.itemproduct_idsp);
            tensp = itemView.findViewById(R.id.itemproduct_tensp);
            giasp = itemView.findViewById(R.id.itemproduct_gia);
            mota = itemView.findViewById(R.id.itemproduct_mota);
            hinhanh = itemView.findViewById(R.id.itemproduct_images);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListner(ItemClickListner itemClickListner) {
            this.itemClickListner = itemClickListner;
        }

        @Override
        public void onClick(View view) {
            itemClickListner.onClick(view, getAdapterPosition(), false);

        }
    }


}
