package com.dierivera.ubp.unidaddos.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dierivera.ubp.unidaddos.Adapters.ClientsListAdapter;
import com.dierivera.ubp.unidaddos.Helpers.Constants;
import com.dierivera.ubp.unidaddos.Helpers.DatabaseHelper;
import com.dierivera.ubp.unidaddos.Helpers.NavigationHelper;
import com.dierivera.ubp.unidaddos.Models.Client;
import com.dierivera.ubp.unidaddos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dierivera on 6/17/17.
 */

public class ClientsListFragment extends Fragment  implements AdapterView.OnItemClickListener {

    private DatabaseHelper db;
    private List<Client> clients;
    private ListView mClientsListView;
    private ClientsListAdapter mAdapter;

    private String TAG = "remove";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clients_list, container, false);
        db = new DatabaseHelper(getContext());
        clients = db.getAll();
        setupUI(view);
        return view;
    }

    private void setupUI(View pRootView)
    {
        mClientsListView = (ListView) pRootView.findViewById(R.id.clients_listview);
        mAdapter = new ClientsListAdapter(getContext(), R.layout.list_element_client ,clients);
        mClientsListView.setAdapter(mAdapter);
        mClientsListView.setOnItemClickListener(this);
        mClientsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        pRootView.findViewById(R.id.btn_add_client).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Total de clientes: " + clients.size(), Toast.LENGTH_LONG).show();
                NavigationHelper.replaceFragment(getFragmentManager(), new CreateClientFragment(), android.R.id.content, false, true);
            }
        });

        mClientsListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // Here you can do something when items are selected/de-selected,
                // such as update the title in the CAB
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_delete_clients:
                        deleteSelectedItems();
                        mode.finish(); // Action picked, so close the CAB
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.fragment_clients_contextual, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Here you can make any necessary updates to the activity when
                // the CAB is removed. By default, selected items are deselected/unchecked.
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // Here you can perform updates to the CAB due to
                // an invalidate() request
                return false;
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TODO: cargar el delete y enviar a edit
        CreateClientFragment fragment = new CreateClientFragment();
        Bundle extras = new Bundle();
        Client client = clients.get(position);
        extras.putInt(Constants.KEY_ID, client.getId());
        extras.putString(Constants.KEY_FIRST_NAME, client.getName());
        extras.putString(Constants.KEY_LAST_NAME, client.getLastName());
        extras.putString(Constants.KEY_EMAIL, client.getEmail());
        extras.putString(Constants.KEY_PHONE_NUMBER, client.getPhoneNumber());
        fragment.setArguments(extras);
        NavigationHelper.replaceFragment(getFragmentManager(), fragment, android.R.id.content, false, true);
    }

    private void deleteSelectedItems()
    {
        int childCount = mClientsListView.getChildCount();
        // Crea una lista con los vehiculos por remover
        List<Client> vehiclesToRemove = new ArrayList<Client>();
        for(int pos = 0; pos < childCount; pos++) {
            View child = mClientsListView.getChildAt(pos);
            if(child.isActivated()) {
                vehiclesToRemove.add(clients.get(pos));
            }
        }

        // Recorre la lista y los elimina
        for(Client client : vehiclesToRemove) {
            //remove from database the selected client
            db.deleteClient(client);
            //Refresca el listview
            clients = db.getAll();
            mAdapter = new ClientsListAdapter(getContext(), R.layout.list_element_client ,clients);
            mClientsListView.setAdapter(mAdapter);
        }
    }


}
