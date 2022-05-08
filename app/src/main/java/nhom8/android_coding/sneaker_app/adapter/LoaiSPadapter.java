package nhom8.android_coding.sneaker_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nhom8.android_coding.sneaker_app.R;
import nhom8.android_coding.sneaker_app.model.LoaiSP;

public class LoaiSPadapter extends BaseAdapter {

    List<LoaiSP> array;
    Context context;

    public LoaiSPadapter(Context context, List<LoaiSP> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        TextView texttensp;
//        ImageView imghinhanh;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_sanpham, null);
            viewHolder.texttensp = view.findViewById(R.id.item_tensp);
//            viewHolder.imghinhanh = view.findViewById(R.id.item_image);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.texttensp.setText(array.get(i).getTensanpham());
//        Glide.with(context).load(array.get(i).getHinhanh()).into(viewHolder.imghinhanh);
        return view;
    }
}
