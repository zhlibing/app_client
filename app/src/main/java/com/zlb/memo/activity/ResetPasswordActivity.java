package com.zlb.memo.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zlb.memo.R;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.utils.SoftHideKeyBoardUtil;
import com.zlb.memo.utils.StatusBarUtil;
import com.zlb.memo.widgets.ValidCodeButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 忘记密码
 * Created by wp on 2017/3/28.
 */

public class ResetPasswordActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.ed_verify_code)
    EditText edVerifyCode;
    @BindView(R.id.bt_code)
    ValidCodeButton btCode;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.resetpwd_btn)
    Button resetPassword;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_resetpwd);
        ButterKnife.bind(this);
        initView();
        SoftHideKeyBoardUtil.assistActivity(this);
    }

    @Override
    public void showIsEmpty() {

    }

    private void initView() {
    }
}
