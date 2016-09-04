package cn.nj.www.my_module.main.base.square_clip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import java.io.File;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.tools.BitmapUtil;
import cn.nj.www.my_module.tools.FileSystemManager;

public class CircleClipActivity extends BaseActivity {
	private ClipCircleImageLayout mClipImageLayout;
	private String path;
	private ProgressDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clipimage);
		initAll();
	}

	@Override
	public void initView() {
		initTitle();
		loadingDialog=new ProgressDialog(this);
		loadingDialog.setTitle("请稍后");
		path=getIntent().getStringExtra("path");
		if(TextUtils.isEmpty(path)||!(new File(path).exists())){
			Toast.makeText(this, "图片不存在", Toast.LENGTH_SHORT).show();
			return;
		}
		Bitmap bitmap= ImageTools.convertToBitmap(path, 600, 600);
		if(bitmap==null){
			Toast.makeText(this, "图片获取失败", Toast.LENGTH_SHORT).show();
			return;
		}
		mClipImageLayout = (ClipCircleImageLayout) findViewById(R.id.id_clipImageLayout);
		mClipImageLayout.setBitmap(bitmap);
		findViewById(R.id.id_action_clip).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				loadingDialog.show();
				new Thread(new Runnable() {
					@Override
					public void run() {
						Bitmap bitmap = mClipImageLayout.clip();
						String path = FileSystemManager.getCacheFilePath(mContext) + System.currentTimeMillis() + ".png";
						ImageTools.savePhotoToSDCard(BitmapUtil.compressImage(bitmap), path);
						loadingDialog.dismiss();
						Intent intent = new Intent();
						intent.putExtra("path",path);
						setResult(RESULT_OK, intent);
						finish();

					}
				}).start();
			}
		});
	}

	@Override
	public void initViewData() {

	}

	@Override
	public void initEvent() {

	}

	@Override
	public void netResponse(BaseResponse event) {

	}

	private void initTitle() {
		View view = findViewById(R.id.common_back);
		HeadView headView = new HeadView((ViewGroup) view);
		headView.setTitleText(getResources().getString(R.string.app_edit_pic));
		headView.setLeftImage(R.mipmap.app_title_back);
		headView.setHiddenRight();
	}

	@Override
	public void onEventMainThread(BaseResponse event) throws Exception {
		if (event instanceof NoticeEvent) {
			String tag = ((NoticeEvent) event).getTag();
			if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
				finish();
			}
		}
	}
	
}
