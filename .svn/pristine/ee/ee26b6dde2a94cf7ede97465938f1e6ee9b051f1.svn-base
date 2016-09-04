package cn.nj.www.my_module.main.base.square_clip;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Tools for handler picture
 * 
 * @author Ryan.Tang
 * 
 */
public final class ImageTools {
	

	
	/**
	 * Save image to the SD card 
	 * @param photoBitmap
	 * @param photoName
	 * @param path
	 */
	public static void savePhotoToSDCard(Bitmap photoBitmap,String path,String photoName){
		if (checkSDCardAvailable()) {
			File photoFile = new File(path , photoName);
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
						fileOutputStream.flush();
					}
				}
			} catch (Exception e) {
				photoFile.delete();
				e.printStackTrace();
			}finally{
				try {
					fileOutputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} 
	}
	
	public static void savePhotoToSDCard(Bitmap photoBitmap,String path){
		if (checkSDCardAvailable()) {
			File photoFile = new File(path);
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
						fileOutputStream.flush();
					}
				}
			} catch (Exception e) {
				photoFile.delete();
				e.printStackTrace();
			} finally{
				try {
					fileOutputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} 
	}
	
	/**
	 * Check the SD card 
	 * @return
	 */
	public static boolean checkSDCardAvailable(){
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * ����·������bitmap
	 * 
	 * @param path
	 *            ·��
	 * @param w
	 *            ��
	 * @param h
	 *            ��
	 * @return
	 */
	public static final Bitmap convertToBitmap(String path, int w, int h) {
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			// ����Ϊtureֻ��ȡͼƬ��С
			opts.inJustDecodeBounds = true;
			opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
			// ����Ϊ��
			BitmapFactory.decodeFile(path, opts);
			int width = opts.outWidth;
			int height = opts.outHeight;
			float scaleWidth = 0.f, scaleHeight = 0.f;
			if (width > w || height > h) {
				// ����
				scaleWidth = ((float) width) / w;
				scaleHeight = ((float) height) / h;
			}
			opts.inJustDecodeBounds = false;
			float scale = Math.max(scaleWidth, scaleHeight);
			opts.inSampleSize = (int) scale;
			WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
			Bitmap bMapRotate = Bitmap.createBitmap(weak.get(), 0, 0, weak.get().getWidth(), weak.get().getHeight(), null, true);
			if (bMapRotate != null) {
				return bMapRotate;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	 /**
     * ��ȡͼƬ���ԣ���ת�ĽǶ�
     *
     * @param path ͼƬ����·��
     * @return degree��ת�ĽǶ�
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    
    public static Bitmap adjustPhotoRotation(Bitmap bm, int orientationDegree) {
        Matrix m = new Matrix();
        m.setRotate((float)orientationDegree, (float)bm.getWidth() / 2.0F, (float)bm.getHeight() / 2.0F);

        try {
            Bitmap ex = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
            return ex;
        } catch (OutOfMemoryError var4) {
            var4.printStackTrace();
            return bm;
        }
    }
	
}
