package com.vinpin.commonutils;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * <pre>
 *     author: vinpin
 *     time  : 2018/04/11 16:31
 *     desc  : 设置状态栏的颜色及黑白字模式
 *             1.Android4.4以上才能修改状态栏的颜色；
 *             2.小米MIUI V6以上、魅族 Flyme 4.0以上、Android6.0以上才能把状态栏文字和图标换成深色(黑字模式)。
 * </pre>
 */
public class StatusBarUtils {

    private static int calculateStatusBarColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    private static boolean isLightMode(int color) {
        // 把颜色转换成灰度值
        int blue = Color.blue(color);
        int green = Color.green(color);
        int red = Color.red(color);
        int toGrey = (red * 38 + green * 75 + blue * 15) >> 7;
        return toGrey > 225;
    }

    /**
     * 设置状态栏的颜色
     *
     * @param statusColor 颜色值
     * @param alpha       透明度 0 - 255
     */
    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusColor, int alpha) {
        setStatusBarColor(activity, calculateStatusBarColor(statusColor, alpha));
    }

    /**
     * 设置状态栏的颜色
     *
     * @param statusColor 颜色值
     */
    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusColor) {
        setStatusBarColor(activity, statusColor, isLightMode(statusColor));
    }

    /**
     * 设置状态栏的颜色
     *
     * @param statusColor 颜色值
     * @param dark        是否把状态栏字体及图标颜色设置为深色
     */
    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusColor, boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.setStatusBarColor(activity, statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.setStatusBarColor(activity, statusColor);
        }
        setStatusBarLightMode(activity, dark);
    }

    public static void setStatusBarColorForCollapsingToolbar(@NonNull Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout,
                                                             Toolbar toolbar, @ColorInt int statusColor) {
        setStatusBarColorForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor, isLightMode(statusColor));
    }

    public static void setStatusBarColorForCollapsingToolbar(@NonNull Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout,
                                                             Toolbar toolbar, @ColorInt int statusColor, boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.setStatusBarColorForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.setStatusBarColorForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
        }
        setStatusBarLightMode(activity, dark);
    }

    public static void translucentStatusBar(@NonNull Activity activity) {
        translucentStatusBar(activity, false);
    }

    /**
     * 设置成透明状态栏
     *
     * @param hideStatusBarBackground hide status bar alpha Background when SDK > 21, true if hide it
     */
    public static void translucentStatusBar(@NonNull Activity activity, boolean hideStatusBarBackground) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.translucentStatusBar(activity, hideStatusBarBackground);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.translucentStatusBar(activity);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static class StatusBarCompatKitKat {

        private static final String TAG_FAKE_STATUS_BAR_VIEW = "statusBarView";
        private static final String TAG_MARGIN_ADDED = "marginAdded";

        /**
         * return statusBar's Height in pixels
         */
        private static int getStatusBarHeight(Context context) {
            int result = 0;
            int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resId > 0) {
                result = context.getResources().getDimensionPixelOffset(resId);
            }
            return result;
        }

        /**
         * 1. Add fake statusBarView.
         * 2. set tag to statusBarView.
         */
        private static View addFakeStatusBarView(Activity activity, int statusBarColor, int statusBarHeight) {
            Window window = activity.getWindow();
            ViewGroup mDecorView = (ViewGroup) window.getDecorView();

            View mStatusBarView = new View(activity);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            layoutParams.gravity = Gravity.TOP;
            mStatusBarView.setLayoutParams(layoutParams);
            mStatusBarView.setBackgroundColor(statusBarColor);
            mStatusBarView.setTag(TAG_FAKE_STATUS_BAR_VIEW);

            mDecorView.addView(mStatusBarView);
            return mStatusBarView;
        }

        /**
         * use reserved order to remove is more quickly.
         */
        private static void removeFakeStatusBarViewIfExist(Activity activity) {
            Window window = activity.getWindow();
            ViewGroup mDecorView = (ViewGroup) window.getDecorView();

            View fakeView = mDecorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
            if (fakeView != null) {
                mDecorView.removeView(fakeView);
            }
        }

        /**
         * add marginTop to simulate set FitsSystemWindow true
         */
        private static void addMarginTopToContentChild(View mContentChild, int statusBarHeight) {
            if (mContentChild == null) {
                return;
            }
            if (!TAG_MARGIN_ADDED.equals(mContentChild.getTag())) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
                lp.topMargin += statusBarHeight;
                mContentChild.setLayoutParams(lp);
                mContentChild.setTag(TAG_MARGIN_ADDED);
            }
        }

        /**
         * remove marginTop to simulate set FitsSystemWindow false
         */
        private static void removeMarginTopOfContentChild(View mContentChild, int statusBarHeight) {
            if (mContentChild == null) {
                return;
            }
            if (TAG_MARGIN_ADDED.equals(mContentChild.getTag())) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
                lp.topMargin -= statusBarHeight;
                mContentChild.setLayoutParams(lp);
                mContentChild.setTag(null);
            }
        }

        /**
         * set StatusBarColor
         * <p>
         * 1. set Window Flag : WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
         * 2. removeFakeStatusBarViewIfExist
         * 3. addFakeStatusBarView
         * 4. addMarginTopToContentChild
         * 5. cancel ContentChild's fitsSystemWindow
         */
        static void setStatusBarColor(Activity activity, int statusColor) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
            View mContentChild = mContentView.getChildAt(0);
            int statusBarHeight = getStatusBarHeight(activity);

            removeFakeStatusBarViewIfExist(activity);
            addFakeStatusBarView(activity, statusColor, statusBarHeight);
            addMarginTopToContentChild(mContentChild, statusBarHeight);

            if (mContentChild != null) {
                mContentChild.setFitsSystemWindows(false);
            }
        }

        /**
         * translucentStatusBar
         * <p>
         * 1. set Window Flag : WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
         * 2. removeFakeStatusBarViewIfExist
         * 3. removeMarginTopOfContentChild
         * 4. cancel ContentChild's fitsSystemWindow
         */
        static void translucentStatusBar(Activity activity) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
            View mContentChild = mContentView.getChildAt(0);

            removeFakeStatusBarViewIfExist(activity);
            removeMarginTopOfContentChild(mContentChild, getStatusBarHeight(activity));
            if (mContentChild != null) {
                mContentChild.setFitsSystemWindows(false);
            }
        }

        /**
         * compat for CollapsingToolbarLayout
         * <p>
         * 1. set Window Flag : WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
         * 2. set FitsSystemWindows for views.
         * 3. add Toolbar's height, let it layout from top, then add paddingTop to layout normal.
         * 4. removeFakeStatusBarViewIfExist
         * 5. removeMarginTopOfContentChild
         * 6. add OnOffsetChangedListener to change statusBarView's alpha
         */
        static void setStatusBarColorForCollapsingToolbar(Activity activity, final AppBarLayout appBarLayout, final CollapsingToolbarLayout collapsingToolbarLayout,
                                                          Toolbar toolbar, int statusColor) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);

            View mContentChild = mContentView.getChildAt(0);
            mContentChild.setFitsSystemWindows(false);
            ((View) appBarLayout.getParent()).setFitsSystemWindows(false);
            appBarLayout.setFitsSystemWindows(false);
            collapsingToolbarLayout.setFitsSystemWindows(false);
            collapsingToolbarLayout.getChildAt(0).setFitsSystemWindows(false);

            toolbar.setFitsSystemWindows(false);
            if (toolbar.getTag() == null) {
                CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
                int statusBarHeight = getStatusBarHeight(activity);
                lp.height += statusBarHeight;
                toolbar.setLayoutParams(lp);
                toolbar.setPadding(toolbar.getPaddingLeft(), toolbar.getPaddingTop() + statusBarHeight, toolbar.getPaddingRight(), toolbar.getPaddingBottom());
                toolbar.setTag(true);
            }

            int statusBarHeight = getStatusBarHeight(activity);
            removeFakeStatusBarViewIfExist(activity);
            removeMarginTopOfContentChild(mContentChild, statusBarHeight);
            final View statusView = addFakeStatusBarView(activity, statusColor, statusBarHeight);

            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
            if (behavior != null && behavior instanceof AppBarLayout.Behavior) {
                int verticalOffset = ((AppBarLayout.Behavior) behavior).getTopAndBottomOffset();
                if (Math.abs(verticalOffset) > appBarLayout.getHeight() - collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
                    statusView.setAlpha(1f);
                } else {
                    statusView.setAlpha(0f);
                }
            } else {
                statusView.setAlpha(0f);
            }

            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (Math.abs(verticalOffset) > appBarLayout.getHeight() - collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
                        if (statusView.getAlpha() == 0) {
                            statusView.animate().cancel();
                            statusView.animate().alpha(1f).setDuration(collapsingToolbarLayout.getScrimAnimationDuration()).start();
                        }
                    } else {
                        if (statusView.getAlpha() == 1) {
                            statusView.animate().cancel();
                            statusView.animate().alpha(0f).setDuration(collapsingToolbarLayout.getScrimAnimationDuration()).start();
                        }
                    }
                }
            });
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static class StatusBarCompatLollipop {

        /**
         * return statusBar's Height in pixels
         */
        private static int getStatusBarHeight(Context context) {
            int result = 0;
            int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resId > 0) {
                result = context.getResources().getDimensionPixelOffset(resId);
            }
            return result;
        }

        /**
         * set StatusBarColor
         * <p>
         * 1. set Flags to call setStatusBarColor
         * 2. call setSystemUiVisibility to clear translucentStatusBar's Flag.
         * 3. set FitsSystemWindows to false
         */
        static void setStatusBarColor(Activity activity, int statusColor) {
            Window window = activity.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(statusColor);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

            ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                mChildView.setFitsSystemWindows(false);
                ViewCompat.requestApplyInsets(mChildView);
            }
        }

        /**
         * translucentStatusBar(full-screen)
         * <p>
         * 1. set Flags to full-screen
         * 2. set FitsSystemWindows to false
         *
         * @param hideStatusBarBackground hide statusBar's shadow
         */
        static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
            Window window = activity.getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (hideStatusBarBackground) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }

            ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                mChildView.setFitsSystemWindows(false);
                ViewCompat.requestApplyInsets(mChildView);
            }
        }

        /**
         * compat for CollapsingToolbarLayout
         * <p>
         * 1. change to full-screen mode(like translucentStatusBar).
         * 2. cancel CollapsingToolbarLayout's WindowInsets, let it layout as normal(now setStatusBarScrimColor is useless).
         * 3. set View's FitsSystemWindow to false.
         * 4. add Toolbar's height, let it layout from top, then add paddingTop to layout normal.
         * 5. change statusBarColor by AppBarLayout's offset.
         * 6. add Listener to change statusBarColor
         */
        static void setStatusBarColorForCollapsingToolbar(Activity activity, final AppBarLayout appBarLayout, final CollapsingToolbarLayout collapsingToolbarLayout,
                                                          Toolbar toolbar, final int statusColor) {
            final Window window = activity.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

            ViewCompat.setOnApplyWindowInsetsListener(collapsingToolbarLayout, new OnApplyWindowInsetsListener() {
                @Override
                public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                    return insets;
                }
            });

            ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                mChildView.setFitsSystemWindows(false);
                ViewCompat.requestApplyInsets(mChildView);
            }

            ((View) appBarLayout.getParent()).setFitsSystemWindows(false);
            appBarLayout.setFitsSystemWindows(false);

            toolbar.setFitsSystemWindows(false);
            if (toolbar.getTag() == null) {
                CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
                int statusBarHeight = getStatusBarHeight(activity);
                lp.height += statusBarHeight;
                toolbar.setLayoutParams(lp);
                toolbar.setPadding(toolbar.getPaddingLeft(), toolbar.getPaddingTop() + statusBarHeight, toolbar.getPaddingRight(), toolbar.getPaddingBottom());
                toolbar.setTag(true);
            }

            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
            if (behavior != null && behavior instanceof AppBarLayout.Behavior) {
                int verticalOffset = ((AppBarLayout.Behavior) behavior).getTopAndBottomOffset();
                if (Math.abs(verticalOffset) > appBarLayout.getHeight() - collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
                    window.setStatusBarColor(statusColor);
                } else {
                    window.setStatusBarColor(Color.TRANSPARENT);
                }
            } else {
                window.setStatusBarColor(Color.TRANSPARENT);
            }

            collapsingToolbarLayout.setFitsSystemWindows(false);
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (Math.abs(verticalOffset) > appBarLayout.getHeight() - collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
                        if (window.getStatusBarColor() != statusColor) {
                            startColorAnimation(window.getStatusBarColor(), statusColor, collapsingToolbarLayout.getScrimAnimationDuration(), window);
                        }
                    } else {
                        if (window.getStatusBarColor() != Color.TRANSPARENT) {
                            startColorAnimation(window.getStatusBarColor(), Color.TRANSPARENT, collapsingToolbarLayout.getScrimAnimationDuration(), window);
                        }
                    }
                }
            });
            collapsingToolbarLayout.getChildAt(0).setFitsSystemWindows(false);
            collapsingToolbarLayout.setStatusBarScrimColor(statusColor);
        }

        /**
         * use ValueAnimator to change statusBarColor when using collapsingToolbarLayout
         */
        static void startColorAnimation(int startColor, int endColor, long duration, final Window window) {
            if (sAnimator != null) {
                sAnimator.cancel();
            }
            sAnimator = ValueAnimator.ofArgb(startColor, endColor)
                    .setDuration(duration);
            sAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    if (window != null) {
                        window.setStatusBarColor((Integer) valueAnimator.getAnimatedValue());
                    }
                }
            });
            sAnimator.start();
        }

        private static ValueAnimator sAnimator;
    }

    /**
     * 设置亮色状态栏模式
     *
     * @param dark 是否把状态栏字体及图标颜色设置为深色(黑字)
     */
    public static void setStatusBarLightMode(@NonNull Activity activity, boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setLightStatusBarForM(activity, dark);
        } else if (isMIUI()) {
            setStatusBarLightModeForMIUI(activity, dark);
        } else if (isMeizu()) {
            setStatusBarLightModeForFlyme(activity, dark);
        }
    }

    private static boolean isMIUI() {
        FileInputStream is = null;
        try {
            is = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
            Properties prop = new Properties();
            prop.load(is);
            return prop.getProperty("ro.miui.ui.version.code") != null
                    || prop.getProperty("ro.miui.ui.version.name") != null
                    || prop.getProperty("ro.miui.internal.storage") != null;
        } catch (final IOException e) {
            return false;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // ignore all exception
                }
            }
        }
    }

    private static boolean isMeizu() {
        return Build.DISPLAY.startsWith("Flyme");
    }

    /**
     * 需要Flyme4.0以上
     *
     * @param dark 是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean setStatusBarLightModeForFlyme(@NonNull Activity activity, boolean dark) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 需要MIUIV6以上
     *
     * @param dark 是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean setStatusBarLightModeForMIUI(@NonNull Activity activity, boolean dark) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                @SuppressLint("PrivateApi")
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                int darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                extraFlagField.invoke(window, dark ? darkModeFlag : 0, darkModeFlag);
                result = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Android6.0设置亮色状态栏模式
     *
     * @param dark 是否把状态栏字体及图标颜色设置为深色
     */
    @TargetApi(Build.VERSION_CODES.M)
    private static void setLightStatusBarForM(@NonNull Activity activity, boolean dark) {
        Window window = activity.getWindow();
        if (window != null) {
            View decor = window.getDecorView();
            int ui = decor.getSystemUiVisibility();
            if (dark) {
                ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decor.setSystemUiVisibility(ui);
        }
    }
}
