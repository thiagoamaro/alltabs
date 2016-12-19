package br.com.prototipoat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class CardapioLanche extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageButton btnComanda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cardapio);

//        btnComanda  = (ImageButton) findViewById(R.id.btnComanda);
        btnComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CardapioLanche.this, ComandaActivity.class);
                startActivity(it);
            }
        });

//        mToolbar    =   (Toolbar) findViewById(R.id.incCardapio);
//        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Intent it = null;
//
//                switch (item.getItemId()){
//                    case R.id.action_lanches:
//                        it = new Intent(CardapioLanche.this, CardapioLanche.class);
//                        break;
//                    case R.id.action_bebidas:
//                        it = new Intent(CardapioLanche.this, CardapioLanche.class);
//                        break;
//                    case R.id.action_sobremesa:
//                        it = new Intent(CardapioLanche.this, CardapioLanche.class);
//                        break;
//                }
//                startActivity(it);
//                return true;
//            }
//        });
//        mToolbar.inflateMenu(R.menu.menu_cardapio);
    }
}
