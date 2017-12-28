package android.weather.app.weatherinfo.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.adapter.SearchAdapter;
import android.weather.app.weatherinfo.databinding.FragmentSearchBinding;
import android.weather.app.weatherinfo.viewmodel.SearchItemViewModel;
import android.weather.app.weatherinfo.viewmodel.SearchViewModel;
import android.weather.app.weatherinfo.viewmodel.ViewModel;

import java.util.List;

public class SearchFragment extends MVVMFragment {
    private SearchViewModel mSearchViewModel;
    private SearchAdapter mSearchAdapter;

    @NonNull
    @Override
    protected ViewModel getViewModel() {
        mSearchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        return mSearchViewModel;
    }

    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.search_title);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        mSearchAdapter = new SearchAdapter(null);
        ((FragmentSearchBinding) mBinding).searchRecyclerView.setAdapter(mSearchAdapter);
        subscribeToSearchResults();
    }

    private void subscribeToSearchResults() {
        mSearchViewModel.getSearchItemViewModelList().observe(this, new Observer<List<SearchItemViewModel>>() {
            @Override
            public void onChanged(@Nullable List<SearchItemViewModel> searchItemViewModels) {
                mSearchAdapter.setSearchItemViewModelList(searchItemViewModels);
            }
        });
    }
}
