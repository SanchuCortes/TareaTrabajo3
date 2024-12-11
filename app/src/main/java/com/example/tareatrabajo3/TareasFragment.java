package com.example.tareatrabajo3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TareasFragment extends Fragment {

    private RecyclerView recyclerView;
    private Adaptador adaptador;
    private List<Tarea> listaTareas = new ArrayList<>();
    private List<Tarea> tareasOcultas = new ArrayList<>();

    public TareasFragment() {
        // Constructor vacío requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tareas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicialización de elementos de la UI
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adaptador = new Adaptador(listaTareas);
        recyclerView.setAdapter(adaptador);

        EditText tareaNueva = view.findViewById(R.id.TareaNueva);
        Button agregar = view.findViewById(R.id.botonAgregar);
        Button eliminar = view.findViewById(R.id.botonEliminar);
        Switch swt = view.findViewById(R.id.switch1);
        Spinner spinner = view.findViewById(R.id.spinner);

        // Configurar el Spinner correctamente
        String[] opciones = {
                getString(R.string.limpieza),
                getString(R.string.lavanderia),
                getString(R.string.cocina),
                getString(R.string.recado)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Establecer un valor predeterminado
        if (spinner.getSelectedItem() == null) {
            spinner.setSelection(0);
        }

        // Configurar el botón de agregar tarea
        agregar.setOnClickListener(v -> {
            String tarea = tareaNueva.getText().toString().trim();

            if (tarea.isEmpty()) {
                Toast.makeText(getContext(), R.string.el_campo_tarea_no_puede_estar_vacio, Toast.LENGTH_LONG).show();
                return;
            }

            int imagen = selectorImagen(spinner);
            if (imagen == R.drawable.defaultimage) {
                Toast.makeText(getContext(), "Error al seleccionar imagen", Toast.LENGTH_SHORT).show();
                return;
            }

            listaTareas.add(new Tarea(tarea, imagen));
            adaptador.notifyDataSetChanged();
            tareaNueva.setText("");
            Toast.makeText(getContext(), R.string.tarea_agregada_correctamente, Toast.LENGTH_LONG).show();
        });

        // Configurar el botón de eliminar tarea
        eliminar.setOnClickListener(v -> {
            if (listaTareas.isEmpty()) {
                Toast.makeText(getContext(), R.string.no_hay_tareas_para_eliminar, Toast.LENGTH_SHORT).show();
                return;
            }

            List<Tarea> tareasAEliminar = new ArrayList<>();
            for (Tarea tarea : listaTareas) {
                if (tarea.getCheck()) {
                    tareasAEliminar.add(tarea);
                }
            }

            if (tareasAEliminar.isEmpty()) {
                Toast.makeText(getContext(), R.string.no_hay_tareas_seleccionadas_para_eliminar, Toast.LENGTH_SHORT).show();
            } else {
                listaTareas.removeAll(tareasAEliminar);
                adaptador.notifyDataSetChanged();
                Toast.makeText(getContext(), R.string.tareas_eliminadas_correctamente, Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Método para seleccionar la imagen según la opción del spinner
    private int selectorImagen(Spinner spinner) {
        if (spinner.getSelectedItem() == null) {
            return R.drawable.defaultimage;
        }

        String seleccion = spinner.getSelectedItem().toString(); // Obtener selección

        if (seleccion.equalsIgnoreCase(getString(R.string.limpieza))) {
            return R.drawable.limpieza;
        } else if (seleccion.equalsIgnoreCase(getString(R.string.lavanderia))) {
            return R.drawable.lavanderia;
        } else if (seleccion.equalsIgnoreCase(getString(R.string.cocina))) {
            return R.drawable.cocinar;
        } else if (seleccion.equalsIgnoreCase(getString(R.string.recado))) {
            return R.drawable.recado;
        } else {
            return R.drawable.defaultimage; // Imagen por defecto en caso de error
        }
    }

}
