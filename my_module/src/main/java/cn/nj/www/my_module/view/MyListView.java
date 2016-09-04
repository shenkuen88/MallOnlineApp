package cn.nj.www.my_module.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * <重写listview 解决listview嵌套问题>
 * <功能详细描述>
 *
 * @author wanghl
 * @version [版本号, 2014年4月28日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MyListView extends ListView
{

    public MyListView(Context context, AttributeSet attrs)
    {

        super(context, attrs);

    }

    public MyListView(Context context)
    {

        super(context);

    }

    public MyListView(Context context, AttributeSet attrs, int defStyle)
    {

        super(context, attrs, defStyle);

    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}
