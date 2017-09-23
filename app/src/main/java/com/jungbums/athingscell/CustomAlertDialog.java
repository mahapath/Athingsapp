package com.jungbums.athingscell;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by 75151 on 2017-09-12.
 */

public class CustomAlertDialog implements DialogInterface.OnClickListener,DialogClickInterface{
    public DialogClickInterface mDialogClickInterface;
    public static CustomAlertDialog mDialog;
    public Context mContext;
    private int mDialogIdentifier;

    public static CustomAlertDialog getInstance(){
        if(mDialog==null)
            mDialog=new CustomAlertDialog();
        return mDialog;
    }
    public void showConfirmDialog(Context pcontext,final int pDialogIdentifier,String imgstring){
        mDialogClickInterface=(DialogClickInterface) pcontext;
        mDialogIdentifier=pDialogIdentifier;
        mContext=pcontext;
        Bitmap bitmap=null; byte[] imageBytes;
        final Dialog dialog =new Dialog(pcontext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        ImageView image=(ImageView)dialog.findViewById(R.id.diaimg);
        Button button=(Button)dialog.findViewById(R.id.diabtn);
        imageBytes= Base64.decode(imgstring,Base64.NO_WRAP|Base64.URL_SAFE);
        bitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
        if(bitmap!=null){
            image.setImageBitmap(bitmap);
        }else{
            image.setImageResource(R.drawable.app);
        }
        dialog.setCancelable(false);
        dialog.show();
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mDialogClickInterface.onClickPositiveButton(dialog,pDialogIdentifier);
            }
        });

    }
    @Override
    public void onClick(DialogInterface pDialog,int pWhich){
        switch(pWhich){
            case DialogInterface.BUTTON_POSITIVE:
                mDialogClickInterface.onClickPositiveButton(pDialog,mDialogIdentifier);
                break;
        }
    }
    @Override
    public void onClickPositiveButton(DialogInterface pDialog,int pDialogIdentifier){
    }
}
