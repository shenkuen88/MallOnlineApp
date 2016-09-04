package cn.nj.www.my_module.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.bean.update.CodeBean;


/**
 * 客户端更新弹出提示框
 */
public class UpdateDialog extends Dialog {
    private Context context;
    private CodeBean codeBean;
    //	private TextView cur_client,server_client,client_info;
    private TextView update_tishi;
    private ImageView update_btn_define, update_btn_cancel;
    private FrameLayout progress_layout;
    private ProgressBar progress_Bar;
    private TextView progress_txt;
    private int i = 0;//0.自动更新页1.手动更新页

    public UpdateDialog(Context context, int theme, CodeBean codeBean, int i) {
        super(context, theme);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.codeBean = codeBean;
        this.i = i;
        setOnKeyListener(keylistener);
        setCanceledOnTouchOutside(false);
       setCancelable(false);
    }
    private OnKeyListener keylistener = new OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }

    };
    public String getVersion() {
        String version ="1.0";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return version;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View layout = getLayoutInflater().inflate(R.layout.update_dialog, null);
        this.setContentView(layout, new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        update_tishi = (TextView) layout.findViewById(R.id.update_tishi);
        progress_Bar = (ProgressBar) layout.findViewById(R.id.progress_Bar);
        progress_layout = (FrameLayout) layout.findViewById(R.id.progress_layout);
        progress_txt = (TextView) layout.findViewById(R.id.progress_txt);
        StringBuffer sb = new StringBuffer();
        sb.append("现有版本：");
        sb.append(getVersion());
        sb.append("\r\n最新版本：");
        sb.append(codeBean.getCodename());
        sb.append("\r\n功能介绍：");
        sb.append(codeBean.getMessage());
        update_tishi.setText(sb.toString());
        update_btn_define = (ImageView) layout.findViewById(R.id.update_btn_define);
        update_btn_cancel = (ImageView) layout.findViewById(R.id.update_btn_cancel);
//        update_btn_define.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                ((BaseActivity) context).dialogOk();
//            }
//        });
//        update_btn_cancel.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                ((BaseActivity) context).dialogCancel();
//            }
//        });
    }
    public void setGXClickListener(View.OnClickListener onClickListener){
        update_btn_define.setOnClickListener(onClickListener);
    }
    public void setCancelClickListener(View.OnClickListener onClickListener){
        update_btn_cancel.setOnClickListener(onClickListener);
    }
    /***
     * 设置进度显示
     *
     * @param progressnum
     */
    public void setProgressNum(int progressnum) {
        if (progress_layout.getVisibility() == ProgressBar.GONE)
            progress_layout.setVisibility(ProgressBar.VISIBLE);
        progress_Bar.setProgress(progressnum);
        progress_txt.setText("下载到" + progressnum + "%");
    }
}
