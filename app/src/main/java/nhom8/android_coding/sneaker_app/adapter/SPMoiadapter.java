package nhom8.android_coding.sneaker_app.adapter;

//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.List;
//
//import nhom8.android_coding.sneaker_app.R;
////import nhom8.android_coding.sneaker_app.model.SPMoi;
//
//public class SPMoiadapter extends RecyclerView.Adapter<SPMoiadapter.MyViewHolder> {
//
//    Context context;
//    List<SPMoi> array;
//
//    public SPMoiadapter(Context context, List<SPMoi> array) {
//        this.context = context;
//        this.array = array;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp_moi, parent, false);
//
//        return new MyViewHolder(item);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        SPMoi spMoi = array.get(position);
//        holder.txtten.setText(spMoi.getTensp());
//        holder.txtgia.setText(spMoi.getGiasp());
//        Glide.with(context).load(spMoi.getHinhanh()).into(holder.imghinhanh);
//    }
//
//    @Override
//    public int getItemCount() {
//        return array.size();
//    }
//
//
//    public class MyViewHolder extends RecyclerView.ViewHolder{
//
//        TextView txtgia, txtten;
//        ImageView imghinhanh;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            txtgia = itemView.findViewById(R.id.itemsp_gia);
//            txtten = itemView.findViewById(R.id.item_tensp);
//            imghinhanh = itemView.findViewById(R.id.item_image);
//        }
//    }
//}

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import nhom8.android_coding.sneaker_app.R;
import nhom8.android_coding.sneaker_app.model.SPMoi;

public class SPMoiadapter extends RecyclerView.Adapter<SPMoiadapter.MyViewHolder> {
    Context context;
    List<SPMoi> array;

    public SPMoiadapter(Context context, List<SPMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp_moi, parent, false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SPMoi spMoi = array.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtten.setText(spMoi.getTensp());
        holder.txtgia.setText("Giá: " + decimalFormat.format(Double.parseDouble(spMoi.getGiasp())) + "đ");
        Glide.with(context).load(spMoi.getHinhanh()).into(holder.imghinhanh);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtgia, txtten;
        ImageView imghinhanh;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtten = itemView.findViewById(R.id.itemsp_ten);
            txtgia = itemView.findViewById(R.id.itemsp_gia);
            imghinhanh = itemView.findViewById(R.id.itemsp_images);
        }
    }
}
