package com.fic.practica1.capa_aplicacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fic.practica1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class activity_detalle_entrega extends AppCompatActivity {

    TextView Nombre,Direccion,Telefono,Producto,Unidades,Estatus;
    Button llamar;
    Button mapa;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_entrega);

        db=FirebaseFirestore.getInstance();

        Nombre=findViewById(R.id.nombreclient);
        Direccion=findViewById(R.id.direccionclient);
        Telefono=findViewById(R.id.telclient);
        Producto=findViewById(R.id.productoclient);
        Unidades=findViewById(R.id.cantidad);
        Estatus=findViewById(R.id.estatusclient);
        llamar=findViewById(R.id.llamar);
        mapa=findViewById(R.id.mapa);

        showAllData();

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero=Telefono.getText().toString();
                Intent i= new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+numero));
                startActivity(i);
            }
        });


        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEntrega();
            }
        });

    }


    private void showAllData() {

        Intent intent=getIntent();
        String nombre_cliente=intent.getStringExtra("Nombre");
        String direccion_cliente=intent.getStringExtra("Direccion");
        String telefono_cliente=intent.getStringExtra("Telefono");
        String producto_cliente=intent.getStringExtra("Producto");
        String unidades=intent.getStringExtra("Unidades");
        String estatus_entrega=intent.getStringExtra("Estatus");

        Nombre.setText(nombre_cliente);
        Direccion.setText(direccion_cliente);
        Telefono.setText(telefono_cliente);
        Producto.setText(producto_cliente);
        Unidades.setText(unidades);
        Estatus.setText(estatus_entrega);

    }

    private void isEntrega() {

        db.collection("Entregas")
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
                                    String direccionDB=doc.getString("Direccion");
                                    String telefonoDB=doc.getString("Telefono");
                                    String productoDB=doc.getString("Producto");
                                    String unidadesDB=doc.getString("Unidades");
                                    String estatusDB=doc.getString("Estatus");

                                    Intent intent=new Intent(getApplicationContext(), activity_mapa.class);

                                    intent.putExtra("Nombre",nombreDB);
                                    intent.putExtra("Direccion",direccionDB);
                                    intent.putExtra("Telefono",telefonoDB);
                                    intent.putExtra("Producto",productoDB);
                                    intent.putExtra("Unidades",unidadesDB);
                                    intent.putExtra("Estatus",estatusDB);
                                    startActivity(intent);

                                }
                            }

                        }
                    }
                });

    }

}