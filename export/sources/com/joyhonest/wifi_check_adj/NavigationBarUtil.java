package com.joyhonest.wifi_check_adj;

import android.view.View;
import android.view.Window;

/* JADX INFO: loaded from: classes.dex */
public class NavigationBarUtil {
    public static void hideNavigationBar(final Window window) {
        window.getDecorView().setSystemUiVisibility(2);
        window.getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() { // from class: com.joyhonest.wifi_check_adj.NavigationBarUtil.1
            @Override // android.view.View.OnSystemUiVisibilityChangeListener
            public void onSystemUiVisibilityChange(int i) {
                window.getDecorView().setSystemUiVisibility(5894);
            }
        });
    }

    public static void focusNotAle(Window window) {
        window.setFlags(8, 8);
    }

    public static void clearFocusNotAle(Window window) {
        window.clearFlags(8);
    }
}
