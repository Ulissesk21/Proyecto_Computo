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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    EditText textInputNombre;
    EditText textInputEmail;
    EditText textInputPhone;
    EditText textInputContraseña;
    EditText textInputConfircontra;
    Button btn_registar;

    FirebaseFirestore db;
    DocumentReference ref;

    Connection connect;
    String ConnectionResult="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db=FirebaseFirestore.getInstance();

        btn_registar=findViewById(R.id.registrar);
        textInputNombre=findViewById(R.id.nombre);
        textInputEmail=findViewById(R.id.email);
        textInputPhone=findViewById(R.id.numero);
        textInputContraseña=findViewById(R.id.contraseña);
        textInputConfircontra=findViewById(R.id.confircontra);

        btn_registar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!validatenombre(textInputNombre) | !validateemail(textInputEmail) | !validatephone(textInputPhone) | !validatecontraseña(textInputContraseña) | !validateconfircontra(textInputConfircontra))
                {
                    return;
                }else {
                    //get all the values
                    String Nombre= textInputNombre.getText().toString();
                    String Email=textInputEmail.getText().toString();
                    String Telefono= textInputPhone.getText().toString();
                    String Contraseña= textInputConfircontra.getText().toString();
                    Map<String,Object> empleado=new HashMap<>();
                    empleado.put("Nombre",Nombre);
                    empleado.put("Email",Email);
                    empleado.put("Telefono",Telefono);
                    empleado.put("Contraseña",Contraseña);

                    db.collection("Empleados")
                            .add(empleado)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(login.this, "Se Registro Corerctamente", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(login.this, "Error:No se pudo registrar", Toast.LENGTH_SHORT).show();
                        }
                    });

                    isUser();
                }
            }
        });
    }

    private boolean validatenombre(EditText textInputNombre){
        String nombre = textInputNombre.getText().toString();
        if(nombre.isEmpty()){
            textInputNombre.setError("El campo no puede estar vacío");
            return false;
        }else{
            textInputNombre.setError(null);
            return true;
        }
    }

    private boolean validateemail(EditText textInputEmail){
        String email = textInputEmail.getText().toString();
        if(email.isEmpty()){
            textInputEmail.setError("El campo no puede estar vacío");
            return false;
        }else{
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validatephone(EditText textInputPhone){
        String numero = textInputPhone.getText().toString();
        if(numero.isEmpty()){
            textInputPhone.setError("El campo no puede estar vacío");
            return false;
        }else{
            textInputPhone.setError(null);
            return true;
        }
    }

    private boolean validatecontraseña(EditText textInputContraseña){
        String contraseña = textInputContraseña.getText().toString();
        if(contraseña.isEmpty()){
            textInputContraseña.setError("El campo no puede estar vacío");
            return false;
        }else{
            textInputContraseña.setError(null);
            return true;
        }
    }

    private boolean validateconfircontra(EditText textInputConfircontra){
        String contraseña = textInputContraseña.getText().toString();
        String confircontra = textInputConfircontra.getText().toString();
        if (confircontra.isEmpty()){
            textInputConfircontra.setError("El campo no puede estar vacío");
            return false;
        }
        if (!confircontra.equals(contraseña)){
            textInputConfircontra.setError("El campo es incorrecto");
            return false;
        }else{
            textInputConfircontra.setError(null);
            return true;
        }
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
                                String a1=textInputNombre.getText().toString().trim();
                                String b1=textInputConfircontra.getText().toString().trim();
                                if(a.equalsIgnoreCase(a1))
                                {
                                    if (b.equalsIgnoreCase(b1)){

                                        String nombreDB=doc.getString("Nombre");
                                        String emailDB=doc.getString("Email");
                                        String telefonoDB=doc.getString("Telefono");
                                        String contraseñaDB=doc.getString("Contraseña");

                                        Intent intent=new Intent(getApplicationContext(),activity_menu.class);

                                        intent.putExtra("Nombre",nombreDB);
                                        intent.putExtra("Email",emailDB);
                                        intent.putExtra("Telefono",telefonoDB);
                                        intent.putExtra("Contraseña",contraseñaDB);

                                        startActivity(intent);
                                        Toast.makeText(login.this, "Logged In", Toast.LENGTH_SHORT).show();
                                    }
                                    else {textInputConfircontra.setError("Contraseña incorrecta");}
                                }
                                else{textInputNombre.setError("Nombre Incorrecto");}
                            }

                        }
                    }
                });
    }


}


