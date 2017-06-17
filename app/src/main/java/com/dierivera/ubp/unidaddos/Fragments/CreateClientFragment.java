package com.dierivera.ubp.unidaddos.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.dierivera.ubp.unidaddos.Helpers.DatabaseHelper;
import com.dierivera.ubp.unidaddos.Models.Client;
import com.dierivera.ubp.unidaddos.R;

/**
 * Created by dierivera on 6/17/17.
 */

public class CreateClientFragment extends Fragment {

    private EditText mEditName;
    private EditText mEditLastName;
    private EditText mEditEmail;
    private EditText mEditPhoneNumber;

    private DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_client, container, false);



        db = new DatabaseHelper(getContext());

        setupUI(view);
        return view;
    }

    private void setupUI(View pRootView)
    {
        mEditName = (EditText)pRootView.findViewById(R.id.edit_name);
        mEditLastName = (EditText)pRootView.findViewById(R.id.edit_last_name);
        mEditEmail = (EditText)pRootView.findViewById(R.id.edit_email);
        mEditPhoneNumber = (EditText)pRootView.findViewById(R.id.edit_phone_number);

        /*if(mVehicle != null)
        {
            mEditPlateNumber.setText(mVehicle.getLicensePlate());
            mCheckMainVehicle.setChecked(mVehicle.getDefaultVehicle());
        }*/

        pRootView.findViewById(R.id.btn_accept).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                attemptCreateUser();
                Toast.makeText(getContext(), "Cliente agregado!", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
        });

        pRootView.findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

    }

    private void attemptCreateUser()
    {
        String name = mEditName.getText().toString();
        String lastName = mEditLastName.getText().toString();
        String email = mEditEmail.getText().toString();
        String phoneNumber = mEditPhoneNumber.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            mEditName.requestFocus();
            mEditName.setError(getString(R.string.error_blank_space));
            return;
        }if(TextUtils.isEmpty(lastName))
        {
            mEditLastName.requestFocus();
            mEditLastName.setError(getString(R.string.error_blank_space));
            return;
        }if(TextUtils.isEmpty(email))
        {
            mEditEmail.requestFocus();
            mEditEmail.setError(getString(R.string.error_blank_space));
            return;
        }if(TextUtils.isEmpty(phoneNumber))
        {
            mEditPhoneNumber.requestFocus();
            mEditPhoneNumber.setError(getString(R.string.error_blank_space));
            return;
        }

        Client client = new Client(name, lastName, email, phoneNumber);
        db.createClient(client);
    }



}
