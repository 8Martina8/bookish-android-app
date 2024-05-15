package com.example.bookish.signup.view;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.bookish.R;

public class CameraFragment extends Fragment {

    private ImageView capturedImage;

    private ActivityResultLauncher<Void> takePhotoLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_camera, container,  false);

        takePhotoLauncher = registerForActivityResult(CameraResultContract.TAKE_PHOTO, result -> {
            if (result != null) {
                capturedImage = rootView.findViewById(R.id.capturedImage);
                capturedImage.setImageBitmap(result);
            }
        });

        Button takePhotoButton = rootView.findViewById(R.id.btnTakePicture);
        takePhotoButton.setOnClickListener(v -> {
            takePhotoLauncher.launch(null);
        });

        return rootView;
    }

}

