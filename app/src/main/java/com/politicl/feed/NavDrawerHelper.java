package com.politicl.feed;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.view.menu.MenuBuilder;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.politicl.MainActivity;
import com.politicl.PoliticlApp;
import com.politicl.R;
import com.politicl.analytics.NavMenuFunnel;
import com.politicl.feed.aggregated.RestQueryPara;
import com.politicl.feed.category.CategoryClient;
import com.politicl.feed.category.MenuCategoryItem;
import com.politicl.feed.dataclient.FeedClient;
import com.politicl.feed.model.Card;
import com.politicl.util.ReleaseUtil;

import java.util.List;


public class NavDrawerHelper {

    public static final int GROUP_MENU_CATEGORIES = 104;

    private final PoliticlApp app = PoliticlApp.getInstance();
    private final MainActivity activity;
    private NavMenuFunnel funnel;
    private TextView accountNameView;
    private ImageView accountNameArrow;
    private boolean isTempExplicitHighlight;
    private MenuItem lastSelectedMenuItem;

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
        new CategoryClient().request(activity.getApplicationContext(), PoliticlApp.getInstance().getSite(), null, new FeedClient.Callback() {
            @Override
            public void success(@NonNull List<? extends Card> cards) {

            }

            @Override
            public void successForMenu(@NonNull List<MenuCategoryItem> cards) {
                setupDynamicNavDrawerItems(cards);
            }

            @Override
            public void error(@NonNull Throwable caught) {

            }
        });

    }

    public NavMenuFunnel getFunnel() {
        return funnel;
    }

    public void setupDynamicNavDrawerItems(List<MenuCategoryItem> cards) {
        int[] menuItemIds = {R.id.nav_item1, R.id.nav_item2, R.id.nav_item3, R.id.nav_item4, R.id.nav_item5, R.id.nav_item6, R.id.nav_item7, R.id.nav_item8, R.id.nav_item9, R.id.nav_item10};
        activity.getNavMenu().setGroupVisible(R.id.category_feed, true);
        int step = 0;
        for (MenuCategoryItem item : cards) {
            int menuItemId = menuItemIds[step++];
            MenuItem menuItem = activity.getNavMenu().findItem(menuItemId);
            menuItem.setVisible(true);
            menuItem.setTitle(item.title());
            menuItem.setIcon(R.drawable.ic_restore_black_24dp);
        }
    }

    public NavigationView.OnNavigationItemSelectedListener getNewListener() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (lastSelectedMenuItem != null) {
                    lastSelectedMenuItem.setChecked(false);
                }
                lastSelectedMenuItem = menuItem;
                activity.toggleFragment(menuItem.getItemId(), (String) menuItem.getTitle());
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
//        clearItemHighlighting();
        activity.getNavMenu().findItem(id).setChecked(true);
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
