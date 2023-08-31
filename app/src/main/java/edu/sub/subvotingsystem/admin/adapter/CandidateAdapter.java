package edu.sub.subvotingsystem.admin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.sub.subvotingsystem.CandidateInfo;
import edu.sub.subvotingsystem.R;
public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.ViewHolder> {
    private List<CandidateInfo> candidateList;
    private OnItemClickListener itemClickListener;

    public CandidateAdapter(List<CandidateInfo> candidateList) {
        this.candidateList = candidateList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_candidate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CandidateInfo candidate = candidateList.get(position);
        holder.candidateNameTextView.setText(candidate.getCandidateName());
        holder.candidateGenderTextView.setText(candidate.getCandidateGender());
        holder.candidatePartyTextView.setText(candidate.getCandidatePartyName());
    }

    @Override
    public int getItemCount() {
        return candidateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView candidateNameTextView;
        TextView candidateGenderTextView;
        TextView candidatePartyTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            candidateNameTextView = itemView.findViewById(R.id.candidateNameTextView);
            candidateGenderTextView = itemView.findViewById(R.id.candidateGenderTextView);
            candidatePartyTextView = itemView.findViewById(R.id.candidatePartyTextView);

            // Set click listener for the entire item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            itemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
