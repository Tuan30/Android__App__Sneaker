package nhom8.android_coding.sneaker_app.adapter;

import android.content.Context;
import android.content.Intent;
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

import nhom8.android_coding.sneaker_app.InterFace.ItemClickListner;
import nhom8.android_coding.sneaker_app.R;
import nhom8.android_coding.sneaker_app.activity.ChiTietActivity;
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
        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (!isLongClick){
                    //click
                    Intent intent = new Intent(context, ChiTietActivity.class);
                    intent.putExtra("chitiet", spMoi);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtgia, txtten;
        ImageView imghinhanh;

        private ItemClickListner itemClickListner;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtten = itemView.findViewById(R.id.itemsp_ten);
            txtgia = itemView.findViewById(R.id.itemsp_gia);
            imghinhanh = itemView.findViewById(R.id.itemsp_images);
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
