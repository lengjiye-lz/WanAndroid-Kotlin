package com.lengjiye.base;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;

public abstract class MActivity<T extends ViewDataBinding, M extends AndroidViewModel> extends FragmentActivity {

    private T t;
    private M m;

    public abstract M getM();

    public abstract T getT();
}
