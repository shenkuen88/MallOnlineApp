package cn.nj.www.my_module.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.tools.GeneralUtils;


/**
 * Created by dell on 2016/2/14.
 */
public class ClearPasswordEditText extends LinearLayout
{

    private Context mContext;
    private LinearLayout ll;
    private boolean isSee = false;
    /**
     * 右侧控制显示明文or密文的按钮
     */
    private ImageView eyes_iv;
    /**
     * 输入框
     */
    EditText input_et;
    /**
     * 在xml中设置的hint字符串
     */
    String hintStr="";
    /**
     * 在xml总设置是否显示最右侧按钮
     */
    boolean isShowEye = false;


    @TargetApi(11)
    public ClearPasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.ClearPasswordEditTextAttrs);
        hintStr = array.getString(R.styleable.ClearPasswordEditTextAttrs_hint);
        isShowEye = array.getBoolean(R.styleable.ClearPasswordEditTextAttrs_mEye,false);
        array.recycle();
        init();
    }

    public ClearPasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearPasswordEditText(Context context) {
        this(context, null);
    }

    private void init() {
        ll = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.clear_password_et, this, true);
        final ImageView clear_iv = (ImageView) ll.findViewById(R.id.clear_iv);
        input_et = (EditText) ll.findViewById(R.id.input_et);
        eyes_iv = (ImageView) ll.findViewById(R.id.eyes_iv);
        if (isShowEye){
            eyes_iv.setVisibility(VISIBLE);
        }else {
            eyes_iv.setVisibility(GONE);
        }
        input_et.setHint(hintStr);
        /**
         * 输入框焦点变化后的显示控制
         */
        input_et.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //确保焦点改变后，显示为密文
                input_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                if (hasFocus) {
                    eyes_iv.setVisibility(View.VISIBLE);
                    eyes_iv.setImageResource(R.mipmap.eyes_close);
                    if (GeneralUtils.isNotNullOrZeroLenght(input_et.getText().toString())) {
                        clear_iv.setVisibility(VISIBLE);
                    } else {
                        clear_iv.setVisibility(GONE);
                    }
                } else {
                    clear_iv.setVisibility(GONE);
                    eyes_iv.setVisibility(GONE);
                }
            }
        });
        /**
         * 输入时，控制清空按钮的显示
         */
        input_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (GeneralUtils.isNullOrZeroLenght(input_et.getText().toString())) {
                    clear_iv.setVisibility(GONE);
                } else {
                    clear_iv.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (GeneralUtils.isNotNullOrZeroLenght(input_et.getText().toString())) {
                    clear_iv.setVisibility(VISIBLE);
                } else {
                    clear_iv.setVisibility(GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (GeneralUtils.isNotNullOrZeroLenght(input_et.getText().toString())) {
                    clear_iv.setVisibility(VISIBLE);
                } else {
                    clear_iv.setVisibility(GONE);
                }
            }
        });
        /**
         * 控制明文密文显示
         */
        eyes_iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isSee) {
                    isSee = true;
                    input_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eyes_iv.setImageResource(R.mipmap.eyes_open);
                } else {
                    isSee = false;
                    input_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eyes_iv.setImageResource(R.mipmap.eyes_close);
                }
                input_et.setSelection(input_et.getText().toString().length());
            }
        });
        /**
         * 清空输入框
         */
        clear_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                input_et.setText("");
            }
        });
    }


    public EditText getEditText(){
        return input_et;
    }
    public void setEyeVisiable(boolean canSee) {
        isSee = canSee;
        if (canSee) {
            eyes_iv.setVisibility(VISIBLE);
        } else {
            eyes_iv.setVisibility(GONE);
        }
    }

    public String getText() {
        return input_et.getText().toString().trim();
    }
}
