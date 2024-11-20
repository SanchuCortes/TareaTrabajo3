package com.example.tareatrabajo3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
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
        String[] opciones = {"Limpieza","Lavanderia","Cocina","Recado"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,opciones);
        spinner.setAdapter(adapter);

        EditText tareaNueva = findViewById(R.id.TareaNueva);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adaptador = new Adaptador(listaTareas);
        recyclerView.setAdapter(adaptador);
        Button agregar = findViewById(R.id.botonAgregar);
        Button eliminar = findViewById(R.id.botonEliminar);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           String tarea = tareaNueva.getText().toString();
           if(tarea.isEmpty()){
               Toast.makeText(MainActivity.this,"El campo tarea no puede estar vacio",Toast.LENGTH_LONG).show();
            return;
           }
           int imagen = selectorImagen();

           listaTareas.add(new Tarea(tarea,imagen));
           adaptador.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,"Tarea agregada correctamente",Toast.LENGTH_LONG).show();
           tareaNueva.setText("");
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listaTareas.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No hay tareas para eliminar", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Tarea> tareasAEliminar = new ArrayList<>();
                for (Tarea tarea : listaTareas) {
                    if (tarea.getCheck()) {
                        tareasAEliminar.add(tarea);
                    }
                }

                if (tareasAEliminar.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No hay tareas seleccionadas para eliminar", Toast.LENGTH_SHORT).show();
                } else {
                    listaTareas.removeAll(tareasAEliminar);
                    adaptador.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Tareas eliminadas correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public int selectorImagen() {
        Spinner spinner = findViewById(R.id.spinner);
        String seleccion = spinner.getSelectedItem().toString();
        int numImagen = 0;

        // Determinamos el número de imagen según la opción seleccionada
        if (seleccion.equals("Limpieza")) {
            numImagen = R.drawable.limpieza;
        } else if (seleccion.equals("Lavanderia")) {
            numImagen = R.drawable.lavanderia;
        } else if (seleccion.equals("Cocina")) {
            numImagen = R.drawable.cocinar;
        } else if (seleccion.equals("Recado")) {
            numImagen = R.drawable.recado;
        }

        return numImagen; // Retornamos el ID del recurso de la imagen
    }
}