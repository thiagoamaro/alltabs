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
import br.com.prototipoat.model.Produto;

public class ComandaAdapter extends ArrayAdapter<Produto> {

    private Context context;
    private int layout;
    private ArrayList<Produto> produtos = new ArrayList<>();
    private ProdutoHolder ph;

    public ComandaAdapter(Context context, int layout, ArrayList<Produto> produtos) {
        super(context, layout, produtos);

        this.context = context;
        this.layout = layout;
        this.produtos = produtos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            //Carregar linha
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layout, parent, false);

            //Referencias com os componentes graficos
            ph  = new ProdutoHolder();
            ph.txvNome      = (TextView) row.findViewById(R.id.txvNomeProduto);
            ph.txvPreco     = (TextView) row.findViewById(R.id.txvPrecoProduto);
            ph.imvStatus    = (ImageView) row.findViewById(R.id.imvStatus);

            row.setTag(ph);

        } else {
            //gerenciar tag (objeto integral)
            ph  =   (ProdutoHolder) row.getTag();
        }

        final Produto produto = produtos.get(position);

        if(produto != null){
            ph.txvNome.setText(produto.getNome());
            ph.txvPreco.setText("R$ " + String.format("%.2f", produto.getPreco()));

            String status = produto.getStatus();
            switch (status) {
                case "Entregue":
                    ph.imvStatus.setImageResource(R.drawable.itemcomanda_status__ok);
                    break;
                case "Em andamento":
                    ph.imvStatus.setImageResource(R.drawable.itemcomanda_status__pending);
                    break;
                case "Cancelado":
                    ph.imvStatus.setImageResource(R.drawable.itemcomanda_status__cancel);
                    break;
            }
//            ph.imvStatus.setImageResource(Integer.parseInt(produto.getStatus()));
        }

        return row;
    }

    public static class ProdutoHolder{
        TextView txvNome;
        TextView txvPreco;
        ImageView imvStatus;
    }
}
