package com.example.tlocal_linux.VIEWS;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.aware.WifiAwareNetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tlocal_linux.AdapterLocales;
import com.example.tlocal_linux.ApiServices.ApiLocal;
import com.example.tlocal_linux.DBHelper.SQLhelper;
import com.example.tlocal_linux.DBHelper.SqlConstants;
import com.example.tlocal_linux.MODELS.listItemCategory;
import com.example.tlocal_linux.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listCategory extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @NotNull
        @Override
        public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiLocal.URL_API)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();

    ApiLocal cliente = retrofit.create(ApiLocal.class);

    ArrayList<listItemCategory> Datos;
    ArrayList<listItemCategory> DatosOfline;
    RecyclerView lista;
    TextView texto;

    SQLhelper conn;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);

        Intent preintent = getIntent();
        String categ = preintent.getStringExtra("categ");

        lista = (RecyclerView) findViewById(R.id.listaItems);
        texto = (TextView) findViewById(R.id.prueba);

        progressDialog = new ProgressDialog(listCategory.this);

        progressDialog.setContentView(R.layout.bc_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        lista.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Datos = new ArrayList<listItemCategory>();
        DatosOfline= new ArrayList<listItemCategory>();

        conn = new SQLhelper(getApplicationContext(), SqlConstants.DB_NAME, null, 1);


        progressDialog.show();



        if(isConnection()){

            chargeList(categ);
        }else{
            loadFromLocalDB(categ);
            Toast.makeText(this, "OFFLINE", Toast.LENGTH_SHORT).show();
        }


    }


    public boolean isConnection(){
        ConnectivityManager cm = (ConnectivityManager) listCategory.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getActiveNetworkInfo();

        boolean isConected = wifi != null && wifi.isConnectedOrConnecting();
        return  isConected;
    }



    private void chargeList(String category){



        Call<List<listItemCategory>> called = cliente.categoriesArrays(category);
        called.enqueue(new Callback<List<listItemCategory>>() {
            @Override
            public void onResponse(Call<List<listItemCategory>> call, Response<List<listItemCategory>> response) {
                for (int it = 0; it < response.body().size(); it++) {
                    Log.i("P2: ", "LISTA --->"+ response.body().get(it).getNameLocal()  );

                    Datos.add(new listItemCategory(response.body().get(it).get_id(), response.body().get(it).getNameLocal(), response.body().get(it).getDescription(), response.body().get(it).getDirImage()));
                }

                texto.setText(category);

                AdapterLocales adapter = new AdapterLocales(getApplicationContext(),Datos);
                progressDialog.dismiss();
                lista.setAdapter(adapter);

                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent _intent = new Intent(getApplicationContext(), pageViewLocal.class);
                        _intent.putExtra("idLocal", Datos.get(lista.getChildAdapterPosition(view)).get_id());
                        startActivity(_intent);
                    }
                });


            }

            @Override
            public void onFailure(Call<List<listItemCategory>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error en la respuesta", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadFromLocalDB(String category){
        Log.i("P2: ", "LISTA --->");

        SQLiteDatabase db = conn.getReadableDatabase();

        String[] params = {category};
        String[] campos = {SqlConstants.ID_LOCAL,SqlConstants.NAME_LOCAL ,SqlConstants.DESCRIPTION_LOCAL, SqlConstants.IMAGE};

        Cursor cursor = db.query(SqlConstants.NameTable, campos,SqlConstants.CATEGORY_LOCAL+"=?",params,null,null,null);

        listItemCategory item = null;
        Log.i("P2: ", String.valueOf(cursor.getCount()));


        while (cursor.moveToNext()) {

                item = new listItemCategory();

                item.set_id(cursor.getString(0));
                item.setNameLocal(cursor.getString(1));
                item.setDescription(cursor.getString(2));
                item.setDirImage(cursor.getString(3));

                DatosOfline.add(item);

            }

        cursor.close();

        db.close();

        texto.setText(category);
        AdapterLocales adapter = new AdapterLocales(getApplicationContext(),DatosOfline);
        lista.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(getApplicationContext(), pageViewLocal.class);
                _intent.putExtra("idLocal", DatosOfline.get(lista.getChildAdapterPosition(view)).get_id());
                startActivity(_intent);
            }
        });

    }


}





















