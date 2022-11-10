package com.example.ramirez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
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
    private List<Photographer> photographers;
    private PhotographerRecyclerViewAdapter adapter;

    String[] specializationsList = new String[]{"Nenhum", "Publicidade", "Arquitetura", "Revistas", "Moda", "Jornalismo", "Astronomia", "Forense", "Comercial", "Industrial", "Natureza", "Subaquático", "Cientifico", "Aerofotografia", "Documentarista", "Eróticas", "Sensuais", "Animais", "Books", "Crianças", "Esportes", "Medicina", "Produtos", "Cinema",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        specializationSpinner = findViewById(R.id.specializationSpinner);
        photographersRecyclerView = findViewById(R.id.photographersRecyclerView);

        // iniciando a lista
        this.photographers = new ArrayList<>();

        ArrayList<String> specializations = new ArrayList<>();
        ArrayList<String> specializations2 = new ArrayList<>();
        ArrayList<String> specialization3 = new ArrayList<>();
        specializations.add("Jornalismo");
        specializations.add("Publicidade");
        specializations2.add("Moda");
        specialization3.add("Astronomia");

        Photographer photographer = new Photographer("1", "Jéssica Gomez", "SP", "Santos", "123", specializations, new Float[]{20f, 30f});
        this.photographers.add(photographer);

        photographer = new Photographer("2", "Lucas Gomez", "SP", "Santos", "123", specializations2, new Float[]{40f, 50f});
        this.photographers.add(photographer);

        photographer = new Photographer("3", "Matheus Gomez", "SP", "Santos", "123", specialization3, new Float[]{35f, 52f});
        this.photographers.add(photographer);

        this.adapter = new PhotographerRecyclerViewAdapter(this.photographers);

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
                this, android.R.layout.simple_spinner_item, specializationsList){

            @Override
            public boolean isEnabled(int position){
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {

                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

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

        filterPhotographerByName();
        filterPhotographerBySpecialization();
        filterListByPrices();
    }

    public void filterPhotographerByName() {
        SearchView inputName = findViewById(R.id.editTextTextPersonName);
        inputName.clearFocus();
        inputName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return false;
            }
        });
    }

    public void filterPhotographerBySpecialization() {
        Spinner specializationSpinner = findViewById(R.id.specializationSpinner);
        specializationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String specialization = adapterView.getSelectedItem().toString();
                filterListBySpecialization(specialization);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void filterListBySpecialization(String text) {
        if (text.contains("Nenhum")) {
            this.adapter.setPhotographers(this.photographers);
            return;
        }
        List<Photographer> filteredList = new ArrayList<>();
        for (Photographer photographer : this.photographers) {
            if (photographer.getSpecializations().contains(text)) {
                filteredList.add(photographer);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Nenhum dado encontrado", Toast.LENGTH_SHORT).show();
        } else {
            this.adapter.setPhotographers(filteredList);
        }
    }

    public void filterListByPrices() {
        EditText minValue = findViewById(R.id.minValue);
        EditText maxValue = findViewById(R.id.maxValue);
        Button searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Photographer> filteredList = new ArrayList<>();

                if (minValue.getText().toString().isEmpty() || maxValue.getText().toString().isEmpty()) {
                    adapter.setPhotographers(photographers);
                    return;
                }

                Float min = Float.parseFloat(minValue.getText().toString());
                Float max = Float.parseFloat(maxValue.getText().toString());

                for (Photographer photographer : photographers) {
                    if (min >= photographer.getMinValue() && max <= photographer.getMaxValue()) {
                        filteredList.add(photographer);
                    }
                }

                if (filteredList.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nenhum dado encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.setPhotographers(filteredList);
                }
            }
        });
    }

    public void filterList(String text) {
        List<Photographer> filteredList = new ArrayList<>();
        for (Photographer photographer : this.photographers) {
            if (photographer.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(photographer);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Nenhum dado encontrado", Toast.LENGTH_SHORT).show();
        } else {
            this.adapter.setPhotographers(filteredList);
        }
    }
}