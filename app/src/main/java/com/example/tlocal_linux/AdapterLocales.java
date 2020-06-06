package com.example.tlocal_linux;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tlocal_linux.ApiServices.ApiLocal;
import com.example.tlocal_linux.MODELS.listItemCategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterLocales
        extends RecyclerView.Adapter<AdapterLocales.ViewHolderLocales>
        implements View.OnClickListener {

    ArrayList<listItemCategory> listaLocales;
    private View.OnClickListener listener;
    private Context context;

    public AdapterLocales(Context context, ArrayList<listItemCategory> listaLocales) {
        this.context = context;
        this.listaLocales = listaLocales;
    }

    @Override
    public AdapterLocales.ViewHolderLocales onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.indato, null,true);

        view.setOnClickListener(this);

        return new ViewHolderLocales(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderLocales holder, int posi) {
        holder._nameLocal.setText(listaLocales.get(posi).getNameLocal());
        holder._description.setText(listaLocales.get(posi).getDescription());
        Picasso.get().load(ApiLocal.URL_API+"view/"+listaLocales.get(posi).getDirImage()).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(holder._idImage);

    }

    @Override
    public int getItemCount() {
        return listaLocales.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }
    }

    public class ViewHolderLocales extends RecyclerView.ViewHolder {
        TextView _nameLocal, _description;
        ImageView _idImage;

        public ViewHolderLocales(View itemView) {
            super(itemView);

            _nameLocal = (TextView) itemView.findViewById(R.id._nameLocal_txt);
            _description = (TextView) itemView.findViewById(R.id._descriptLocal_Text);
            _idImage = (ImageView) itemView.findViewById(R.id._imgLocal);
        }
    }

}
