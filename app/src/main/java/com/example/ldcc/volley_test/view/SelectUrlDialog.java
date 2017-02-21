package com.example.ldcc.volley_test.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ldcc.volley_test.R;

public class SelectUrlDialog extends Dialog {
    public interface ICallBack{
        void confirm(String url);
    }

    private Context mContext;
    private TextView mUrlTxt;
    private Button mDialogConfirm;
    private Button mDialogDismiss;
    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    private RadioButton mRadioButton3;
    private EditText mEditText;
    private ICallBack mICallBack;

    public SelectUrlDialog(Context context, ICallBack iCallBack) {
        super(context);
        mContext = context;
        mICallBack = iCallBack;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_insert_url);

        init();
        initButton();
    }

    void init(){
        mUrlTxt = (TextView) findViewById(R.id.dialog_url) ;
        mDialogConfirm = (Button) findViewById(R.id.dialogConfirm);
        mDialogDismiss = (Button) findViewById(R.id.dialogDismiss);
        mRadioButton1 = (RadioButton) findViewById(R.id.dialog_radio_url1);
        mRadioButton2 = (RadioButton) findViewById(R.id.dialog_radio_url2);
        mRadioButton3 = (RadioButton) findViewById(R.id.dialog_radio_url3);
        mEditText = (EditText) findViewById(R.id.dialog_radio_url3_real_txt);
        mRadioButton3.setChecked(true);
    }

    void initButton(){
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRadioButton3.setChecked(true);
            }
        });
        mRadioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText(mRadioButton1.getText().toString());
            }
        });
        mRadioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText(mRadioButton2.getText().toString());
            }
        });
        mDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRadioButton1.isChecked()){
                    mICallBack.confirm(mRadioButton1.getText().toString());
                }
                else if(mRadioButton2.isChecked()){
                    mICallBack.confirm(mRadioButton2.getText().toString());
                }
                else if(mRadioButton3.isChecked()){
                    mICallBack.confirm(mEditText.getText().toString());
                }
                dismiss();
            }
        });
        mDialogDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void show(String url){
        super.show();
        mUrlTxt.setText(url);
        mEditText.setText(url);
    }

}
