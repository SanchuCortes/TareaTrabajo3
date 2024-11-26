package com.example.tareatrabajo3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adaptador adaptador;
    private List<Tarea> listaTareas = new ArrayList<>();
    private List<Tarea> tareasOcultas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Spinner spinner = findViewById(R.id.spinner);
        String[] opciones = {this.getString(R.string.limpieza),this.getString(R.string.lavanderia),this.getString(R.string.cocina),this.getString(R.string.recado)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,opciones);
        spinner.setAdapter(adapter);

        EditText tareaNueva = findViewById(R.id.TareaNueva);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adaptador = new Adaptador(listaTareas);
        recyclerView.setAdapter(adaptador);
        Button agregar = findViewById(R.id.botonAgregar);
        Button eliminar = findViewById(R.id.botonEliminar);
        Switch swt = findViewById(R.id.switch1);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           String tarea = tareaNueva.getText().toString();
           if(tarea.isEmpty()){
               Toast.makeText(MainActivity.this, R.string.el_campo_tarea_no_puede_estar_vacio,Toast.LENGTH_LONG).show();
            return;
           }
           int imagen = selectorImagen();

           listaTareas.add(new Tarea(tarea,imagen));
           adaptador.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, R.string.tarea_agregada_correctamente,Toast.LENGTH_LONG).show();
           tareaNueva.setText("");
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listaTareas.isEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.no_hay_tareas_para_eliminar, Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Tarea> tareasAEliminar = new ArrayList<>();
                for (Tarea tarea : listaTareas) {
                    if (tarea.getCheck()) {
                        tareasAEliminar.add(tarea);
                    }
                }

                if (tareasAEliminar.isEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.no_hay_tareas_seleccionadas_para_eliminar, Toast.LENGTH_SHORT).show();
                } else {
                    listaTareas.removeAll(tareasAEliminar);
                    adaptador.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, R.string.tareas_eliminadas_correctamente, Toast.LENGTH_SHORT).show();
                }
            }
        });

        swt.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Ocultar tareas seleccionadas
                    tareasOcultas.clear();
                    for (Tarea tarea : listaTareas) {
                        if (tarea.getCheck()) { // Si la tarea está seleccionada
                            tareasOcultas.add(tarea); // Guardar la tarea para restaurar después
                        }
                    }

                    listaTareas.removeAll(tareasOcultas); // Eliminar tareas de la lista principal
                    adaptador.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this, R.string.tareas_seleccionadas_ocultas, Toast.LENGTH_SHORT).show();
                } else {
                    // Restaurar tareas ocultas y conservar el estado de los checks
                    for (Tarea tarea : tareasOcultas) {
                        tarea.setCheck(true);
                    }

                    listaTareas.addAll(tareasOcultas);
                    tareasOcultas.clear();
                    adaptador.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this, R.string.tareas_restauradas, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public int selectorImagen() {
        Spinner spinner = findViewById(R.id.spinner);
        String seleccion = spinner.getSelectedItem().toString();
        int numImagen = 0;


        if (seleccion.equals(getString(R.string.limpieza))) {
            numImagen = R.drawable.limpieza;
        } else if (seleccion.equals(getString(R.string.lavanderia))) {
            numImagen = R.drawable.lavanderia;
        } else if (seleccion.equals(getString(R.string.cocina))) {
            numImagen = R.drawable.cocinar;
        } else if (seleccion.equals(getString(R.string.recado))) {
            numImagen = R.drawable.recado;
        }

        return numImagen;
    }
}