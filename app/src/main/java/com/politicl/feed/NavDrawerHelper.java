package com.politicl.feed;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.politicl.MainActivity;
import com.politicl.PoliticlApp;
import com.politicl.R;
import com.politicl.analytics.NavMenuFunnel;
import com.politicl.feed.category.CategoryClient;
import com.politicl.feed.category.MenuCategoryItem;
import com.politicl.feed.dataclient.FeedClient;
import com.politicl.feed.model.Card;
import com.politicl.util.ReleaseUtil;

import java.util.List;


public class NavDrawerHelper {

    private final PoliticlApp app = PoliticlApp.getInstance();
    private final MainActivity activity;
    private NavMenuFunnel funnel;
    private TextView accountNameView;
    private ImageView accountNameArrow;
    private boolean accountToggle;
    private boolean isTempExplicitHighlight;

    public NavDrawerHelper(@NonNull MainActivity activity, View navDrawerHeader) {
        this.funnel = new NavMenuFunnel();
        this.activity = activity;
        activity.getSupportFragmentManager()
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        updateItemSelection(NavDrawerHelper.this.activity.getTopFragment());
                    }
                });
//        accountNameView = (TextView) navDrawerHeader.findViewById(R.id.nav_account_text);
//        accountNameArrow = (ImageView) navDrawerHeader.findViewById(R.id.nav_account_arrow);
//        updateMenuGroupToggle();

        new CategoryClient().request(activity.getApplicationContext(), PoliticlApp.getInstance().getSite(), -1, new FeedClient.Callback() {
            @Override
            public void success(@NonNull List<? extends Card> cards) {

            }

            @Override
            public void successForMenu(@NonNull List<MenuCategoryItem> cards) {

            }

            @Override
            public void error(@NonNull Throwable caught) {

            }
        });

    }

    public NavMenuFunnel getFunnel() {
        return funnel;
    }

    public void setupDynamicNavDrawerItems() {
        accountToggle = false;
        updateMenuGroupToggle();

        if (!ReleaseUtil.isDevRelease()) {
//            activity.getNavMenu().findItem(R.id.nav_item_feed).setVisible(false);
        }
    }

    public NavigationView.OnNavigationItemSelectedListener getNewListener() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                clearItemHighlighting();
                menuItem.setChecked(true);
                activity.setNavItemSelected(true);
                return true;
            }
        };
    }

    public void updateItemSelection(@Nullable Fragment fragment) {
//        @IdRes Integer id = fragment == null ? null : fragmentToMenuId(fragment.getClass());
//        if (id != null && !isTempExplicitHighlight) {
//            setMenuItemSelection(id);
//        }
    }

    public void setTempExplicitHighlight(Class<? extends Fragment> fragmentClass) {
//        @IdRes Integer id = fragmentToMenuId(fragmentClass);
//        if (id != null) {
//            setMenuItemSelection(id);
//        }
//        isTempExplicitHighlight = true;
    }

    public void clearTempExplicitHighlight() {
        isTempExplicitHighlight = false;
    }

    private void setMenuItemSelection(@IdRes int id) {
        clearItemHighlighting();
        activity.getNavMenu().findItem(id).setChecked(true);
    }

    private void toggleAccountMenu() {
        accountToggle = !accountToggle;
        updateMenuGroupToggle();
    }

    private void updateMenuGroupToggle() {
//        activity.getNavMenu().setGroupVisible(R.id.group_main, !accountToggle);
//        activity.getNavMenu().setGroupVisible(R.id.group_user, accountToggle);
//        accountNameArrow.setVisibility(app.getUserInfoStorage().isLoggedIn() ? View.VISIBLE : View.INVISIBLE);
//        accountNameArrow.setImageResource(accountToggle ? R.drawable.ic_arrow_drop_up_white_24dp
//                : R.drawable.ic_arrow_drop_down_white_24dp);
    }


    /**
     * Un-highlight all nav menu entries.
     */
    private void clearItemHighlighting() {
        for (int i = 0; i < activity.getNavMenu().size(); i++) {
            activity.getNavMenu().getItem(i).setChecked(false);
        }
    }

    private void startActivity(@NonNull Intent intent) {
        activity.closeNavDrawer();
        activity.startActivity(intent);
    }

    private void startActivityForResult(@NonNull Intent intent, int reqCode) {
        activity.closeNavDrawer();
        activity.startActivityForResult(intent, reqCode);
    }


}
