package com.example.sqlapplication.presentation;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.sqlapplication.R;
import com.example.sqlapplication.data.AdapterCustomer;
import com.example.sqlapplication.db.DBCustomer;
import com.example.sqlapplication.model.Customer;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sqlapplication.databinding.ActivityScrollingBinding;

import java.util.ArrayList;

public class ScrollingActivity extends AppCompatActivity {

    //arreglo para llenar el recyclerView
    ArrayList<Customer> listCustomers;
    RecyclerView rcvCustomers;
    AdapterCustomer adapter;

    //administrador de la base de datos
    DBCustomer admin = new DBCustomer(this, "dbcustomers", null, 1);

    private ActivityScrollingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());
        
        /////////////////////////////////////////////////////////
        //para el RecyclerView
        listCustomers = new ArrayList<Customer>();
        rcvCustomers = findViewById(R.id.rcvCustomers);

        //administrar el RecyclerView
        rcvCustomers.setLayoutManager(new LinearLayoutManager(this));

        //llenar el arreglo de clientes
        fillCustomersList();

        //para escuchar los clic en cada elemento del RecyclerView
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent para llamar la Activity
                Intent i = new Intent(getBaseContext(), EditActivity.class);

                //tomar todos los datos del cliente
                i.putExtra("idcustomer", listCustomers.get(
                        rcvCustomers.getChildAdapterPosition(view))
                        .getIdcustomer());

                i.putExtra("name", listCustomers.get(
                        rcvCustomers.getChildAdapterPosition(view))
                        .getName());

                i.putExtra("gender", listCustomers.get(
                        rcvCustomers.getChildAdapterPosition(view))
                        .getGender());

                i.putExtra("phone", listCustomers.get(
                        rcvCustomers.getChildAdapterPosition(view))
                        .getPhone());

                i.putExtra("address", listCustomers.get(
                        rcvCustomers.getChildAdapterPosition(view))
                        .getAddress());

                i.putExtra("email", listCustomers.get(
                        rcvCustomers.getChildAdapterPosition(view))
                        .getEmail());

                //lanzar la activity EditActivity
                startActivity(i);
            }
        });
        /////////////////////////////////////////////////////////

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.msg_fab, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                ///////////////////////////////////////////////////////
                //para limpiar los datos de recyclerView
                listCustomers.clear();
                adapter = new AdapterCustomer(listCustomers);
                rcvCustomers.setAdapter(adapter);
                ///////////////////////////////////////////////////////
                //llenar el arreglo de clientes
                fillCustomersList();

                //para escuchar los clic en cada elemento del RecyclerView
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Intent para llamar la Activity
                        Intent i = new Intent(getBaseContext(), EditActivity.class);

                        //tomar todos los datos del cliente
                        i.putExtra("idcustomer", listCustomers.get(
                                rcvCustomers.getChildAdapterPosition(view))
                                .getIdcustomer());

                        i.putExtra("name", listCustomers.get(
                                rcvCustomers.getChildAdapterPosition(view))
                                .getName());

                        i.putExtra("gender", listCustomers.get(
                                rcvCustomers.getChildAdapterPosition(view))
                                .getGender());

                        i.putExtra("phone", listCustomers.get(
                                rcvCustomers.getChildAdapterPosition(view))
                                .getPhone());

                        i.putExtra("address", listCustomers.get(
                                rcvCustomers.getChildAdapterPosition(view))
                                .getAddress());

                        i.putExtra("email", listCustomers.get(
                                rcvCustomers.getChildAdapterPosition(view))
                                .getEmail());

                        //lanzar la activity EditActivity
                        startActivity(i);
                    }
                });
                ///////////////////////////////////////////////////////////////
            }
        });
    }

    private void fillCustomersList() {
        //objeto administrador de la bd
        SQLiteDatabase db = admin.getWritableDatabase();

        //para tomar cada registro de la consulta
        Customer customer = null;

        //consulta SQLite
        Cursor cursor = db.rawQuery("SELECT * FROM customers", null);

        //recorrer los resultados
        while (cursor.moveToNext()){
            //llenar el modelo
            customer = new Customer();
            customer.setIdcustomer(cursor.getInt(0));
            customer.setName(cursor.getString(1));
            customer.setGender(cursor.getString(2));
            customer.setPhone(cursor.getString(3));
            customer.setAddress(cursor.getString(4));
            customer.setEmail(cursor.getString(5));

            //agregar fila al array
            listCustomers.add(customer);

            //Usamos la clase AdapterCustomer para pasar los datos
            adapter = new AdapterCustomer(listCustomers);

            //asignar el adaptador al RecyclerView
            rcvCustomers.setAdapter(adapter);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_about:
                Toast.makeText(this, "go about", Toast.LENGTH_SHORT).show();
                //launchAbout();
                break;
            case R.id.action_help:
                Toast.makeText(this, "go help", Toast.LENGTH_SHORT).show();
                //launchHelp();
                break;
            case R.id.action_exit:
                finish();
                Toast.makeText(this, "Hasta la vista, baby...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_search:
                launchSearch();
                break;
            case R.id.action_customer:
                launchNew();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void launchNew() {
        //lanzar el activity
        Intent i = new Intent(this, NewActivity.class);
        startActivity(i);
    }

    private void launchSearch() {
        //lanzar el activity
        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);
    }
}