package nhom8.android_coding.sneaker_app.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import nhom8.android_coding.sneaker_app.InterFace.ImageClickListnner;
import nhom8.android_coding.sneaker_app.R;
import nhom8.android_coding.sneaker_app.model.EventBus.TongEvent;
import nhom8.android_coding.sneaker_app.model.GioHang;
import nhom8.android_coding.sneaker_app.utils.Utils;

public class GioHangadapter extends RecyclerView.Adapter<GioHangadapter.MyviewHolder> {

    Context context;
    List<GioHang> gioHangList;

    public GioHangadapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.item_giohang_tensp.setText(gioHang.getTensp());
        holder.item_giohang_soluong.setText(gioHang.getSoluong() + "");
        holder.item_giohang_size.setText("Size: " + gioHang.getSizesp());
        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohang_giasp.setText(decimalFormat.format((gioHang.getGiasp())));
        long gia = gioHang.getSoluong() * gioHang.getGiasp();
        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
        holder.setListnner(new ImageClickListnner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                Log.d("TAG", "onImageClick" + pos + " ..." + giatri);
                if (giatri == 1 ){
                    if (gioHangList.get(pos).getSoluong() > 1){
                        int soluongmoi = gioHangList.get(pos).getSoluong()-1;
                        gioHangList.get(pos).setSoluong(soluongmoi);

                        holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong() + " ");
                        long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                        EventBus.getDefault().postSticky(new TongEvent());
                    }else if (gioHangList.get(pos).getSoluong() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa sản phẩm khỏi giỏ hàng");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.manggiohang.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TongEvent());
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                }else if( giatri == 2){
                    if (gioHangList.get(pos).getSoluong() < 11){
                        int soluongmoi = gioHangList.get(pos).getSoluong()+1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                    }
                    holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong() + " ");
                    long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                    holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                    EventBus.getDefault().postSticky(new TongEvent());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return  gioHangList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image, imgtru, imgcong;
        TextView item_giohang_tensp, item_giohang_giasp, item_giohang_giasp2, item_giohang_soluong, item_giohang_size;
        ImageClickListnner listnner;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            imgtru = itemView.findViewById(R.id.item_giohang_tru);
            imgcong = itemView.findViewById(R.id.item_giohang_cong);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_images);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_size = itemView.findViewById(R.id.item_giohang_size);
            item_giohang_giasp = itemView.findViewById(R.id.item_giohang_giasp);
            item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);

            //event Click
            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);
        }

        public void setListnner(ImageClickListnner listnner) {
            this.listnner = listnner;
        }

        @Override
        public void onClick(View view) {
            if (view == imgtru){
                listnner.onImageClick(view, getAdapterPosition(), 1);
                //1 tru
            }else if( view == imgcong){
                listnner.onImageClick(view, getAdapterPosition(), 2);
            }
        }
    }
}
