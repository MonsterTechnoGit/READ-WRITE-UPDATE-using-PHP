package net.monstertechno.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.monstertechno.project.R;
import net.monstertechno.project.URLs;
import net.monstertechno.project.model.Data;

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



        if(data.getAnswer_yes().equals("null")){
            holder.yes.setVisibility(View.GONE);
        }
        if(data.getAnswer_no().equals("null")){
            holder.no.setVisibility(View.GONE);
        }
        if(data.getAnswer_one().equals("null")){
            holder.one.setVisibility(View.GONE);
        }
        if(data.getAnswer_two().equals("null")){
            holder.two.setVisibility(View.GONE);
        }
        if(data.getAnswer_three().equals("null")){
            holder.three.setVisibility(View.GONE);
        }
        if(data.getAnswer_four().equals("null")){
            holder.four.setVisibility(View.GONE);
        }

        holder.yes.setText(data.getAnswer_yes());
        holder.no.setText(data.getAnswer_no());
        holder.one.setText(data.getAnswer_one());
        holder.two.setText(data.getAnswer_two());
        holder.three.setText(data.getAnswer_three());
        holder.four.setText(data.getAnswer_four());

        Log.d("JsonData",data.getQuestions());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView question;
        public RadioButton yes, no,one,two,three,four;


        public ViewHolder(View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.main_question);
            yes = itemView.findViewById(R.id.answer_yes);
            no = itemView.findViewById(R.id.answer_no);
            two = itemView.findViewById(R.id.answer_two);
            three = itemView.findViewById(R.id.answer_three);
            four = itemView.findViewById(R.id.answer_four);
            one = itemView.findViewById(R.id.answer_one);

            final RadioGroup radio = (RadioGroup) itemView.findViewById(R.id.group);

            radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    View radioButton = radio.findViewById(checkedId);
                    int index = radio.indexOfChild(radioButton);

                    // Add logic here

                    switch (index) {
                        case 0: // first button
                            URLs.yes = true;

                            break;
                        case 1: // secondbutton
                            URLs.yes = false;

                            break;
                    }
                }
            });

        }
    }
}
