package com.stanleycen.facepunch.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.event.LoginResponseEvent;
import com.stanleycen.facepunch.request.LoginRequest;
import com.stanleycen.facepunch.util.API;
import com.stanleycen.facepunch.util.Util;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {
    SystemBarTintManager tintManager;
    RotateAnimation logoAnimation;
    boolean showAnimation = false;

    @ViewById
    EditText username;

    @ViewById
    EditText password;

    @ViewById
    Button loginButton;

    @ViewById
    ImageView facepunchLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (API.isLoggedIn()) {
            gotoMainActivity();
            return;
        }

        if (Util.isKitKat()) {
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.facepunch_red);
        }

        logoAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        logoAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        logoAnimation.setDuration(750);
        logoAnimation.setFillEnabled(true);
        logoAnimation.setFillAfter(true);
        logoAnimation.setRepeatCount(Animation.INFINITE);
        logoAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                facepunchLogo.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (!showAnimation) animation.cancel();
            }
        });

        EventBus.getDefault().register(this);
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
        finish();
    }

    void updateForm(boolean enabled) {
        username.setEnabled(enabled);
        password.setEnabled(enabled);
        loginButton.setEnabled(enabled);
    }

    @Click
    void loginButton() {
        facepunchLogo.startAnimation(logoAnimation);
        updateForm(false);
        showAnimation = true;
        username.setError(null);
        password.setError(null);

        LoginRequest req = new LoginRequest(username.getText().toString(), password.getText().toString());
        API.queue.add(req);
    }

    public void onEventMainThread(LoginResponseEvent loginResponseEvent) {
        updateForm(true);
        showAnimation = false;

        if (loginResponseEvent.success) {
            if (API.isLoggedIn()) {
                gotoMainActivity();
            }
            else {
                username.setError("Invalid credentials");
                password.setError("Invalid credentials");
            }
        }
        else {
            Util.toast(this, "Error connecting to Facepunch");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
