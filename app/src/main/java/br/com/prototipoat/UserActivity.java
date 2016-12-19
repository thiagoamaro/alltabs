package br.com.prototipoat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.prototipoat.model.Usuario;

public class UserActivity extends AppCompatActivity {

    private Usuario usuario;

    private Toolbar mToolbar;
    private Intent itMain;

    //activity_perfil
    private ImageView imvFotoUsuario;
    private TextView txvNomeUsuario;
    private Button btnVoucher;
    private Button btnInfoUsuario;
    private Button btnCartao;

    //info_usuario
    private EditText edtCpf;
    private EditText edtEmail;

    //cartao
    private EditText edtNomeCartao;
    private EditText edtNumeroCartao;
    private EditText edtMesValidade;
    private EditText edtAnoValidade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Vinculos
        imvFotoUsuario = (ImageView) findViewById(R.id.imvFotoUsuario);
        txvNomeUsuario = (TextView) findViewById(R.id.txvNomeUsuario);
        btnVoucher = (Button) findViewById(R.id.btnVoucher);
        btnInfoUsuario = (Button) findViewById(R.id.btnInfoUsuario);
        btnCartao = (Button) findViewById(R.id.btnCartao);

        btnVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVoucher();
            }
        });

        btnInfoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInfo();
            }
        });

        btnCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCartao();
            }
        });



        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.incMenu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                itMain = new Intent(UserActivity.this, MainActivity.class);
                startActivity(itMain);
                return true;
            }
        });
        mToolbar.inflateMenu(R.menu.menu_main);

    }

    public void onVoucher(){
        String names[] ={"5%","10%","15%"};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.voucher, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Vouchers");
        ListView lv = (ListView) convertView.findViewById(R.id.lsvVoucher);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        lv.setAdapter(adapter);
        alertDialog.show();
    }

    public void onInfo(){
        edtCpf = (EditText) findViewById(R.id.edtCpf);
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.info_usuario, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Informações Adicionais");
        alertDialog.setPositiveButton("Salvar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // ação a ser executada ao clicar no botão
//                usuario.setCpf(edtCpf.getEditableText().toString());
//                usuario.setEmail(edtEmail.getEditableText().toString());
                Toast.makeText(UserActivity.this,"Salvo", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.show();
    }

    public void onCartao(){

        edtNomeCartao = (EditText) findViewById(R.id.edtNomeCartao);
        edtNumeroCartao = (EditText) findViewById(R.id.edtNumeroCartao);
        edtMesValidade = (EditText) findViewById(R.id.edtMesValidade);
        edtAnoValidade = (EditText) findViewById(R.id.edtAnoValidade);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.cartao, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Meu Cartão");
        alertDialog.setPositiveButton("Salvar",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // ação a ser executada ao clicar no botão
                Toast.makeText(UserActivity.this,"Salvo", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.show();
    }
}
