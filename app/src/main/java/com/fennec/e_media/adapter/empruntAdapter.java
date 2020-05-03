package com.fennec.e_media.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fennec.e_media.R;
import com.fennec.e_media.controller.EmpruntActivity;
import com.fennec.e_media.controller.MediaActivity;
import com.fennec.e_media.entity.emprunts;
import com.fennec.e_media.entity.media;
import com.fennec.e_media.repository.empruntsRepository;
import com.fennec.e_media.repository.mediaRepository;
import com.fennec.e_media.repository.userRepository;

import java.util.List;

public class empruntAdapter extends RecyclerView.Adapter<empruntAdapter.MyViewHolder> {

    public List<emprunts> list;
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

    public empruntAdapter(List<emprunts> list)
    {
        this.list = list;
    }

    @Override
    public empruntAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin, parent, false);

        return new empruntAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == getItemCount()-1 && showAdd)
            return 2;
        return 1;
    }

    @Override
    public void onBindViewHolder(final empruntAdapter.MyViewHolder holder, final int position)
    {
        final emprunts myEmprunts = list.get(position);

        holder.member_name.setText("Titre du Media : "+mediaRepository.getMediabyId(myEmprunts.id_element).titre);
        holder.member_email.setText("emprunter par : "+ userRepository.getUserById(myEmprunts.id_user).nom+" "+userRepository.getUserById(myEmprunts.id_user).prenom);

        if(myEmprunts.rendu == 1)
        {
            holder.status.setText("Status : Non Emprunter");
            holder.status.setTextColor(Color.rgb(0,100,0));
        }else
        {
            holder.status.setText("Status : Emprunter");
            holder.status.setTextColor(Color.RED);
        }

        holder.parent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EmpruntActivity.to_newIntent(myEmprunts.id);
            }
        });

        holder.infos_media.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EmpruntActivity.to_newIntent(myEmprunts.id);
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for (int i = 0; i < empruntsRepository.list_emprunts.size(); i++)
                {
                    if(empruntsRepository.list_emprunts.get(i).id == myEmprunts.id)
                    {
                        empruntsRepository.list_emprunts.remove(i);
                        EmpruntActivity.updateRecycle();
                        EmpruntActivity.DeleteEmprunt(myEmprunts.id);
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

