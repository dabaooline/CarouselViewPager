package com.baoli;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.lang.ref.WeakReference;

/**
 * Created by baoli on 2016/3/17 10:16.
 * 由baoli创建于2016/3/17 10:16,非本人修改,请在此备注,给予提示.
 */
public class CarouselViewPager extends ViewPager {


    private ViewPagerHandler<CarouselViewPager> handler;

    public CarouselViewPager(Context context) {
        this(context, null);
    }

    public CarouselViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void startCarousel() {

        if (getAdapter() != null) {
            handler = new ViewPagerHandler<>(getContext().getMainLooper(), new WeakReference<CarouselViewPager>(this));
            handler.sendEmptyMessageAtTime(ViewPagerHandler.MSG_PAGE_FIRST, 10);//之所以有个延迟,是为了给系统留点时间
        } else {
            throw new RuntimeException("sorry,please set up Adapter ");
        }
    }

    public void stopCarousel() {
        handler.sendEmptyMessage(ViewPagerHandler.MSG_KEEP_SILENT);
    }

    public void continueCarousel() {
        handler.sendEmptyMessage(ViewPagerHandler.MSG_BREAK_SILENT);
    }



    public static class ViewPagerHandler <T extends CarouselViewPager> extends Handler {
        /**
         * 请求更新显示的View。
         */
        protected static final int MSG_UPDATE_IMAGE  = 1;
        /**
         * 请求暂停轮播。
         */
        protected static final int MSG_KEEP_SILENT   = 2;
        /**
         * 请求恢复轮播。
         */
        protected static final int MSG_BREAK_SILENT  = 3;
        /**
         * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
         * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
         * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
         */
        protected static final int MSG_PAGE_FIRST  = 4;
        //轮播间隔时间
        protected static final long MSG_DELAY = 1500;
        //使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
        private CarouselViewPager mViewPager;
        private int currentItem = 1000;
        public ViewPagerHandler(Looper looper,WeakReference<T> viewPager){
            super(looper);
            mViewPager = viewPager.get();
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (mViewPager==null){
                //Activity已经回收，无需再处理UI了
                return ;
            }
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (hasMessages(MSG_UPDATE_IMAGE)){
                removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currentItem = mViewPager.getCurrentItem();
                    mViewPager.setCurrentItem(++currentItem);
                    //准备下次播放
                    sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    //只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT:
                    sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_FIRST:
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    mViewPager.setCurrentItem(currentItem);
                    sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,10);
                    break;
                default:
                    break;
            }
        }
    }
}
