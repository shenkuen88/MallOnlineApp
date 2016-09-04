package cn.nj.www.my_module.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by dell on 2016/3/2.
 * 完成输入并且失去焦点时再计算,防止输入太快过多计算导致卡死
 */
public class TransferEditText extends EditText implements TextView.OnEditorActionListener {

    public TransferEditText(Context context) {
        super(context);
        init();
    }


    public TransferEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TransferEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (!focused && mOnImputCompleteListener != null) {
            mOnImputCompleteListener.onImputComplete();
        }
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE) {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            if (mOnImputCompleteListener != null) {
                mOnImputCompleteListener.onImputComplete();
            }
            return true;
        }
        return false;
    }


    public void setOnImputCompleteListener(OnImputCompleteListener l) {
        this.mOnImputCompleteListener = l;
    }

    public OnImputCompleteListener mOnImputCompleteListener;


    public interface OnImputCompleteListener {
        void onImputComplete();

    }

    public void init() {

        setOnEditorActionListener(this);
    }


}







