package com.fic.practica1.capa_aplicacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fic.practica1.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class activity_mapa extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    TextView Direccion,Distancia;
    String direccion_entrega;
    Location currentLocation;
    MarkerOptions place1,place2;
    Address address;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    Button terminar_entrega;
    ApiInterface apiInterface;
    SupportMapFragment mapFragment;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        db=FirebaseFirestore.getInstance();
        terminar_entrega=findViewById(R.id.btn_terminar);

        terminar_entrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEntrega();
                Eliminar();
                ShowDialog();
            }
        });
        Direccion=findViewById(R.id.direccion);
        direccion_entrega=Direccion.getText().toString();

        Distancia=findViewById(R.id.distancia);

        showAllData();

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLasLocation();
        geocoder=new Geocoder(this);


    }

    public void ShowDialog(){
        LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.activity_dialog,null);
        AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setView(view).create();
        alertDialog.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        direccion_entrega=Direccion.getText().toString();
        mMap=googleMap;

        mMap.setMinZoomPreference(15f);
        mMap.setMaxZoomPreference(20f);
        mMap.setTrafficEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        try {
            List<Address> addresses= geocoder.getFromLocationName(""+direccion_entrega+"",1);
            if(addresses.size()>0)
            {
                address =addresses.get(0);
                LatLng london=new LatLng(address.getLatitude(),address.getLongitude());
                MarkerOptions markerOptions2=new MarkerOptions().position(london).title(address.getLocality());
                place2 = new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongitude())).title(address.getLocality());
                mMap.addMarker(place2);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(london,16));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        LatLng latLng=new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions1=new MarkerOptions().position(latLng).title("Estoy Aqui");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
        place1 = new MarkerOptions().position(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude())).title("Estoy aqui");
        mMap.addMarker(place1);


        float results[]=new float[5];
        Location.distanceBetween(currentLocation.getLatitude(),currentLocation.getLongitude(),address.getLatitude(),address.getLongitude(),results);
        Distancia.setText((results[0]/1000)+"Km");
    }

    private void fetchLasLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(@NonNull Location location) {
                if(location != null)
                {
                    currentLocation=location;
                    Toast.makeText(getApplicationContext(),currentLocation.getLatitude()+""+currentLocation.getLongitude(),Toast.LENGTH_SHORT).show();
                    mapFragment.getMapAsync(activity_mapa.this);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLasLocation();
                }
                break;
        }
    }

    private void showAllData() {
        Intent intent=getIntent();
        String Direccion_entrega=intent.getStringExtra("Direccion");

        Direccion.setText(Direccion_entrega);

    }


    private void isEntrega() {

        db.collection("Entregas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                String a=doc.getString("Direccion");
                                String a1=Direccion.getText().toString().trim();
                                if(a.equalsIgnoreCase(a1)) {
                                    String nombreDB=doc.getString("Nombre");
                                    String direccionDB=doc.getString("Direccion");
                                    String telefonoDB=doc.getString("Telefono");
                                    String productoDB=doc.getString("Producto");
                                    String unidadesDB=doc.getString("Unidades");
                                    String estatusDB="Completado";


                                    Map<String,Object> Entrega_completada=new HashMap<>();
                                    Entrega_completada.put("Nombre",nombreDB);
                                    Entrega_completada.put("Direccion",direccionDB);
                                    Entrega_completada.put("Telefono",telefonoDB);
                                    Entrega_completada.put("Producto",productoDB);
                                    Entrega_completada.put("Unidades",unidadesDB);
                                    Entrega_completada.put("Estatus",estatusDB);

                                    db.collection("Entregas Completadas")
                                            .add(Entrega_completada)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(activity_mapa.this, "Se Registro Corerctamente", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(activity_mapa.this, "Error:No se pudo registrar", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }

                        }
                    }
                });

    }


    private void Eliminar() {

        db.collection("Entregas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                String a=doc.getString("Direccion");
                                String a1=Direccion.getText().toString().trim();
                                if(a.equalsIgnoreCase(a1)) {
                                    String id=doc.getId();
                                    db.collection("Entregas")
                                            .document(id).delete();

                                }
                            }

                        }
                    }
                });

    }


}