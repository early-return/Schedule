package info.zhiqing.schedule.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import info.zhiqing.schedule.R;
import info.zhiqing.schedule.models.Database;
import info.zhiqing.schedule.models.Score;

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
        Database db = new Database(context);
        this.scores = db.getScores();
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public void updateData(String year, String semester) {
        Database db = new Database(context);
        this.scores = db.getScores(year, semester);
        notifyDataSetChanged();
    }

    public void updateData(){
        updateData("*", "*");
    }

    @Override
    public ScoreListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScoreListViewHolder(inflater.inflate(R.layout.score_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ScoreListViewHolder holder, final int position) {
        holder.titleTextView.setText(scores.get(position).getName());
        final float score = Math.max(scores.get(position).getScore(), scores.get(position).getResit());
        holder.scoreTextView.setText(score + "");
        if(score >= 60) {
            holder.cardView.setBackground(context.getResources().getDrawable(R.drawable.score_item_card_passed_style));
        } else {
            holder.cardView.setBackground(context.getResources().getDrawable(R.drawable.score_item_card_not_passed_style));
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(context, ScoreInfoActivity.class);
                //intent.putExtra(ScoreInfoActivity.EXTRA_SCORE, scores.get(position));
                //context.startActivity(intent);
                if(!holder.isInfoLayoutVisibled){
                    holder.infoCodeTextView.setText(scores.get(position).getCode());
                    holder.infoCreditTextView.setText(scores.get(position).getCredit() + "");
                    holder.infoTypeTextView.setText(scores.get(position).getType());
                    holder.infoUsualTextView.setText(scores.get(position).getUsual() + "");
                    holder.infoExamTextView.setText(scores.get(position).getExam() + "");
                    holder.infoScoreTextView.setText(scores.get(position).getScore() + "");
                    holder.infoResitTextView.setText(scores.get(position).getResit() + "");
                    holder.infoLayout.setVisibility(View.VISIBLE);
                    holder.isInfoLayoutVisibled = true;
                } else {
                    holder.infoLayout.setVisibility(View.GONE);
                    holder.isInfoLayoutVisibled = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return scores == null ? 0 : scores.size();
    }

    public static class ScoreListViewHolder extends RecyclerView.ViewHolder {
        boolean isInfoLayoutVisibled = false;

        TextView titleTextView;
        TextView scoreTextView;
        CardView cardView;
        LinearLayout infoLayout;

        TextView infoCodeTextView;
        TextView infoCreditTextView;
        TextView infoTypeTextView;
        TextView infoUsualTextView;
        TextView infoExamTextView;
        TextView infoScoreTextView;
        TextView infoResitTextView;

        public ScoreListViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView)itemView.findViewById(R.id.scoreItemTitleTextView);
            scoreTextView = (TextView)itemView.findViewById(R.id.scoreItemScoreTextView);
            cardView = (CardView)itemView.findViewById(R.id.scoreItemCard);
            infoLayout = (LinearLayout)itemView.findViewById(R.id.scoreItemInfoLayout);

            infoCodeTextView = (TextView)itemView.findViewById(R.id.scoreInfoCodeTextView);
            infoCreditTextView = (TextView)itemView.findViewById(R.id.scoreInfoCreditTextView);
            infoTypeTextView = (TextView)itemView.findViewById(R.id.scoreInfoTypeTextView);
            infoUsualTextView = (TextView)itemView.findViewById(R.id.scoreInfoUsualTextView);
            infoExamTextView = (TextView)itemView.findViewById(R.id.scoreInfoExamTextView);
            infoScoreTextView = (TextView)itemView.findViewById(R.id.scoreInfoScoreTextView);
            infoResitTextView = (TextView)itemView.findViewById(R.id.scoreInfoResitTextView);

        }
    }
}
