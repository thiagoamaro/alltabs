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
import br.com.prototipoat.model.Restaurante;

public class RestauranteAdapter extends ArrayAdapter<Restaurante>{

    private Context context;
    private int layout;
    private ArrayList<Restaurante> restaurantes = new ArrayList<>();
    private RestauranteHolder rh;

    public RestauranteAdapter(Context context, int layout, ArrayList<Restaurante> restaurantes) {
        super(context, layout, restaurantes);

        this.context = context;
        this.layout = layout;
        this.restaurantes = restaurantes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            //Carregar linha
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layout, parent, false);

            //Referencias com os componentes graficos
            rh = new RestauranteHolder();
            rh.imgLogo = (ImageView) row.findViewById(R.id.imvLogoRestaurante);
            rh.imvRank = (ImageView) row.findViewById(R.id.imvMedalhaRestaurante);

            row.setTag(rh);

        } else {
            //gerenciar tag (objeto integral)
            rh = (RestauranteHolder) row.getTag();
        }

        final Restaurante restaurante = restaurantes.get(position);

        if(restaurante != null){
            rh.imgLogo.setImageResource(restaurante.getLogo());
            rh.imvRank.setImageResource(Integer.parseInt(restaurante.getRank()));
        }

        return row;
    }

    public static class RestauranteHolder{
        ImageView   imgLogo;
        ImageView   imvRank;
    }
}
