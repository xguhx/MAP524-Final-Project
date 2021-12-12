package com.example.finalproject;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.TasksViewHolder> {

    interface FavouriteClickListner {
        public void favouriteClicked(ImaginaryFriend selectediF);
    }
    private Context mCtx;
    public List<ImaginaryFriend> ImaginaryFriendList;
    FavouriteClickListner listner;

    public FavouritesAdapter(Context mCtx, List<ImaginaryFriend> iFList) {
        this.mCtx = mCtx;
        this.ImaginaryFriendList = iFList;
        listner = (FavouriteClickListner)mCtx;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_row, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        ImaginaryFriend t = ImaginaryFriendList.get(position);
        holder.iFTextView.setText(t.getFirstName()); //display first name


    }

    @Override
    public int getItemCount() {
        return ImaginaryFriendList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView iFTextView, countryTextView;

        public TasksViewHolder(View itemView) {
            super(itemView);

            iFTextView= itemView.findViewById(R.id.recyclerRow);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ImaginaryFriend friend = ImaginaryFriendList.get(getAdapterPosition());
            listner.favouriteClicked(friend);

        }
    }


}