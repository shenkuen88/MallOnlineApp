package com.nannong.mall.activity.order.wx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.nannong.mall.R;
import com.nannong.mall.activity.order.wx.uikit.CameraUtil;
import com.tencent.mm.sdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.sdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class GetFromWXActivity extends Activity {

	private static final int THUMB_SIZE = 150;

	private IWXAPI api;
	private Bundle bundle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// acquire wxapi
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		bundle = getIntent().getExtras();

		setContentView(R.layout.get_from_wx);
		initView();
	}

	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		bundle = intent.getExtras();
	}

	private void initView() {

		findViewById(R.id.get_text).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final EditText editor = new EditText(GetFromWXActivity.this);
				editor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
				editor.setText(R.string.share_text_default);

			}
		});



		findViewById(R.id.get_video).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				WXVideoObject video = new WXVideoObject();
				video.videoUrl = "http://www.baidu.com";

				WXMediaMessage msg = new WXMediaMessage(video);
				msg.title = "Video Title";
				msg.description = "Video Description";

				GetMessageFromWX.Resp resp = new GetMessageFromWX.Resp();
				resp.transaction = getTransaction();
				resp.message = msg;
				
				api.sendResp(resp);
				finish();
			}
		});

		findViewById(R.id.get_webpage).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				WXWebpageObject webpage = new WXWebpageObject();
				webpage.webpageUrl = "http://www.baidu.com";

				WXMediaMessage msg = new WXMediaMessage(webpage);
				msg.title = "WebPage Title";
				msg.description = "WebPage Description";

				GetMessageFromWX.Resp resp = new GetMessageFromWX.Resp();
				resp.transaction = getTransaction();
				resp.message = msg;
				
				api.sendResp(resp);
				finish();
			}
		});

		findViewById(R.id.get_appdata).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// respond with appdata by taking photo
				CameraUtil.takePhoto(GetFromWXActivity.this, "/mnt/sdcard/tencent/", "get_appdata", 0x100);
			}
		});
	}

//	@Override
//	protected int getLayoutId() {
//		return R.layout.get_from_wx;
//	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case 0x100: {
			if (resultCode == RESULT_OK) {
				final WXAppExtendObject appdata = new WXAppExtendObject();
				final String path = CameraUtil.getResultPhotoPath(this, data, "/mnt/sdcard/tencent/");
				appdata.filePath = path;
				appdata.extInfo = "this is ext info";
	
				final WXMediaMessage msg = new WXMediaMessage();
				msg.setThumbImage(Util.extractThumbNail(path, 150, 150, true));
				msg.title = "this is title";
				msg.description = "this is description";
				msg.mediaObject = appdata;
	
				
				GetMessageFromWX.Resp resp = new GetMessageFromWX.Resp();
				resp.transaction = getTransaction();
				resp.message = msg;
				
				api.sendResp(resp);
				finish();
			}
			break;
		}

		default:
			break;
		}
	}

	private String getTransaction() {
		final GetMessageFromWX.Req req = new GetMessageFromWX.Req(bundle);
		return req.transaction;
	}
}
