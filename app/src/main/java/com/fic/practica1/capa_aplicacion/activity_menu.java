package com.fic.practica1.capa_aplicacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.core.widget.TintableCheckedTextView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.NativeActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fic.practica1.R;
import com.fic.practica1.capa_aplicacion.activity_entregas_disponibles;
import com.fic.practica1.capa_aplicacion.activity_historial;
import com.fic.practica1.capa_aplicacion.activity_informacion;
import com.fic.practica1.capa_aplicacion.activity_perfil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class activity_menu extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    TextView Nombre, Email;
    ImageView sidemenu;
    DrawerLayout drawer;
    NavigationView navigationView;
    CardView perfil, Entregas,Historial,Acerca;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        db=FirebaseFirestore.getInstance();

        Nombre = findViewById(R.id.textView1);
        Email = findViewById(R.id.textView2);
        sidemenu=findViewById(R.id.nav_drawer);
        drawer=findViewById(R.id.drawer);

        navigationView=findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        showAllData();

        perfil = (CardView) findViewById(R.id.perfil);
        Entregas = (CardView) findViewById(R.id.Entregas);
        Historial=(CardView) findViewById(R.id.historial);
        Acerca=(CardView)findViewById(R.id.Acerca);

        sidemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                drawer.openDrawer(GravityCompat.START);
            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
               isUser();
            }
        });

        Entregas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showActivity = new Intent(getApplicationContext(), activity_entregas_disponibles.class);
                startActivity(showActivity);
            }
        });

        Historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showActivity = new Intent(getApplicationContext(), activity_historial.class);
                startActivity(showActivity);
            }
        });

        Acerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showActivity = new Intent(getApplicationContext(), activity_informacion.class);
                startActivity(showActivity);
            }
        });


    }

    private void showAllData() {
        Intent intent=getIntent();
        String nombre_usuario=intent.getStringExtra("Nombre");
        String email_usuario=intent.getStringExtra("Email");


        Nombre.setText(nombre_usuario);
        Email.setText(email_usuario);

        TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.slidename);
        txtProfileName.setText(nombre_usuario);

        TextView txtProfileemail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.slideemail);
        txtProfileemail.setText(email_usuario);


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
                                    Intent intent=new Intent(getApplicationContext(), activity_perfil.class);

                                    intent.putExtra("Nombre",nombreDB);
                                    intent.putExtra("Email",emailDB);
                                    intent.putExtra("Telefono",telefonoDB);
                                    intent.putExtra("Contraseña",contraseñaDB);
                                    startActivity(intent);

                                }
                            }

                        }
                    }
                });

    }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.home_menu:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.perfil_menu:
                isUser();
                break;
            case R.id.salir_menu:
                Intent showActivity2 = new Intent(getApplicationContext(), activity_ingreso.class);
                startActivity(showActivity2);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}