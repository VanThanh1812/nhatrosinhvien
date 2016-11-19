package com.mnetwork.app.nhatrosv.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.mnetwork.app.nhatrosv.R;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance("", "Chọn giá phù hợp", R.drawable.intro_first, Color.parseColor("#66000000")));
        addSlide(AppIntroFragment.newInstance("", "Xem thông tin", R.drawable.intro_two, Color.parseColor("#66000000")));
        addSlide(AppIntroFragment.newInstance("", "Oke thì nhấn gọi hoặc gửi tin nhắn ...", R.drawable.intro_three, Color.parseColor("#66000000")));

        addSlide(AppIntroFragment.newInstance("Welcome", "", R.drawable.ic_app, Color.parseColor("#66000000")));
        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#4dd0e1"));
        setSeparatorColor(Color.parseColor("#ffffff"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        goMainActivity();
    }

    private void goMainActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        goMainActivity();
    }
}
