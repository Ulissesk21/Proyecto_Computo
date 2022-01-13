package com.fic.practica1.capa_aplicacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fic.practica1.capa_datos.Entregas;
import com.fic.practica1.R;
import com.fic.practica1.capa_datos.EntregasCompletadas;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class activity_historial extends AppCompatActivity {


    RecyclerView recycler;
    ArrayList<Entregas> datalist;
    FirebaseFirestore db;
    EntregasCompletadas entregasCompletadas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);


        recycler=(RecyclerView) findViewById(R.id.Historial_Entregas);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        datalist=new ArrayList<>();
        entregasCompletadas=new EntregasCompletadas(this,datalist);
        recycler.setAdapter(entregasCompletadas);


        db=FirebaseFirestore.getInstance();
        db.collection("Entregas Completadas").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> List=queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d: List)
                        {
                            Entregas entregas=d.toObject(Entregas.class);
                            datalist.add(entregas);
                        }
                        entregasCompletadas.notifyDataSetChanged();
                    }
                });

    }
}