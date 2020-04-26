package com.example.mycontacts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mycontacts.R;
import com.example.mycontacts.item.NotepadBean;

import java.util.List;
//适配器
public  class NotepadAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater; //加载item的布局界面
    private List<NotepadBean> list;         //列表中需要显示的数据的集合
    public NotepadAdapter(Context context, List<NotepadBean> list){
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ?0 :list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder viewHoder;
        if (view == null){
            view = layoutInflater.inflate(R.layout.contact_item_layout,null);
            viewHoder = new ViewHoder(view);
            view.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder)view.getTag();
        }
        NotepadBean notepadBean = (NotepadBean) getItem(i);
        viewHoder.tvNotepadContent.setText(notepadBean.getNotepadContent());
        viewHoder.tvNotepadTime.setText(notepadBean.getNotepadContent());

        return view;
    }

    class ViewHoder{

        private TextView tvNotepadContent;
        private TextView tvNotepadTime;
        public ViewHoder(View view){
            tvNotepadContent = view.findViewById(R.id.item_content);//记录内容
            tvNotepadTime = view.findViewById(R.id.item_time);//保存记录的时间
        }
    }
}
