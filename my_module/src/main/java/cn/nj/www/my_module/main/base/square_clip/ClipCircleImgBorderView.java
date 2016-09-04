package cn.nj.www.my_module.main.base.square_clip;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class ClipCircleImgBorderView extends View
{
	/**
	 * ˮƽ������View�ı߾�
	 */
	private int mHorizontalPadding;
	/**
	 * �߿�Ŀ�� ��λdp
	 */
	private int mBorderWidth = 2;
	
	private Paint mPaint;//Բ��������Բ��paint
	private Paint mPaintCirle;//Բ��ʵ��Բ��paint
	private Paint mPaintRect;//��Ӱ��paint
	
	private Canvas mCanvas;//��Ӱ�㻭��
	private RectF mRect;//������Ļ
	
	private Bitmap mBgBitmap;//���ڻ�����Ӱ���bitmap

	public ClipCircleImgBorderView(Context context)
	{
		this(context, null);
	}

	public ClipCircleImgBorderView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public ClipCircleImgBorderView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		mBorderWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mBorderWidth, getResources().getDisplayMetrics());

		//������Ӱ��
		mPaintRect = new Paint();
		mPaintRect.setARGB(185, 0,0, 0);
		
		// ����ʵ��Բ
		mPaintCirle = new Paint();
		mPaintCirle.setStrokeWidth((getWidth()-2*mHorizontalPadding)/2);  //ʵ��Բ�뾶
        mPaintCirle.setARGB(255, 0,0, 0);
        mPaintCirle.setXfermode(new PorterDuffXfermode(Mode.XOR));//XORģʽ���ص����ֱ��Ϳ�
        
		// ����Բ��
		mPaint = new Paint();
		mPaint.setStyle(Style.STROKE);
		mPaint.setAntiAlias(true); //�����
		mPaint.setColor(Color.WHITE);//�߿���ɫ��ɫ
		mPaint.setStrokeWidth(mBorderWidth);//���ʿ��
		
		
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if(mBgBitmap==null){
			mBgBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);  
			mCanvas = new  Canvas(mBgBitmap);
			mRect = new RectF(0, 0, getWidth(), getHeight());
		}
		//������Ӱ��
		mCanvas.drawRect(mRect, mPaintRect);
		 //����ʵ��Բ �����������mCanvas�����У�mPaintRect��mPaintCirle�ཻ���ּ����Ϳ�
		mCanvas.drawCircle( getWidth()/2, getHeight()/2, getWidth()/2-mHorizontalPadding, mPaintCirle);
		 //����Ӱ�㻭����View�Ļ�����
        canvas.drawBitmap(mBgBitmap, null, mRect, new Paint());
      //����Բ��
      	canvas.drawCircle( getWidth()/2, getHeight()/2, getWidth()/2-mHorizontalPadding, mPaint);
		
	}

	public void setHorizontalPadding(int mHorizontalPadding)
	{
		this.mHorizontalPadding = mHorizontalPadding;
		
	}

}
