package com.fic.practica1.capa_aplicacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fic.practica1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class activity_perfil extends AppCompatActivity {

    TextView usuarionombre,usuarioemail;
    EditText Nombre,Email,Telefono,Contraseña;
    FirebaseFirestore db;

    String nombre_usuario;
    String email_usuario;
    String telefono_usuario;
    String contraseña_usuario;
    Button actualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        db=FirebaseFirestore.getInstance();

        usuarionombre=findViewById(R.id.nombreusuario);
        usuarioemail=findViewById(R.id.usuarioemail);

        Nombre=findViewById(R.id.nombreperfil);
        Email=findViewById(R.id.emailperfil);
        Telefono=findViewById(R.id.numeroperfil);
        Contraseña=findViewById(R.id.contraseñaperfil);
        actualizar=findViewById(R.id.actualizar);



        showAllData();


        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nombre_act=Nombre.getText().toString();
                String Email_act=Email.getText().toString();
                String Telefono_act=Telefono.getText().toString();
                String Contraseña_act=Contraseña.getText().toString();
                updatedata(Nombre_act,Email_act,Telefono_act,Contraseña_act);
            }

        });

    }

    private void updatedata(String nombre_act, String email_act, String telefono_act, String contraseña_act) {
        String Nombre_Actualizar=usuarionombre.getText().toString();
        Map<String,Object> userdetail=new HashMap<>();
        userdetail.put("Nombre",nombre_act);
        userdetail.put("Email",email_act);
        userdetail.put("Telefono",telefono_act);
        userdetail.put("Contraseña",contraseña_act);

        db.collection("Empleados")
                .whereEqualTo("Nombre",Nombre_Actualizar)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !task.getResult().isEmpty()){
                    DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                    String documentID=documentSnapshot.getId();
                    db.collection("Empleados")
                            .document(documentID)
                            .update(userdetail)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(@NonNull Void unused) {
                                    Toast.makeText(activity_perfil.this,"Actualizado Correctamente",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(activity_perfil.this,"Ocurrio un Error",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void showAllData() {
        Intent intent=getIntent();
        nombre_usuario=intent.getStringExtra("Nombre");
        email_usuario=intent.getStringExtra("Email");
        telefono_usuario=intent.getStringExtra("Telefono");
        contraseña_usuario=intent.getStringExtra("Contraseña");

        usuarionombre.setText(nombre_usuario);
        usuarioemail.setText(email_usuario);

        Nombre.setText(nombre_usuario);
        Email.setText(email_usuario);
        Telefono.setText(telefono_usuario);
        Contraseña.setText(contraseña_usuario);
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
                                String a1=Nombre.getText().toString().trim();
                                if(a.equalsIgnoreCase(a1)) {
                                    String nombreDB=doc.getString("Nombre");
                                    String emailDB=doc.getString("Email");
                                    String telefonoDB=doc.getString("Telefono");
                                    String contraseñaDB=doc.getString("Contraseña");
                                    Intent intent_perfil=new Intent(getApplicationContext(), activity_menu.class);

                                    intent_perfil.putExtra("Nombre",nombreDB);
                                    intent_perfil.putExtra("Email",emailDB);
                                    intent_perfil.putExtra("Telefono",telefonoDB);
                                    intent_perfil.putExtra("Contraseña",contraseñaDB);
                                    startActivity(intent_perfil);

                                }
                            }

                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {
        isUser();
    }


}