package cn.nj.www.my_module.tools.permission;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import java.util.List;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.tools.StatusBarCompat;


public class PermissionActivity extends FragmentActivity implements
        EasyPermissions.PermissionCallbacks {

    protected static final int RC_PERM = 123;

    protected static int reSting = R.string.ask_again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initStatusBar(String strColor) {
        StatusBarCompat.setStatusBarColor(this, Color.parseColor(strColor));
        StatusBarCompat.translucentStatusBar(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private CheckPermListener mListener;

    public interface CheckPermListener {
        void superPermission();
    }

    public void checkPermission(CheckPermListener listener, int resString, String... mPerms) {
        mListener  = listener;
        if (EasyPermissions.hasPermissions(this, mPerms)) {
            if (mListener != null)
                mListener.superPermission();
        } else {
            EasyPermissions.requestPermissions(this, getString(resString),
                    RC_PERM, mPerms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EasyPermissions.SETTINGS_REQ_CODE) {
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsAllGranted() {
        if (mListener != null)
            mListener.superPermission();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.perm_tip),
                R.string.setting, R.string.cancel, null, perms);
    }



}
