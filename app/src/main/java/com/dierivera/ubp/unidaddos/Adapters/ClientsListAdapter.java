package com.dierivera.ubp.unidaddos.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dierivera.ubp.unidaddos.Models.Client;
import com.dierivera.ubp.unidaddos.R;

import java.util.List;


/**
 * Created by dierivera on 6/17/17.
 */

public class ClientsListAdapter extends ArrayAdapter<Client> {

    public ClientsListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ClientsListAdapter(Context context, int resource, List<Client> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            convertView	=  vi.inflate(R.layout.list_element_client, null);
            holder = new ViewHolder();
            holder.mClientName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mClientEmail = (TextView)convertView.findViewById(R.id.tv_email);
            holder.mClientPhoneNumber = (TextView)convertView.findViewById(R.id.tv_phone_number);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Client client = getItem(position);
        if (client != null) {
            holder.mClientName.setText(client.getName() + " " + client.getLastName());
            holder.mClientEmail.setText(client.getEmail());
            holder.mClientPhoneNumber.setText(client.getPhoneNumber());
        }

        return convertView;
    }



    static class ViewHolder {
        TextView mClientName;
        TextView mClientEmail;
        TextView mClientPhoneNumber;
    }
}

