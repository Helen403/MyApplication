package com.example.snoy.myapplication.lib.LRecyclerView.interfaces;

import android.view.View;

/**
 * Click and LongClick
 */
public interface OnItemClickListener {
    void onItemClick(View view, int position);
    void onItemLongClick(View view, int position);
}
