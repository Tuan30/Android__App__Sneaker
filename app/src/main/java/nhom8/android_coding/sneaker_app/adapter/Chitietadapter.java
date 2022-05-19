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

import java.text.DecimalFormat;
import java.util.List;

import nhom8.android_coding.sneaker_app.R;
import nhom8.android_coding.sneaker_app.model.Item;

public class Chitietadapter extends RecyclerView.Adapter<Chitietadapter.MyViewHolder> {
    List<Item> itemList;
    Context context;

    public Chitietadapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.txtten.setText(item.getTensp() + "");
        holder.txtsoluong.setText("Số lượng: " + item.getSoluong());
        holder.txtsize.setText("Size: " + item.getSizesp());
        Glide.with(context).load(item.getHinhanh()).into(holder.imagechitiet);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imagechitiet;
        TextView txtten, txtsoluong, txtsize, txtgia;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtsize = itemView.findViewById(R.id.item_sizechitiet);
            imagechitiet = itemView.findViewById(R.id.item_imgchitiet);
            txtten = itemView.findViewById(R.id.item_tenspchitiet);
            txtsoluong = itemView.findViewById(R.id.item_soluongchitiet);
        }
    }
}
