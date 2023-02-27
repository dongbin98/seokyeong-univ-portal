package com.dbsh.skup.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.model.UserData;
import com.dbsh.skup.databinding.QrcodeFormBinding;
import com.dbsh.skup.viewmodels.QrcodeViewModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QrcodeFragment extends Fragment implements OnBackPressedListener {

    private QrcodeFormBinding binding;
    private QrcodeViewModel viewModel;

	// parent Fragment
	private HomeCenterContainer homeCenterContainer;
	private HomeLeftContainer homeLeftContainer;

    Button QRBtn;
    ImageView QR_CODE;
	String type;

    UserData userData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* DataBinding */
	    binding = DataBindingUtil.inflate(inflater, R.layout.qrcode_form, container, false);
        viewModel = new QrcodeViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();	// 바인딩 강제 즉시실행

	    userData = ((UserData) getActivity().getApplication());
	    if(getArguments() != null) {
		    type = getArguments().getString("type");
	    }

	    if(type.equals("left"))
		    homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());
	    else if(type.equals("center"))
		    homeCenterContainer = ((HomeCenterContainer) this.getParentFragment());

        Toolbar mToolbar = binding.qrToolbar;
	    ((HomeActivity) getActivity()).setSupportActionBar(mToolbar);
		((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    ((HomeActivity) getActivity()).getSupportActionBar().setTitle("");

        String id = userData.getId();
        String date = "0000000022";

        QRBtn = binding.QRBtn;
        QR_CODE = binding.QRcode;
        String data = ("").
                concat("7082").
                concat("\n").
                concat(id).
                concat("\n").
                concat("02").
                concat("\n").
                concat(date);
        //System.out.println(data);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE,700,700);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            QR_CODE.setImageBitmap(bitmap);
        } catch (Exception e){}


        QRBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String data = ("").
                        concat("7082").
                        concat("\n").
                        concat(id).
                        concat("\n").
                        concat("02").
                        concat("\n").
                        concat(date);
                //System.out.println(data);

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE,700,700);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    QR_CODE.setImageBitmap(bitmap);
                }catch (Exception e){}
            }
        });

		return binding.getRoot();
    }

	@Override
	public void onBackPressed() {
		if(type.equals("center")) {
			homeCenterContainer.getChildFragmentManager().beginTransaction().remove(this).commit();
			homeCenterContainer.getChildFragmentManager().popBackStackImmediate();
            homeCenterContainer.popFragment();
		} else if(type.equals("left")){
			homeLeftContainer.getChildFragmentManager().beginTransaction().remove(this).commit();
			homeLeftContainer.getChildFragmentManager().popBackStackImmediate();
            homeLeftContainer.popFragment();
		}
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		((HomeActivity)context).setOnBackPressedListener(this);
	}
}
