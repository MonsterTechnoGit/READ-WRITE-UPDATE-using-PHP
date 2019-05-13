package net.simplifiedcoding.simplifiedcoding.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.simplifiedcoding.simplifiedcoding.R;
import net.simplifiedcoding.simplifiedcoding.model.Data;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private Context context;
    private List<Data> list;

    public DataAdapter(Context context, List<Data> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Data data = list.get(position);
        holder.question.setText(data.getQuestions());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView question, textRating, textYear;

        public ViewHolder(View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.main_question);
        }
    }
}
