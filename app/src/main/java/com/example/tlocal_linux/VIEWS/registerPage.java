package com.example.tlocal_linux.VIEWS;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tlocal_linux.ApiServices.ApiLocal;
import com.example.tlocal_linux.MODELS.localInfo;
import com.example.tlocal_linux.MapsActivity;
import com.example.tlocal_linux.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registerPage extends AppCompatActivity {

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
            .client(client)
            .baseUrl(ApiLocal.URL_API)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();

    ApiLocal cliente = retrofit.create(ApiLocal.class);

    EditText nametxt, descriptiontxt, direcciontxt, teltxt,namepersona;
    Spinner categoria, ih, fh;
    CheckBox inhome, inlocal;
    Button btnregister, btnSelectImage, openMap;

    ImageView preImage, check_icon;
    final int IMG_REQUEST = 777;
    Bitmap bitmap;

    ProgressDialog progressDialog;

    List<String> location = new ArrayList<>();
    boolean status_check_ubiq = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        nametxt = (EditText) findViewById(R.id.nametxt);
        descriptiontxt = (EditText) findViewById(R.id.descriptiontxt);
        direcciontxt = (EditText) findViewById(R.id.addresstxt);
        teltxt = (EditText) findViewById(R.id.numberteltxt);
        ih = (Spinner) findViewById(R.id.timeinit);
        fh = (Spinner) findViewById(R.id.timefinal);
        namepersona = (EditText) findViewById(R.id.nombrePersonatxt);

        inhome = (CheckBox) findViewById(R.id.homedeliv);
        inlocal = (CheckBox) findViewById(R.id.localdeliv);

        categoria = (Spinner) findViewById(R.id.categories);

        preImage = (ImageView) findViewById(R.id.upImage);
        preImage.setVisibility(View.GONE);
        check_icon = (ImageView) findViewById(R.id.check_icon);
        check_icon.setVisibility(View.GONE);

        btnregister = (Button) findViewById(R.id.btnpostregister);
        btnregister.setVisibility(View.GONE);

        btnSelectImage = (Button) findViewById(R.id.btnimage);

        openMap = findViewById(R.id.openMap);

        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intentM = new Intent(getApplicationContext(), MapsActivity.class);
                startActivityForResult(_intentM,333);
            }
        });


        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.categories_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoria.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getApplicationContext(),R.array.time1_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getApplicationContext(),R.array.time2_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ih.setAdapter(adapter2);
        fh.setAdapter(adapter3);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> deliverys = null;
                deliverys = new ArrayList<String>();

                if(nametxt.getText().toString().isEmpty()) {
                    Toast.makeText(registerPage.this, "Falta NOMBRE DEL LOCAL", Toast.LENGTH_SHORT).show();
                }else {
                    if(descriptiontxt.getText().toString().isEmpty()) {
                        Toast.makeText(registerPage.this, "Falta DESCRIPCION DEL LOCAL", Toast.LENGTH_SHORT).show();

                    }else {
                        if(direcciontxt.getText().toString().isEmpty()) {
                            Toast.makeText(registerPage.this, "Falta DIRECCION DEL LOCAL", Toast.LENGTH_SHORT).show();

                        }else {
                            if(teltxt.getText().toString().isEmpty() ) {
                                Toast.makeText(registerPage.this, "Falta TELEFONO DE CONTACTO", Toast.LENGTH_SHORT).show();

                            }else {
                                if(namepersona.getText().toString().isEmpty()) {
                                    Toast.makeText(registerPage.this, "Falta el NOMBRE DE PROPIETARIO", Toast.LENGTH_SHORT).show();
                                }else {
                                    if (checkDelvers(deliverys) != true){
                                        Toast.makeText(registerPage.this, "SELECCIONE EL METODO DE ENTREGA", Toast.LENGTH_SHORT).show();
                                    }else{
                                        if (status_check_ubiq != true)
                                        {
                                            Toast.makeText(registerPage.this, "SELECCIONE UBICACION EN EL MAPA", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                progressDialog = new ProgressDialog(registerPage.this);
                                                progressDialog.show();
                                                progressDialog.setContentView(R.layout.bc_dialog);
                                                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                                checkDelvers(deliverys);
                                                postDataLocal();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null)
        {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
                preImage.setImageBitmap(bitmap);
                preImage.setVisibility(View.VISIBLE);
                btnregister.setVisibility(View.VISIBLE);

            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 333 && data != null && resultCode == RESULT_OK)
        {
            Bundle ubq = data.getExtras();

            //String lg = data.getStringExtra("lg");
            check_icon.setVisibility(View.VISIBLE);
            status_check_ubiq = true;

            location.add(ubq.getString("lt"));
            location.add(ubq.getString("lg"));

        }

    }


    public void postDataLocal() throws IOException {
        String imagen = imageToString();
        String openTime = ih.getSelectedItem().toString() + " a " + fh.getSelectedItem().toString();
        List<String> deliverysF = null;
        deliverysF = new ArrayList<String>();
        checkDelvers(deliverysF);

        localInfo localed = new localInfo(null,
                nametxt.getText().toString(), descriptiontxt.getText().toString(),
                direcciontxt.getText().toString(), teltxt.getText().toString(),
                namepersona.getText().toString(), openTime, categoria.getSelectedItem().toString(),
                deliverysF, imagen, location
        );

        Call<localInfo> call = cliente.sendLocal(localed);
        call.enqueue(new Callback<localInfo>() {
            @Override
            public void onResponse(Call<localInfo> call, Response<localInfo> response) {
                progressDialog.dismiss();
                finish();
            }

            @Override
            public void onFailure(Call<localInfo> call, Throwable t) {
                Toast.makeText(registerPage.this, "No se envio:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    public boolean checkDelvers(List<String> x){
        if (inhome.isChecked() && inlocal.isChecked() ){
            x.add(inlocal.getText().toString());
            x.add(inhome.getText().toString());
            return true;
        }
        else if (inhome.isChecked()){
            x.add(inhome.getText().toString());
            return true;
        }
        else if (inlocal.isChecked()){
            x.add(inlocal.getText().toString());
            return true;
        }
        return false;
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/jpeg/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        //return imgBytes;
        return Base64.encodeToString(imgBytes,Base64.NO_WRAP);
    }








}




