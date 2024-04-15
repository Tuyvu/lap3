package com.example.baikt;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Activity context;
    int Idlayout;
    private ArrayList<Thisinh> data;

    public CustomAdapter(Activity context, int idlayout, ArrayList<Thisinh> data) {
        this.context = context;
        Idlayout = idlayout;
        this.data = data;
    }

    public int getCount() {
        // Trả về độ dài của ArrayList data, thay vì 0
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // Trả về mục dữ liệu ở vị trí cụ thể trong ArrayList
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Trả về một ID duy nhất cho mỗi mục trong list, trong trường hợp này là vị trí
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        if(v==null)
            v = inflater.inflate(R.layout.layout_list, null);

        TextView SBD = v.findViewById((R.id.txtSBD));
        TextView HoTen = v.findViewById((R.id.txtHoTen));
        TextView DTB = v.findViewById((R.id.txtTBD));

        Thisinh Ts = data.get(position);
        SBD.setText(String.valueOf(Ts.getSBD()));
        HoTen.setText(Ts.getHoten());
        DTB.setText(String.valueOf(Ts.TinhdiemTB()));
        return v;
    }
}
