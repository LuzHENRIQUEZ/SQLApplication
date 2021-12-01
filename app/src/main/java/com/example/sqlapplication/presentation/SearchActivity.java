package com.example.sqlapplication.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlapplication.R;
import com.example.sqlapplication.data.AdapterCustomer;
import com.example.sqlapplication.db.DBCustomer;
import com.example.sqlapplication.model.Customer;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    //arreglo para llenar el recyclerView
    ArrayList<Customer> listCustomers;
    RecyclerView rcvCustomers;
    AdapterCustomer adapter;
    EditText edtValor;

    //administrador de la base de datos
    DBCustomer admin = new DBCustomer(this, "dbcustomers", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //vincular las vistas
        edtValor = findViewById(R.id.edtValor);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_back:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchCustomer(View view){

        //para el RecyclerView
        listCustomers = new ArrayList<Customer>();
        rcvCustomers = findViewById(R.id.rcvCustomers);

        //administrar el RecyclerView
        rcvCustomers.setLayoutManager(new LinearLayoutManager(this));

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

    private void fillCustomersList() {
        //objeto administrador de la bd
        SQLiteDatabase db = admin.getWritableDatabase();

        //para tomar cada registro de la consulta
        Customer customer = null;

        //consulta SQLite
        Cursor cursor = db.rawQuery("SELECT * FROM customers WHERE idcustomer = " + edtValor.getText().toString(), null);

        if(cursor.getCount() == 0){
            Toast.makeText(getBaseContext(), R.string.msg_search, Toast.LENGTH_SHORT).show();
        }else{
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
    }
}