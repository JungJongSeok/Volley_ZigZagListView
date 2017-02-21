package com.example.ldcc.volley_test.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.ldcc.volley_test.R;
import com.example.ldcc.volley_test.controller.ListViewAdater;
import com.example.ldcc.volley_test.etc.Consts;
import com.example.ldcc.volley_test.etc.Dlog;
import com.example.ldcc.volley_test.etc.Sharedpreferences;
import com.example.ldcc.volley_test.model.CustomRequest;
import com.example.ldcc.volley_test.model.ObjectKeyValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class APIFragment extends Fragment {

    private static final String TAG = "APIFragment";

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    // SharedPreferences에 저장할 때 key 값으로 사용됨.
    public static final String PROPERTY_REG_ID = "registration_id";

    // SharedPreferences에 저장할 때 key 값으로 사용됨.
    private static final String PROPERTY_APP_VERSION = "appVersion";

    String SENDER_ID = "Your-Sender-ID";

    private View convertView;

    private TextView mUrlTxt;

    private ListView mHeaderListView;
    private ListViewAdater mHeaderAdapter;

    private ListView mParamsListView;
    private ListViewAdater mParamsAdapter;

    private Button mHeaderBtn;
    private Button mParamsBtn;
    private Button mHeaderRemoveBtn;
    private Button mParamsRemoveBtn;
    private Button mGetApiBtn;
    private Button mPostApiBtn;

    private ArrayList<ObjectKeyValue> mHeaderArrayList;
    private ArrayList<ObjectKeyValue> mParamsArrayList;

    private Map<String, String> mParamsHashMap = new HashMap<>();
    private Map<String, String> mHeaderHashMap = new HashMap<>();

    private RequestQueue queue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.fragment_api, container, false);
        return convertView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        initAdater();
        initButton();
        initData();
    }

    private void init(){
        mUrlTxt = (TextView) convertView.findViewById(R.id.url_txt);
        mHeaderListView = (ListView) convertView.findViewById(R.id.headerListView);
        mHeaderArrayList = new ArrayList<>();
        mHeaderAdapter = new ListViewAdater(getContext(), mHeaderArrayList);

        mParamsListView = (ListView) convertView.findViewById(R.id.paramsListView);
        mParamsArrayList = new ArrayList<>();
        mParamsAdapter = new ListViewAdater(getContext(), mParamsArrayList);

        mHeaderBtn = (Button) convertView.findViewById(R.id.insertHeaderBtn);
        mParamsBtn = (Button) convertView.findViewById(R.id.insertParamsBtn);
        mHeaderRemoveBtn = (Button) convertView.findViewById(R.id.removeHeaderBtn);
        mParamsRemoveBtn = (Button) convertView.findViewById(R.id.removeParamsBtn);

        mGetApiBtn = (Button) convertView.findViewById(R.id.getApiBtn);
        mPostApiBtn = (Button) convertView.findViewById(R.id.postApiBtn);
    }

    private void initAdater(){
        mHeaderListView.setAdapter(mHeaderAdapter);
        mParamsListView.setAdapter(mParamsAdapter);
    }

    private void initData(){
        boolean isStart = Sharedpreferences.getBooleanPreferences(getContext(), "isStart");
        if(isStart == false) {
            insertObjectKeyValue(mHeaderArrayList, mHeaderHashMap, "api_ver", "3.1", true);
            insertObjectKeyValue(mHeaderArrayList, mHeaderHashMap, "api_key", "u4h0p0kd62dcte8osgj11gv0cv", true);
            insertObjectKeyValue(mHeaderArrayList, mHeaderHashMap, "user_no", "14783", true);

            insertObjectKeyValue(mParamsArrayList, mParamsHashMap, "package", "com.lottemembers.android", false);
            Sharedpreferences.savePreferences(getContext(), "isStart", true);
        }
        else{
            mHeaderArrayList.removeAll(mHeaderArrayList);
            mParamsArrayList.removeAll(mParamsArrayList);

            if(Sharedpreferences.getArrayListCPUPreferences(getContext(), Consts.HEADER_ARRAYLIST) != null) {
                mHeaderArrayList.addAll(Sharedpreferences.getArrayListPreferences(getContext(), Consts.HEADER_ARRAYLIST));
            }
            if(Sharedpreferences.getArrayListCPUPreferences(getContext(), Consts.PARAMS_ARRAYLIST) != null) {
                mParamsArrayList.addAll(Sharedpreferences.getArrayListPreferences(getContext(), Consts.PARAMS_ARRAYLIST));
            }

            mHeaderHashMap = Sharedpreferences.getMapPreferences(getContext(), Consts.HEADER_HASHMAP);
            mParamsHashMap = Sharedpreferences.getMapPreferences(getContext(), Consts.PARAMS_HASHMAP);


            mHeaderAdapter.notifyDataSetChanged();
            mParamsAdapter.notifyDataSetChanged();
            Dlog.d(TAG,mHeaderHashMap.toString());
            Dlog.d(TAG,mParamsHashMap.toString());
        }
    }
    private void insertObjectKeyValue(ArrayList<ObjectKeyValue> arrayList, Map<String,String> hashMap, String Key, String Value, boolean isHeader){
        arrayList.add(new ObjectKeyValue(Key,Value));
        hashMap.put(Key,Value);
        if(isHeader) {
            Sharedpreferences.saveArrayListPreferences(getContext(), Consts.HEADER_ARRAYLIST, arrayList);
            Sharedpreferences.saveMapPreferences(getContext(), Consts.HEADER_HASHMAP, hashMap);
        }
        else{
            Sharedpreferences.saveArrayListPreferences(getContext(), Consts.PARAMS_ARRAYLIST, arrayList);
            Sharedpreferences.saveMapPreferences(getContext(), Consts.PARAMS_HASHMAP, hashMap);
        }
    }
    private void updateObjectKeyValue(ArrayList<ObjectKeyValue> arrayList, Map<String,String> hashMap, int position, String Key, String Value, boolean isHeader){
        hashMap.remove(arrayList.get(position).getKey());
        arrayList.set(position, new ObjectKeyValue(Key,Value));
        hashMap.put(Key,Value);
        if(isHeader) {
            Sharedpreferences.saveArrayListPreferences(getContext(), Consts.HEADER_ARRAYLIST, arrayList);
            Sharedpreferences.saveMapPreferences(getContext(), Consts.HEADER_HASHMAP, hashMap);
        }
        else{
            Sharedpreferences.saveArrayListPreferences(getContext(), Consts.PARAMS_ARRAYLIST, arrayList);
            Sharedpreferences.saveMapPreferences(getContext(), Consts.PARAMS_HASHMAP, hashMap);
        }
    }
    private void removeObjectKeyValue(ArrayList<ObjectKeyValue> arrayList, Map<String,String> hashMap, int position, boolean isHeader){
        hashMap.remove(arrayList.get(position).getKey());
        arrayList.remove(position);
        if(isHeader) {
            Sharedpreferences.saveArrayListPreferences(getContext(), Consts.HEADER_ARRAYLIST, arrayList);
            Sharedpreferences.saveMapPreferences(getContext(), Consts.HEADER_HASHMAP, hashMap);
        }
        else{
            Sharedpreferences.saveArrayListPreferences(getContext(), Consts.PARAMS_ARRAYLIST, arrayList);
            Sharedpreferences.saveMapPreferences(getContext(), Consts.PARAMS_HASHMAP, hashMap);
        }
    }

    private void initButton(){
        mUrlTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectUrlDialog mSelectUrlDialog = new SelectUrlDialog(getContext(), new SelectUrlDialog.ICallBack() {
                    @Override
                    public void confirm(String url) {
                        mUrlTxt.setText(url);
                    }
                });
                mSelectUrlDialog.show(mUrlTxt.getText().toString());
            }
        });
        mHeaderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String key = mHeaderArrayList.get(position).getKey();
                String value = mHeaderArrayList.get(position).getValue();
                AddParamsDialog mAddParamsDialog = new AddParamsDialog(getContext(), new AddParamsDialog.ICallBack() {
                    @Override
                    public void confirm(String key, String value) {
                        updateObjectKeyValue(mHeaderArrayList, mHeaderHashMap, position, key, value, true);
                        mHeaderAdapter.notifyDataSetChanged();
                    }
                });
                mAddParamsDialog.show(key, value);
            }
        });
        mParamsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String key = mParamsArrayList.get(position).getKey();
                String value = mParamsArrayList.get(position).getValue();
                AddParamsDialog mAddParamsDialog = new AddParamsDialog(getContext(), new AddParamsDialog.ICallBack() {
                    @Override
                    public void confirm(String key, String value) {
                        updateObjectKeyValue(mParamsArrayList, mParamsHashMap, position, key, value, false);
                        mParamsAdapter.notifyDataSetChanged();
                    }
                });
                mAddParamsDialog.show(key, value);
            }
        });
        mHeaderListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog mAlertDialog = new AlertDialog.Builder(getContext())
                        .setCancelable(false)
                        .setTitle("Header 삭제")
                        .setMessage("Key : " + mHeaderArrayList.get(position).getKey() + "\n" +
                                "Value : " + mHeaderArrayList.get(position).getValue())
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                removeObjectKeyValue(mHeaderArrayList, mHeaderHashMap, position,true);
                                mHeaderAdapter.notifyDataSetChanged();
                            }
                        })//setPositiveButton
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })//setNegativeButton
                        .create();
                mAlertDialog.show();
                return false;
            }
        });
        mParamsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog mAlertDialog = new AlertDialog.Builder(getContext())
                        .setCancelable(false)
                        .setTitle("Params 삭제")
                        .setMessage("Key : " + mParamsArrayList.get(position).getKey() + "\n" +
                                "Value : " + mParamsArrayList.get(position).getValue())
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                removeObjectKeyValue(mParamsArrayList, mParamsHashMap, position,false);
                                mParamsAdapter.notifyDataSetChanged();
                            }
                        })//setPositiveButton
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })//setNegativeButton
                        .create();
                mAlertDialog.show();
                return false;
            }
        });

        mHeaderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddParamsDialog mAddParamsDialog = new AddParamsDialog(getContext(), new AddParamsDialog.ICallBack() {
                    @Override
                    public void confirm(String key, String value) {
                        insertObjectKeyValue(mHeaderArrayList, mHeaderHashMap, key, value, true);
                        mHeaderAdapter.notifyDataSetChanged();
                    }
                });
                mAddParamsDialog.show();
            }
        });
        mParamsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddParamsDialog mAddParamsDialog = new AddParamsDialog(getContext(), new AddParamsDialog.ICallBack() {
                    @Override
                    public void confirm(String key, String value) {
                        insertObjectKeyValue(mParamsArrayList, mParamsHashMap, key, value, false);
                        mParamsAdapter.notifyDataSetChanged();
                    }
                });
                mAddParamsDialog.show();
            }
        });
        mHeaderRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHeaderArrayList.size() > 0) {
                    AlertDialog mAlertDialog = new AlertDialog.Builder(getContext())
                            .setCancelable(false)
                            .setTitle("Header 삭제")
                            .setMessage("Key : " + mHeaderArrayList.get(mHeaderArrayList.size() - 1).getKey() + "\n" +
                                    "Value : " + mHeaderArrayList.get(mHeaderArrayList.size() - 1).getValue())
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    removeObjectKeyValue(mHeaderArrayList, mHeaderHashMap, mParamsArrayList.size() - 1,true);
                                    mHeaderAdapter.notifyDataSetChanged();
                                }
                            })//setPositiveButton
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            })//setNegativeButton
                            .create();
                    mAlertDialog.show();
                }
            }
        });
        mParamsRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mParamsArrayList.size() > 0) {
                    AlertDialog mAlertDialog = new AlertDialog.Builder(getContext())
                            .setCancelable(false)
                            .setTitle("Params 삭제")
                            .setMessage("Key : " + mParamsArrayList.get(mParamsArrayList.size() - 1).getKey() + "\n" +
                                    "Value : " + mParamsArrayList.get(mParamsArrayList.size() - 1).getValue())
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    removeObjectKeyValue(mParamsArrayList, mParamsHashMap, mParamsArrayList.size() - 1,false);
                                    mParamsAdapter.notifyDataSetChanged();
                                }
                            })//setPositiveButton
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            })//setNegativeButton
                            .create();
                    mAlertDialog.show();
                }
            }
        });

        mGetApiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyAPI(Request.Method.GET);
            }
        });
        mPostApiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyAPI(Request.Method.POST);
            }
        });
    }
    public void VolleyAPI(int method){
        // initialize request queue
        queue = Volley.newRequestQueue(getContext());

        String url = mUrlTxt.getText().toString();
        mParamsHashMap = Sharedpreferences.getMapPreferences(getContext(), Consts.PARAMS_HASHMAP);

        CustomRequest sr = new CustomRequest(method, url, mParamsHashMap,
                new Response.Listener<JSONObject>() { // response listener
                    @Override
                    public void onResponse(JSONObject response) {
                        AlertDialog mAlertDialog = new AlertDialog.Builder(getContext())
                                .setCancelable(false)
                                .setTitle("Result : 성공")
                                .setMessage(response.toString())
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                    }
                                })//setPositiveButton
                                .create();
                        mAlertDialog.show();
                        Dlog.d(TAG, "성공");
                        Dlog.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() { // Error listener
            @Override
            public void onErrorResponse(VolleyError error) {
                // do nothing...
                String responseBody = null;
                try {
                    responseBody = new String( error.networkResponse.data, "utf-8" );
                    JSONObject jsonObject = new JSONObject( responseBody );
                } catch ( JSONException e ) {
                    //Handle a malformed json response
                } catch (UnsupportedEncodingException e){

                }

                AlertDialog mAlertDialog = new AlertDialog.Builder(getContext())
                        .setCancelable(false)
                        .setTitle("Result : 실패")
                        .setMessage(error.toString()+"\n"+
                                responseBody)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })//setPositiveButton
                        .create();
                mAlertDialog.show();
                Dlog.d(TAG, "에러남");
            }

        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getParams() {
                return mParamsHashMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return mHeaderHashMap;
            }

        };

        sr.setTag(TAG);
        // add a request to the queue
        queue.add(sr);
    }

    @Override
    public void onStop() {
        super.onStop();
        // cancel request
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }
}


