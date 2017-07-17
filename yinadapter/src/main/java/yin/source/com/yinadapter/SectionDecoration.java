package yin.source.com.yinadapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yin on 2017/4/27.
 * 分组Decoration，类似联系人列表首字母的分组
 * 泛型T的意义见{@link SectionCallback}
 */

public class SectionDecoration<T> extends RecyclerView.ItemDecoration {

    private SectionCallback<T> sectionCallback;
    private Paint textPaint;
    private Paint sectionBackgroundPaint;
    private Context context;
    private int sectionDecorationHeight = 80;

    public SectionDecoration(Context context, SectionCallback<T> sectionCallback) {
        super();
        this.context = context;
        this.sectionCallback = sectionCallback;
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(40);
        sectionBackgroundPaint = new Paint();
        sectionBackgroundPaint.setColor(Color.LTGRAY);
    }

    public SectionDecoration(Context context, SectionCallback<T> sectionCallback, ConfigureCallback configureCallback) {
        this(context, sectionCallback);
        if (configureCallback != null) {

            Paint sectionBackgroundPaint = configureCallback.getSectionBackgroundPaint();
            if (sectionBackgroundPaint != null) {
                setSectionBackgroundPaint(sectionBackgroundPaint);
            }

            Paint textPaint = configureCallback.getTextPaint();
            if (textPaint != null) {
                setTextPaint(textPaint);
            }
            this.sectionDecorationHeight = dip2px(context, configureCallback.getSectionDecorationHeight());
        }

    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        boolean firstInGroup = isFirstInGroup(childAdapterPosition);
        if (firstInGroup) {
            outRect.set(0, sectionDecorationHeight, 0, 0);
        }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //注意，这里的count获取的是界面上显示的数量，本质上是显示在界面上的viewGroup所拥有的childView的数量
        int childCount = parent.getChildCount();
        int paddingLeft = parent.getPaddingLeft();
        for (int i = 0; i < childCount; i++) {
            View childViewInViewGroup = parent.getChildAt(i);
            //这里才获取了在adapter中的真正位置
            int childAdapterPosition = parent.getChildAdapterPosition(childViewInViewGroup);
            int childViewTop = childViewInViewGroup.getTop();

            if (isFirstInGroup(childAdapterPosition)) {
                String sectionTitle = sectionCallback.getSectionTitle(childAdapterPosition);
                c.drawRect(paddingLeft, childViewTop - sectionDecorationHeight,
                        parent.getWidth() - parent.getPaddingRight(), childViewTop, sectionBackgroundPaint);
                Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();

                c.drawText(sectionTitle, paddingLeft, childViewTop - sectionDecorationHeight / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2, textPaint);
            }
        }
    }


    private boolean isFirstInGroup(int position) {
        if (position == 0) {
            //第一条数据上一定有一个分组栏
            return true;
        }

        T preItemSign = sectionCallback.getSectionSign(position - 1);
        T sign = sectionCallback.getSectionSign(position);

        //如果当前数据与上一条数据的分类标志相同，则不是组中的第一项
        return !preItemSign.equals(sign);
    }


    public void setTextPaint(Paint textPaint) {
        textPaint.setAntiAlias(true);
        this.textPaint = textPaint;
    }

    public void setSectionBackgroundPaint(Paint sectionBackgroundPaint) {
        this.sectionBackgroundPaint = sectionBackgroundPaint;
    }

    public interface SectionCallback<T> {

        //返回每一条数据所属组别的标志，用于判断数据的分组
        T getSectionSign(int position);

        //返回用于显示在分组栏上的文字
        String getSectionTitle(int position);

    }

    public interface ConfigureCallback {
        //用于绘制分组栏文字的画笔
        Paint getTextPaint();

        //用于绘制分组栏北京的画笔
        Paint getSectionBackgroundPaint();

        //分组栏的高度，单位dp
        int getSectionDecorationHeight();
    }
}
