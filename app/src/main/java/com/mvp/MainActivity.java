package com.mvp;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.mvp.Presenter.MainPresenter;
import com.mvp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;
    private ActivityMainBinding binding;
    private ArrayList<Album> albumList;
    private AlbumsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        presenter = new MainPresenter(this);
        albumList = new ArrayList<>();
        Album a = new Album("True Romance", 13,R.mipmap.ic_launcher);
        albumList.add(a);

        a = new Album("Xscpae", 8,R.mipmap.ic_launcher);
        albumList.add(a);

        a = new Album("Maroon 5", 11,R.mipmap.ic_launcher);
        albumList.add(a);

        a = new Album("Born to Die", 12,R.mipmap.ic_launcher);
        albumList.add(a);

        a = new Album("Honeymoon", 14,R.mipmap.ic_launcher);
        albumList.add(a);

        a = new Album("I Need a Doctor", 1,R.mipmap.ic_launcher);
        albumList.add(a);

        adapter = new AlbumsAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void setOnSucess(ArrayList<String> array) {
        // you have to set into adapter
        Log.d("Sucess", String.valueOf(array.size()));
    }

    @Override
    public void setOnFailure(String errorMsg) {
        Log.d("Failure", errorMsg);
    }

    public void onSubmit(View view) {
        if (presenter.checkValidation(binding.editText.getText().toString(), binding.editText2.getText().toString())) {
            // call your api
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("username", binding.editText.getText().toString());
            hashMap.put("password", binding.editText2.getText().toString());
            presenter.getApiResponse(hashMap);
        }
    }

    @Override
    public void onEmailInValid() {
        binding.editText.setError("Wrong Email");
    }

    @Override
    public void onPasswordInvalid() {
        binding.editText2.setError("Wrong Password");
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
