package com.fennec.e_media.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fennec.e_media.R;
import com.fennec.e_media.controller.MediaActivity;
import com.fennec.e_media.controller.MembreActivity;
import com.fennec.e_media.entity.media;
import com.fennec.e_media.repository.empruntsRepository;
import com.fennec.e_media.repository.mediaRepository;
import com.fennec.e_media.repository.userRepository;

import java.util.List;

public class mediaAdminAdapter extends RecyclerView.Adapter<mediaAdminAdapter.MyViewHolder> {

    public List<media> list;
    public boolean showAdd = false;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView member_name,member_email,status;
        public ImageButton btn_delete;
        public View parent;
        public ImageButton infos_media;
        public RecyclerView recyclerView;

        public MyViewHolder(View view)
        {
            super(view);
            parent= view;
            member_name = (TextView) view.findViewById(R.id.member_name);
            member_email = (TextView) view.findViewById(R.id.member_email);
            status = (TextView) view.findViewById(R.id.status);

            btn_delete  = (ImageButton) view.findViewById(R.id.btn_delete);
            infos_media = (ImageButton) view.findViewById(R.id.infos_media);
        }
    }

    public mediaAdminAdapter(List<media> list)
    {
        this.list = list;
    }

    @Override
    public mediaAdminAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin, parent, false);

        return new mediaAdminAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == getItemCount()-1 && showAdd)
            return 2;
        return 1;
    }

    @Override
    public void onBindViewHolder(final mediaAdminAdapter.MyViewHolder holder, final int position)
    {
        final media myMedia = list.get(position);

        holder.member_name.setText(myMedia.titre);
        holder.member_email.setText(myMedia.des);

        if(empruntsRepository.findRenduById(myMedia.id).rendu == 1)
        {
            holder.status.setText("Non Emprunter");
            holder.status.setTextColor(Color.rgb(0,100,0));
        }else
        {
            holder.status.setText("Emprunter");
            holder.status.setTextColor(Color.RED);
        }

        holder.parent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                MediaActivity.to_newIntent(myMedia.id);
            }
        });

        holder.infos_media.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MediaActivity.to_newIntent(myMedia.id);
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for (int i = 0; i < mediaRepository.list_media.size(); i++)
                {
                    if(mediaRepository.list_media.get(i).id == myMedia.id)
                    {
                        mediaRepository.list_media.remove(i);
                        MediaActivity.updateRecycle();
                        MediaActivity.DeleteUser(myMedia.id);
                    }
                }
            }
        });

       }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
