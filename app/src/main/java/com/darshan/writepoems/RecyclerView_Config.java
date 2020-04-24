package com.darshan.writepoems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darshan.writepoems.model.PoemModel;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private PoemAdapter poemAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<PoemModel> poemList, List<String> keyList) {
        this.mContext = context;
        poemAdapter = new PoemAdapter(poemList, keyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(poemAdapter);
    }

    class PoemItemView extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView poem;

        private String key;

        public PoemItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.poem_layout, parent, false));

            title = itemView.findViewById(R.id.poem_title);
            poem = itemView.findViewById(R.id.main_poem);
        }

        public void bind(PoemModel poemModel, String key) {
            String mainPoem = poemModel.getPoem();

            if (mainPoem.length() >= 40)
                mainPoem = mainPoem.substring(0, 40) + "...";

            title.setText(poemModel.getTitle());
            poem.setText(mainPoem);
            this.key = key;
        }
    }

    class PoemAdapter extends RecyclerView.Adapter<PoemItemView> {
        private List<PoemModel> poemList;
        private List<String> keyList;

        public PoemAdapter(List<PoemModel> poemList, List<String> keyList) {
            this.poemList = poemList;
            this.keyList = keyList;
        }

        @NonNull
        @Override
        public PoemItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PoemItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PoemItemView holder, int position) {
            holder.bind(poemList.get(position), keyList.get(position));
        }

        @Override
        public int getItemCount() {
            return poemList.size();
        }
    }
}
