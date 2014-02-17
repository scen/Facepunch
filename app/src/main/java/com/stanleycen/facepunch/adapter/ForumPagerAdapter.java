package com.stanleycen.facepunch.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.stanleycen.facepunch.event.ActionBarTitleUpdateEvent;
import com.stanleycen.facepunch.event.PagerPosUpdate;
import com.stanleycen.facepunch.fragment.nested.ForumFragment;
import com.stanleycen.facepunch.model.ITitleable;

import java.util.HashMap;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
* Created by scen on 2/14/14.
*/
public class ForumPagerAdapter extends FragmentStatePagerAdapter {
    ViewPager pager;
    SparseArray<Fragment> fragmentMap;
    HashMap<Fragment, Integer> keyMap;
    public int count = 0;
    Fragment primary;

    @DebugLog
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment f = fragmentMap.get(position);
        keyMap.remove(f);
        super.destroyItem(container, position, object);
        fragmentMap.remove(position);
    }

    @DebugLog
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        primary = (Fragment) object;
    }

    @DebugLog
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment f = (Fragment) super.instantiateItem(container, position);
        fragmentMap.put(position, f);
        keyMap.put(f, position);
        return f;
    }

    public ForumPagerAdapter(FragmentManager manager, ViewPager pager) {
        super(manager);
        this.pager = pager;
        fragmentMap = new SparseArray<>();
        keyMap = new HashMap<>();
        pager.setAdapter(this);
        pager.setOnPageChangeListener(listener);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @DebugLog
    public void addPage() {
        count++;
        this.notifyDataSetChanged();
        pager.setCurrentItem(getCount() - 1);
    }

//    // @return true if it was removed, false otherwise
//    @DebugLog
//    public boolean removeLast() {
//        if (fragments.size() <= 1) return false;
//        pager.setCurrentItem(getCount() - 2);
//        return true;
//    }

    @DebugLog
    @Override
    public Fragment getItem(int i) {
        return ForumFragment.newInstance();
    }

    @DebugLog
    @Override
    public int getItemPosition(Object object) {
        if (keyMap.get(object) >= getCount()) {
            return POSITION_NONE;
        }
        return POSITION_UNCHANGED;
    }

    @Override
    public int getCount() {
        return count;
    }

    ViewPager.SimpleOnPageChangeListener listener = new ViewPager.SimpleOnPageChangeListener() {
        boolean pageChanged = false;
        int pos = 0;

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (pageChanged) {
                    if (pos < getCount() - 1) {
                        count--;
                        ForumPagerAdapter.this.notifyDataSetChanged();
                    }

                    // change nav drawer to up carat
                    EventBus.getDefault().post(new PagerPosUpdate(pos));
                    EventBus.getDefault().post(new ActionBarTitleUpdateEvent(((ITitleable) fragmentMap.get(pos)).getTitle()));
                    pageChanged = false;
                }
            }
            else {
                super.onPageScrollStateChanged(state);
            }
        }

        @Override
        @DebugLog
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            pageChanged = true;
            pos = position;
        }
    };
    static public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
