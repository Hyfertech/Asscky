package com.hypherweb.www.asscky.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hypherweb.www.asscky.Model.Board;
import com.hypherweb.www.asscky.R;

import java.util.HashMap;

/**
 * Created by AirUnknown on 2016-11-07.
 */

public class ProfileBoardsListAdaptor extends BaseAdapter {

    String TAG = ProfileBoardsListAdaptor.class.getSimpleName();
    private HashMap<String, Object> mData = new HashMap<>();
    private String[] mKeys;
    private Context mContext;

    public ProfileBoardsListAdaptor(HashMap<String, Object> data, Context context) {
        mData = data;
        mKeys = mData.keySet().toArray(new String[data.size()]);
        mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(mKeys[position]);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(mContext).inflate(R.layout.profile_boards_list_item, parent, false);
        }

        Board currentItem = new Board((HashMap<String, Object>) getItem(pos));

        TextView boardTitle = (TextView) listItemView.findViewById(R.id.board_title);
        TextView boardNumber = (TextView) listItemView.findViewById(R.id.board_number);

        boardTitle.setText(currentItem.getTitle());
        boardNumber.setText(currentItem.getBoardNumber());

        return listItemView;
    }

}
