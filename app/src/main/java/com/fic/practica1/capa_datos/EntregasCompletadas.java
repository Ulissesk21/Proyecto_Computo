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

import com.fic.practica1.capa_aplicacion.Historial_entregas;
import com.fic.practica1.R;

import java.util.ArrayList;

public class EntregasCompletadas extends RecyclerView.Adapter<EntregasCompletadas.MyViewHolder>{
    Context context;
    ArrayList<Entregas> listentregas;

    public EntregasCompletadas(Context context,ArrayList<Entregas> listentregas){
        this.context=context;
        this.listentregas=listentregas;
    }


    @NonNull
    @Override
    public EntregasCompletadas.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_item_historial,parent,false);
        return new EntregasCompletadas.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EntregasCompletadas.MyViewHolder holder, int position) {
        Entregas entrega=listentregas.get(position);
        holder.Nombre_Historial.setText(entrega.getNombre());
        holder.Direccion_Historial.setText(entrega.getDireccion());

        holder.Detalles_Historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(holder.Detalles_Historial.getContext(), Historial_entregas.class);

                intent.putExtra("Nombre",entrega.getNombre());
                intent.putExtra("Direccion",entrega.getDireccion());
                intent.putExtra("Telefono",entrega.getTelefono());
                intent.putExtra("Producto",entrega.getProducto());
                intent.putExtra("Unidades",entrega.getUnidades());
                intent.putExtra("Estatus",entrega.getEstatus());
                holder.Detalles_Historial.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return listentregas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Nombre_Historial;
        TextView Direccion_Historial;
        Button Detalles_Historial;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Nombre_Historial=itemView.findViewById(R.id.Nombre_Historial);
            Direccion_Historial=itemView.findViewById(R.id.Direccion_Historial);
            Detalles_Historial=itemView.findViewById(R.id.Detalle_Historial);
        }
    }

}
