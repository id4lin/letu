package com.letu.app.game.strategy.ui.common.view;

/**
 * Created by ${user} on 2018/7/16
 */
//public class DrawableTextView extends TextView {
//    public DrawableTextView(Context context) {
//        this(context, null);
//    }
//
//    public DrawableTextView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        TypedArray appearance = context.obtainStyledAttributes(attrs,
//                R.styleable.TabItem, defStyleAttr, 0);
//        if (appearance != null) {
//            int textColor = 0;
//            int drawableColor = 0;
//            int n = appearance.getIndexCount();
//            for (int i = 0; i < n; i++) {
//                int attr = appearance.getIndex(i);
//                switch (attr) {
//                    case R.styleable.TabItem_tinColor:
//                        mTintColor = appearance.getColor(attr, 0);
//                        setTintColor(tintColor);
//                        break;
//                    case R.styleable.TabItem_textColor:
//                        textColor = appearance.getColor(attr, textColor);
//                        setTextColor(textColor);
//                        break;
//                    case R.styleable.TabItem_drawableColor:
//                        drawableColor = appearance.getColor(attr, drawableColor);
//                        setDrawableColor(drawableColor);
//                        break;
//                }
//            }
//            appearance.recycle();
//        }
//    }
//
//    /**
//     * 文字和图片都上色
//     */
//    public void setTintColor(int color) {
//        setTextColor(color);
//        setDrawableColor(color);
//    }
//
//    /**
//     * 图片上色
//     */
//    public void setDrawableColor(int color) {
//        Drawable[] drawables = this.getCompoundDrawables();
//        for (int i = 0, size = drawables.length; i < size; i++) {
//            if (null != drawables[i]) {
//                drawables[i].setColorFilter(new PorterDuffColorFilter(color,
//                        PorterDuff.Mode.SRC_IN));
//            }
//        }
//    }
//}
