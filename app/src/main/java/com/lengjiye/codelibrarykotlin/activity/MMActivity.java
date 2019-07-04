package com.lengjiye.codelibrarykotlin.activity;

import androidx.databinding.ViewDataBinding;
import com.lengjiye.base.MActivity;
import com.lengjiye.base.viewmode.MViewMode;
import com.lengjiye.codelibrarykotlin.viewmode.BesselViewMode;

public class MMActivity<T extends ViewDataBinding, M extends MViewMode> extends MActivity<T, M> {

    @Override
    public M getM() {
        return new BesselViewMode(getApplication());
    }

    @Override
    public T getT() {
        return null;
    }

}
