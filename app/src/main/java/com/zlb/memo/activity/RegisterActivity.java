package com.zlb.memo.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.RxRegTool;
import com.vondear.rxtools.view.RxToast;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.bean.User;
import com.zlb.memo.mvvm.requestModel.RegistRequestModel;
import com.zlb.memo.mvvm.viewResult.RegistResultView;
import com.zlb.memo.utils.SoftHideKeyBoardUtil;
import com.zlb.memo.utils.StatusBarUtil;
import com.zlb.memo.widgets.ValidCodeButton;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册界面
 */
public class RegisterActivity extends BaseActivity implements RegistResultView {
    RegistRequestModel registRequestModel = new RegistRequestModel(this);
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.ed_verify_code)
    EditText edVerifyCode;
    @BindView(R.id.bt_code)
    ValidCodeButton btCode;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.ed_confirm_password)
    EditText edConfirmPassword;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initGui();
        SoftHideKeyBoardUtil.assistActivity(this);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);
    }

    protected void initGui() {
    }

    @Override
    public void Fail(String msg) {

    }

    @Override
    public void Loading(String msg) {

    }

    @Override
    public void RegistSucess(User user) {
        RxToast.error(user.getUserId());
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registRequestModel.Recycle();
    }

    @Override
    public void showIsEmpty() {

    }

    @OnClick({R.id.bt_code, R.id.confirm_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_code:
                btCode.startCountDownTimer();
                break;
            case R.id.confirm_btn:
                if (RxRegTool.isMobile(etPhone.getText().toString())) {
                    if (!RxDataTool.isEmpty(edPassword.getText().toString())) {
                        if (edPassword.getText().toString().equals(edConfirmPassword.getText().toString())) {
                            Map<Object, Object> map = new HashMap<>();
                            map.put("loginName", etPhone.getText().toString());
                            map.put("code", 1008);
                            map.put("password", edPassword.getText().toString());
                            registRequestModel.Regist(map);
                        } else {
                            RxToast.error("两次输入的密码不一致");
                        }
                    } else {
                        RxToast.error("请输入密码");
                    }
                } else {
                    RxToast.error("请输入正确的手机号");
                }
                break;
        }
    }
}