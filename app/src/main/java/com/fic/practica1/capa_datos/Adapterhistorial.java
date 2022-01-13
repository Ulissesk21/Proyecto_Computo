package com.fic.practica1.capa_datos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fic.practica1.R;
import com.fic.practica1.capa_aplicacion.activity_detalle_entrega;

import java.util.ArrayList;

public class Adapterhistorial extends RecyclerView.Adapter<Adapterhistorial.MyViewHolder2>{
    Context context;
    ArrayList<Entregas> listentregas;

    public Adapterhistorial(Context context, ArrayList<Entregas> listentregas){
        this.context=context;
        this.listentregas=listentregas;
    }


    @NonNull
    @Override
    public Adapterhistorial.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.cardview_historial,parent,false);
        return new Adapterhistorial.MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapterhistorial.MyViewHolder2 holder, int position) {
        Entregas entrega=listentregas.get(position);
        holder.Nombre_Cliente.setText(entrega.getNombre());
        holder.Direccion_Cliente.setText(entrega.getDireccion());

        holder.Detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(holder.Detalles.getContext(), activity_detalle_entrega.class);

                intent.putExtra("Nombre",entrega.getNombre());
                intent.putExtra("Direccion",entrega.getDireccion());
                intent.putExtra("Telefono",entrega.getTelefono());
                intent.putExtra("Producto",entrega.getProducto());
                intent.putExtra("Unidades",entrega.getUnidades());
                intent.putExtra("Estatus",entrega.getEstatus());
                holder.Detalles.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return listentregas.size();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView Nombre_Cliente;
        TextView Direccion_Cliente;
        Button Detalles;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            Nombre_Cliente=itemView.findViewById(R.id.Nombre_Card);
            Direccion_Cliente=itemView.findViewById(R.id.Direccion_Card);
            Detalles=itemView.findViewById(R.id.ver_detalles_Card);
        }
    }

}
