package cn.nj.www.my_module.main.base.square_clip;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class ClipImageBorderView extends View
{
	
	/**
	 * ˮƽ������View�ı߾�
	 */
	private int mHorizontalPadding;
	/**
	 * ��ֱ������View�ı߾�
	 */
	private int mVerticalPadding;
	/**
	 * ���Ƶľ��εĿ��
	 */
	private int mWidth;
	/**
	 * �߿�Ŀ�� ��λdp
	 */
	private int mBorderWidth = 2;

	private Paint mPaint,mPaintRect;

	public ClipImageBorderView(Context context)
	{
		this(context, null);
	}

	public ClipImageBorderView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public ClipImageBorderView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		mBorderWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mBorderWidth, getResources().getDisplayMetrics());
		
		//������Ӱ
		mPaintRect=new Paint();
		mPaintRect.setARGB(185, 0, 0, 0);
		mPaintRect.setStyle(Style.FILL);
		
		// ���Ʊ߿�
		mPaint = new Paint();
		mPaint.setStyle(Style.STROKE);
		mPaint.setAntiAlias(true); 
		mPaint.setColor(Color.WHITE);
		mPaint.setStrokeWidth(mBorderWidth);
		
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		// �����������Ŀ��
		mWidth = getWidth() - 2 * mHorizontalPadding;
		// ���������Ļ��ֱ�߽� �ı߾�
		mVerticalPadding = (getHeight() - mWidth) / 2;

		// �������
		canvas.drawRect(0, 0, mHorizontalPadding, getHeight()-mVerticalPadding, mPaintRect);
		// �����ұ�
		canvas.drawRect(getWidth() - mHorizontalPadding, mVerticalPadding, getWidth(),getHeight(), mPaintRect);
		// �����ϱ�
		canvas.drawRect(mHorizontalPadding, 0, getWidth(),mVerticalPadding, mPaintRect);
		// �����±�
		canvas.drawRect(0, getHeight() - mVerticalPadding,getWidth() - mHorizontalPadding, getHeight(), mPaintRect);
		
		//���Ʊ߿�
		canvas.drawRect( mHorizontalPadding, mVerticalPadding,mHorizontalPadding+mWidth, mVerticalPadding+mWidth, mPaint);
		
	}

	public void setHorizontalPadding(int mHorizontalPadding)
	{
		this.mHorizontalPadding = mHorizontalPadding;
		
	}

}
