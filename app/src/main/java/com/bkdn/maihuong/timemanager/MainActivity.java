package com.bkdn.maihuong.timemanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etCv, etNd;
    TextView tvNgay, tvGio;
    Button btnNgay, btnGio, btnAdd;
    ListView listView;
    ArrayList<String> arrayListCongViec;
    ArrayList<String> arrayListChiTiet;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        arrayListCongViec = new ArrayList<>();
        arrayListChiTiet = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayListCongViec);
        listView.setAdapter(arrayAdapter);

        //display detail task
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Nội dung chi tiết");
                dialog.setMessage(arrayListChiTiet.get(position));
                dialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.create().show();
            }
        });

        //delete task
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                arrayListCongViec.remove(position);
                arrayListChiTiet.remove(position);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Đã xóa !!!",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
    public void findView(){
        etCv = (EditText)findViewById(R.id.et_cv);
        etNd = (EditText)findViewById(R.id.et_nd);
        tvNgay = (TextView)findViewById(R.id.tv_ngay);
        tvGio = (TextView)findViewById(R.id.tv_gio);
        btnNgay = (Button)findViewById(R.id.btn_date);
        btnGio = (Button)findViewById(R.id.btn_time);
        btnAdd = (Button)findViewById(R.id.btn_add);
        listView = (ListView)findViewById(R.id.listView);
    }
    //show time picker
    public void showGio(View view) {
        Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tvGio.setText(hourOfDay+":"+minute);
            }
        };
        TimePickerDialog dialog = new TimePickerDialog(this, onTimeSetListener,gio, phut,true);
        dialog.show();


    }
    //show date picker
    public void showDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.HOUR_OF_DAY);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tvNgay.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(this,onDateSetListener,nam,thang,ngay);
        dialog.show();

    }
    //add task
    public void addTask(View view) {
        if(etCv.getText().toString().equals("")
                ||etNd.getText().toString().equals("")
                ||tvNgay.getText().toString().equals("")
                ||tvGio.getText().toString().equals(""))
        {
            Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
        } else{
            arrayListCongViec.add(etCv.getText().toString()+" - "
                    +tvNgay.getText().toString()+" - "
                    +tvGio.getText().toString());
            arrayListChiTiet.add(etNd.getText().toString());
            etCv.setText("");
            etNd.setText("");
            tvNgay.setText("");
            tvGio.setText("");
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
