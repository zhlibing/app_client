package com.zlb.memo.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vise.xsnow.cache.SpCache;
import com.vondear.rxtools.RxActivityTool;
import com.vondear.rxtools.view.RxToast;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.bean.User;
import com.zlb.memo.mvvm.requestModel.LoginRequestModel;
import com.zlb.memo.mvvm.viewResult.LoginResultView;
import com.zlb.memo.overall.C;
import com.zlb.memo.utils.SoftHideKeyBoardUtil;
import com.zlb.memo.utils.StatusBarUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登陆
 * Created by wp on 2017/3/27.
 */

public class LoginActivity extends BaseActivity implements LoginResultView {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_pswd)
    EditText etPswd;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.login_register)
    TextView loginRegister;
    @BindView(R.id.tv_weixin)
    ImageView tvWeixin;
    @BindView(R.id.tv_qq)
    ImageView tvQq;
    @BindView(R.id.tv_sina)
    ImageView tvSina;
    @BindView(R.id.iv_state)
    ImageView ivState;
    @BindView(R.id.tv_tiyan)
    TextView tvTiyan;
    @BindView(R.id.login_forget_password)
    TextView loginForgetPassword;

    LoginRequestModel loginRequestModel = new LoginRequestModel(this);
    SpCache spCache;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        spCache = new SpCache(this);
        initView();
        SoftHideKeyBoardUtil.assistActivity(this);
        StatusBarUtil.immersive(this);
    }

    private void initView() {
        etUsername.setText(spCache.get("loginName", ""));
        etPswd.setText(spCache.get("passWord", ""));

        tvTiyan.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        loginForgetPassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        loginRegister.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        etPswd.setSelection(etPswd.getText().length());
    }

    @OnClick({R.id.bt_login, R.id.login_forget_password, R.id.login_register, R.id.tv_weixin, R.id.tv_qq, R.id.tv_sina, R.id.iv_state, R.id.tv_tiyan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                Map<Object, Object> map = new HashMap<>();
                map.put("loginName", etUsername.getText().toString());
                map.put("passWord", etPswd.getText().toString());
                loginRequestModel.Login(map);
                break;
            case R.id.login_forget_password:
                RxActivityTool.skipActivity(this, ResetPasswordActivity.class);
                break;
            case R.id.login_register:
                RxActivityTool.skipActivity(this, RegisterActivity.class);
                break;
            case R.id.tv_weixin:
                break;
            case R.id.tv_qq:
                break;
            case R.id.tv_sina:
                break;
            case R.id.iv_state://密码可见不可见
                ivState.setSelected(!ivState.isSelected());
                if (ivState.isSelected()) {
                    etPswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPswd.setSelection(etPswd.getText().length());
                } else {
                    etPswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPswd.setSelection(etPswd.getText().length());
                }
                break;
            case R.id.tv_tiyan:
                RxActivityTool.skipActivityAndFinish(this, MainActivity.class);
                break;
        }
    }

    @Override
    public void LoginSucess(User user) {
        dismissLoading();
        C.user = user;
        spCache.put("loginName", etUsername.getText().toString());
        spCache.put("passWord", etPswd.getText().toString());
        RxActivityTool.skipActivityAndFinish(this, MainActivity.class);
    }

    @Override
    public void Fail(String msg) {
        dismissLoading();
        RxToast.error(msg);
    }

    @Override
    public void Loading(String msg) {
        showLoading(msg);
    }

    @Override
    protected void onDestroy() {
        if (loginRequestModel != null) {
            loginRequestModel.Recycle();
        }
        super.onDestroy();
    }

    @Override
    public void showIsEmpty() {

    }
}
