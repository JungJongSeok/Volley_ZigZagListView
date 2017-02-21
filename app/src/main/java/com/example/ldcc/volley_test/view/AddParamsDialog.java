package com.example.ldcc.volley_test.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.ldcc.volley_test.R;

public class AddParamsDialog extends Dialog {
    public interface ICallBack{
        void confirm(String key, String value);
    }

    private Context mContext;
    private Button mDialogConfirm;
    private Button mDialogDismiss;
    private EditText mKeyEditText;
    private EditText mValueEditText;
    private ICallBack mICallBack;

    public AddParamsDialog(Context context,ICallBack iCallBack) {
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

        setContentView(R.layout.dialog_add_params);

        init();
        initButton();
    }

    void init(){
        mDialogConfirm = (Button) findViewById(R.id.dialogConfirm);
        mDialogDismiss = (Button) findViewById(R.id.dialogDismiss);
        mKeyEditText = (EditText) findViewById(R.id.editKey);
        mValueEditText = (EditText) findViewById(R.id.editValue);
    }

    void initButton(){
        mDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mKeyEditText.getText().toString().equals("") == false && mValueEditText.getText().toString().equals("") == false) {
                    mICallBack.confirm(mKeyEditText.getText().toString(), mValueEditText.getText().toString());
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

    void show(String key, String value){
        super.show();
        mKeyEditText.setText(key);
        mValueEditText.setText(value);
    }

}
