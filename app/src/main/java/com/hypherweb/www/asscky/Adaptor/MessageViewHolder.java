package com.hypherweb.www.asscky.Adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hypherweb.www.asscky.R;

/**
 * Created by AirUnknown on 2016-10-31.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {
    public TextView mMessageText;
    public TextView mDateText;

    public MessageViewHolder(View v) {
        super(v);
        mMessageText = (TextView) itemView.findViewById(R.id.message_text);
        mDateText = (TextView)  itemView.findViewById(R.id.message_date_text);

    }
}