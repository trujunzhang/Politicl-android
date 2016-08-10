package com.politicl;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.politicl.feed.FeedFragment;
import com.politicl.feed.NavDrawerHelper;
import com.politicl.feed.aggregated.RestQueryPara;
import com.politicl.history.HistoryEntry;
import com.politicl.tooltip.ToolTipUtil;
import com.politicl.util.ShareUtil;

import org.apache.commons.lang3.ArrayUtils;

import static com.politicl.util.DeviceUtil.hideSoftKeyboard;
import static com.politicl.util.DeviceUtil.isBackKeyUp;

public class MainActivity extends AppCompatActivity implements FeedFragment.Callback {

    public static final String ALL_STORIES_TITLE = "Politicl";

    private View fragmentContainerView;
    private boolean navItemSelected;
    private Menu navMenu;
    private NavDrawerHelper navDrawerHelper;
    private ActionBarDrawerToggle mDrawerToggle;
    private View toolbarContainer;
    private MainActivityToolbarCoordinator toolbarCoordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FeedFragment) getTopFragment()).scrollToTop();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new MainDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        toolbarContainer = findViewById(R.id.main_toolbar_container);
        toolbarCoordinator = new MainActivityToolbarCoordinator(this, toolbarContainer, (Toolbar) findViewById(R.id.toolbar));

        getSupportFragmentManager()
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        updateToolbarForFragment();
                    }
                });

        NavigationView navDrawer = (NavigationView) findViewById(R.id.nav_view);
        navMenu = navDrawer.getMenu();
        navDrawerHelper = new NavDrawerHelper(this, navDrawer.getHeaderView(0));
        navDrawer.setNavigationItemSelectedListener(navDrawerHelper.getNewListener());

        fragmentContainerView = findViewById(R.id.content_fragment_container);

        if (savedInstanceState == null) {
            // if there's no savedInstanceState, and we're not coming back from a Theme change,
            // then we must have been launched with an Intent, so... handle it!
            handleIntent(getIntent());
        }
    }

    public Menu getNavMenu() {
        return navMenu;
    }

    public void setNavItemSelected(boolean wasSelected) {
        navItemSelected = wasSelected;
    }

    private boolean wasNavItemSelected() {
        return navItemSelected;
    }

    private void updateToolbarForFragment() {
        if (getTopFragment() instanceof MainActivityToolbarProvider) {
            toolbarCoordinator.setOverrideToolbar(((MainActivityToolbarProvider) getTopFragment()).getToolbar());
        } else {
            toolbarCoordinator.removeOverrideToolbar();
        }
    }

    @Override
    public void onBackPressed() {
        if (closeNavDrawer())
            return;
        if (popTopFragement())
            return;

        this.finish();

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//
//        }
    }

    public boolean closeNavDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Get the Fragment that is currently at the top of the Activity's backstack.
     * This activity's fragment container will hold multiple fragments stacked onto
     * each other using FragmentManager, and this function will return the current
     * topmost Fragment. It's up to the caller to cast the result to a more specific
     * fragment class, and perform actions on it.
     *
     * @return Fragment at the top of the backstack.
     */
    public Fragment getTopFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.content_fragment_container);
    }

    public void showFeed(RestQueryPara para) {
//        if (getTopFragment() instanceof FeedFragment) {
//            ((FeedFragment) getTopFragment()).scrollToTop();
//            resetFeedFragment((FeedFragment) getTopFragment(), para);
//        } else {
//            popTopFragmentsExcept(FeedFragment.class);
        pushFragment(FeedFragment.newInstance(para));
//        }
    }

    public void resetFeedFragment(FeedFragment f, RestQueryPara para) {
        closeNavDrawer();
        f.resetFeed(para);
    }

    private boolean popTopFragement() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) { // more than 1.
            getSupportFragmentManager().popBackStackImmediate();
            return true;
        }

        return false;
    }

    private void popTopFragmentsExcept(Class<?>... frags) {
        while (getSupportFragmentManager().getBackStackEntryCount() > 0
                && !ArrayUtils.contains(frags, getTopFragment().getClass())) {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    /**
     * Add a new fragment to the top of the activity's backstack.
     *
     * @param f New fragment to place on top.
     */
    public void pushFragment(Fragment f) {
        pushFragment(f, false);
    }


    private void beforeFragmentChanged() {
        closeNavDrawer();
    }

    /**
     * Add a new fragment to the top of the activity's backstack, and optionally  allow state loss.
     * Useful for cases where we might push a fragment from an AsyncTask result.
     *
     * @param f              New fragment to place on top.
     * @param allowStateLoss Whether to allow state loss.
     */
    public void pushFragment(Fragment f, boolean allowStateLoss) {
        beforeFragmentChanged();
        // if the new fragment is the same class as the current topmost fragment,
        // then just keep the previous fragment there.
        // e.g. if the user selected History, and there's already a History fragment on top,
        // then there's no need to load a new History fragment.
//        if (getTopFragment() != null && (getTopFragment().getClass() == f.getClass())) {
//            return;
//        }

//        if (getTopFragment() == null || (getTopFragment().getClass() != f.getClass())) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        trans.add(R.id.content_fragment_container, f);
        trans.addToBackStack(null);
        if (allowStateLoss) {
            trans.commitAllowingStateLoss();
        } else {
            trans.commit();
        }
//        }
        afterFragmentChanged();
    }

    private void afterFragmentChanged() {
    }


//    @NonNull
//    public static Intent newIntent(@NonNull Context context,
//                                   @NonNull HistoryEntry entry,
//                                   @NonNull PageTitle title) {
//        return new Intent(MainActivity.ACTION_PAGE_FOR_TITLE)
//                .setClass(context, MainActivity.class)
//                .putExtra(MainActivity.EXTRA_HISTORYENTRY, entry)
//                .putExtra(MainActivity.EXTRA_PAGETITLE, title);
//    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        showFeed(new RestQueryPara(ALL_STORIES_TITLE));
//        showFeed(new RestQueryPara(1, "Top Story"));
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    // Note: this method is invoked even when in CAB mode.
    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        return isBackKeyUp(event) && ToolTipUtil.dismissToolTip(this)
                || super.dispatchKeyEvent(event);
    }

    public void toggleFragment(int category_id, String title) {
        showFeed(new RestQueryPara(category_id, title));

    }


    private class MainDrawerToggle extends ActionBarDrawerToggle {
        private boolean oncePerSlideLock = false;

        MainDrawerToggle(android.app.Activity activity,
                         android.support.v4.widget.DrawerLayout drawerLayout,
                         int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            // if we want to change the title upon closing:
            //getSupportActionBar().setTitle("");
            if (!wasNavItemSelected()) {
                navDrawerHelper.getFunnel().logCancel();
            }
            navDrawerHelper.clearTempExplicitHighlight();
            setNavItemSelected(false);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            // if we want to change the title upon opening:
            //getSupportActionBar().setTitle("");
            // If we're in the search state, then get out of it.
//            if (isSearching()) {
//                searchFragment.closeSearch();
//            }
//            // also make sure we're not inside an action mode
//            if (isCabOpen()) {
//                finishActionMode();
//            }
            updateNavDrawerSelection(getTopFragment());
            navDrawerHelper.getFunnel().logOpen();
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            super.onDrawerSlide(drawerView, 0);
            if (!oncePerSlideLock) {
                // Hide the keyboard when the drawer is opened
                hideSoftKeyboard(MainActivity.this);
                //also make sure ToC is hidden
//                if (getCurPageFragment() != null) {
//                    getCurPageFragment().toggleToC(PageFragment.TOC_ACTION_HIDE);
//                }
                //and make sure to update dynamic items and highlights
//                navDrawerHelper.setupDynamicNavDrawerItems();
                oncePerSlideLock = true;
            }
            // and make sure the Toolbar is showing
//            showToolbar();
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            super.onDrawerStateChanged(newState);
            if (newState == DrawerLayout.STATE_IDLE) {
                oncePerSlideLock = false;
            }
        }
    }

    public void updateNavDrawerSelection(Fragment fragment) {
        navDrawerHelper.updateItemSelection(fragment);
    }


    @Override
    public void onFeedSelectPage(HistoryEntry entry) {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(entry.getTitle().getCustomSourceUrl()));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onFeedAddPageToList(HistoryEntry entry) {

    }

    @Override
    public void onFeedSharePage(HistoryEntry entry) {
        ShareUtil.shareText(this, entry.getTitle());
    }

}
