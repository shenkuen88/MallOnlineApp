package cn.nj.www.my_module.view;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.ToastUtil;


/**
 * @author 作者 胡清
 * @version 创建时间：2015年2月3日 下午12:36:17 类说明 : 将继承的布局改变了
 *          android.widget.RelativeLayout
 * @E-mail: 506966949@qq.com
 */
public class PlusMinusBar extends RelativeLayout {


    public Button pm_plux;
    public Button pm_minus;
    public EditText pm_number;

    public void setMaxInput(int maxInput) {
        this.maxInput = maxInput;
    }

    private int maxInput = 1000;

    public int getNum() {
        return num;
    }

    private int num = 1;

    public PlusMinusBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public PlusMinusBar(Context context) {
        super(context);
        init(context);
    }

    public PlusMinusBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.plusminus, this);
        pm_minus = (Button) findViewById(R.id.pm_minus);
        pm_plux = (Button) findViewById(R.id.pm_plux);
        pm_number = (EditText) findViewById(R.id.pm_number);
        pm_number.setSelection(pm_number.getText().length());
        setMaxInput(5);
        pm_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String numString = s.toString();
                if (numString == null || numString.equals("")) {
                    num = 0;
                } else {
                    int numInt = Integer.parseInt(numString);
                    if (numInt < 1) {

                    } else if (numInt > maxInput) {
                        //设置EditText光标位置 为文本末端
                        pm_number.setText(maxInput + "");
                        ToastUtil.makeText(context, "最多输入" + maxInput);
                    } else {
                        pm_number.setSelection(pm_number.getText().toString().length());
                        num = numInt;
                    }
                }
                if (iResult!=null){
                    iResult.onResult(num);
                }
            }
        });

        pm_minus.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
//				if (mOnBnClick!=null) {
//					mOnBnClick.minuxNum();
//				}
                if (GeneralUtils.isNotNullOrZeroLenght(pm_number.getText().toString())) {
                    if (num == 0) {
                        return;
                    } else {
                        if (num > 0) {
                            pm_number.setText("" + (num - 1));
                        } else {
                            pm_number.setText("0");
                        }
                    }
                } else {
                    pm_number.setText("0");
                    return;
                }
            }
        });

        pm_plux.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//				if (mOnBnClick!=null) {
//					mOnBnClick.addNum();
//				}
                pm_number.setText("" + (num + 1));
            }
        });
    }


    public interface OnBnClick {
        void addNum();

        void minuxNum();
    }

    public interface IResult {
        void onResult(int num);
    }

    private OnBnClick mOnBnClick;
    private IResult iResult;

    public IResult getiResult() {
        return iResult;
    }

    public void setiResult(IResult iResult) {
        this.iResult = iResult;
    }

    public void setOnBnClick(OnBnClick mOnBnClick) {
        this.mOnBnClick = mOnBnClick;
    }



//	test_PlusMinusBar.setOnBnClick(new OnBnClick() {
//		@Override
//		public void minuxNum() {
//		 number = Integer.parseInt(test_PlusMinusBar.pm_number.getText().toString());
//			if (number>=1) {
//				test_PlusMinusBar.pm_number.setText((number-1)+"");
//			}
//		}
//		
//		@Override
//		public void addNum() {
//			 number = Integer.parseInt(test_PlusMinusBar.pm_number.getText().toString());
//			 test_PlusMinusBar.pm_number.setText(""+(number+1) );
//		}
//	});
}
