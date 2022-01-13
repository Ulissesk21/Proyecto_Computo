package com.fic.practica1.capa_aplicacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.fic.practica1.capa_datos.Entregas;
import com.fic.practica1.R;
import com.fic.practica1.capa_datos.MyAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class activity_entregas_disponibles extends AppCompatActivity {

    RecyclerView recycler;
    ArrayList<Entregas>datalist;
    FirebaseFirestore db;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entregas_disponibles);

        recycler=(RecyclerView) findViewById(R.id.Entregas_lista);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        datalist=new ArrayList<>();
        adapter=new MyAdapter(this,datalist);
        recycler.setAdapter(adapter);

        db=FirebaseFirestore.getInstance();
        db.collection("Entregas").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot>List=queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d: List)
                        {
                            Entregas entregas=d.toObject(Entregas.class);
                            datalist.add(entregas);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }





}














