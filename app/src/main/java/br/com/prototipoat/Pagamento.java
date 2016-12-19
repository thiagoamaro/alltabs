package br.com.prototipoat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.prototipoat.model.Restaurante;



public class Pagamento extends AppCompatActivity {

    private Float totalComanda;
    private Float total;
    private int umaPessoa;
    private int nPessoa;

    private TextView txvValorTotal;
    private EditText edtQtdePessoas;
    private TextView txvValorPorPessoa;

    private Button btnSolicitarPagamento;

    private ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pagamento);

        txvValorTotal       = (TextView) findViewById(R.id.txvValorTotal);
        edtQtdePessoas      = (EditText) findViewById(R.id.edtQtdePessoas);
        txvValorPorPessoa   = (TextView) findViewById(R.id.txvValorPorPessoa);
        btnSolicitarPagamento = (Button) findViewById(R.id.btnSolicitarPagamento);

        //Lendo as preferencias
        SharedPreferences prefs = getSharedPreferences("valorPedido", MODE_PRIVATE);
        totalComanda = Float.valueOf(prefs.getString("valor", ""));

        txvValorTotal.setText("R$ " + String.format("%.2f", totalComanda));
        txvValorPorPessoa.setText("R$ " + String.format("%.2f", totalComanda));


        btnSolicitarPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaQtde();
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Pagamento.this);
                alertDialog.setTitle("Pagamento Realizado");
                alertDialog.setMessage("Pedido Finalizado com Sucesso!" + "\n" + "Agradecemos por utilizar o AllTabs.");
                alertDialog.setNeutralButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alertDialog.show();
            }

        });
    }


    public void verificaQtde(){
        int qtdPessoa = edtQtdePessoas.getEditableText().length();
        if (qtdPessoa == 0){
            Toast.makeText(Pagamento.this, "É necessário ao menos uma pessoa para finalizar o pedido, Obrigado.", Toast.LENGTH_SHORT).show();
        } else if(qtdPessoa > 2){
            Toast.makeText(Pagamento.this, "Por Favor,"+"\n"+"Insira uma quantidade de até 2 caracteres", Toast.LENGTH_SHORT).show();
        } else if (qtdPessoa <= 2){
            umaPessoa = 1;
            nPessoa = Integer.parseInt(edtQtdePessoas.getEditableText().toString().trim());
            if(umaPessoa == nPessoa){
                txvValorPorPessoa.setText("R$ " + String.format("%.2f", totalComanda));
//                Toast.makeText(Pagamento.this, "Pessoa Vazia", Toast.LENGTH_LONG).show();
            } else if (umaPessoa < nPessoa){
                total = totalComanda / nPessoa;
                txvValorPorPessoa.setText("R$ " + String.format("%.2f", total));
            }
        } else {
            Toast.makeText(Pagamento.this, "Erro!" +"\n"+"Contate o suporte.", Toast.LENGTH_SHORT).show();
        }
    }
}

