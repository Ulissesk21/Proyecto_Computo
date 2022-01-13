package com.fic.practica1.capa_aplicacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fic.practica1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class activity_ingreso extends AppCompatActivity {

    EditText Nombre;
    EditText Contraseña;
    Button btn_registar;
    Button crear_cuenta;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);

        crear_cuenta=findViewById(R.id.crearcuenta);
        btn_registar=findViewById(R.id.registrar);
        Nombre=findViewById(R.id.nombre);
        Contraseña= findViewById(R.id.contraseña);

        db=FirebaseFirestore.getInstance();

        btn_registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatenombre(Nombre) | !validatecontraseña(Contraseña)) {
                    return;
                }else {
                    isUser();
                }
            }

        });

        crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showActivity = new Intent(getApplicationContext(), login.class);
                startActivity(showActivity);
            }
        });


    }

    private void isUser() {

        db.collection("Empleados")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                String a=doc.getString("Nombre");
                                String b=doc.getString("Contraseña");
                                String a1=Nombre.getText().toString().trim();
                                String b1=Contraseña.getText().toString().trim();
                                if(a.equalsIgnoreCase(a1)) {
                                    if (b.equalsIgnoreCase(b1))
                                    {
                                        String nombreDB=doc.getString("Nombre");
                                        String emailDB=doc.getString("Email");
                                        String telefonoDB=doc.getString("Telefono");
                                        String contraseñaDB=doc.getString("Contraseña");

                                        Intent intent=new Intent(getApplicationContext(), activity_menu.class);

                                        intent.putExtra("Nombre",nombreDB);
                                        intent.putExtra("Email",emailDB);
                                        intent.putExtra("Telefono",telefonoDB);
                                        intent.putExtra("Contraseña",contraseñaDB);

                                        startActivity(intent);

                                        Toast.makeText(activity_ingreso.this, "Logged In", Toast.LENGTH_SHORT).show();
                                        break;

                                    }else {Contraseña.setError("Contraseña incorrecta");}
                                }else{Nombre.setError("Nombre Incorrecto");}

                            }

                        }
                    }
                });

    }

    private boolean validatenombre(EditText Nombre){
        String nombre = Nombre.getText().toString();
        if(nombre.isEmpty()){
            Nombre.setError("El campo no puede estar vacío");
            return false;
        }else{
            Nombre.setError(null);
            return true;
        }
    }

    private boolean validatecontraseña(EditText Contraseña){
        String contraseña = Contraseña.getText().toString();
        if(contraseña.isEmpty()){
            Contraseña.setError("El campo no puede estar vacío");
            return false;
        }else{
            Contraseña.setError(null);
            return true;
        }
    }





}