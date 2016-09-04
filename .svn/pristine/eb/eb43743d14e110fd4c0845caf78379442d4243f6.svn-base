package cn.nj.www.my_module.tools;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.LayoutInflater;
import android.view.View;

import cn.nj.www.my_module.R;


/**
 * <只控制显示或者不显示对话框，或者单纯的显示对话框没有线程>
 * <功能详细描述>
 *
 * @author wangtao
 * @version [版本号, Apr 18, 2014]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class NetLoadingDialog
{

    private Dialog mDialog;

    private boolean isLDShow = false;

    private Context context;

    private NetLoadingDialog()
    {
    }

    private static NetLoadingDialog netLoadingDialog;


    public static NetLoadingDialog getInstance()
    {

        if (netLoadingDialog == null)
        {
            netLoadingDialog = new NetLoadingDialog();
        }
        return netLoadingDialog;
    }


    /**
     * <显示Dialog>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void loading(Context context)
    {
        this.context = context;
        try
        {
            if (isLDShow)
            {
                hideLoadingDialog();
            }
            createDialog();
            mDialog.show();
            mDialog.setCanceledOnTouchOutside(false);
            isLDShow = true;
        } catch (Exception e)
        {
            if (isLDShow && mDialog != null)
            {
                hideLoadingDialog();
            }
        }
    }

    /**
     * <隐藏对话框>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    private void hideLoadingDialog()
    {
        isLDShow = false;
        if (mDialog != null && mDialog.isShowing())
        {
            mDialog.dismiss();
        }
    }

    /**
     * <创建对话框>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    private void createDialog()
    {
        mDialog = null;
        mDialog = new Dialog(context, R.style.loading_dialog);
//        mDialog.setOnDismissListener(onDismissListener);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        mDialog.setContentView(view);
    }

    /**
     * <dismiss Dialog>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void dismissDialog()
    {
        hideLoadingDialog();
    }

    private OnDismissListener onDismissListener = new OnDismissListener()
    {

        @Override
        public void onDismiss(DialogInterface dialog)
        {
        }
    };

}
