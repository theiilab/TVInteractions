package com.yuanren.tvinteractions.view.base;

import androidx.leanback.widget.FocusHighlight;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.PresenterSelector;
import androidx.leanback.widget.RowPresenter;

public class RowPresenterSelector extends PresenterSelector {
    private ListRowPresenter listRowPresenter;
    private ListRowPresenter mShadowEnabledRowPresenter;
    private ListRowPresenter mShadowDisabledRowPresenter;

    public RowPresenterSelector() {
        listRowPresenter = new ListRowPresenter(FocusHighlight.ZOOM_FACTOR_LARGE, false);
        listRowPresenter.setSelectEffectEnabled(false);
        listRowPresenter.setShadowEnabled(true);

        mShadowEnabledRowPresenter = listRowPresenter;
        mShadowEnabledRowPresenter.setNumRows(1);
        mShadowDisabledRowPresenter = listRowPresenter;
        mShadowDisabledRowPresenter.setShadowEnabled(true);
    }

    @Override
    public Presenter getPresenter(Object item) {
        return item instanceof ListRow ? mShadowDisabledRowPresenter : mShadowDisabledRowPresenter;
    }

    @Override
    public Presenter[] getPresenters() {
        Presenter[] presenters = new Presenter[] {mShadowDisabledRowPresenter, mShadowEnabledRowPresenter};
        return presenters;
    }
}
