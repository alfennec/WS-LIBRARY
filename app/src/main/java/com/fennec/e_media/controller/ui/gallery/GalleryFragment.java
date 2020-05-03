package com.fennec.e_media.controller.ui.gallery;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;

import com.fennec.e_media.R;
import com.fennec.e_media.controller.CaptureActivityAnyOrientation;
import com.fennec.e_media.entity.information;
import com.fennec.e_media.entity.media;
import com.fennec.e_media.repository.empruntsRepository;
import com.fennec.e_media.repository.informationRepository;
import com.fennec.e_media.repository.mediaRepository;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    String scanContent;
    String scanFormat;
    TextView tv_nom_media,tv_titre,tv_isbn,tv_nbr_page,tv_date_achat,tv_qrcode,tv_autre,tv_emprunter;
    Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);


        tv_nom_media    = (TextView) root.findViewById(R.id.tv_nom_media);
        tv_titre        = (TextView) root.findViewById(R.id.tv_titre);
        tv_isbn         = (TextView) root.findViewById(R.id.tv_isbn);
        tv_nbr_page     = (TextView) root.findViewById(R.id.tv_nbr_page);
        tv_date_achat   = (TextView) root.findViewById(R.id.tv_date_achat);
        tv_qrcode       = (TextView) root.findViewById(R.id.tv_qrcode);
        tv_autre        = (TextView) root.findViewById(R.id.tv_autre);

        tv_emprunter    = (TextView) root.findViewById(R.id.tv_emprunter);



        button = (Button) root.findViewById(R.id.button_scanne);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for fragment you need to instantiate integrator in this way
                IntentIntegrator scanIntegrator = IntentIntegrator.forSupportFragment(GalleryFragment.this);
                scanIntegrator.setPrompt("Scan");
                scanIntegrator.setBeepEnabled(true);

                //enable the following line if you want QR code
                //scanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);

                scanIntegrator.setCaptureActivity(CaptureActivityAnyOrientation.class);
                scanIntegrator.setOrientationLocked(true);
                scanIntegrator.setBarcodeImageEnabled(true);
                scanIntegrator.initiateScan();
            }
        });


        return root;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null)
            {
                scanContent = scanningResult.getContents().toString();
                scanFormat = scanningResult.getFormatName().toString();
            }

            Toast.makeText(getActivity(), scanContent + "   type:" + scanFormat, Toast.LENGTH_SHORT).show();

            setAll(scanContent);

        } else {
            Toast.makeText(getActivity(), "Rien a scanner", Toast.LENGTH_SHORT).show();
        }
    }

    public void setAll(String scanContent)
    {
        information currentInfo = informationRepository.getInfoScann(scanContent);

        if(currentInfo.id > 0)
        {
            media currentMedia = mediaRepository.getMediabyId(currentInfo.id_element);

            tv_nom_media.setText(""+currentMedia.titre);
            tv_titre.setText(""+currentMedia.titre);
            tv_isbn.setText(""+currentInfo.isbn);
            tv_nbr_page.setText(""+currentInfo.nbr_page);
            tv_date_achat.setText(""+currentInfo.date_achat);
            tv_qrcode.setText(""+currentInfo.qrcode);
            tv_autre.setText("--");

            if(empruntsRepository.findRenduById(currentMedia.id).rendu == 1)
            {
                tv_emprunter.setText("Non Emprunter");
                tv_emprunter.setTextColor(Color.GREEN);
            }else
            {
                tv_emprunter.setText("Emprunter");
                tv_emprunter.setTextColor(Color.RED);
            }

        }

    }
}