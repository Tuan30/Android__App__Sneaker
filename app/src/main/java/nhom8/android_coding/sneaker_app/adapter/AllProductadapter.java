package nhom8.android_coding.sneaker_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

import nhom8.android_coding.sneaker_app.R;
import nhom8.android_coding.sneaker_app.model.SPMoi;

public class AllProductadapter extends RecyclerView.Adapter<AllProductadapter.MyViewHolder>{
    Context context;
    List<SPMoi> array;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allproduct,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SPMoi sanPham = array.get(position);
        holder.tensp.setText(sanPham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.giasp.setText("Giá: " + decimalFormat.format(Double.parseDouble(sanPham.getGiasp())) + "đ");
        holder.mota.setText(sanPham.getMota());
        Glide.with(context).load(sanPham.getHinhanh()).into(holder.hinhanh);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tensp, giasp, mota;
        ImageView hinhanh;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemproduct_tensp);
            giasp = itemView.findViewById(R.id.itemproduct_gia);
            mota = itemView.findViewById(R.id.itemproduct_mota);
            hinhanh = itemView.findViewById(R.id.itemproduct_images);
        }
    }
}
