package com.jungbums.athingscell;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 75151 on 2017-09-03.
 */

public class Doorbell extends AppCompatActivity implements DialogClickInterface{
    private int identifier=0;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    DoorbellEntryAdapter adapter;
    private String TAG="Galaxy";
    List<DoorbellEntry> list;
    ListView listView;
    ChildEventListener mChildEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doorbell);

        database=FirebaseDatabase.getInstance();
        myRef=database.getReference().child("logs");
    }

    @Override
    public void onStart(){
        super.onStart();
        ListviewInit();
        Listenerinit();//리스터 설정
    }

    @Override
    public void onStop(){
        super.onStop();
        if(adapter!=null){
            adapter=null;
        }
        myRef.removeEventListener(mChildEventListener);
    }

    private void ListviewInit(){
        List<DoorbellEntry> image_details=getListData();
        listView=(ListView) findViewById(R.id.listView);
        adapter=new DoorbellEntryAdapter(this,image_details);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o= listView.getItemAtPosition(position);
                DoorbellEntry entry = (DoorbellEntry)o;
                //Toast.makeText(Doorbell.this,"Selected :"+" " +entry,Toast.LENGTH_LONG).show();
                showImage(entry.getImage());
            }
        });
    }


    private List<DoorbellEntry> getListData(){
        list =new ArrayList<DoorbellEntry>();
        return list;
    }

    public void Listenerinit(){
        mChildEventListener =new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DoorbellEntry entry=dataSnapshot.getValue(DoorbellEntry.class);
                adapter.add_list(entry);
                adapter.notifyDataSetChanged();
                listView.smoothScrollToPosition(adapter.getCount());
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int count=adapter.getCount();
                for(int i=0;i<count;i++){
                    adapter.remove(adapter.getItem(i));
                }
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        myRef.addChildEventListener(mChildEventListener);
    }
    private void showImage(String img){
        CustomAlertDialog.getInstance().showConfirmDialog(this,identifier,img);
    }
    @Override
    public void onClickPositiveButton(DialogInterface pDialog,int pDialogInterfier){
        if(pDialogInterfier ==0){
            Toast.makeText(this,"확인완료",Toast.LENGTH_SHORT).show();
        }
        pDialog.cancel();
    }
}
