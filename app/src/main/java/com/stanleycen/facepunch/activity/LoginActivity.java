package com.stanleycen.facepunch.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import de.greenrobot.event.EventBus;

public class LoginActivity extends Activity {
    SystemBarTintManager tintManager;
    RotateAnimation logoAnimation;
    boolean showAnimation = false;

    EditText username;
    EditText password;
    Button loginButton;
    ImageView facepunchLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (API.isLoggedIn()) {
            gotoMainActivity();
            return;
        }

        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);
        facepunchLogo = (ImageView) findViewById(R.id.facepunchLogo);

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

        Util.eventBusRegister(this);
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    void updateForm(boolean enabled) {
        username.setEnabled(enabled);
        password.setEnabled(enabled);
        loginButton.setEnabled(enabled);
    }

    public void onLoginButtonClick(View v) {
        facepunchLogo.startAnimation(logoAnimation);
        updateForm(false);
        showAnimation = true;
        username.setError(null);
        password.setError(null);

        LoginRequest req = new LoginRequest(username.getText().toString(), password.getText().toString());
        API.addToQueue(req);
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

        Util.eventBusUnregister(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
