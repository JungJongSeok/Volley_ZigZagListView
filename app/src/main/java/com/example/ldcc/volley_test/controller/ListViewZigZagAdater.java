package com.example.ldcc.volley_test.controller;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ldcc.volley_test.R;
import com.example.ldcc.volley_test.model.ObjectCPU;

import java.util.ArrayList;

public class ListViewZigZagAdater extends BaseAdapter {

    private Context mContext;

    // 문자열을 보관 할 ArrayList
    private ArrayList<ObjectCPU> m_List;

    // 생성자
    public ListViewZigZagAdater(Context context, ArrayList<ObjectCPU> ListView) {
        m_List = ListView;
        mContext = context;
    }

    // 현재 아이템의 수를 리턴
    @Override
    public int getCount() {
        return m_List.size();
    }

    // 현재 아이템의 오브젝트를 리턴, Object를 상황에 맞게 변경하거나 리턴받은 오브젝트를 캐스팅해서 사용
    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }

    // 아이템 position의 ID 값 리턴
    @Override
    public long getItemId(int position) {

        return m_List.indexOf(getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        return position%2;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    // 출력 될 아이템 관리
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CustomHolder holder = null;

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if (convertView == null) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(position % 2 == 0) {
                convertView = inflater.inflate(R.layout.view_left_cpu_obj, null);
            }
            else{
                convertView = inflater.inflate(R.layout.view_right_cpu_obj, null);
            }

            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.mHostNameView = (TextView) convertView.findViewById(R.id.hostNameText);
            holder.mMemoryView = (TextView) convertView.findViewById(R.id.memoryText);
            holder.mUseView = (TextView) convertView.findViewById(R.id.useText);
            holder.mCpuView = (TextView) convertView.findViewById(R.id.cpuText);
            holder.mDiskTotalView = (TextView) convertView.findViewById(R.id.diskTotalText);
            holder.mDiskFreeView = (TextView) convertView.findViewById(R.id.diskFreeText);
            holder.mDiskStatusView = (TextView) convertView.findViewById(R.id.diskStatusFreeText);
            holder.mDateView = (TextView) convertView.findViewById(R.id.dateText);
            convertView.setTag(holder);
        }
        else {
            holder = (CustomHolder) convertView.getTag();
        }

        holder.mHostNameView.setText("hostName : " + m_List.get(position).getHostName());
        holder.mMemoryView.setText("memory : " + m_List.get(position).getMemory());
        holder.mUseView.setText("use : " + m_List.get(position).getUse());
        holder.mCpuView.setText("cpu : " + m_List.get(position).getCpu());
        holder.mDiskTotalView.setText("diskTotal : " + m_List.get(position).getDiskTotal());
        holder.mDiskFreeView.setText("diskFree : " + m_List.get(position).getDiskFree());
        holder.mDiskStatusView.setText("diskStatus : " + m_List.get(position).getDiskStatus());
        holder.mDateView.setText(m_List.get(position).getDate());

        return convertView;
    }

    private class CustomHolder {
        TextView mHostNameView;
        TextView mMemoryView;
        TextView mUseView;
        TextView mCpuView;
        TextView mDiskTotalView;
        TextView mDiskFreeView;
        TextView mDiskStatusView;
        TextView mDateView;
    }

//    // 외부에서 아이템 추가 요청 시 사용
//    public void add(String key, String value) {
//        m_List.add(new ObjectCPU(key, value));
//    }
//
//    // 외부에서 아이템 삭제 요청 시 사용
//    public void remove(int _position) {
//        m_List.remove(_position);
//    }
//
//    public void update(ArrayList<ObjectKeyValue> newList_){
//        ArrayList<ObjectKeyValue> newList = new ArrayList<>();
//        newList.addAll(newList_);
//        m_List.clear();
//        m_List.addAll(newList);
//        this.notifyDataSetChanged();
//    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        super.unregisterDataSetObserver(observer);
    }
}
