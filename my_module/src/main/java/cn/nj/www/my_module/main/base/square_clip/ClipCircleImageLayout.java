package cn.nj.www.my_module.main.base.square_clip;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

public class ClipCircleImageLayout extends RelativeLayout
{

	private ClipZoomImageView mZoomImageView;
	private ClipCircleImgBorderView mClipImageView;

	private int mHorizontalPadding =60;

	private boolean circle = true;

	public void setCircle(boolean circle) {
		this.circle = circle;
	}

	public ClipCircleImageLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mZoomImageView = new ClipZoomImageView(context);

//		mClipImageView = new ClipImageBorderView(context);//显示矩形
		mClipImageView = new ClipCircleImgBorderView(context);//显示圆形

		android.view.ViewGroup.LayoutParams lp = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		
		this.addView(mZoomImageView, lp);
		this.addView(mClipImageView, lp);

		
		// ����padding��px
		mHorizontalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding, getResources().getDisplayMetrics());
		mZoomImageView.setHorizontalPadding(mHorizontalPadding);
		mClipImageView.setHorizontalPadding(mHorizontalPadding);
	}

	/**
	 * ���⹫�����ñ߾�ķ���,��λΪdp
	 * 
	 * @param mHorizontalPadding
	 */
	public void setHorizontalPadding(int mHorizontalPadding)
	{
		this.mHorizontalPadding = mHorizontalPadding;
	}

	/**
	 * ����ͼƬ
	 * 
	 * @return
	 */
	public Bitmap clip()
	{
		return mZoomImageView.clip();
	}

	public void setBitmap(Bitmap bitmap) {
		mZoomImageView.setImageBitmap(bitmap);
	}

}
