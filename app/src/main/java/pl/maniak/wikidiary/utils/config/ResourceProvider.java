package pl.maniak.wikidiary.utils.config;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public interface ResourceProvider {

    String getString(@StringRes int stringResId);
    Drawable getDrawable(@DrawableRes int drawableResId);
    int getColor(@ColorRes int colorResId);
}
