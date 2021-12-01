package com.example.sqlapplication.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlapplication.R;
import com.example.sqlapplication.model.Customer;

import java.util.ArrayList;

public class AdapterCustomer extends RecyclerView.Adapter<AdapterCustomer.CustomersViewHolder>
        implements View.OnClickListener{

    //arreglo para los destinos
    ArrayList<Customer> listCustomers;

    //escuchador del click en el recyclerView
    private View.OnClickListener listener;

    //constructor
    public AdapterCustomer(ArrayList<Customer> listCustomers) {
        this.listCustomers = listCustomers;
    }

    //se encarga de escuchar el evento
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override //vincular el layout con los elementos a mostrar
    public CustomersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customers_list, null, false);

        view.setOnClickListener(this);
        return new CustomersViewHolder(view);
    }

    @Override //llenar los elementos del layout
    public void onBindViewHolder(@NonNull CustomersViewHolder holder, int position) {
        holder.txvId.setText(String.valueOf(listCustomers.get(position).getIdcustomer()));
        holder.txvName.setText(listCustomers.get(position).getName());
        holder.txvGender.setText(listCustomers.get(position).getGender());
        if (listCustomers.get(position).getGender().equals("Masculino")){
            holder.imgFotoId.setImageResource(R.drawable.ic_customer_circle_m);
        }else {
            holder.imgFotoId.setImageResource(R.drawable.ic_customer_circle_f);
        }
        holder.txvPhone.setText(listCustomers.get(position).getPhone());
        holder.txvAddress.setText(listCustomers.get(position).getAddress());
        holder.txvEmail.setText(listCustomers.get(position).getEmail());
    }

    @Override //optener el tamaño del arreglo
    public int getItemCount() {
        return listCustomers.size();
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }


    public class CustomersViewHolder extends RecyclerView.ViewHolder {
        //elementos de la vista a modificar
        TextView txvId, txvName, txvGender, txvPhone, txvAddress, txvEmail;
        ImageView imgFotoId;

        public CustomersViewHolder(@NonNull View itemView) {
            super(itemView);

            //vincular objetos con los recursos
            txvId =itemView.findViewById(R.id.txvId);
            txvName =itemView.findViewById(R.id.txvName);
            txvGender =itemView.findViewById(R.id.txvGender);
            txvPhone =itemView.findViewById(R.id.txvPhone);
            txvAddress =itemView.findViewById(R.id.txvAddress);
            txvEmail =itemView.findViewById(R.id.txvEmail);
            imgFotoId = itemView.findViewById(R.id.imgFotoId);
        }
    }
}
