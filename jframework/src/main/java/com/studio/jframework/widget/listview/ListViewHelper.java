package com.studio.jframework.widget.listview;

import android.os.Build;
import android.widget.AbsListView;

public class ListViewHelper {
    private ListViewHelper() {
        //Do not new
    }

    public static void scrollToTop(final AbsListView listView) {
        if (listView == null) {
            return;
        }
        scroll(listView, 0);
        listView.postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.setSelection(0);
            }
        }, 200);
    }

    public static void scroll(AbsListView listView, int position) {
        if (Build.VERSION.SDK_INT > 7) {
            listView.smoothScrollToPositionFromTop(0, position);
        } else {
            listView.setSelection(position);
        }
    }
}