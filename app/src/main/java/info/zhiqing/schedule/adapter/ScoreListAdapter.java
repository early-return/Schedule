package info.zhiqing.schedule.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhiqing on 16-10-14.
 */

public class ScoreListAdapter extends RecyclerView.Adapter<ScoreListAdapter.ScoreListViewHolder> {

    @Override
    public ScoreListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ScoreListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ScoreListViewHolder extends RecyclerView.ViewHolder {

        public ScoreListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
