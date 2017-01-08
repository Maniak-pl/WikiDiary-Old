package pl.maniak.wikidiary.utils.config;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResourceProviderImpl implements ResourceProvider {
    private final Context appContext;

    @Override
    public String getString(@StringRes int stringResId) {
        return appContext.getString(stringResId);
    }

    @Override
    public Drawable getDrawable(@DrawableRes int drawableResId) {
        return ContextCompat.getDrawable(appContext, drawableResId);
    }
}
