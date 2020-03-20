package com.fitbit.sampleandroidoauth2.fragments;

//import android.app.LoaderManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fitbit.api.loaders.ResourceLoaderResult;
import com.fitbit.sampleandroidoauth2.R;
import com.fitbit.sampleandroidoauth2.databinding.LayoutInfoBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;


public abstract class InfoFragment<T> extends Fragment implements  SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<ResourceLoaderResult<T>> {
    protected LayoutInfoBinding binding;
    protected final String TAG = getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_info, container, false);

        binding.setTitleText(getTitleResourceId());
        setMainText(getActivity().getString(R.string.no_data));
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.setLoading(true);

        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();

        getActivity().getSupportLoaderManager().initLoader(getLoaderId(), null, this).forceLoad();
    }

    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<ResourceLoaderResult<T>> loader, ResourceLoaderResult<T> data) {
        binding.swipeRefreshLayout.setRefreshing(false);
        binding.setLoading(false);
        switch (data.getResultType()) {
            case ERROR:
                Toast.makeText(getActivity(), R.string.error_loading_data, Toast.LENGTH_LONG).show();
                break;
            case EXCEPTION:
                Log.e(TAG, "Error loading data", data.getException());
                Toast.makeText(getActivity(), R.string.error_loading_data, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<ResourceLoaderResult<T>> loader) {

    }

    public abstract int getTitleResourceId();

    protected abstract int getLoaderId();

    @Override
    public void onRefresh() {
        getActivity().getSupportLoaderManager().initLoader(getLoaderId(), null, this).forceLoad();
    }


    private String formatNumber(Number number) {
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(number);
    }

    private boolean isImageUrl(String string) {
        return (string.startsWith("http") &&
                (string.endsWith("jpg")
                        || string.endsWith("gif")
                        || string.endsWith("png")));
    }

    protected void printKeys(StringBuilder stringBuilder, Object object) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(object));
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = jsonObject.get(key);
                if (!(value instanceof JSONObject)
                        && !(value instanceof JSONArray)) {
                    stringBuilder.append("&nbsp;&nbsp;&nbsp;&nbsp;<b>");
                    stringBuilder.append(key);
                    stringBuilder.append(":</b>&nbsp;");
                    if (value instanceof Number) {
                        stringBuilder.append(formatNumber((Number) value));
                    } else if (isImageUrl(value.toString())) {
                        stringBuilder.append("<br/>");
                        stringBuilder.append("<center><img src=\"");
                        stringBuilder.append(value.toString());
                        stringBuilder.append("\" width=\"150\" height=\"150\"></center>");
                    } else {
                        stringBuilder.append(value.toString());
                    }
                    stringBuilder.append("<br/>");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void setMainText(String text) {
        binding.webview.loadData(text, "text/html", "UTF-8");
    }


}
