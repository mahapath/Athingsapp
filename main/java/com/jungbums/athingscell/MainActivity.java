package com.jungbums.athingscell;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private DatabaseReference tapref1,tapref2,tapref3,tapref4;
    private DatabaseReference ledref;
    private CheckBox b1,b2,b3,b4;
    private RadioButton rb1,rb2;
    int test=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebaseinit();
        viewinit();
        clicklistenerinit();

    }
    private void Firebaseinit(){
        mdatabase= FirebaseDatabase.getInstance().getReference().child("lock").child("open");
        tapref1=FirebaseDatabase.getInstance().getReference().child("tap").child("tap1");
        tapref2=FirebaseDatabase.getInstance().getReference().child("tap").child("tap2");
        tapref3=FirebaseDatabase.getInstance().getReference().child("tap").child("tap3");
        tapref4=FirebaseDatabase.getInstance().getReference().child("tap").child("tap4");
        ledref=FirebaseDatabase.getInstance().getReference().child("LED").child("led");
    }

    public void FragmentLoad(View v){
        Intent intent= new Intent(getApplicationContext(),Doorbell.class);
        startActivity(intent);
    }
    //아래 함수는 layout에서 쓰므로 public 사용
    public void doorlock(View v){
        mdatabase.setValue(1);
    }
    public void init(View v){
        mdatabase.setValue(0);
    }

    private void viewinit(){
        b1=(CheckBox)findViewById(R.id.box1);
        b2=(CheckBox)findViewById(R.id.box2);
        b3=(CheckBox)findViewById(R.id.box3);
        b4=(CheckBox)findViewById(R.id.box4);
        rb1=(RadioButton)findViewById(R.id.radioBtn1);
        rb2=(RadioButton)findViewById(R.id.radioBtn2);
    }
    private void clicklistenerinit(){
        View.OnClickListener listener =new View.OnClickListener(){
            public void onClick(View v){
                switch (v.getId()) {
                    case R.id.box1:
                        if (b1.isChecked()) {
                            tapref1.setValue(1);
                            break;
                        } else {
                            tapref1.setValue(0);
                            break;
                        }

                    case R.id.box2:
                        if (b2.isChecked()) {
                            tapref2.setValue(1);
                            break;
                        } else {
                            tapref2.setValue(0);
                            break;
                        }

                    case R.id.box3:
                        if (b3.isChecked()) {
                            tapref3.setValue(1);
                            break;
                        } else {
                            tapref3.setValue(0);
                            break;
                        }

                    case R.id.box4:
                        if (b4.isChecked()) {
                            tapref4.setValue(1);
                            break;
                        } else {
                            tapref4.setValue(0);
                            break;
                        }
                    case R.id.radioBtn1:
                        if(rb1.isChecked())
                        {
                            ledref.setValue(1);
                            rb2.setChecked(false);
                        }
                    case R.id.radioBtn2:
                        if(rb2.isChecked())
                        {
                            ledref.setValue(0);
                            rb1.setChecked(false);
                        }
                }
            }
        };
        b1.setOnClickListener(listener);
        b2.setOnClickListener(listener);
        b3.setOnClickListener(listener);
        b4.setOnClickListener(listener);
        rb1.setOnClickListener(listener);
        rb2.setOnClickListener(listener);
    }
}
