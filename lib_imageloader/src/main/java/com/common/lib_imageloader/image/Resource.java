package com.common.lib_imageloader.image;

import androidx.annotation.DrawableRes;

import java.io.Serializable;

public class Resource implements Serializable {
    public int placeholder;
    public int error;

    public int getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(@DrawableRes int placeholder) {
        this.placeholder = placeholder;
    }

    public int getError() {
        return error;
    }

    public void setError(@DrawableRes int error) {
        this.error = error;
    }
}
