package com.vinpin.commonutils;

import android.app.Activity;

import java.util.Stack;

/**
 * activity堆栈式管理
 *
 * @author zwp
 * create at 2017/8/15 10:00
 */
public final class AppManager {

    private static Stack<Activity> activityStack;
    private static volatile AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getInstance() {
        if (instance == null) {
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new AppManager();
                }
                if (activityStack == null) {
                    activityStack = new Stack<>();
                }
            }
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            return;
        }
        activityStack.add(activity);
    }

    /**
     * 移除Activity出堆栈
     */
    public void removeActivity(Activity activity) {
        if (activityStack == null) {
            return;
        }
        activityStack.remove(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack == null) {
            return null;
        }
        if (activityStack.isEmpty()) {
            return null;
        }
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = currentActivity();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            removeActivity(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack == null) {
            return;
        }
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 是否包含指定的activity
     */
    public boolean hasActivity(Class<?> cls) {
        if (activityStack == null) {
            return false;
        }
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 结束指定类名的所有Activity
     */
    public void finishActivityOfClass(Class<?> cls) {
        if (cls == null || activityStack == null || activityStack.size() == 0) {
            return;
        }
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            if (activityStack.get(i).getClass().equals(cls)) {
                finishActivity(activityStack.get(i));
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack == null) {
            return;
        }
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束所有Activity,除了指定的Activity
     */
    public void finishActivitysExceptAssign(Class<?> cls) {
        if (activityStack == null || cls == null) {
            return;
        }
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (null != activity && !(activity.getClass().equals(cls))) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Activity数量
     */
    public int getActivityCount() {
        return activityStack != null ? activityStack.size() : 0;
    }
}