package br.com.prototipoat.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.prototipoat.R;
import br.com.prototipoat.model.ProdutoCardapio;

public class ProdutoAdapter extends ArrayAdapter<ProdutoCardapio>{

    private Context context;
    private int layout;
    private ArrayList<ProdutoCardapio> produtoCardapios;
    private ProdutoCardapioHolder pch;

    public ProdutoAdapter(Context context, int layout, ArrayList<ProdutoCardapio> produtoCardapios) {
        super(context, layout, produtoCardapios);

        this.context = context;
        this.layout = layout;
        this.produtoCardapios = produtoCardapios;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            //Carregar linha
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layout, parent, false);

            //Referencias com os componentes graficos
            pch  = new ProdutoCardapioHolder();
            pch.txvNome      = (TextView) row.findViewById(R.id.txvNomeProdutoC);
            pch.txvPreco     = (TextView) row.findViewById(R.id.txvPrecoProdutoC);

            row.setTag(pch);

        } else {
            //gerenciar tag (objeto integral)
            pch  =   (ProdutoCardapioHolder) row.getTag();
        }

        final ProdutoCardapio produtoCardapio = produtoCardapios.get(position);

        if(produtoCardapio != null){
            pch.txvNome.setText(produtoCardapio.getNomeItem());
            pch.txvPreco.setText("R$ " + String.format("%.2f", produtoCardapio.getPrecoItem()));
        }

        return row;
    }

    public static class ProdutoCardapioHolder{
        TextView txvNome;
        TextView txvPreco;
    }
}
