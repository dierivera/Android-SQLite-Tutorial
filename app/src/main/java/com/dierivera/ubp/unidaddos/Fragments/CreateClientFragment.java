package com.dierivera.ubp.unidaddos.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dierivera.ubp.unidaddos.Helpers.Constants;
import com.dierivera.ubp.unidaddos.Helpers.DatabaseHelper;
import com.dierivera.ubp.unidaddos.Models.Client;
import com.dierivera.ubp.unidaddos.R;

import org.w3c.dom.Text;

/**
 * Created by dierivera on 6/17/17.
 */

public class CreateClientFragment extends Fragment {

    private EditText mEditName;
    private EditText mEditLastName;
    private EditText mEditEmail;
    private EditText mEditPhoneNumber;
    private TextView mTitleTextView;

    private DatabaseHelper db;
    private Client mClient;

    private String TAG = "create_client";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_client, container, false);

        db = new DatabaseHelper(getContext());

        mClient=null;
        Bundle extras = this.getArguments();
        if(extras != null) {
            int id = extras.getInt(Constants.KEY_ID);
            String name = extras.getString(Constants.KEY_FIRST_NAME);
            String lastName = extras.getString(Constants.KEY_LAST_NAME);
            String email = extras.getString(Constants.KEY_EMAIL);
            String phoneNumber = extras.getString(Constants.KEY_PHONE_NUMBER);
            mClient = new Client(id, name, lastName, email, phoneNumber);
            //Log.i(TAG, "client object created id: " + id);
        }
        setupUI(view);
        return view;
    }

    private void setupUI(View pRootView)
    {
        mEditName = (EditText)pRootView.findViewById(R.id.edit_name);
        mEditLastName = (EditText)pRootView.findViewById(R.id.edit_last_name);
        mEditEmail = (EditText)pRootView.findViewById(R.id.edit_email);
        mEditPhoneNumber = (EditText)pRootView.findViewById(R.id.edit_phone_number);
        mTitleTextView = (TextView)pRootView.findViewById(R.id.tv_client_form_title);

        //TODO:
        //edit title
        //load content to edittexts

        if(mClient != null)
        {
            mTitleTextView.setText("Editar Cliente");
            mEditName.setText(mClient.getName());
            mEditLastName.setText(mClient.getLastName());
            mEditEmail.setText(mClient.getEmail());
            mEditPhoneNumber.setText(mClient.getPhoneNumber());
        }

        pRootView.findViewById(R.id.btn_accept).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mClient==null) {
                    if (attemptCreateUser()) {
                        Toast.makeText(getContext(), "Cliente agregado!", Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStack();
                    }
                }else{
                    if (attemptUpdateUser()) {
                        Toast.makeText(getContext(), "Cliente editado!", Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStack();
                    }
                }
            }
        });

        pRootView.findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

    }

    private boolean attemptCreateUser()
    {
        String name = mEditName.getText().toString();
        String lastName = mEditLastName.getText().toString();
        String email = mEditEmail.getText().toString();
        String phoneNumber = mEditPhoneNumber.getText().toString();

        if (checkTextFields(name, lastName, email, phoneNumber)) {
            Client client = new Client(name, lastName, email, phoneNumber);
            db.createClient(client);
            return true;
        }else{
            return false;
        }
    }

    private boolean attemptUpdateUser()
    {
        String name = mEditName.getText().toString();
        String lastName = mEditLastName.getText().toString();
        String email = mEditEmail.getText().toString();
        String phoneNumber = mEditPhoneNumber.getText().toString();

        if (checkTextFields(name, lastName, email, phoneNumber)) {
            mClient.setName(name);
            mClient.setLastName(lastName);
            mClient.setEmail(email);
            mClient.setPhoneNumber(phoneNumber);
            db.updateClient(mClient);
            return true;
        }else{
            return false;
        }
    }

    private boolean checkTextFields(String name, String lastName, String email, String phoneNumber){
        if(TextUtils.isEmpty(name))
        {
            mEditName.requestFocus();
            mEditName.setError(getString(R.string.error_blank_space));
            return false;
        }if(TextUtils.isEmpty(lastName))
        {
            mEditLastName.requestFocus();
            mEditLastName.setError(getString(R.string.error_blank_space));
            return false;
        }if(TextUtils.isEmpty(email))
        {
            mEditEmail.requestFocus();
            mEditEmail.setError(getString(R.string.error_blank_space));
            return false;
        }if(TextUtils.isEmpty(phoneNumber))
        {
            mEditPhoneNumber.requestFocus();
            mEditPhoneNumber.setError(getString(R.string.error_blank_space));
            return false;
        }
        return true;
    }




}
