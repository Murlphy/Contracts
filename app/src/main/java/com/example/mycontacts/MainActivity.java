package com.example.mycontacts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mycontacts.adapter.NotepadAdapter;
import com.example.mycontacts.database.SQLiteHelper;
import com.example.mycontacts.item.NotepadBean;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private SQLiteHelper msqLiteHelper;
    private List<NotepadBean>list;
    private NotepadAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        ImageView imageView = findViewById(R.id.add);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RecordActivity.class);//跳转
                startActivityForResult(intent,1);//跳转到添加记录的界面
            }
        });
        initData();

    }
    public void initData(){
        msqLiteHelper  = new SQLiteHelper(this);
        showQueryData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NotepadBean notepadBean = list.get(i);
                Intent intent = new Intent(MainActivity.this,RecordActivity.class);
                intent.putExtra("id",notepadBean.getId());
                intent.putExtra("content",notepadBean.getNotepadContent());
                intent.putExtra("mcontent",notepadBean.getMoblieContent());
                intent.putExtra("time",notepadBean.getNotepadTime());
                MainActivity.this.startActivityForResult(intent,1);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {   //长按删除
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("是否删除此记录")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                NotepadBean notepadBean = list.get(position);
                                if(msqLiteHelper.deleteData(notepadBean.getId())){
                                    list.remove(position);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });
                dialog = builder.create();
                dialog.show();

                return true;
            }
        });
    }
    private  void showQueryData(){
        if (list!=null){
            list.clear();
        }
        list = msqLiteHelper.query();
        adapter = new NotepadAdapter(this,list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2){
            showQueryData();
        }
    }
}
