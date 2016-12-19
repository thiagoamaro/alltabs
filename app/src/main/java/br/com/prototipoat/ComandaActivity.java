package br.com.prototipoat;


import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;

import android.widget.Button;


import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.prototipoat.adapter.ComandaAdapter;
import br.com.prototipoat.adapter.ProdutoAdapter;
import br.com.prototipoat.model.Produto;
import br.com.prototipoat.model.ProdutoCardapio;
import br.com.prototipoat.model.Restaurante;



public class ComandaActivity extends AppCompatActivity {

    private Button btnFinalizar;
    private Button btnAddItem;
    private ListView lsvItemComanda;
    private ListView lsvItemCardapio;
    private TextView txvTotalComanda;

    private Intent itMain;
    private Float totalComanda;

    private SwipeRefreshLayout mySwipeRefreshLayout;

    private ProdutoCardapio produtoCardapio;
    private ArrayList<ProdutoCardapio> produtoCardapios;

    private Produto produto;
    private ArrayList<Produto> produtos;


    private ComandaAdapter adapterComanda;
    private ProdutoAdapter adapterProduto;

    private Restaurante restaurante;
    private String idRestaurante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_comanda);

        lsvItemComanda          = (ListView) findViewById(R.id.lsvItemComanda);

        txvTotalComanda         = (TextView) findViewById(R.id.txvTotalComanda);
        mySwipeRefreshLayout    = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        btnFinalizar            = (Button) findViewById(R.id.btnFinalizar);
        btnAddItem              = (Button) findViewById(R.id.btnAddItem);


        getItemsComanda();
        getItemCardapio();
        pegaRestaurante();

        btnFinalizar.setEnabled(false);
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalComanda == 0){
                    Toast.makeText(ComandaActivity.this, "Não foi possível finalizar o pedido,"+"\n"+"Você não possui itens entregues em sua comanda.", Toast.LENGTH_SHORT).show();

                } else if(totalComanda != 0) {
                    SharedPreferences prefs = getSharedPreferences("valorPedido", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("valor", String.valueOf(totalComanda));
                    editor.commit();
                    itMain = new Intent(ComandaActivity.this, Pagamento.class);
                    startActivity(itMain);
                    finish();
                }

            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ComandaActivity.this);

                    LayoutInflater inflater = getLayoutInflater();
                    View convertView = (View) inflater.inflate(R.layout.activity_tela_cardapio, null);
                    alertDialog.setView(convertView);
                    lsvItemCardapio         = (ListView) convertView.findViewById(R.id.lsvItemCardapio);
                    carregarListViewProduto();
                    alertDialog.show();

                    lsvItemCardapio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String posicao = String.valueOf(adapterProduto.getItem(position));
//                        Log.i("ID-Item: ", posicao);
                        postItem(posicao);
                        Toast.makeText(ComandaActivity.this, "Item Adicionado", Toast.LENGTH_SHORT).show();
                        
                    }
                });
            }
        });

        //SWIPE REFRESH
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
//                        Log.i("Swipe", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        boolean status = getItemsComanda();
                        mySwipeRefreshLayout.setEnabled(true);
                        mySwipeRefreshLayout.setRefreshing(true);
                        getItemsComanda();
                        if (status = true) {
                            mySwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }
        );
    }

    //Carrega e preenche o ListView
    public void carregarListViewComanda(){
        adapterComanda = new ComandaAdapter(ComandaActivity.this, R.layout.item_comanda, produtos);
        adapterComanda.notifyDataSetChanged();
        lsvItemComanda.setAdapter(adapterComanda);
    }
    //Carrega e preenche o ListView
    public void carregarListViewProduto(){
        adapterProduto = new ProdutoAdapter(ComandaActivity.this, R.layout.item_produto, produtoCardapios);
        adapterProduto.notifyDataSetChanged();
        lsvItemCardapio.setAdapter(adapterProduto);
    }

    //Pega o id do restaurante
    public void pegaRestaurante(){
        itMain = getIntent();
        restaurante = (Restaurante) itMain.getSerializableExtra("restaurante");
//        Log.i("ID:", String.valueOf(restaurante));
    }

    //GET ITEMS DA COMANDA
    public boolean getItemsComanda() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://alltabs.com.br/api.php/comanda?include=item_comanda,item_cardapio&filter=id,eq,1&transform=1 ";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
//                        Log.i("Response is: ", response);
                        totalComanda = Float.valueOf(0);
                        try {
                            JSONObject comandaObj = new JSONObject(response);
//                            Log.i("Obj is: ", ""+comandaObj);
                            JSONArray comandaInfoArray = comandaObj.getJSONArray("comanda");
//                            Log.i("Array is: ", ""+comandaInfoArray);
                            JSONObject comandaInfoObj = comandaInfoArray.getJSONObject(0);
                            int idComanda = comandaInfoObj.getInt("id");
//                            Log.i("ID COMANDA is: ", ""+idComanda);
                            JSONArray itemsArray = comandaInfoObj.getJSONArray("item_comanda");
//                            Log.i("ID COMANDA is: ", ""+itemsArray);
                            int itemsQty = itemsArray.length();
//                            Log.i("QTY ITEMS is: ", ""+itemsQty);

                            produtos = new ArrayList<Produto>();
                            for (int i = 0; i < itemsQty; i++) {
                                produto = new Produto();
                                JSONObject itemComanda = itemsArray.getJSONObject(i);
//                                Log.i("ITEMS: ", ""+ itemComanda);
                                JSONArray itemCardapio = itemComanda.getJSONArray("item_cardapio");
//                                Log.i("ITEMS CARDAPIO: ", ""+ itemCardapio);
                                JSONObject itemFinal = itemCardapio.getJSONObject(0);
//                                Log.i("ITEMS FINAL: ", ""+ itemFinal);
                                produto.setCategoria(itemFinal.getString("categoria"));
                                produto.setNome(itemFinal.getString("nome"));
                                produto.setPreco(Float.valueOf(itemFinal.getString("preco")));
                                produto.setStatus(itemComanda.getString("status"));
                                produtos.add(produto);

//                                if (!produto.getStatus().equals("Cancelado") || !produto.getStatus().equals("Em andamento")) {
//                                    totalComanda = totalComanda + produto.getPreco();
//                                }
                                if (produto.getStatus().equals("Entregue")){
                                    totalComanda = totalComanda + produto.getPreco();
                                } else if (produto.getStatus().equals("Cancelado") || !produto.getStatus().equals("Em andamento")){
                                    Log.i("Status", "Preço não alterado, pedido não entregue");
                                }

                            }
//                            Log.i("Total: ", ""+ totalComanda);
                            txvTotalComanda.setText("R$ " + String.format("%.2f", totalComanda));
                            if (totalComanda != 0){
                                btnFinalizar.setEnabled(true);
                                btnFinalizar.setBackground(getDrawable(R.drawable.fundoaccent));
                                btnFinalizar.setTextColor(getResources().getColor(R.color.colorBackground));
                            }

                            carregarListViewComanda();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("resp", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return true;
    }
    //GET ITEMS DO CARDAPIO
    public void getItemCardapio(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://alltabs.com.br/api.php/item_cardapio?where=cardapio_id,fq,1,eq&transform=1";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
//                        Log.i("Response is: ", response);
                        try {
                            JSONObject cardapioObj = new JSONObject(response);
//                            Log.i("Obj is: ", ""+cardapioObj);
                            JSONArray cardapioObjInfoArray = cardapioObj.getJSONArray("item_cardapio");
//                            Log.i("Array is: ", ""+cardapioObjInfoArray);
                            JSONObject cardapioInfoObj = cardapioObjInfoArray.getJSONObject(1);
                            int idCardapio = cardapioInfoObj.getInt("id");
//                            Log.i("ID CARDAPIO is: ", ""+idCardapio);
                            int itemsQty = cardapioObjInfoArray.length();
//                            Log.i("QTY ITEMS is: ", ""+itemsQty);

                            produtoCardapios = new ArrayList<ProdutoCardapio>();
                            for (int i = 0; i < itemsQty; i++) {
                                produtoCardapio = new ProdutoCardapio();
                                JSONObject itemCardapio = cardapioObjInfoArray.getJSONObject(i);
//                                Log.i("ITEMS: ", ""+ itemCardapio);
                                produtoCardapio.setIdItem(Integer.parseInt(itemCardapio.getString("id")));
                                produtoCardapio.setCategoriaItem(itemCardapio.getString("categoria"));
                                produtoCardapio.setNomeItem(itemCardapio.getString("nome"));
                                produtoCardapio.setPrecoItem(Float.valueOf(itemCardapio.getString("preco")));
                                produtoCardapios.add(produtoCardapio);

                            }
//                            carregarListViewProduto();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("resp", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
//        return true;
    }
    //ADICIONA ITEM DO CARDAPIO A COMANDA
    public void postItem(final String item_id){
        String url = "http://alltabs.com.br/api.php/item_comanda";
            StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("comanda_id", String.valueOf(1));
                    params.put("item_id", String.valueOf(item_id));
//                    Log.i("ADcionandoItem", String.valueOf(params));
                    return params;
                }

            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(sr);
    }


}

// ListView/ AdapterListView
//          ArrayAdapter<Produto> adapter = new ArrayAdapter<Produto>(ComandaActivity.this, android.R.layout.simple_list_item_1, produtos);
//          adapter = new ComandaAdapter(ComandaActivity.this, R.layout.item_comanda, produtos);
//          listItems.setAdapter(adapter);
