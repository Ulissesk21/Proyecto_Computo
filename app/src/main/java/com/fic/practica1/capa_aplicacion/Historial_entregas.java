package com.fic.practica1.capa_aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fic.practica1.R;
import com.fic.practica1.capa_aplicacion.activity_historial;

public class Historial_entregas extends AppCompatActivity {

    TextView Nombre,Direccion,Telefono,Producto,Unidades,Estatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_entregas);

        Nombre=findViewById(R.id.Historial_Nombre);
        Direccion=findViewById(R.id.Historial_Direccion);
        Telefono=findViewById(R.id.Historial_Telefono);
        Producto=findViewById(R.id.Historial_Producto);
        Unidades=findViewById(R.id.Historial_Unidades);
        Estatus=findViewById(R.id.Historial_Estatus);
        showAllData();



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

}