package com.example.baikt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    Button btnAdd;
    MyDB db;
    ArrayList list;
    CustomAdapter adap;

    boolean isAscending;
    int SelectedItemID;
    private NetworkReceiver networkReceiver;
    private BatteryReceiver batteryReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd=findViewById(R.id.btnADDD);
        lv=findViewById(R.id.listv);
       list= new ArrayList<>();
        db = new MyDB(this, "ContactDB",null,1);
//        db.addContact(new Thisinh("001","Vht",3,5,6));
//        db.addContact(new Thisinh("002","Vht1",3,5,6));
//        db.addContact(new Thisinh("003","Vht12",3,5,6));
        list = db.getAllContact();
        adap = new CustomAdapter(MainActivity.this, R.layout.layout_list,list);
        lv.setAdapter(adap);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ActivityAdd.class);
                startActivityForResult(intent, 100);
            }
        });
        registerForContextMenu(lv);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SelectedItemID = position;
                return false;
            }
        });
        // Đăng ký NetworkReceiver
        IntentFilter filter;
        networkReceiver = new NetworkReceiver();
        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);
        // dk pin yeu
        batteryReceiver = new BatteryReceiver();
        filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            if (data != null) {
                Bundle b = data.getExtras();
                if (b != null) {
                    String Name = b.getString("Name");
                    String SDT = b.getString("SDT");
                    Float T = b.getFloat("Toan");
                    Float L = b.getFloat("Ly");
                    Float H = b.getFloat("Hoa");
                    db.addContact(new Thisinh(SDT, Name, T, L, H));
                    list.clear();
                    list.addAll(db.getAllContact());
                    adap.notifyDataSetChanged();
                }
            }
        }
        if ( resultCode == 200 && data != null) {
            if (data != null) {
                Bundle b = data.getExtras();
                if (b != null) {
                    String Name = b.getString("Name");
                    String SDT = b.getString("SBD");
                    Float T = b.getFloat("Toan");
                    Float L = b.getFloat("Ly");
                    Float H = b.getFloat("Hoa");
                    Thisinh updatedThisinh = new Thisinh(SDT, Name, T, L, H);
                    Toast.makeText(this,"Sort DES "+ SDT,Toast.LENGTH_SHORT).show();
                    db.updateContact(SDT, updatedThisinh);
                    list.clear();
                    list.addAll(db.getAllContact());
                    adap.notifyDataSetChanged();
                }
            }
        }
    }
    //MENU
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.mnugiam){
            isAscending = true;
            Collections.sort(list, dtbComparator);
            adap.notifyDataSetChanged();
            Toast.makeText(this,"Sort DES",Toast.LENGTH_SHORT).show();
        }else if (item.getItemId()==R.id.mnutang){
            isAscending = false;
            Collections.sort(list, dtbComparator);
            adap.notifyDataSetChanged();
            Toast.makeText(this,"Sort ASC",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menumain, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public Comparator<Thisinh> dtbComparator = new Comparator<Thisinh>() {
        @Override
        public int compare(Thisinh a, Thisinh b) {
            float dtbA = a.TinhdiemTB();
            float dtbB = b.TinhdiemTB();

            // So sánh theo hướng tăng dần hoặc giảm dần
            if (isAscending) {
                return Float.compare(dtbA, dtbB);
            } else {
                return Float.compare(dtbB, dtbA);
            }
        }
    };
    //contextmenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.contextmenu, menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Thisinh p = (Thisinh) list.get(SelectedItemID);
        if(item.getItemId() == R.id.mnusua){
            Intent intent = new Intent(MainActivity.this,SubActivity.class);
            Bundle b = new Bundle();
            b.putString("Name", p.getHoten().toString());
            b.putString("SBD", p.getSBD().toString());
            b.putFloat("Toan", p.getT());
            b.putFloat("Ly", p.getL());
            b.putFloat("Hoa", p.getH());
            intent.putExtras(b);
            startActivityForResult(intent, 1000);
        }

        return super.onContextItemSelected(item);
    }
    // boarcat
    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo == null || !networkInfo.isConnected()) {
                // Hiển thị cảnh báo mất kết nối internet
                Toast.makeText(context, "Mất kết nối internet!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = (level / (float)scale) * 100;

            if (batteryPct < 20) {
                // Hiển thị cảnh báo pin yếu
                Toast.makeText(context, "Pin yếu!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}