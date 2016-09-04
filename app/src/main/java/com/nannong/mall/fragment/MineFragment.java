package com.nannong.mall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nannong.mall.R;
import com.nannong.mall.activity.mine.AboutUsActivity;
import com.nannong.mall.activity.mine.AccountManageActy;
import com.nannong.mall.activity.mine.BillListActivity;
import com.nannong.mall.activity.mine.CouponsActivity;
import com.nannong.mall.activity.mine.FeedBackActivity;
import com.nannong.mall.activity.mine.LoginActy;
import com.nannong.mall.activity.mine.MyBBSActy;
import com.nannong.mall.activity.mine.MyFavourActivity;
import com.nannong.mall.activity.mine.MyPinTuanActivity;
import com.nannong.mall.activity.mine.RechargeActivity;
import com.nannong.mall.activity.mine.RecieveAddressListActy;
import com.nannong.mall.activity.mine.SettingActy;
import com.nannong.mall.activity.order.OrderListActivity;
import com.nannong.mall.response.mine.LoginResponse;
import com.nannong.mall.tool.AppUtil;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseFragment;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.tools.V;

/**
 * 我的
 * Created by jwei on 2016/8/24 0024.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener
{

    /**
     * 我的订单
     */
    private RelativeLayout rlOrder;

    /**
     * 地址
     */
    private RelativeLayout rlAddress;

    /**
     * 优惠券
     */
    private RelativeLayout rlDiscount;

    /**
     * 我的收藏
     */
    private RelativeLayout rlCollect;

    /**
     * 消费记录
     */
    private RelativeLayout rlCostHistory;

    /**
     * 团购
     */
    private RelativeLayout rlTeamBuy;

    /**
     * 意见反馈
     */
    private RelativeLayout rlReply;

    /**
     * 关于我们
     */
    private RelativeLayout rlAbout;

    /**
     * 设置
     */
    private ImageView ivSet;

    /**
     * 账户管理
     */
    private RelativeLayout rlAccount;

    /**
     * 登录
     */
    private LinearLayout llLogin;

    /**
     * vip图标
     */
    private ImageView ivVip;

    /**
     * 头像
     */
    private ImageView ivHead;

    /**
     * 账号管理
     */
    private TextView tvManage;

    /**
     * 登录后显示的用户名
     */
    private TextView tvName;

    /**
     * 待付款，待发货，待收货，待评价，退款
     */
    private LinearLayout dfk, dfh, dsh, dpj, tk;

    private static MineFragment instance;

    private ImageView ivNoLoginHead;

    public MineFragment()
    {
    }

    public static MineFragment getInstance()
    {
        if (instance == null)
        {
            instance = new MineFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.from(getActivity()).inflate(R.layout.fragment_mine, null);
        initAll(v);
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (getUserVisibleHint())
        {

        }
    }

    @Override
    public void netResponse(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_USER_EXIT.equals(tag))
            {
                //用户退出登录，头部样式修改
                showLoginOrNotView();
            }
            else if (NotiTag.TAG_LOGIN_SUCCESS.equals(tag))
            {
                //更换头部显示
                showLoginOrNotView();
            }
        }
        if (event instanceof NetResponseEvent)
        {
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(LoginResponse.class.getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    LoginResponse loginResponse = GsonHelper.toType(result, LoginResponse.class);
                    if (Constants.SUCESS_CODE.equals(loginResponse.getResultCode()))
                    {
                    }
                    else
                    {
                        ErrorCode.doCode(getActivity(), loginResponse.getResultCode(), loginResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(getActivity());
                }
            }
        }
    }

    @Override
    public void initView(View mView)
    {

        rlOrder = (RelativeLayout) mView.findViewById(R.id.order_rl);
        rlAddress = (RelativeLayout) mView.findViewById(R.id.address_rl);
        rlDiscount = (RelativeLayout) mView.findViewById(R.id.discount_rl);
        rlCollect = (RelativeLayout) mView.findViewById(R.id.collect_rl);
        rlCostHistory = (RelativeLayout) mView.findViewById(R.id.cost_rl);
        rlTeamBuy = (RelativeLayout) mView.findViewById(R.id.view_rl);
        rlReply = (RelativeLayout) mView.findViewById(R.id.suggest_rl);
        rlAbout = (RelativeLayout) mView.findViewById(R.id.about_rl);
        ivSet = (ImageView) mView.findViewById(R.id.mine_set_iv);
        rlAccount = (RelativeLayout) mView.findViewById(R.id.no_login_rl);
        llLogin = (LinearLayout) mView.findViewById(R.id.login_ll);
        ivVip = (ImageView) mView.findViewById(R.id.vip_iv);
        ivNoLoginHead = (ImageView) mView.findViewById(R.id.no_login_iv);
        ivHead = (ImageView) mView.findViewById(R.id.user_head_iv);
        tvManage = (TextView) mView.findViewById(R.id.manage_tv);
        mView.findViewById(R.id.community_rl).setOnClickListener(this);
        tvName = (TextView) mView.findViewById(R.id.name_tv);
        dfk = V.f(mView, R.id.dfk);
        dfh = V.f(mView, R.id.dfh);
        dsh = V.f(mView, R.id.dsh);
        dpj = V.f(mView, R.id.dpj);
        tk = V.f(mView, R.id.tk);

        //点击跳转
        tvManage.setOnClickListener(this);
        rlAccount.setOnClickListener(this);
        llLogin.setOnClickListener(this);
        rlOrder.setOnClickListener(this);
        rlAddress.setOnClickListener(this);
        rlDiscount.setOnClickListener(this);
        rlCollect.setOnClickListener(this);
        rlCostHistory.setOnClickListener(this);
        rlTeamBuy.setOnClickListener(this);
        rlReply.setOnClickListener(this);
        rlAbout.setOnClickListener(this);
        ivSet.setOnClickListener(this);

        dfk.setOnClickListener(this);
        dfh.setOnClickListener(this);
        dsh.setOnClickListener(this);
        dpj.setOnClickListener(this);
        tk.setOnClickListener(this);

        showLoginOrNotView();
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
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.community_rl:
                AppUtil.toActyOtherwiseLogin(getActivity(), MyBBSActy.class);
                break;
            case R.id.manage_tv://充值
                AppUtil.toActyOtherwiseLogin(getActivity(), RechargeActivity.class);
                break;
            case R.id.order_rl:
//                startActivity(new Intent(getActivity(), OrderListActivity.class));
                AppUtil.toActyOtherwiseLogin(getActivity(), OrderListActivity.class);
                break;
            case R.id.address_rl:
                if (GeneralUtils.isLogin())
                {
                    Intent intent = new Intent(getActivity(), RecieveAddressListActy.class);
                    intent.putExtra(IntentCode.CHOOSE_ADDRESS_WITH_PHONE, "");
                    startActivity(intent);
                }
                else
                {
                    startActivity(new Intent(getActivity(), LoginActy.class));
                }
                break;
            case R.id.discount_rl:
               AppUtil.toActyOtherwiseLogin(getActivity(), CouponsActivity.class);
                break;
            case R.id.collect_rl:
                AppUtil.toActyOtherwiseLogin(getActivity(), MyFavourActivity.class);
                break;
            case R.id.cost_rl:
                AppUtil.toActyOtherwiseLogin(getActivity(), BillListActivity.class);
                break;
            case R.id.view_rl:
                AppUtil.toActyOtherwiseLogin(getActivity(), MyPinTuanActivity.class);
                break;
//            case R.id.play_rl:
//                AppUtil.toActyOtherwiseLogin(getActivity(), HistoryVideoActivity.class);
//                break;
//            case R.id.view_rl:
//                AppUtil.toActyOtherwiseLogin(getActivity(), HistoryGoodsActivity.class);
//                break;
            case R.id.suggest_rl:
                AppUtil.toActyOtherwiseLogin(getActivity(), FeedBackActivity.class);
                break;
            case R.id.about_rl:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.mine_set_iv:
                AppUtil.toActyOtherwiseLogin(getActivity(), SettingActy.class);
                break;
            case R.id.no_login_rl:
                AppUtil.toActyOtherwiseLogin(getActivity(), AccountManageActy.class);
                break;
            case R.id.login_ll:
                startActivity(new Intent(getActivity(), LoginActy.class));
                break;
            case R.id.dfk:
                if (GeneralUtils.isLogin())
                {
                    Intent intent = new Intent(getActivity(), OrderListActivity.class);
                    intent.putExtra("orderstate", 1);
                    startActivity(intent);
                }
                else
                {
                    startActivity(new Intent(getActivity(), LoginActy.class));
                }
                break;
            case R.id.dfh:
                if (GeneralUtils.isLogin())
                {
                    Intent intent = new Intent(getActivity(), OrderListActivity.class);
                    intent.putExtra("orderstate", 2);
                    startActivity(intent);
                }
                else
                {
                    startActivity(new Intent(getActivity(), LoginActy.class));
                }
                break;
            case R.id.dsh:
                if (GeneralUtils.isLogin())
                {
                    Intent intent = new Intent(getActivity(), OrderListActivity.class);
                    intent.putExtra("orderstate", 3);
                    startActivity(intent);
                }
                else
                {
                    startActivity(new Intent(getActivity(), LoginActy.class));
                }
                break;
            case R.id.dpj:
                if (GeneralUtils.isLogin())
                {
                    Intent intent = new Intent(getActivity(), OrderListActivity.class);
                    intent.putExtra("orderstate", 4);
                    startActivity(intent);
                }
                else
                {
                    startActivity(new Intent(getActivity(), LoginActy.class));
                }
                break;
            case R.id.tk:
                if (GeneralUtils.isLogin())
                {
                    Intent intent = new Intent(getActivity(), OrderListActivity.class);
                    intent.putExtra("orderstate", 5);
                    startActivity(intent);
                }
                else
                {
                    startActivity(new Intent(getActivity(), LoginActy.class));
                }
                break;
        }
    }

    /**
     * 展示登录 or 未登录状况下的样式
     */
    private void showLoginOrNotView()
    {
        if (GeneralUtils.isLogin())
        {
            ivVip.setVisibility(View.VISIBLE);
            ivHead.setVisibility(View.VISIBLE);
            tvName.setText(Global.getUserName());
            llLogin.setVisibility(View.GONE);
            GeneralUtils.setRoundImageViewWithUrl(getActivity(), Global.getThumUserHeadUrl(), ivHead, R.drawable.default_head);
        }
        else
        {
            tvName.setText("");
            llLogin.setVisibility(View.VISIBLE);
            ivVip.setVisibility(View.GONE);
            ivHead.setVisibility(View.INVISIBLE);
        }
    }
}
