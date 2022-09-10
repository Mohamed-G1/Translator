package com.android.tanslatorapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.android.tanslatorapp.R;
import com.android.tanslatorapp.SwipeToDeleteCallback;
import com.android.tanslatorapp.databinding.ActivitySavedBinding;
import com.android.tanslatorapp.db.SavedDataBase;
import com.android.tanslatorapp.db.SavedEntity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

public class SavedActivity extends AppCompatActivity {
    ActivitySavedBinding binding;
    private ListAdapter adapter;
    SavedEntity item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_TanslatorApp);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_saved);
        initRecyclerView();
        loadSaved();
        enableSwipeToDeleteAndUndo();
    }

    // delete and resave the text when swipe the item position
    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                 item = adapter.getData().get(position);
                adapter.removeItem(position);
                SavedDataBase db = SavedDataBase.getINSTANCE(getApplicationContext());
                db.savedDao().delete(item);
                Snackbar snackbar = Snackbar
                        .make(binding.coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapter.restoreItem(item, position);
                        binding.recyclerView.scrollToPosition(position);
                        db.savedDao().insert(item);
                    }
                });
                snackbar.setActionTextColor(Color.BLUE);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(binding.recyclerView);
    }

    // handle recycler view
    private void initRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new ListAdapter(this);
        binding.recyclerView.setAdapter(adapter);
    }

    // load translated text that's in local database
    private void loadSaved() {
        SavedDataBase db = SavedDataBase.getINSTANCE(this.getApplicationContext());
        List<SavedEntity> entityList = db.savedDao().getAllSaved();
        adapter.setList(entityList);
    }
}