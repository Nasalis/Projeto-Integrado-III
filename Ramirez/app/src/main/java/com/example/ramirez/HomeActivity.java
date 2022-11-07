package com.example.ramirez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramirez.adapter.PhotographerRecyclerViewAdapter;
import com.example.ramirez.helpers.RecyclerItemClickListener;
import com.example.ramirez.model.Photographer;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private Spinner specializationSpinner;
    private RecyclerView photographersRecyclerView;
    String[] specializations = new String[]{"Escolher espacialização", "Especialização 1", "Especialização 2", "Especialização 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        specializationSpinner = findViewById(R.id.specializationSpinner);
        photographersRecyclerView = findViewById(R.id.photographersRecyclerView);

        // iniciando a lista
        List<Photographer> photographers = new ArrayList<>();
        ArrayList<String> specializations = new ArrayList<>();
        specializations.add("Jornalistico");

        Photographer photographer = new Photographer("1", "Jéssica Gomez", "SP", "Santos", "123", specializations);
        photographers.add(photographer);

        photographer = new Photographer("2", "Lucas Gomez", "SP", "Santos", "123", specializations);
        photographers.add(photographer);

        photographer = new Photographer("3", "Matheus Gomez", "SP", "Santos", "123", specializations);
        photographers.add(photographer);

        PhotographerRecyclerViewAdapter adapter = new PhotographerRecyclerViewAdapter(photographers);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        photographersRecyclerView.setLayoutManager(layoutManager);
        photographersRecyclerView.hasFixedSize();
        photographersRecyclerView.setAdapter(adapter);

        photographersRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        photographersRecyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(HomeActivity.this,"item selecionado: ", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, specializations){

            @Override
            public boolean isEnabled(int position){
                if(position == 0){
                    // Disabilita a primeira posição (hint)
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {

                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                if(position == 0){

                    // Deixa o hint com a cor cinza ( efeito de desabilitado)
                    tv.setTextColor(Color.GRAY);

                }else {
                    tv.setTextColor(Color.BLACK);
                }

                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specializationSpinner.setAdapter(spinnerArrayAdapter);
        specializationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelected =  adapterView.getSelectedItem().toString();
                Toast.makeText(HomeActivity.this,"item selecionado: " + itemSelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}