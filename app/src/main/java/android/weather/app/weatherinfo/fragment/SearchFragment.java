package android.weather.app.weatherinfo.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.activity.WeatherInfoActivity;
import android.weather.app.weatherinfo.adapter.SearchAdapter;
import android.weather.app.weatherinfo.databinding.FragmentSearchBinding;
import android.weather.app.weatherinfo.handler.SearchFragmentHandler;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.utils.Constants;
import android.weather.app.weatherinfo.viewmodel.SearchItemViewModel;
import android.weather.app.weatherinfo.viewmodel.SearchViewModel;
import android.weather.app.weatherinfo.viewmodel.ViewModel;

import java.util.List;

public class SearchFragment extends MVVMFragment {
    private SearchViewModel mSearchViewModel;
    private SearchAdapter mSearchAdapter;

    private SearchFragmentHandler searchFragmentHandler = new SearchFragmentHandler() {
        @Override
        public void onItemClicked(City city) {
            Intent intent = new Intent(getActivity(), WeatherInfoActivity.class);
            intent.putExtra(Constants.EXTRA_CITY, city);
            startActivity(intent);
        }

        @Override
        public void onFavorite(City city) {

        }
    };

    @NonNull
    @Override
    protected ViewModel getViewModel() {
        mSearchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        mSearchViewModel.setSearchFragmentHandler(searchFragmentHandler);
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

    @Override
    public void onDestroyView() {
        mSearchViewModel.setSearchFragmentHandler(null);
        super.onDestroyView();
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
