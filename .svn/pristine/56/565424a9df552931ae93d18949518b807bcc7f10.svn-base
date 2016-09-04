package com.nannong.mall.activity.mine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.nannong.mall.R;
import com.nannong.mall.response.mine.AddReceiveAddressResponse;
import com.nannong.mall.response.mine.AddressBean;
import com.nannong.mall.response.mine.AddressListResponse;
import com.nannong.mall.response.mine.DeleteAddressResponse;
import com.nannong.mall.response.mine.EditReceiveAddressResponse;

import java.util.ArrayList;
import java.util.List;

import cn.nj.www.my_module.adapter.CommonAdapter;
import cn.nj.www.my_module.adapter.ViewHolder;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.DisplayUtil;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.swipemenulist.SwipeMenu;
import cn.nj.www.my_module.view.swipemenulist.SwipeMenuCreator;
import cn.nj.www.my_module.view.swipemenulist.SwipeMenuItem;
import cn.nj.www.my_module.view.swipemenulist.SwipeMenuListView;
import cn.nj.www.my_module.view.varyview.VaryViewHelper;
import de.greenrobot.event.EventBus;

/**
 * Created by huqing on 2016/7/4.
 * 收货地址列表  addres_item
 */
public class RecieveAddressListActy extends BaseActivity
{

    private SwipeMenuListView lvAddress;

    private List<AddressBean> addressBeanList = new ArrayList<AddressBean>();

    private CommonAdapter<AddressBean> mAdapter;

    private int deletePosition = -1;

    private String chooseAddress = "";// 1或者是电话号码： 选择地址 展示checkbox

    private String addressDetail = "";

    private VaryViewHelper mVaryViewHelper;

    List<CheckBox> cbList = new ArrayList<>();

    boolean isFirst = true;

    private boolean isLoadIing;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_list);
        //查看收货地址列表chooseAddress 为"" or 选择地址chooseAddress为地址内容
        chooseAddress = getIntent().getStringExtra(IntentCode.CHOOSE_ADDRESS_WITH_PHONE);
        if (chooseAddress.length() > 1)
        {
            addressDetail = getIntent().getStringExtra(IntentCode.EDIT_ADDRESS_BEAN_DETAIL);
        }
        initAll();
    }

    private void initData()
    {
        isLoadIing = true;
        UserServiceImpl.instance().getReceiveAddressList(AddressListResponse.class.getName());
    }

    @Override
    public void initView()
    {
        initTitle();
        lvAddress = (SwipeMenuListView) findViewById(R.id.address_lv);


    }

    @Override
    public void initViewData()
    {
        mAdapter =
                new CommonAdapter<AddressBean>(mContext, addressBeanList, R.layout.item_address)
                {
                    @Override
                    public void convert(final ViewHolder helper, final AddressBean item)
                    {
                        helper.getView(R.id.address_ll).setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(mContext, AddRecieveAddressActy.class);
                                intent.putExtra(IntentCode.EDIT_ADDRESS, "1");
                                intent.putExtra(IntentCode.EDIT_ADDRESS_POSITION, helper.getPosition());
                                intent.putExtra(IntentCode.EDIT_ADDRESS_BEAN_DETAIL, item);
                                startActivity(intent);
                            }
                        });

                        Button button = helper.getView(R.id.default_view);
                        if (item.getIsDefault() != null && item.getIsDefault().equals("1"))
                        {
                            button.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            button.setVisibility(View.GONE);
                        }
                        final CheckBox showCB = helper.getView(R.id.show);
                        if (chooseAddress != null && chooseAddress.length() > 0)
                        {
                            helper.getView(R.id.address_ll).setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    SharePref.saveString(Constants.CHOOSE_ADDRESS, GsonHelper.toJson(item));
                                    EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_CHOOSE_ADDRESS_OK));
                                    finish();
                                }
                            });
                            cbList.add(showCB);
                            //展示是否选中
                            if (item.getPhone().trim().equals(chooseAddress.trim()) && item.getDetail().trim().equals(addressDetail))
                            {
                                showCB.setChecked(true);
                                helper.getView(R.id.default_view).setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                showCB.setChecked(false);
                            }
                        }
                        else
                        {
                            showCB.setVisibility(View.GONE);
                        }

                        helper.setText(R.id.name_tv, item.getDeliveryUser());
                        helper.setText(R.id.tel_tv, item.getPhone());
                        helper.setText(R.id.address_tv, item.getProvince() + " " + item.getCity() + " " + item.getArea() + " " + item.getDetail());


                    }
                };
        lvAddress.setAdapter(mAdapter);
        initLeftSlideList(lvAddress);

        if (addressBeanList.size() == 0)
        {
            mVaryViewHelper = new VaryViewHelper.Builder()
                    .setDataView(findViewById(R.id.content_view))//放数据的父布局，逻辑处理在该Activity中处理
                    .setEmptyView(R.mipmap.ic_launcher, "还未添加收货地址")//空页面，图+文字
                    .setRefreshListener(new View.OnClickListener()//断网等错误时并且没有数据，显示错误，点击按钮刷新
                    {
                        @Override
                        public void onClick(View v)
                        {
                            if (GeneralUtils.isNetworkConnected(mContext))
                            {
                                mVaryViewHelper.showLoadingView();
                                if (!isLoadIing)
                                {
                                    initData();
                                }
                            }
                            else
                            {
                                ToastUtil.showError(mContext);
                            }
                        }
                    })//错误页点击刷新实现
                    .build(mContext);
            mVaryViewHelper.showLoadingView();
            initData();
        }
        if (GeneralUtils.isNotNullOrZeroLenght(SharePref.getString(Constants.ADDRESS_LIST, "")))
        {
            AddressListResponse mAddressListResponse = GsonHelper.toType(SharePref.getString(Constants.ADDRESS_LIST, ""), AddressListResponse.class);
            if (Constants.SUCESS_CODE.equals(mAddressListResponse.getResultCode()))
            {
                addressBeanList = mAddressListResponse.getUserAddressList();
                mAdapter.setData(addressBeanList);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void initEvent()
    {

    }

    @Override
    public void netResponse(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            //关闭页面
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                finish();
            }
            else if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                Intent intent = new Intent(mContext, AddRecieveAddressActy.class);
                intent.putExtra(IntentCode.EDIT_ADDRESS, "0");
                startActivity(intent);
            }
            else if (NotiTag.TAG_CHANGE_RECEIVE_ADDRESS.equals(tag))
            {
                //收货地址改变了
                String result = ((NoticeEvent) event).getText();
                int position = ((NoticeEvent) event).getPosition();
                EditReceiveAddressResponse mEditReceiveAddressResponse = GsonHelper.toType(result, EditReceiveAddressResponse.class);
                AddressBean address = mEditReceiveAddressResponse.getUserAddress();
                if (address.getIsDefault() != null && address.getIsDefault().equals("1"))
                {
                    for (AddressBean bean : addressBeanList)
                    {
                        bean.setIsDefault("0");
                    }
                }
                addressBeanList.set(position, address);
                mAdapter.notifyDataSetChanged();
            }
            else if (NotiTag.NEW_SAVE_ADDRESS_RESULT.equals(tag))
            {
                //新增收货地址
                mVaryViewHelper.showDataView();
                String result = ((NoticeEvent) event).getText();
                AddReceiveAddressResponse mAddReceiveAddressResponse = GsonHelper.toType(result, AddReceiveAddressResponse.class);
                AddressBean address = mAddReceiveAddressResponse.getUserAddress();
                if (address.getIsDefault() != null && address.getIsDefault().equals("1"))
                {
                    for (AddressBean bean : addressBeanList)
                    {
                        bean.setIsDefault("0");
                    }
                }
                addressBeanList.add(0, mAddReceiveAddressResponse.getUserAddress());
                mAdapter.notifyDataSetChanged();
            }
        }
        if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            isLoadIing = false;
            if (tag.equals(AddressListResponse.class.getName()))
            {
                mVaryViewHelper.showDataView();
                CMLog.e(Constants.HTTP_TAG, result);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    AddressListResponse mAddressListResponse = GsonHelper.toType(result, AddressListResponse.class);
                    if (Constants.SUCESS_CODE.equals(mAddressListResponse.getResultCode()))
                    {
                        SharePref.saveString(Constants.ADDRESS_LIST, result);
                        addressBeanList = mAddressListResponse.getUserAddressList();
                        mAdapter.setData(addressBeanList);
                        mAdapter.notifyDataSetChanged();
                        lvAddress.setVisibility(View.VISIBLE);
                        if (addressBeanList.size() == 0)
                        {
                            mVaryViewHelper.showEmptyView();
                        }
                    }
                    else
                    {
                        if (addressBeanList.size() == 0)
                        {
                            mVaryViewHelper.showEmptyView();
                        }
                        ErrorCode.doCode(mContext, mAddressListResponse.getResultCode(), mAddressListResponse.getDesc());
                    }
                }
                else
                {
                    if (addressBeanList.size() == 0)
                    {
                        mVaryViewHelper.showErrorView();
                    }
                    ToastUtil.showError(mContext);
                }
            }
            else if (tag.equals(DeleteAddressResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    CMLog.e(Constants.HTTP_TAG, result);
                    DeleteAddressResponse mDeleteAddressResponse = GsonHelper.toType(result, DeleteAddressResponse.class);
                    if (Constants.SUCESS_CODE.equals(mDeleteAddressResponse.getResultCode()))
                    {
                        ToastUtil.makeText(mContext, "删除成功!");
                        if (deletePosition != -1)
                        {
                            addressBeanList.remove(deletePosition);
                            deletePosition = -1;
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mDeleteAddressResponse.getResultCode(), mDeleteAddressResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }

    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        if (chooseAddress != null && chooseAddress.length() > 0)
        {
            headView.setRightText("添加");
        }
        else
        {
            headView.setRightText("添加");
        }
        headView.setTitleText("收货地址");
    }

    //初始化向左滑动出现删除按钮
    private void initLeftSlideList(SwipeMenuListView listview)
    {
        SwipeMenuCreator creator = new SwipeMenuCreator()
        {

            @Override
            public void create(SwipeMenu menu)
            {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(getResources().getColor(R.color.app_color)));
                // set item width
                openItem.setWidth(DisplayUtil.dip2px(mContext, 60));
                // set item title
                openItem.setTitle("删除");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
            }
        };
        // set creator
        listview.setMenuCreator(creator);

        // step 2. listener item click event
        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index)
            {
                NetLoadingDialog.getInstance().loading(mContext);
                deletePosition = position;
                UserServiceImpl.instance().deleteAddress(addressBeanList.get(position).getRecordID(),
                        DeleteAddressResponse.class.getName());
                return false;
            }
        });
        View bottom = LayoutInflater.from(mContext).inflate(R.layout.line_thin_view, null);
        listview.addFooterView(bottom);
    }
}
