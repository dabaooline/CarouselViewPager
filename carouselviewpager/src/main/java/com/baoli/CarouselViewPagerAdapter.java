package com.baoli;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.List;

/**
 * Created by baoli on 2016/3/17 13:58.
 * 由baoli创建于2016/3/17 13:58,非本人修改,请在此备注,给予提示.
 */
public class CarouselViewPagerAdapter extends PagerAdapter {
    private final List<View> mList;

    public CarouselViewPagerAdapter(List<View> list) {
        this.mList = list;
    }

    @Override
    public int getCount() {
//            return mList.size();
        return mList.size() > 0 ? Integer.MAX_VALUE : 0;

    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (mList.size() > 0) {
            position %= mList.size();
        }
        //对ViewPager页号求模取出View列表中要显示的项

        if (position < 0) {
            position = mList.size() + position;
        }

        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = mList.get(position).getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(mList.get(position));
        }
        container.addView(mList.get(position));
        //add listeners here if necessary
        return mList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       /* if (mList.size() > 0) {
            position %= mList.size();
        }
        container.removeView( mList.get(position));*/
    }
}
