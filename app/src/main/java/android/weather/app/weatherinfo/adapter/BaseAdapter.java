package android.weather.app.weatherinfo.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.weather.app.weatherinfo.BR;

public abstract class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(
                layoutInflater, viewType, parent, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Object obj = getObjForPosition(position);
        holder.bind(obj);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }


    protected abstract Object getObjForPosition(int position);

    protected abstract int getLayoutIdForPosition(int position);

    protected static class BaseViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mBinding;

        public BaseViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(Object obj) {
            mBinding.setVariable(BR.rv, obj);
            mBinding.executePendingBindings();
        }
    }
}
