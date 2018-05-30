package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ZoomedPictureFragment extends DialogFragment {

    private static final String ARG_PICTURE = "picture";

    private ImageView mImageView;

    public static ZoomedPictureFragment newInstance(String picturePath) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PICTURE, picturePath);

        ZoomedPictureFragment fragment = new ZoomedPictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String picturePath = (String) getArguments().getSerializable(ARG_PICTURE);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_zoom, null);

        mImageView = (ImageView) v.findViewById(R.id.image_zoom_layout);
        Bitmap bitmap = PictureUtils.getScaledBitmap(picturePath, getActivity());
        mImageView.setImageBitmap(bitmap);
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.date_picker_title).setPositiveButton(android.R.string.ok, null).create();
    }

}
