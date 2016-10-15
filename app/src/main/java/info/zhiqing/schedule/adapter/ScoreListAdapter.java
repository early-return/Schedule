package info.zhiqing.schedule.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.zhiqing.schedule.R;
import info.zhiqing.schedule.models.Score;
import info.zhiqing.schedule.ui.ScoreInfoActivity;

/**
 * Created by zhiqing on 16-10-14.
 */

public class ScoreListAdapter extends RecyclerView.Adapter<ScoreListAdapter.ScoreListViewHolder> {
    private final LayoutInflater inflater;
    private final Context context;
    private List<Score> scores;


    public ScoreListAdapter(Context context, List<Score> scores){
        this.scores = scores;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public ScoreListAdapter(Context context) {
        this(context, null);
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    @Override
    public ScoreListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScoreListViewHolder(inflater.inflate(R.layout.score_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ScoreListViewHolder holder, final int position) {
        holder.titleTextView.setText(scores.get(position).getName());
        float score = Math.max(scores.get(position).getScore(), scores.get(position).getResit());
        holder.scoreTextView.setText(score + "");
        if(score >= 60) {
            holder.scoreTextView.setTextColor(context.getResources().getColor(R.color.score_passed));
        } else {
            holder.scoreTextView.setTextColor(context.getResources().getColor(R.color.score_not_passed));
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ScoreInfoActivity.class);
                intent.putExtra(ScoreInfoActivity.EXTRA_SCORE, scores.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return scores == null ? 0 : scores.size();
    }

    public static class ScoreListViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView scoreTextView;
        CardView cardView;

        public ScoreListViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView)itemView.findViewById(R.id.scoreItemTitleTextView);
            scoreTextView = (TextView)itemView.findViewById(R.id.scoreItemScoreTextView);
            cardView = (CardView)itemView.findViewById(R.id.scoreItemCard);
        }
    }
}
