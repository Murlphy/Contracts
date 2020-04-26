package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycontacts.database.SQLiteHelper;
import com.example.mycontacts.utils.DBUtils;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView note_back;
    ImageView message;
    ImageView phone;
    TextView note_time;
    EditText content;
    EditText mcontent;

    Button note_save;
    TextView noteName;
    private SQLiteHelper msqLiteHelper;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        note_back = (ImageView) findViewById(R.id.note_back);  //后退键
        note_time = (TextView) findViewById(R.id.tv_time);      //保存记录的时间
        content = (EditText) findViewById(R.id.note_content);   //记录的内容
        mcontent = (EditText) findViewById(R.id.mobile_content);//记录的号码
        note_save =  findViewById(R.id.note_save);   ///保存的按钮
        noteName = (TextView) findViewById(R.id.note_name);     //标题的名称
        phone =(ImageView)findViewById(R.id.phone);
        message=(ImageView)findViewById(R.id.message);
        note_back.setOnClickListener(this);
        note_save.setOnClickListener(this);
        phone.setOnClickListener(this);
        message.setOnClickListener(this);
        initData();
    }
    public void initData(){
        msqLiteHelper = new SQLiteHelper(this);
        noteName.setText("添加联系人");
        Intent intent = getIntent();
        if(intent!=null){
            id = intent.getStringExtra("id");
            if(id!=null){
                noteName.setText("查看联系人");
                content.setText(intent.getStringExtra("content"));
                mcontent.setText(intent.getStringExtra("mcontent"));
                note_time.setText(intent.getStringExtra("time"));
                note_time.setVisibility(View.VISIBLE);
            }
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.note_back:
                finish();
                break;
            case R.id.note_save:
                String noteContent = content.getText().toString().trim();
                String mobileContent = mcontent.getText().toString().trim();
                if (id!=null){
                    //修改记录的功能
                    if (noteContent.length()>0 ){
                        if(msqLiteHelper.updateData(id,noteContent,mobileContent, DBUtils.getTime())){
                            showToast("修改成功");
                            setResult(2);
                            finish();
                        }else{
                            showToast("修改失败");
                        }
                    }else{
                        showToast("保存不能为空");
                    }
                }else{
                    //添加记录的功能
                    if (noteContent.length()>0 ){
                        if(msqLiteHelper.insertData(noteContent,mobileContent, DBUtils.getTime())){
                            showToast("保存成功");
                            setResult(2);
                            finish();
                        }else{
                            showToast("保存失败");
                        }
                    }else{
                        showToast("保存不能为空");
                    }
                }
                break;
            case R.id.phone:

                callPhone( mcontent.getText().toString().trim());
                break;
            case R.id.message:
                sendmessage(mcontent.getText().toString().trim());
                break;
        }
    }

    public  void showToast(String message){
        Toast.makeText(RecordActivity.this,message,Toast.LENGTH_LONG).show();
    }

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
    public void sendmessage(String phonenum){
        Uri data = Uri.parse("smsto:"+phonenum);
        Intent intentMessage = new Intent(Intent.ACTION_VIEW,data);
        startActivity(intentMessage);
    }
}
