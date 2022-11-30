package com.example.ramirez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ramirez.adapter.PhotographerRecyclerViewAdapter;
import com.example.ramirez.helpers.RecyclerItemClickListener;
import com.example.ramirez.helpers.SessionManager;
import com.example.ramirez.helpers.UsersService;
import com.example.ramirez.model.Photographer;
import com.example.ramirez.services.SpecializationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    private List<Photographer> photographers = new ArrayList<>();
    private PhotographerRecyclerViewAdapter adapter = new PhotographerRecyclerViewAdapter(new ArrayList<>());

    String[] specializationsList = {"Nenhum","Eventos Sociais","Publicidade","Arquitetura","Revistas","Moda","Jornalismo","Astronomia","Forense","Comercial","Industrial","Natureza","Subaquático","Cientifico","Documentos Oficiais","Aerofotografia","Documentarista","Nu Artístico","Modelos Dental","Eróticas","Sensuais","Animais","Books","Crianças","Esportes","Medicina","Festas Infantis","Produtos","Abstrata e Artística"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_home);

        Spinner specializationSpinner = findViewById(R.id.specializationSpinner);
        RecyclerView photographersRecyclerView = findViewById(R.id.photographersRecyclerView);

        SessionManager sessionManager = new SessionManager(this);
        UsersService usersService = new UsersService(sessionManager);
        SpecializationService specializationService = new SpecializationService(sessionManager);

        photographers = usersService.getPhotographersByDatabase();
        adapter = new PhotographerRecyclerViewAdapter(photographers);

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
                                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                                intent.putExtra("PROFILE_ID", photographers.get(position).getId());
                                startActivity(intent);
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
                                        @NonNull ViewGroup parent) {

                return super.getDropDownView(position, convertView, parent);
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

        searchButton.setOnClickListener(view -> {
            List<Photographer> filteredList = new ArrayList<>();

            if (minValue.getText().toString().isEmpty() || maxValue.getText().toString().isEmpty()) {
                adapter.setPhotographers(photographers);
                return;
            }

            float min = Float.parseFloat(minValue.getText().toString());
            float max = Float.parseFloat(maxValue.getText().toString());

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