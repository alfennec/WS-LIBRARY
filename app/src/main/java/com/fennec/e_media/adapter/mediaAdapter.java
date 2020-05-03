package com.fennec.e_media.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fennec.e_media.R;
import com.fennec.e_media.controller.HomeActivity;
import com.fennec.e_media.entity.media;
import com.fennec.e_media.repository.empruntsRepository;

import java.util.List;

public class mediaAdapter extends RecyclerView.Adapter<mediaAdapter.MyViewHolder> {

    public List<media> list;
    public boolean showAdd = false;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView media_name,media_date,status;
        public ImageView image_media;
        public View parent;
        public ImageButton infos_media;
        public RecyclerView recyclerView;

        public MyViewHolder(View view)
        {
            super(view);
            parent=view;
            media_name = (TextView) view.findViewById(R.id.media_titre);
            media_date = (TextView) view.findViewById(R.id.media_des);
            status = (TextView) view.findViewById(R.id.status);

            infos_media = (ImageButton) view.findViewById(R.id.infos_media);

            image_media = (ImageView) view.findViewById(R.id.image_media);
        }
    }

    public mediaAdapter(List<media> list)
    {
        this.list = list;
    }

    @Override
    public mediaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media, parent, false);

        return new mediaAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == getItemCount()-1 && showAdd)
            return 2;
        return 1;
    }

    @Override
    public void onBindViewHolder(final mediaAdapter.MyViewHolder holder, final int position)
    {
        final media myMedia = list.get(position);

        holder.media_name.setText(myMedia.titre);
        holder.media_date.setText(myMedia.des);

        if(empruntsRepository.findRenduById(myMedia.id).rendu == 1)
        {
            holder.status.setText("Non Emprunter");
            holder.status.setTextColor(Color.rgb(0,100,0));
        }else
            {
                holder.status.setText("Emprunter");
                holder.status.setTextColor(Color.RED);
            }


        int R = (int)(Math.random() * 254 + 1);
        int G = (int)(Math.random() * 254 + 1);
        int B = (int)(Math.random() * 254 + 1);

        holder.image_media.setColorFilter(Color.rgb(R,G,B));


        holder.parent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                HomeActivity.to_newIntent(myMedia.id);
            }
        });

        holder.infos_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                HomeActivity.to_newIntent(myMedia.id);
            }
        });

        //Log.d("TAG_JSON", "onClick: ADAPTER RESTAURANT " + myRestaurant.intituler+" "+myRestaurant.specialiter+" "+myRestaurant.prix_transp+" "+myRestaurant.situation+" "+myRestaurant.restaurant_image);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
