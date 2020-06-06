package com.example.tlocal_linux.VIEWS;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tlocal_linux.ApiServices.ApiLocal;
import com.example.tlocal_linux.DBHelper.SQLhelper;
import com.example.tlocal_linux.DBHelper.SqlConstants;
import com.example.tlocal_linux.MODELS.localInfo;
import com.example.tlocal_linux.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class pageViewLocal extends AppCompatActivity {
    static String numberCall = "";

    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @NotNull
        @Override
        public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    //.addHeader()
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


    TextView nameLocal, descriptionLocal, openTimeLocal, addressLocal,deleveryLocal_d, deleveryLocal_p ;
    Button btnCall, btnWhats;
    ImageView imageView;

    SQLhelper conn;
    localInfo info;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_view_local);

        Intent preIntent = getIntent();
        String idlocal = preIntent.getStringExtra("idLocal");

        progressDialog = new ProgressDialog(pageViewLocal.this);
        progressDialog.setContentView(R.layout.bc_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();


        nameLocal = (TextView) findViewById(R.id.nameLocal_txt);
        descriptionLocal = (TextView) findViewById(R.id.descriptionLocal);
        addressLocal = (TextView) findViewById(R.id.addressLocal);
        openTimeLocal = (TextView) findViewById(R.id.openTimeLocal);
        deleveryLocal_d = (TextView) findViewById(R.id.deleveryDom);
        deleveryLocal_p = (TextView) findViewById(R.id.deleveryRecol);

        btnCall = (Button) findViewById(R.id.btnCall);
        btnWhats = (Button) findViewById(R.id.btnWhats);

        imageView = (ImageView) findViewById(R.id.imageLocal);



        conn = new SQLhelper(getApplicationContext(), SqlConstants.DB_NAME, null, 1);

        if (isConnection()){
            loadRemoteServer(idlocal);
        }else {
            loadDBLocal(idlocal);
        }


        btnWhats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbrirWhatsApp(String.valueOf(numberCall));
            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbrirLlamada(String.valueOf(numberCall));
            }
        });


    }

    public void loadRemoteServer(String idlocal){


        Call<localInfo> call = cliente.getInfoLocal(idlocal);
        call.enqueue(new Callback<localInfo>() {
            @Override
            public void onResponse(Call<localInfo> call, Response<localInfo> response) {
                nameLocal.setText(response.body().getNameLocal());
                descriptionLocal.setText(response.body().getDescription());
                openTimeLocal.setText(response.body().getOpenTime());
                addressLocal.setText(response.body().getAddress());
                deleveryLocal_d.setText(" -"+response.body().getDelivery().get(0));
                try {
                    deleveryLocal_p.setText(" -"+response.body().getDelivery().get(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                numberCall = response.body().getPhoneNumber();

                progressDialog.dismiss();

                Picasso.get().load(ApiLocal.URL_API+"view/"+response.body().getdirImage()).placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(imageView);
            }

            @Override
            public void onFailure(Call<localInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fallo", Toast.LENGTH_SHORT).show();
            }
        });
    }


    
    private void AbrirWhatsApp(String telefono)
    {
        Intent _intencion = new Intent("android.intent.action.MAIN");
        _intencion.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
        _intencion.putExtra("jid", PhoneNumberUtils.stripSeparators("521" + telefono)+"@s.whatsapp.net");
        startActivity(_intencion);
    }
    private void AbrirLlamada(String telefono)
    {
        String dial = "tel:" + telefono;
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
    }


    public void loadDBLocal(String idlocal){

        SQLiteDatabase db = conn.getReadableDatabase();

        String[] params = {idlocal};
        String[] campos = {
                SqlConstants.ID_LOCAL,
                SqlConstants.NAME_LOCAL ,
                SqlConstants.DESCRIPTION_LOCAL,
                SqlConstants.ADDRESS_LOCAL,
                SqlConstants.PERSONA_LOCAL,
                SqlConstants.TEL_LOCAL,
                SqlConstants.TIME_LOCAL,
                SqlConstants.CATEGORY_LOCAL,
                SqlConstants.DELIVERY_LOCAL,
                SqlConstants.IMAGE
        };

        Cursor cursor = db.query(SqlConstants.NameTable, campos,SqlConstants.ID_LOCAL+"=?",params,null,null,null);

        cursor.moveToFirst();

        nameLocal.setText(cursor.getString(1));
        descriptionLocal.setText(cursor.getString(2));

        openTimeLocal.setText(cursor.getString(6));
        addressLocal.setText(cursor.getString(3));
        deleveryLocal_d.setText(cursor.getString(8));
        try {
            deleveryLocal_p.setText(cursor.getString(8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        numberCall = cursor.getString(5);
        String picture = cursor.getString(7);
        switch (picture){
            case "Abarrotes":
                Picasso.get().load(R.drawable.abarrotes).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(imageView);
                break;
            case "Alimentos":
                Picasso.get().load(R.drawable.restaurante).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(imageView);
                break;
            case "Divisas":
                Picasso.get().load(R.drawable.divisas).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(imageView);
                break;
            case "Farmacias":
                Picasso.get().load(R.drawable.farmacias).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(imageView);
                break;
            case "Fiestas":
                Picasso.get().load(R.drawable.fiestas2).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(imageView);
                break;
            case "Floreria":
                Picasso.get().load(R.drawable.floreria).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(imageView);
                break;
            case "Muebleria":
                Picasso.get().load(R.drawable.muebleria).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(imageView);
                break;
            case "Papeleria":
                Picasso.get().load(R.drawable.papeleria).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(imageView);
                break;
            case "Pinturas":
                Picasso.get().load(R.drawable.pinturas).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(imageView);
                break;
            case "Publicidad":
                Picasso.get().load(R.drawable.publicidad).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(imageView);
                break;
            case "Refaccionarias":
                Picasso.get().load(R.drawable.refaccionaria).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(imageView);
                break;
            case "Salud":
                Picasso.get().load(R.drawable.salud).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(imageView);
                break;
            case "Servicios":
                Picasso.get().load(R.drawable.servicios).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(imageView);
                break;
            case "Ropa":
                Picasso.get().load(R.drawable.ropa).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(imageView);
                break;
            case "Zapateria":
                Picasso.get().load(R.drawable.zapateria).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(imageView);
                break;



            default:
                Picasso.get().load(R.drawable.ic_launcher_foreground).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(imageView);
                break;
        }

        Toast.makeText(this, cursor.getString(7), Toast.LENGTH_SHORT).show();
        cursor.close();
        db.close();
    }

    public boolean isConnection(){
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getActiveNetworkInfo();

        boolean isConected = wifi != null && wifi.isConnectedOrConnecting();
        return  isConected;
    }

}
