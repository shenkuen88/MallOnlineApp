package cn.nj.www.my_module.view.photopicker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.view.photopicker.model.ImageItem;
import cn.nj.www.my_module.view.photopicker.util.ImageDisplayer;


public class ImagePublishSmallAdapter extends BaseAdapter
{
	private List<ImageItem> mDataList = new ArrayList<ImageItem>();
	private Context mContext;
	private int maxCount = Constants.MAX_IMAGE_SIZE;

	public ImagePublishSmallAdapter(Context context, List<ImageItem> dataList)
	{
		this.mContext = context;
		this.mDataList = dataList;
		String className = SharePref.getString(Constants.PUBLIC_NEED_IMG_ACTY, "");
//		if (className.equals(PublishActivity.class.getName())) {//发帖
//			maxCount = Constants.MAX_IMAGE_SIZE;
//		} else if (className.equals(PublicCommentActy.class.getName())) {//评论
//			maxCount = 4;
//		} else if (className.equals(RefundActy.class.getName())) {//退款
//			maxCount = 4;
//		}
	}

	public int getCount()
	{
		// 多返回一个用于展示添加图标
		if (mDataList == null)
		{
			return 1;
		}
		else if (mDataList.size() ==maxCount)
		{
			return maxCount;
		}
		else
		{
			return mDataList.size() + 1;
		}
	}

	public Object getItem(int position)
	{
		if (mDataList != null
				&& mDataList.size() == maxCount)
		{
			return mDataList.get(position);
		}

		else if (mDataList == null || position - 1 < 0
				|| position > mDataList.size())
		{
			return null;
		}
		else
		{
			return mDataList.get(position - 1);
		}
	}

	public long getItemId(int position)
	{
		return position;
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent)
	{
		//所有Item展示不满一页，就不进行ViewHolder重用了，避免了一个拍照以后添加图片按钮被覆盖的奇怪问题
		convertView = View.inflate(mContext, R.layout.item_publish_small, null);
		ImageView imageIv = (ImageView) convertView
				.findViewById(R.id.item_grid_image);

		if (isShowAddItem(position))
		{
			imageIv.setImageResource(R.mipmap.default_upload_pic);
			imageIv.setBackgroundResource(R.color.white);
		}
		else
		{
			final ImageItem item = mDataList.get(position);
			ImageDisplayer.getInstance(mContext).displayBmp(imageIv,
					item.thumbnailPath, item.sourcePath);
		}

		return convertView;
	}

	private boolean isShowAddItem(int position)
	{
		int size = mDataList == null ? 0 : mDataList.size();
		return position == size;
	}

}
