package com.darshan.heartry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darshan.heartry.model.PoemModel;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private PoemAdapter poemAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<PoemModel> poemList, List<String> keyList, OnPoemListener onPoemListener) {
        this.mContext = context;
        poemAdapter = new PoemAdapter(poemList, keyList, onPoemListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(poemAdapter);
    }

    public interface OnPoemListener {
        void onPoemClick(int position);
    }

    class PoemItemView extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnPoemListener onPoemListener;
        private TextView title;
        private TextView poem;
        private String key;

        public PoemItemView(ViewGroup parent, OnPoemListener onPoemListener) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.poem_layout, parent, false));

            this.onPoemListener = onPoemListener;
            title = itemView.findViewById(R.id.poem_title);
            poem = itemView.findViewById(R.id.main_poem);
            itemView.setOnClickListener(this);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, AddPoem.class);
//                    intent.putExtra("title", title.getText().toString());
//                    intent.putExtra("PoemKEY",key);
//                    intent.putExtra("poem",poem.getText().toString());
//                    intent.putExtra("UPDATE",true);
//                    mContext.startActivity(intent);
//                }
//            });
        }

        public void bind(PoemModel poemModel, String key) {
            String mainPoem = poemModel.getPoem();

            if (mainPoem.length() >= 40)
                mainPoem = mainPoem.substring(0, 40) + "...";

            title.setText(poemModel.getTitle());
            poem.setText(mainPoem);
            this.key = key;
        }

        @Override
        public void onClick(View v) {
            onPoemListener.onPoemClick(getAdapterPosition());
        }
    }

    class PoemAdapter extends RecyclerView.Adapter<PoemItemView> {
        private List<PoemModel> poemList;
        private List<String> keyList;
        private OnPoemListener mOnPoemListener;

        public PoemAdapter(List<PoemModel> poemList, List<String> keyList, OnPoemListener onPoemListener) {
            this.poemList = poemList;
            this.keyList = keyList;
            this.mOnPoemListener = onPoemListener;
        }

        @NonNull
        @Override
        public PoemItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PoemItemView(parent, mOnPoemListener);
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
