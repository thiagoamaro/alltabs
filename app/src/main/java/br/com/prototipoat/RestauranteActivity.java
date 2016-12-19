package br.com.prototipoat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.com.prototipoat.controller.Biblioteca;
import br.com.prototipoat.model.Restaurante;

public class RestauranteActivity extends AppCompatActivity{

    private ImageView imvLogo;
    private ImageView imvRank;
    private TextView txvDescricao;
    private ImageButton imbComanda;

    private Restaurante restaurante;
    private Intent itMain;
    private String idRestaurante;
    private int rankRestaurante;
    private String logoRestaurante;
    private String descricaoRestaurante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil_restaurante);

        imvLogo = (ImageView) findViewById(R.id.imvLogoRestaurante);
        imvRank = (ImageView) findViewById(R.id.imvMedalhaRestaurante);
        txvDescricao = (TextView) findViewById(R.id.txvDescricao);
        imbComanda = (ImageButton) findViewById(R.id.imbAbreComanda);

        pegaRestaurante();

//        Log.i("ID", idRestaurante);
        imvLogo.setImageResource(Integer.parseInt(logoRestaurante));
        imvRank.setImageResource(rankRestaurante);
        txvDescricao.setText(descricaoRestaurante);
        imbComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itMain = new Intent(RestauranteActivity.this, ComandaActivity.class);
                itMain.putExtra("restaurante", restaurante);
                startActivity(itMain);
            }
        });
    }


    public void pegaRestaurante(){
        itMain = getIntent();
        restaurante = (Restaurante) itMain.getSerializableExtra("restaurante");
//        Log.i("Rest:", String.valueOf(restaurante));
        idRestaurante   = restaurante.getId();
        rankRestaurante = Integer.parseInt(restaurante.getRank());
        logoRestaurante = String.valueOf(restaurante.getLogo());
        descricaoRestaurante = restaurante.getDescricao();
    }
}


