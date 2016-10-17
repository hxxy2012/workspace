package com.hike.digitalgymnastic.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hike.digitalgymnastic.entitiy.Message;
import com.hike.digitalgymnastic.fragment.MessageFrament;
import com.hike.digitalgymnastic.request.MessageDao;
import com.hiko.enterprisedigital.R;
import com.roamer.slidelistview.SlideBaseAdapter;

import java.util.List;

public class MenuMessageAdapter extends SlideBaseAdapter {
	private List<Message> data;
	private Resources res;
    private MessageDao dao;
    private MessageFrament messageFrament;
	public MenuMessageAdapter(Context context, MessageDao dao, MessageFrament messageFrament) {
		super(context);
		this.res = context.getResources();
		this.dao = dao;
		this.messageFrament = messageFrament;
	}

	public void setMessagecontext(List<Message> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public final class ViewHolder {
		private TextView tv_context;
		private TextView tv_time;
		private Button btn_delete;
		private ImageView pic_tv;
		private View v_line;
	}

	@SuppressLint("InflateParams")
	public View getView(final int position, View convertView, ViewGroup arg2) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = createConvertView(position);
			holder = new ViewHolder();
			holder.tv_context = (TextView) convertView
					.findViewById(R.id.tv_context_str);
			holder.tv_time = (TextView) convertView
					.findViewById(R.id.tv_time_str);
			holder.pic_tv = (ImageView) convertView.findViewById(R.id.pic_tv);
			holder.btn_delete = (Button) convertView
					.findViewById(R.id.btn_delete);
			holder.v_line = (View) convertView
					.findViewById(R.id.v_line);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_context.setText(data.get(position).getContent());
//		data.get(position).getCreatedTime()
		holder.tv_time.setText(data.get(position).getCreatedTime());
		if (holder.btn_delete != null) {
			holder.btn_delete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					messageFrament.showProgress(true);
//					dao.DeleteMessage(data.get(position).getId().toString());
				}
			});
		}
		/* 将默认头像转为圆形 */
//		Bitmap bmp = BitmapFactory.decodeResource(res,
//				R.mipmap.btn_takepic_camera);
		holder.pic_tv.setImageResource(R.mipmap.message_d_3x);
		if(position==data.size()-1){
			holder.v_line.setVisibility(View.GONE);
		}
		return convertView;
	}

	@Override
	public int getFrontViewId(int position) {
		return R.layout.menu_message_list_item;
	}

	@Override
	public int getRightBackViewId(int position) {
		return R.layout.menu_message_lv_item_righ_view;
	}

	@Override
	public int getItemViewType(int position) {
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getLeftBackViewId(int position) {
		return 0;
	}
}
