package br.com.prototipoat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.Toast;


import com.onesignal.OneSignal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;


import br.com.prototipoat.adapter.RestauranteAdapter;
import br.com.prototipoat.controller.Biblioteca;
import br.com.prototipoat.model.Restaurante;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Intent itMain;

    private ListView lsvRestaurantes;

    private ArrayList<Restaurante> restaurantes;
    private RestauranteAdapter adapter;
    private Restaurante restaurante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OneSignal.startInit(this).init();

        //ListView
        lsvRestaurantes = (ListView) findViewById(R.id.lsvRestaurantes);

        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.incMenu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                itMain = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(itMain);
                return true;
            }
        });
        mToolbar.inflateMenu(R.menu.menu_main);

        leituraTexto();

        lsvRestaurantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pega posição do Restaurante na Lista
                restaurante = adapter.getItem(position);
//                Log.i("Rest", restaurante.getNome());
//                Toast.makeText(MainActivity.this, idRestaurante, Toast.LENGTH_SHORT).show();
                itMain = new Intent(MainActivity.this, RestauranteActivity.class);
                itMain.putExtra("restaurante", restaurante);
                startActivity(itMain);
            }
        });
    }

    private void carregarListView() {
        adapter = new RestauranteAdapter(MainActivity.this, R.layout.item_restaurante, restaurantes);
        lsvRestaurantes.setAdapter(adapter);
    }

    private void leituraTexto() {
                //--------Leitura do arquivo texto-------
                try {
                    Context context = getApplicationContext();

                    InputStream arquivo = context.getResources().openRawResource(R.raw.catalogo_restaurantes);
                    InputStreamReader isr = new InputStreamReader(arquivo);

                    BufferedReader br = new BufferedReader(isr);

                    String linha = "";
                    restaurantes = new ArrayList<>();

                    while ((linha = br.readLine()) != null) {
                        restaurante = new Restaurante(linha);
                        String id = Biblioteca.parserNome(restaurante.getId());
                        String nome = Biblioteca.parserNome(restaurante.getNome());
                        String descricao = Biblioteca.parserNome(restaurante.getDescricao());
//                Log.i("NOME", nome);
                        String rank = Biblioteca.parserNome(restaurante.getRank());
                        int imagem = context.getResources().getIdentifier(nome, "mipmap", context.getPackageName());
                        restaurante.setLogo(imagem);

                        int imgrank = context.getResources().getIdentifier(rank, "drawable", context.getPackageName());
                        restaurante.setRank(String.valueOf(imgrank));

                        restaurantes.add(restaurante);
                    }
                    carregarListView();
                    br.close();
                    isr.close();
                    arquivo.close();
//            Log.i("Quantidade", "" + produtos.size());
                } catch (Exception erro) {
                    Log.e("FRUTARIA Erro", erro.getMessage());
                }

    }
}

//        //Criando Gson
//        Gson gson = new GsonBuilder().registerTypeAdapter(Restaurante.class, new RestauranteDeserializer()).create();
//
//        Retrofit retrofit = new Retrofit
//                .Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//        RestInterface restAPI = retrofit.create(RestInterface.class);
//
//        // ONE CAR - REQUEST
//        Call<Restaurante> call = restAPI.getRestaurante(1);
//        call.enqueue(new Callback<Restaurante>() {
//            @Override
//            public void onResponse(Call<Restaurante> call, Response<Restaurante> response) {
//                Restaurante r = response.body();
//
//                if (r != null) {
//                    Log.i(TAG, "Nome Um Rest: " + r.getNome() + " " + r.getRank());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Restaurante> call, Throwable t) {
//                Log.i(TAG, "Error ONE CAR: " + t.getMessage());
//            }
//        });
//
//        // MANY CAR - REQUEST
//        final Call<List<Restaurante>> callMany = restAPI.getListRestaurante("nome");
//
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    List<Restaurante> list = callMany.execute().body();
//
//                    if (list != null) {
//                        for (Restaurante r : list) {
//                            Log.i(TAG, "Many Nome: " + r.getNome());
//                        }
//                    }
//                } catch (IOException e) {
//                    Log.e(TAG, "Erro" + e);
//                }
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.i(TAG, "MANY CAR request Ok");
//                    }
//                });
//            }
//        }.start();
//    }
//



