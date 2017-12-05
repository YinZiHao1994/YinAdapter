package yin.source.com.yinadapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by yin on 2017/12/4.
 */

public class SideMenuLayout extends FrameLayout {

    private ViewDragHelper viewDragHelper;
    private int menuStartLeft;
    private int menuWidth;
    private OnMenuClickListener onMenuClickListener;

    //触发菜单打开的比例，比如当用户划开菜单总长度的0.3之后松手菜单自动继续打开
    private float menuTrigger = 0.3f;

    //内容
    private View contentView;
    //菜单
    private View menu;

    private MenuState menuState = MenuState.close;

    public enum MenuState {
        open, close;
    }

    public SideMenuLayout(Context context) {
        this(context, null);
    }

    public SideMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideMenuLayout(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SideMenuLayout,
                defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SideMenuLayout_menu_trigger) {
                float aFloat = a.getFloat(attr, -1);
                if (aFloat != -1 && aFloat > 0) {
                    menuTrigger = aFloat;
                }
            }
        }
        a.recycle();

        viewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
//                Log.d("yzh", "left = " + left + "\ndx = " + dx);
                if (left > 0) {
                    return 0;
                } else if (left < -menuWidth) {
                    return -menuWidth;
                }
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return super.clampViewPositionVertical(child, top, dy);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                if (changedView == contentView) {
                    menu.offsetLeftAndRight(dx);
                }
                invalidate();
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
//                Log.d("yzh", "xvel = " + xvel + "\nview.getLeft() = " + menu.getLeft() + "\nmenuStartLeft = " + menuStartLeft);
                if (xvel < 0) {
                    open();
                } else if (xvel > 0) {
                    close();
                } else if (menuState == MenuState.close && menuStartLeft - menu.getLeft() > menu.getMeasuredWidth() * menuTrigger) {
                    open();
                } else {
                    close();
                }
            }
        });
    }

    private void close() {
        if (viewDragHelper.smoothSlideViewTo(contentView, 0, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        if (menuState == MenuState.open) {
            menuState = MenuState.close;
        }
    }

    private void open() {
        if (viewDragHelper.smoothSlideViewTo(contentView, 0 - menuWidth, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        if (menuState == MenuState.close) {
            menuState = MenuState.open;
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // 开始执行动画
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev);
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();

        if (childCount > 0) {
            contentView = getChildAt(0);
        }
        if (childCount > 1) {
            menu = getChildAt(1);
            menu.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMenuClickListener != null) {
                        onMenuClickListener.onMenuClickListener();
                    }
                }
            });
        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        Log.d("yzh", "left = " + left + "\ttop = " + top + "\tright = " + right + "\tbottom = " + bottom);
        if (contentView != null) {
            menuStartLeft = contentView.getMeasuredWidth();
        }
        if (menu != null) {
            menuWidth = menu.getMeasuredWidth();
            menu.layout(menuStartLeft, 0, menuStartLeft + menuWidth, menu.getBottom());
        }

    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    public interface OnMenuClickListener {
        void onMenuClickListener();
    }

    public void openMenu() {
        if (menuState == MenuState.close) {
            open();
        }
    }

    public void closeMenu() {
        if (menuState == MenuState.open) {
            close();
        }
    }
}
