package cn.nj.www.my_module.view.birthdate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import cn.nj.www.my_module.R;

/**
 * Created by zhangle on 2015/7/15.
 */
public class MyDayView extends View {
    private Context mContext;
    private Paint paint;
    private int textColor = R.color.primary_material_dark;
    private int textSize = 30;
    private int paddingTopSize = 10;
    private ArrayList<String> showList = new ArrayList<>();
    private ArrayList<String> totalList = new ArrayList<>();
    private int index = 0;
    float iny = 0;
    private int days = 31;
    private Paint linePaint;

    public MyDayView(Context context) {
        super(context);
        mContext = context;
        initData(days);
        textSize = dip2px(mContext, 18);
    }

    public MyDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData(days);
        textSize = dip2px(mContext, 18);
    }

    public MyDayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData(days);
        textSize = dip2px(mContext, 18);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, (textSize + dip2px(mContext, 8)) * 7);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paddingTopSize = dip2px(mContext, 8);
        paint = new Paint();
        paint.setColor(getResources().getColor(textColor));
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(getResources().getColor(R.color.line));
        linePaint.setStrokeWidth(1);
        for (int i = 0; i < showList.size(); i++) {
            paddingTopSize = paddingTopSize + dip2px(mContext, 8);
            if (i == 0 || i == 6) {
                paint.setAlpha(30);
                canvas.drawText(showList.get(i), (getWidth() - 72) / 2, paddingTopSize, paint);
            } else if (i == 1 || i == 5) {
                paint.setAlpha(63);
                canvas.drawText(showList.get(i), (getWidth() - 72) / 2, paddingTopSize, paint);
            } else if (i == 2 || i == 4) {
                paint.setAlpha(125);
                canvas.drawText(showList.get(i), (getWidth() - 72) / 2, paddingTopSize, paint);
            } else {
                linePaint.setAlpha(255);
                canvas.drawLine(0, paddingTopSize - textSize, getWidth(), paddingTopSize - textSize, linePaint);
                canvas.drawText(showList.get(i), (getWidth() - 72) / 2, paddingTopSize, paint);
                canvas.drawLine(0, paddingTopSize + 10, getWidth(), paddingTopSize + 10, linePaint);
            }
            paddingTopSize = paddingTopSize + textSize;
        }
    }

    public void initData(int thedays) {
        totalList.clear();
        //index=0;
        for (int i = 1; i <= thedays; i++) {
            String yearString = i + "日";
            totalList.add(yearString);
        }
        index = getCurrentTime() - 1;
        initShowList(index);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            iny = event.getY();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float movey = event.getY();
            if (movey > iny && (movey - iny) > (textSize + 12) / 2) {
                if (index == 0) {
                    iny = event.getY();
                    index = totalList.size() - 1;
                    initShowList(index);
                } else {
                    iny = event.getY();
                    index--;
                    initShowList(index);
                }
            }
            if (movey < iny && (iny - movey) > (textSize + 12) / 2) {
                if (index == totalList.size() - 1) {
                    iny = event.getY();
                    index = 0;
                    initShowList(index);
                } else {
                    iny = event.getY();
                    index++;
                    initShowList(index);
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
        }
        return super.onTouchEvent(event);
    }

    private void initShowList(int index) {
        showList.clear();
        if (index == 0) {
            showList.add(totalList.get(totalList.size() - 3));
            showList.add(totalList.get(totalList.size() - 2));
            showList.add(totalList.get(totalList.size() - 1));
            showList.add(totalList.get(index));
            showList.add(totalList.get(index + 1));
            showList.add(totalList.get(index + 2));
            showList.add(totalList.get(index + 3));
        } else if (index == 1) {
            showList.add(totalList.get(totalList.size() - 2));
            showList.add(totalList.get(totalList.size() - 1));
            showList.add(totalList.get(index - 1));
            showList.add(totalList.get(index));
            showList.add(totalList.get(index + 1));
            showList.add(totalList.get(index + 2));
            showList.add(totalList.get(index + 3));
        } else if (index == 2) {
            showList.add(totalList.get(totalList.size() - 1));
            showList.add(totalList.get(index - 2));
            showList.add(totalList.get(index - 1));
            showList.add(totalList.get(index));
            showList.add(totalList.get(index + 1));
            showList.add(totalList.get(index + 2));
            showList.add(totalList.get(index + 3));
        } else if (index == totalList.size() - 1) {
            showList.add(totalList.get(index - 3));
            showList.add(totalList.get(index - 2));
            showList.add(totalList.get(index - 1));
            showList.add(totalList.get(index));
            showList.add(totalList.get(0));
            showList.add(totalList.get(1));
            showList.add(totalList.get(2));
        } else if (index == totalList.size() - 2) {
            showList.add(totalList.get(index - 3));
            showList.add(totalList.get(index - 2));
            showList.add(totalList.get(index - 1));
            showList.add(totalList.get(index));
            showList.add(totalList.get(index + 1));
            showList.add(totalList.get(0));
            showList.add(totalList.get(1));
        } else if (index == totalList.size() - 3) {
            showList.add(totalList.get(index - 3));
            showList.add(totalList.get(index - 2));
            showList.add(totalList.get(index - 1));
            showList.add(totalList.get(index));
            showList.add(totalList.get(index + 1));
            showList.add(totalList.get(index + 2));
            showList.add(totalList.get(0));
        } else {
            showList.add(totalList.get(index - 3));
            showList.add(totalList.get(index - 2));
            showList.add(totalList.get(index - 1));
            showList.add(totalList.get(index));
            showList.add(totalList.get(index + 1));
            showList.add(totalList.get(index + 2));
            showList.add(totalList.get(index + 3));
        }
        postInvalidate();
    }

    public String getTime() {
        String time;
        time = totalList.get(index);
        return time.replace("日","");
    }

    private int getCurrentTime() {
//        Calendar calendar = Calendar.getInstance();
        return 1;
//        return calendar.get(Calendar.DATE);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
