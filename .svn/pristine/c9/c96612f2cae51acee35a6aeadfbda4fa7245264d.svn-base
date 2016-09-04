package cn.nj.www.my_module.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

import cn.nj.www.my_module.constant.Constants;

/**
 * <管理本地文件目录>
 * <功能详细描述>
 *
 * @author Administrator
 * @version [版本号, 2015-4-23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FileSystemManager
{
    /**
     * 根目录缓存目录
     */
    private static String cacheFilePath;

    /**
     * 用户头像缓存目录
     */
    private static String userHeadPath;

    /**
     * 组图缓存目录
     */
    private static String mallComplaintsPicPath;

    /**
     * 崩溃日志缓存目录（上传成功则删除）
     */
    private static String crashPath;

    /**
     * 临时目录
     */
    private static String temporaryPath;

    /**
     * 列表页面图片缓存目录
     */
    private static String cacheImgFilePath;
    /**
     * 保存的图片路径
     */
    private static String imgPath;

    /**
     * 保存图片到本地,这个是把图片压缩成字节流然后保存到本地，所以本地的图片是无法显示的且保存在data/data...中 占用系统内存 不建议使用
     *
     * @param mBitmap
     * @param imageURL
     * @param cxt
     */
    public static void saveBitmap(Bitmap mBitmap, String imageURL, Context cxt) {

        String bitmapName = imageURL.substring(imageURL.lastIndexOf("/") + 1); // 传入一个远程图片的url，然后取最后的图片名字

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = cxt.openFileOutput(bitmapName, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
            // 这里是保存文件产生异常
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    // oos流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取本地私有文件夹的图片
     *
     * @param cxt
     * @return
     */
    public static Bitmap getBitmap(String fileName, Context cxt) {
        String bitmapName = fileName.substring(fileName.lastIndexOf("/") + 1);
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = cxt.openFileInput(bitmapName);
            ois = new ObjectInputStream(fis);
            byte[] byteArray = (byte[]) ois.readObject();
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                    byteArray.length);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            // 这里是读取文件产生异常
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    // ois流关闭异常
                    e.printStackTrace();
                }
            }
        }
        // 读取产生异常，返回null
        return null;
    }

    // 通过这种方式保存在本地的图片，是可以看到的保存在sd卡中指定路径
    public static void saveBitmap2(Context context,Bitmap mBitmap, String imageURL) {
        String newurl =imageURL;
        if(imageURL.contains("?")) {
            newurl = imageURL.split("\\?")[0];
        }
        if(GeneralUtils.isNullOrZeroLenght(newurl)||newurl.equals("null"))return;
        String bitmapName = newurl.substring(newurl.lastIndexOf("/"));

        File file = new File(FileSystemManager.getTemporaryPath(context), bitmapName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            // 这里是保存文件产生异常
            CMLog.e(Constants.LOCAL_TAG,"图片保存失败");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // fos流关闭异常
                    e.printStackTrace();
                    CMLog.e(Constants.LOCAL_TAG, "图片保存失败 fos流关闭异常");
                }
            }
        }
    }

    public static Bitmap getBitmap2(String fileName) {
        String bitmapName = fileName.substring(fileName.lastIndexOf("/") + 1);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(mallComplaintsPicPath + "/" + bitmapName);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            // 这里是读取文件产生异常
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // fis流关闭异常
                    e.printStackTrace();
                }
            }
        }
        // 读取产生异常，返回null
        return null;
    }

    /**
     * 判断本地的私有文件夹里面是否存在当前名字的文件
     */
    public static boolean isFileExist(String fileName) {
        String bitmapName = fileName.substring(fileName.lastIndexOf("/") + 1);
        File file = new File(mallComplaintsPicPath + "/" + bitmapName);
        return file.exists();
//		List<String> nameLst = Arrays.asList(cxt.fileList());
//		if (nameLst.contains(bitmapName)) {
//			return true;
//		} else {
//			return false;
//		}

    }
    /**
     * <根目录缓存目录>
     * <功能详细描述>
     *
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getCacheFilePath(Context context)
    {
        cacheFilePath = FileUtil.getSDPath(context) + File.separator + Constants.BASE_PROJECT + File.separator;
        return cacheFilePath;
    }

    /**
     * 创建文件夹
     * @param context
     */
    public static void getCacheFilePathAll(Context context)
    {
        String mCacheFilePath = getCacheFilePath(context);
        CMLog.i("info", mCacheFilePath);
        FileUtil.createNewFile(mCacheFilePath);
        FileUtil.createNewFile(mCacheFilePath + File.separator);
        FileUtil.createNewFile(mCacheFilePath + File.separator + "head");
        FileUtil.createNewFile(mCacheFilePath + File.separator + "img");
        FileUtil.createNewFile(mCacheFilePath + File.separator + "temp");
    }
    /**
     * 删除指定目录下文件及目录
     * @param deleteThisPath
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /**
     * <用户头像地址>
     * <功能详细描述>
     *
     * @param context
     * @param userId
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getUserHeadPath(Context context, String userId)
    {
        userHeadPath =
                FileUtil.createNewFile(getCacheFilePath(context) + "head" + File.separator + userId + File.separator);
        return userHeadPath;
    }
    /**
     * <用户头像地址>
     * <功能详细描述>
     *
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getImgPath(Context context)
    {
        imgPath =
                FileUtil.createNewFile(getCacheFilePath(context) + "img" + File.separator);
        return imgPath;
    }

    /**
     * <列表页面图片缓存目录>
     * <功能详细描述>
     *
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getCacheImgFilePath(Context context)
    {
        cacheImgFilePath = FileUtil.createNewFile(getCacheFilePath(context) + "img" + File.separator);
        return cacheImgFilePath;
    }

    /**
     * <组图缓存目录>
     * <功能详细描述>
     *
     * @param context
     * @param userId
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getMallComplaintsPicPath(Context context, String userId)
    {
        mallComplaintsPicPath =
                FileUtil.createNewFile(getCacheFilePath(context) + "mallcomplaints" + File.separator + userId
                        + File.separator + "pic" + File.separator);
        return mallComplaintsPicPath;
    }

    /**
     * <崩溃日志缓存目录（上传成功则删除）>
     * <功能详细描述>
     *
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getCrashPath(Context context)
    {
        crashPath = FileUtil.createNewFile(getCacheFilePath(context) + "crash" + File.separator);
        return crashPath;
    }

    /**
     * <临时图片缓存目录>
     * <功能详细描述>
     *
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getTemporaryPath(Context context)
    {
        temporaryPath = FileUtil.createNewFile(getCacheFilePath(context) + "temp" + File.separator);
        return temporaryPath;
    }
    /**
     * 获取文件夹大小
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(File file){

        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++)
            {
                if (fileList[i].isDirectory())
                {
                    size = size + getFolderSize(fileList[i]);

                }else{
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //return size/1048576;
        return size;
    }



    /**
     * 格式化单位
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        if (size==0){
            return "";
        }
        double kiloByte = size/1024;
        if(kiloByte < 1) {
            return size + "Byte(s)";
        }

        double megaByte = kiloByte/1024;
        if(megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte/1024;
        if(gigaByte < 1) {
            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte/1024;
        if(teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}
