package com.example.tareatrabajo3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.VistaTareas> {

    private List<Tarea> listaTareas;
    public Adaptador(List<Tarea> listaTareas){this.listaTareas = listaTareas;}

    @NonNull
    @Override
    public Adaptador.VistaTareas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new VistaTareas(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.VistaTareas holder, int position) {
    Tarea tarea = listaTareas.get(position);
    holder.nombreTarea.setText(tarea.getNombre());
    holder.imagen.setImageResource(tarea.getImagen());
    holder.check.setChecked(tarea.getCheck());
    holder.check.setOnCheckedChangeListener((buttonView, isChecked) -> {tarea.setCheck(isChecked);
        });

    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    static class VistaTareas extends RecyclerView.ViewHolder{
        ImageView imagen;
        TextView nombreTarea;
        CheckBox check;

        public VistaTareas(@NonNull View item){
            super(item);
            nombreTarea = item.findViewById(R.id.TextViewTarea);
            imagen = item.findViewById(R.id.imagenTarea);
            check = item.findViewById(R.id.checkBox3);

        }

    }

}
