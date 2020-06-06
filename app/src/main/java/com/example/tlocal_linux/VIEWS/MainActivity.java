package com.example.tlocal_linux.VIEWS;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tlocal_linux.ApiServices.ApiLocal;
import com.example.tlocal_linux.DBHelper.SQLhelper;
import com.example.tlocal_linux.DBHelper.SqlConstants;
import com.example.tlocal_linux.MODELS.localInfo;
import com.example.tlocal_linux.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.WildcardType;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    int versionDb = 1;

    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {

        @Override
        public okhttp3.Response intercept( Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    Retrofit.Builder builder = new Retrofit.Builder()
            .client(client)
            .baseUrl(ApiLocal.URL_API)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();

    ApiLocal cliente = retrofit.create(ApiLocal.class);

    CardView cartaAbarrotes,
            cartaAlimentos,
            cartaDivisas,
            cartaFarmacia,
            cartaFiesta,
            cartaFloreria,
            cartaMuebleria,
            cartaPapeleria,
            cartaPinturas,
            cartaPublicidad,
            cartaRefaccionaria,
            cartaSalud,
            cartaRopa,
            cartaServicios,
            cartaTiendas,
            cartaElectronica,
            cartaZapateria,
            cartaOtros;
    Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cartaAbarrotes = (CardView) findViewById(R.id.itemAbarrotes);
        cartaAlimentos = (CardView) findViewById(R.id.itemAlimentos);
        cartaDivisas = (CardView) findViewById(R.id.itemDivisas);
        cartaFarmacia = (CardView) findViewById(R.id.itemFarmacias);
        cartaFiesta = (CardView) findViewById(R.id.itemFiestas);
        cartaFloreria = (CardView) findViewById(R.id.itemFloreria);
        cartaMuebleria = (CardView) findViewById(R.id.itemMuebleria);
        cartaPapeleria = (CardView) findViewById(R.id.itemPapeleria);
        cartaPinturas = (CardView) findViewById(R.id.itemPinturas);
        cartaPublicidad = (CardView) findViewById(R.id.itemPublicidad);
        cartaRefaccionaria = (CardView)findViewById(R.id.itemRefaccionarias);
        cartaSalud = (CardView) findViewById(R.id.itemSalud);
        cartaServicios = (CardView) findViewById(R.id.itemServicios);
        cartaRopa = (CardView) findViewById(R.id.itemRopa);
        cartaElectronica = (CardView) findViewById(R.id.itemElectronica);
        cartaTiendas = (CardView) findViewById(R.id.itemTiendas);
        cartaZapateria = (CardView) findViewById(R.id.itemZapateria);
        cartaOtros = (CardView) findViewById(R.id.itemOtros);



        btn_register = (Button) findViewById(R.id.btnRegister);


        cartaAbarrotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(getApplicationContext(), listCategory.class);
                _intent.putExtra("categ", "Abarrotes");
                startActivity(_intent);

            }
        });
        cartaAlimentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(getApplicationContext(), listCategory.class);
                _intent.putExtra("categ", "Comida");
                startActivity(_intent);
            }
        });
        cartaDivisas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(getApplicationContext(), listCategory.class);
                _intent.putExtra("categ", "Divisas");
                startActivity(_intent);
            }
        });
        cartaFarmacia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(MainActivity.this, listCategory.class);
                _intent.putExtra("categ", "Farmacia");
                startActivity(_intent);
            }
        });
        cartaFiesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(MainActivity.this, listCategory.class);
                _intent.putExtra("categ", "Fiestas");
                startActivity(_intent);
            }
        });
        cartaFloreria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(getApplicationContext(), listCategory.class);
                _intent.putExtra("categ", "Floreria");
                startActivity(_intent);
            }
        });
        cartaMuebleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(getApplicationContext(), listCategory.class);
                _intent.putExtra("categ", "Muebleria");
                startActivity(_intent);
            }
        });
        cartaPapeleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(getApplicationContext(), listCategory.class);
                _intent.putExtra("categ", "Papeleria");
                startActivity(_intent);
            }
        });
        cartaPinturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(MainActivity.this, listCategory.class);
                _intent.putExtra("categ", "Pinturas");
                startActivity(_intent);
            }
        });
        cartaPublicidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(MainActivity.this, listCategory.class);
                _intent.putExtra("categ", "Publicidad");
                startActivity(_intent);
            }
        });
        cartaRefaccionaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(MainActivity.this, listCategory.class);
                _intent.putExtra("categ", "Refaccionaria");
                startActivity(_intent);
            }
        });
        cartaSalud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(MainActivity.this, listCategory.class);
                _intent.putExtra("categ", "Salud");
                startActivity(_intent);
            }
        });
        cartaServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(getApplicationContext(), listCategory.class);
                _intent.putExtra("categ", "Servicios");
                startActivity(_intent);
            }
        });
        cartaRopa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(getApplicationContext(), listCategory.class);
                _intent.putExtra("categ", "Ropa");
                startActivity(_intent);
            }
        });
        cartaZapateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(getApplicationContext(), listCategory.class);
                _intent.putExtra("categ", "Zapateria");
                startActivity(_intent);
            }
        });
        cartaOtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(getApplicationContext(), listCategory.class);
                _intent.putExtra("categ", "Otros");
                startActivity(_intent);
            }
        });
        cartaTiendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(MainActivity.this, listCategory.class);
                _intent.putExtra("categ", "Tiendas");
                startActivity(_intent);
            }
        });
        cartaElectronica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(MainActivity.this, listCategory.class);
                _intent.putExtra("categ", "Electronica");
                startActivity(_intent);
            }
        });


        if(isConnection()){
            loadDB();
            btn_register.setVisibility(View.VISIBLE);
        }else {
            btn_register.setVisibility(View.GONE);
        }



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(getApplicationContext(), registerPage.class);
                startActivity(_intent);
            }
        });


    }



    public void loadDB(){
        SQLhelper conn = new SQLhelper(MainActivity.this, SqlConstants.DB_NAME, null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();




        Log.i("P1: ", "BASE ANTERIOR BORRADA");


        ContentValues values = new ContentValues();


        Call<List<localInfo>> call = cliente.loadAllInfo();
        call.enqueue(new Callback<List<localInfo>>() {
            @Override
            public void onResponse(Call<List<localInfo>> call, Response<List<localInfo>> response) {
                versionDb = versionDb +1;
                conn.onUpgrade(db, 1, versionDb);
                //Log.i("P1: ", String.valueOf(response.body().size()));
                for (int i = 0 ; i < response.body().size(); i++ ){


                    values.put(SqlConstants.ID_LOCAL, response.body().get(i).get_id());
                    values.put(SqlConstants.NAME_LOCAL, response.body().get(i).getNameLocal());
                    values.put(SqlConstants.DESCRIPTION_LOCAL, response.body().get(i).getDescription());
                    values.put(SqlConstants.ADDRESS_LOCAL, response.body().get(i).getAddress());
                    values.put(SqlConstants.TEL_LOCAL, response.body().get(i).getPhoneNumber());
                    values.put(SqlConstants.PERSONA_LOCAL, response.body().get(i).getNamePersonal());
                    values.put(SqlConstants.TIME_LOCAL, response.body().get(i).getOpenTime());
                    values.put(SqlConstants.CATEGORY_LOCAL, response.body().get(i).getCategory());
                    values.put(SqlConstants.DELIVERY_LOCAL, response.body().get(i).getDelivery().toString());
                    values.put(SqlConstants.IMAGE, response.body().get(i).getdirImage());

                    db.insert(SqlConstants.NameTable, null, values);

                }
                db.close();
            }

            @Override
            public void onFailure(Call<List<localInfo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error al Actualiozar Registros ", Toast.LENGTH_SHORT).show();
            }
        });



    }

    public boolean isConnection(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getActiveNetworkInfo();

        boolean isConected = wifi != null && wifi.isConnectedOrConnecting();
        return  isConected;
    }

}
