package com.fennec.e_media.controller.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fennec.e_media.R;
import com.fennec.e_media.adapter.mediaAdapter;
import com.fennec.e_media.apiComm.UrlComm;
import com.fennec.e_media.apiComm.empruntsJson;
import com.fennec.e_media.apiComm.informationJson;
import com.fennec.e_media.apiComm.mediaJson;
import com.fennec.e_media.repository.empruntsRepository;
import com.fennec.e_media.repository.informationRepository;
import com.fennec.e_media.repository.mediaRepository;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public static View root;

    public static HomeFragment main;

    public static RecyclerView recyclerView;

    public static mediaAdapter myMediaAdapter;

    public static ProgressDialog dialog;

    public static LayoutInflater myInflater;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myInflater = inflater;
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        main = this;

        //////////// clear all data exesting
        mediaRepository.list_media.clear();
        informationRepository.list_information.clear();
        empruntsRepository.list_emprunts.clear();

        String url_informations = UrlComm.url_host+"media";
        mediaJson jsonMedia = new mediaJson(url_informations, inflater.getContext());
        dialog = ProgressDialog.show(inflater.getContext(), "", "Traitement de données. Veulliez attendre ...", true);

        return root;
    }

    public static void onSuccesMedia()
    {
        String url_informations = UrlComm.url_host+"information";
        informationJson jsonInformation = new informationJson(url_informations, myInflater.getContext());
    }

    public static void onSuccesInfos()
    {
        String url_informations = UrlComm.url_host+"emprunt";
        empruntsJson jsonEnprunt = new empruntsJson(url_informations, myInflater.getContext());
    }

    public static void onSucces()
    {
        /** adapter for test we have to improve our self for this app  **/
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(main.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        myMediaAdapter = new mediaAdapter(mediaRepository.list_media);
        recyclerView.setAdapter(myMediaAdapter);
        /** adapter for test we have to improve our self for this end  **/

        dialog.dismiss();
    }
}