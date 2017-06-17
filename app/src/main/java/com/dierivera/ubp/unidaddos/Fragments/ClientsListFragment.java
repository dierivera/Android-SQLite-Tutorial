package com.dierivera.ubp.unidaddos.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dierivera.ubp.unidaddos.Adapters.ClientsListAdapter;
import com.dierivera.ubp.unidaddos.Helpers.DatabaseHelper;
import com.dierivera.ubp.unidaddos.Helpers.NavigationHelper;
import com.dierivera.ubp.unidaddos.Models.Client;
import com.dierivera.ubp.unidaddos.R;

import java.util.List;

/**
 * Created by dierivera on 6/17/17.
 */

public class ClientsListFragment extends Fragment  implements AdapterView.OnItemClickListener {

    private DatabaseHelper db;
    private List<Client> clients;
    private ListView mClientsListView;
    private ClientsListAdapter mAdapter;

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
        //mClientsListView.setOnItemClickListener(this);

        pRootView.findViewById(R.id.btn_add_client).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Total de clientes: " + clients.size(), Toast.LENGTH_LONG).show();
                NavigationHelper.replaceFragment(getFragmentManager(), new CreateClientFragment(), android.R.id.content, false, true);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TODO: cargar el delete y enviar a edit
    }


}
