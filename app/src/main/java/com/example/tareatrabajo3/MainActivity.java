package com.example.tareatrabajo3;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;    //creamos las listas, adaptador y recyclerView para usar despues
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
        String[] opciones = {this.getString(R.string.limpieza), this.getString(R.string.lavanderia), this.getString(R.string.cocina), this.getString(R.string.recado)}; //creamos el spinner y le metemos sus 4 opciones
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, opciones);

        spinner.setAdapter(adapter);  //Creamos el adaptador y metemos el spinner y lo asignamos al adaptador

        EditText tareaNueva = findViewById(R.id.TareaNueva);
         TextView labelTareas = findViewById(R.id.LabelTareas);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adaptador = new Adaptador(listaTareas);  //Creamos el adaptador y le asignamos una lista y el recyclerView
        recyclerView.setAdapter(adaptador);
        Button agregar = findViewById(R.id.botonAgregar);
        Button eliminar = findViewById(R.id.botonEliminar);
        Switch swt = findViewById(R.id.switch1);
// commit
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tarea = tareaNueva.getText().toString();
                if (tarea.isEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.el_campo_tarea_no_puede_estar_vacio, Toast.LENGTH_LONG).show();
                    return; //creamos el toast
                }
                int imagen = selectorImagen(); //aqui dependiendo que opcion del spinner se asigna una imagen mediante un metodo

                listaTareas.add(new Tarea(tarea, imagen)); //se añade la tarea a la lista y se actualiza los cambios para que refresque el recycler y aparezca el nuevo item
                adaptador.notifyDataSetChanged();
                Log.d("MainActivity", "Tarea agregada: " + tarea);
                Toast.makeText(MainActivity.this, R.string.tarea_agregada_correctamente, Toast.LENGTH_LONG).show();
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
                    Log.d("MainActivity", "Tareas eliminadas: " + tareasAEliminar.size());
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
                    Log.d("MainActivity", "Tareas ocultas: " + tareasOcultas.size());
                    Toast.makeText(MainActivity.this, R.string.tareas_seleccionadas_ocultas, Toast.LENGTH_SHORT).show();
                } else {
                    // Restaurar tareas ocultas y conservar el estado de los checks
                    for (Tarea tarea : tareasOcultas) {
                        tarea.setCheck(true);
                    }

                    listaTareas.addAll(tareasOcultas);
                    tareasOcultas.clear();
                    adaptador.notifyDataSetChanged();
                    Log.d("MainActivity", "Tareas restauradas: " + listaTareas.size());
                    Toast.makeText(MainActivity.this, R.string.tareas_restauradas, Toast.LENGTH_SHORT).show();
                }
            }
        });
        TabLayout tabLayout = findViewById(R.id.tablayout);
// Añadir pestañas
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
// Acción al seleccionar una pestaña
                Log.d("TabLayout", "Tab seleccionada: " + tab.getText());
                if (tab.getPosition() ==  1){
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Proximamente", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Deshacer", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Acción al hacer clic en "Deshacer"
                            Toast.makeText(getApplicationContext(), "Acción deshecha", Toast.LENGTH_SHORT).show();
                        }
                    });

                    snackbar.show();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
// Acción al deseleccionar una pestaña
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
// Acción al volver a seleccionar una pestaña
            }
        });

        labelTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpMenu(view);
            }
        });
    }
    // Método para asociar un menú emergente popup al pulsar el textView
    public void showPopUpMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.color_menu, popupMenu.getMenu());
        // Manejador de clicks
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                TextView tv;
                if (item.getItemId() == R.id.itemRojo) {
                    tv = (TextView) view;
                    tv.setTextColor(Color.RED);
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Color Rojo", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                if (item.getItemId() == R.id.itemVerde) {
                    tv = (TextView) view;
                    tv.setTextColor(Color.GREEN);
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Color Verde", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                if (item.getItemId() == R.id.itemAzul) {
                    tv = (TextView) view;
                    tv.setTextColor(Color.BLUE);
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Color Azul", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                return true;
            }
        });
        // mostrarlo
        popupMenu.show();
    }


        public int selectorImagen() {   //este es el metodo selector de imagen que escoge la imagen dependiendo del texto que este en el spinner
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
