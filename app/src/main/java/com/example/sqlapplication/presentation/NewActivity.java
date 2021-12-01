package com.example.sqlapplication.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

public class NewActivity extends AppCompatActivity {

    //objetos
    EditText edtName, edtPhone, edtAddress, edtEmail;
    RadioButton rdbMasculino, rdbFemenino;

    //para el género
    String gender;

    //administrador de la base de datos
    DBCustomer admin = new DBCustomer(this, "dbcustomers", null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        //vincular las vistas
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        edtEmail = findViewById(R.id.edtEmail);
        rdbFemenino= findViewById(R.id.rdbFemenino);
        rdbMasculino= findViewById(R.id.rdbMasculino);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new, menu);
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
            case R.id.action_clean:
                clean();
                break;
            case R.id.action_back:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clean() {
        edtName.setText("");
        edtPhone.setText("");
        edtAddress.setText("");
        edtEmail.setText("");
        rdbMasculino.setChecked(true);
        edtName.requestFocus();

        Toast.makeText(this, getString(R.string.msg_clean), Toast.LENGTH_SHORT).show();
    }

    //guardar
    public void saveNew(View view){
        try {
            //objeto administrador de la bd
            SQLiteDatabase db = admin.getWritableDatabase();

            //para guardar
            ContentValues data = new ContentValues();

            if(edtName.getText().toString().isEmpty() || edtPhone.getText().toString().isEmpty()
                    || edtAddress.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty()) {
                //pedir todos los datos
                Toast.makeText(this, getString(R.string.msg_data), Toast.LENGTH_SHORT).show();
            }else{
                //recolectar los datos
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

                //insertar
                db.insert("customers", null, data);
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
}