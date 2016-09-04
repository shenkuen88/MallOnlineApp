package cn.nj.www.my_module.view.nine_image;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.R;


public class NinePicActy extends Activity {
    private ListView listView;
    private List<List<ImageBean>> imagesList;

    private String[][] images=new String[][]{
            {"http://pic25.nipic.com/20121112/5955207_224247025000_2.jpg","250","250"}
            ,{"http://img3.douban.com/view/photo/photo/public/p2249526036.jpg","640","960"}
            ,{"file:///android_asset/img3.jpg","250","250"}
            ,{"file:///android_asset/img4.jpg","250","250"}
            ,{"file:///android_asset/img5.jpg","250","250"}
            ,{"file:///android_asset/img6.jpg","250","250"}
            ,{"file:///android_asset/img7.jpg","250","250"}
            ,{"file:///android_asset/img8.jpg","250","250"}
            ,{"http://img4.douban.com/view/photo/photo/public/p2252689992.jpg","1280","800"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_nie_image_acty);

        listView= (ListView) findViewById(R.id.lv_main);
        initData();
        listView.setAdapter(new MainAdapter(this,imagesList));

    }

    private void initData() {
        imagesList=new ArrayList<List<ImageBean>>();
       //这里单独添加一条单条的测试数据，用来测试单张的时候横竖图片的效果
        ArrayList<ImageBean> singleList=new ArrayList<ImageBean>();
        singleList.add(new ImageBean(images[8][0],Integer.parseInt(images[8][1]),Integer.parseInt(images[8][2])));
        imagesList.add(singleList);
        //从一到9生成9条朋友圈内容，分别是1~9张图片
        for(int i=0;i<9;i++){
            ArrayList<ImageBean> itemList=new ArrayList<ImageBean>();
             for(int j=0;j<=i;j++){
                 itemList.add(new ImageBean(images[j][0],Integer.parseInt(images[j][1]),Integer.parseInt(images[j][2])));
             }
            imagesList.add(itemList);
        }
    }
}
