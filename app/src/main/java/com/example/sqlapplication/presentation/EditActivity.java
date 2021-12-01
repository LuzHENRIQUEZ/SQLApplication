package com.example.sqlapplication.presentation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.sqlapplication.R;
import com.example.sqlapplication.db.DBCustomer;

public class EditActivity extends AppCompatActivity {

    //objetos
    EditText edtId, edtName, edtPhone, edtAddress, edtEmail;
    RadioButton rdbMasculino, rdbFemenino;

    //administrador de la bd
    SQLiteDatabase db;

    //para el género
    String gender;

    //administrador de la base de datos
    DBCustomer admin = new DBCustomer(this, "dbcustomers", null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //vincular las vistas
        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        edtEmail = findViewById(R.id.edtEmail);
        rdbFemenino= findViewById(R.id.rdbFemenino);
        rdbMasculino= findViewById(R.id.rdbMasculino);

        //para tomar los datos enviados
        Bundle bundle = getIntent().getExtras();

        //llenar el formulario
        edtId.setText(String.valueOf(bundle.getInt("idcustomer")));
        edtName.setText(bundle.getString("name"));
        edtPhone.setText(bundle.getString("phone"));
        edtAddress.setText(bundle.getString("address"));
        edtEmail.setText(bundle.getString("email"));

        if (bundle.getString("gender").equals("Masculino")){
            rdbMasculino.setChecked(true);
        }else{
            rdbFemenino.setChecked(true);
        }

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


    //editar
    public void editCustomer(View view){
        try {
            //objeto administrador de la bd
            db = admin.getWritableDatabase();

            //para editar
            ContentValues data = new ContentValues();

            if(edtId.getText().toString().isEmpty() || edtName.getText().toString().isEmpty() || edtPhone.getText().toString().isEmpty()
                    || edtAddress.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty()) {
                //pedir todos los datos
                Toast.makeText(this, getString(R.string.msg_data), Toast.LENGTH_SHORT).show();
            }else{

                //recolectar los datos para el comando SQL
                data.put("name", edtName.getText().toString());
                data.put("phone", edtPhone.getText().toString());
                data.put("address", edtAddress.getText().toString());
                data.put("email", edtEmail.getText().toString());
                //seleccionar el sexo
                if (rdbMasculino.isChecked()) {
                    gender = getString(R.string.formnew_gender_m);
                } else {
                    gender = getString(R.string.formnew_gender_f);
                }
                data.put("gender", gender);

                //actualizar
                db.update("customers",data,"idcustomer = " + edtId.getText().toString(),null);

                //cerrar la conexión
                db.close();

                Toast.makeText(this, getString(R.string.msg_save),
                        Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            Toast.makeText(this, getString(R.string.msg_error) + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    //para borrar
    public void deleteCustomer(View view){
        //para el mensaje emergente
        AlertDialog.Builder alertEliminar = new AlertDialog.Builder(this);
        alertEliminar.setTitle("ELIMINAR CLIENTE:");
        alertEliminar.setMessage("¿Seguro que desea eliminar?");
        alertEliminar.setCancelable(true);
        alertEliminar.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try {
                    //objeto administrador de la bd
                    db = admin.getWritableDatabase();

                    //para editar
                    ContentValues data = new ContentValues();

                    //eliminar
                    db.delete("customers","idcustomer = " + edtId.getText().toString(),null);

                    //cerrar la conexión
                    db.close();

                    Toast.makeText(getApplicationContext(), getString(R.string.msg_delete), Toast.LENGTH_LONG).show();

                    finish();
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), getString(R.string.msg_error) + ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        alertEliminar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Sin acción", Toast.LENGTH_SHORT).show();
            }
        });

        alertEliminar.show();
    }
}