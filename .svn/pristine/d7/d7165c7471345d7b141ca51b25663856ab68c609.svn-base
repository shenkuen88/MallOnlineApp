package com.nannong.mall.activity.index;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.nannong.mall.R;
import com.nannong.mall.response.index.SearchWordsResponse;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.XCFlowLayout;

/**
 * 搜索页面
 */
public class SearchActy extends BaseActivity implements View.OnClickListener
{

    private ImageView ivClearSearch;

    private EditText etSearch;

    private XCFlowLayout flowLayout;

    //关闭页面
    private ImageView ivFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initTitle();
        initAll();
        showSearchList();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    private void showSearchList()
    {
        etSearch.setText("");
        if (GeneralUtils.isNotNullOrZeroLenght(SharePref.getString(Constants.SEARCH_SEARCH_WORDS, "")))
        {
            SearchWordsResponse mSearchWordsResponse = GsonHelper.toType(SharePref.getString(Constants.SEARCH_SEARCH_WORDS, ""), SearchWordsResponse.class);
            List<String> hotList;
            if (mSearchWordsResponse != null && mSearchWordsResponse.getSearchList() != null)
            {
                hotList = mSearchWordsResponse.getSearchList();
                initChildViews((String[]) hotList.toArray(new String[hotList.size()]));
                findViewById(R.id.tvHistory).setVisibility(View.VISIBLE);
            }
            else
            {
                hotList = new ArrayList<>();
                findViewById(R.id.tvHistory).setVisibility(View.GONE);
            }

        }
    }

    private void initChildViews(String hotWords[])
    {
        flowLayout.removeAllViews();
        final List<TextView> tvList = new ArrayList<>();

        MarginLayoutParams lp = new MarginLayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for (int i = 0; i < hotWords.length; i++)
        {
            final TextView view = new TextView(this);
            if (GeneralUtils.isNotNullOrZeroLenght(hotWords[i]))
            {
                view.setText(hotWords[i]);
                view.setTextColor(Color.GRAY);
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
                tvList.add(view);
                view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        clearOther(tvList);
                        view.setTextColor(getResources().getColor(R.color.app_color));
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_pink_bg));
//                        Intent intent = new Intent(mContext, com.zhongchao.mall.main.acty.index.baby.SearchActy.class);
//                        intent.putExtra(IntentCode.SEARCH_KEYORD, view.getText().toString());
//                        startActivity(intent);
                    }
                });
                flowLayout.addView(view, lp);
            }
        }

    }

    private void clearOther(List<TextView> tvList)
    {
        for (int i = 0; i < tvList.size(); i++)
        {
            tvList.get(i).setTextColor(Color.GRAY);
            tvList.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
        }
    }


    @Override
    public void onEventMainThread(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                finish();
            }
            else if (NotiTag.TAG_DO_RIGHT.equals(tag))
            {
            }
            else if (NotiTag.TAG_LOGIN_SUCCESS.equals(tag))
            {//修改成功相当于登录后修改消息
                initViewData();
            }
        }
        if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();

        }
    }


    private void initTitle()
    {

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_search_clear:
                etSearch.setText("");
                break;

            case R.id.finish_iv:
                finish();
                break;

        }
    }

    @Override
    public void initView()
    {
        etSearch = (EditText) findViewById(R.id.et_search);
        ivClearSearch = (ImageView) findViewById(R.id.iv_search_clear);
        ivFinish = (ImageView) findViewById(R.id.finish_iv);
        ivFinish.setOnClickListener(this);
        flowLayout = (XCFlowLayout) findViewById(R.id.search_flowlayout);
        ivClearSearch.setOnClickListener(this);
//        UserServiceImpl.instance().search(etSearch.getText().toString(), 1, SearchResponse.class.getName());
        etSearch.setOnEditorActionListener(new OnEditorActionListener()
        {


            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2)
            {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH)
                {
                    if (GeneralUtils.isNotNullOrZeroLenght(etSearch.getText().toString()))
                    {

                        List<String> hotList;
                        //保存搜索过的词语
                        if (GeneralUtils.isNotNullOrZeroLenght(SharePref.getString(Constants.SEARCH_SEARCH_WORDS, "")))
                        {
                            SearchWordsResponse mSearchWordsResponse = GsonHelper.toType(SharePref.getString(Constants.SEARCH_SEARCH_WORDS, ""), SearchWordsResponse.class);
                            if (mSearchWordsResponse != null && mSearchWordsResponse.getSearchList() != null)
                            {
                                hotList = mSearchWordsResponse.getSearchList();
                                hotList.add(etSearch.getText().toString());
                                mSearchWordsResponse.setSearchList(hotList);
                                SharePref.saveString(Constants.SEARCH_SEARCH_WORDS, GsonHelper.toJson(mSearchWordsResponse));
                            }
                            else
                            {
                                hotList = new ArrayList<String>();
                                hotList.add(etSearch.getText().toString());
                                mSearchWordsResponse.setSearchList(hotList);
                                SharePref.saveString(Constants.SEARCH_SEARCH_WORDS, GsonHelper.toJson(mSearchWordsResponse));
                            }
                        }
                        else
                        {
                            hotList = new ArrayList<String>();
                            hotList.add(etSearch.getText().toString());
                            SearchWordsResponse mSearchWordsResponse = new SearchWordsResponse();
                            mSearchWordsResponse.setSearchList(hotList);
                            SharePref.saveString(Constants.SEARCH_SEARCH_WORDS, GsonHelper.toJson(mSearchWordsResponse));
                        }
                        showSearchList();

//                     Intent intent = new Intent(mContext, com.zhongchao.mall.main.acty.index.baby.SearchActy.class);
//                     intent.putExtra(IntentCode.SEARCH_KEYORD,etSearch.getText().toString());
//                     startActivity(intent);
                    }
                    else
                    {
                        ToastUtil.makeText(mContext, "请输入搜索内容");
                    }
                }
                return false;
            }

        });
    }

    @Override
    public void initViewData()
    {
    }

    @Override
    public void initEvent()
    {

    }

    @Override
    public void netResponse(BaseResponse event)
    {

    }


}
