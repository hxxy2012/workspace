package com.hike.digitalgymnastic.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hike.digitalgymnastic.entitiy.DailySportData;
import com.hike.digitalgymnastic.entitiy.HomeSportData;
import com.hike.digitalgymnastic.utils.FileUtil;
import com.hike.digitalgymnastic.utils.PhotoPicker;
import com.hike.digitalgymnastic.utils.ReqeustCode;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.view.annotation.ContentView;

import java.io.File;
import java.util.List;

@ContentView(R.layout.sharedialog)
public class ShareDialog extends Dialog implements View.OnClickListener{
	
	Context context;
	TextView tv_top_title;
	TextView tv_desc;
	GridView gv_sport_type;
	Button btn_share;
	public ShareDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public ShareDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public ShareDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		// TODO Auto-generated method stub

		this.context = context;
		setContentView(R.layout.sharedialog);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		gv_sport_type = (GridView) findViewById(R.id.gv_sport_type);
		btn_share=(Button) findViewById(R.id.btn_share);
		btn_share.setOnClickListener(this);
		setCanceledOnTouchOutside(false);

	}
	HomeSportData hsd;
	public void setData(HomeSportData hsd,int gender) {
		
		if (hsd != null && hsd.getDailyList() != null) {
			this.hsd=hsd;
			tv_top_title.setText("你共完成" + hsd.getDailyList().size() + "项训练项目");
			if(gender==1||gender==0)
				tv_desc.setText("分享你的运动成果让你更好的坚持");
			else
				tv_desc.setText("运动后美美哒");
			TypeAdapter adapter=new TypeAdapter(context, hsd.getDailyList());
			gv_sport_type.setAdapter(adapter);
		}
	}

	class TypeAdapter extends BaseAdapter {
		List<DailySportData> list;
		Context context;
		LayoutInflater inflater;

		public TypeAdapter(Context context, List<DailySportData> list) {
			this.context = context;
			this.list = list;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Holder holder;
			DailySportData dad = (DailySportData) getItem(position);
			if (convertView == null) {
				holder = new Holder();

				convertView = inflater.inflate(R.layout.sharedialog_item, null);
				holder.iv_type = (ImageView) convertView
						.findViewById(R.id.iv_type);
				holder.tv_type = (TextView) convertView
						.findViewById(R.id.tv_type);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			holder.tv_type.setText(dad.getSportName());
			try {
				String name = "icon_sport_type_" + dad.getSportType();
				int id = context.getResources().getIdentifier(name, "mipmap",
						context.getPackageName());
				holder.iv_type.setImageResource(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return convertView;
		}

		class Holder {
			ImageView iv_type;
			TextView tv_type;
		}
	}
	File captureFile;
	public File getCaptureFile() {
		return captureFile;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_share:

			 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
             intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);  
             captureFile = FileUtil.getCaptureFile(context);
			 dismiss();
			 PhotoPicker.launchCamera((Activity) context, ReqeustCode.FROM_CAPTURE, captureFile);

			break;

		default:
			break;
		}
	}
}
