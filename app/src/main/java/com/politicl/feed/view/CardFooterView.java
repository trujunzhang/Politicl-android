package com.politicl.feed.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.politicl.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardFooterView extends RelativeLayout {
    @BindView(R.id.view_card_footer_text) TextView textView;

    public CardFooterView(Context context) {
        super(context);

        inflate(getContext(), R.layout.view_card_footer, this);
        ButterKnife.bind(this);
    }

    public CardFooterView setText(@Nullable CharSequence text) {
        textView.setText(text);
        return this;
    }
}