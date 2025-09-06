package com.felipe.bertelli.fakestore;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.felipe.bertelli.fakestore.adapter.ProductAdapter;
import com.felipe.bertelli.fakestore.model.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listProducts; // Componente visual para mostrar a lista de produtos activity_main.xml
    private ProductAdapter productAdapter; // classe ProductAdapter para converter produtos em linhas visuais
    private List<Product> products; // Lista de produtos obtida da API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listProducts = findViewById(R.id.listProducts);
        products = new ArrayList<>(); // Inicializa a lista de produtos como vazia
        productAdapter = new ProductAdapter(this, products); // Cria o adapter com a lista vazia
        listProducts.setAdapter(productAdapter); // Associa o adapter ao ListView

        listProducts.setOnItemClickListener((parent, view, position, id) -> {
            Product clickedProduct = products.get(position);
            showProductDialog(clickedProduct); // Mostra detalhes do produto clicado
        });

        fetchProductFromApi(); // Método para buscar produtos da API
    }

    private void fetchProductFromApi() {

        runOnUiThread(() -> findViewById(R.id.progress).setVisibility(View.VISIBLE));

        new Thread(() -> {
            try {

                URL baseUrl = new URL("https://fakestoreapi.com/products"); //Cria o objeto baseUrl apontando para a API
                HttpURLConnection conn = (HttpURLConnection) baseUrl.openConnection(); //Abre conexao HTTP com a baseUrl
                conn.setRequestMethod("GET"); //Define o metodo GET

                int responseCode = conn.getResponseCode(); // Pega o codigo HTTP da resposta

                if(responseCode == 200){ //Se o codigo for 200 quer dizer que o servidor responder

                    // Se resposta for OK, lê o corpo da resposta (JSON)
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    // Lê linha por linha (montando a resposta completa)
                    while((line = reader.readLine()) != null) response.append(line);
                    reader.close();

                    //Converte a resposta JSON em um Array de objetos
                    JSONArray arr = new JSONArray();

                    // Lista temporária para guardar os produtos carregados
                    List<Product> fetchedProdutcs = new ArrayList<>();

                    //Percorre cada objeto do Array Json (arr)
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        // Cria objeto Product e preenche com os campos do JSON
                        Product p = new Product();
                        p.id = obj.getInt("id");
                        p.title = obj.getString("title");
                        p.price = obj.getDouble("price");
                        // Adiciona o produto na lista temporária
                        fetchedProdutcs.add(p);
                    }
                    runOnUiThread(()->{
                        products.clear();
                        products.addAll(fetchedProdutcs);
                        productAdapter.notifyDataSetChanged();
                        findViewById(R.id.progress).setVisibility(View.GONE);
                    });
                } else {
                    showError("Erro ao buscar produtos. HTTP STATUS CODE: " + responseCode);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                showError("Erro: " + e.getMessage());
            }
        }).start();
    }


    // Função para exibir mensagem de erro na tela
    private void showError(String messageError) {
        // Tudo que é dinamico na tela deve ser feito na UI Thread
        runOnUiThread(()->{
          findViewById(R.id.progress).setVisibility(View.GONE);
            TextView textError = findViewById(R.id.textError);
            textError.setText(messageError);
            textError.setVisibility(View.VISIBLE);
        });
    }

    // Função para mostrar detalhes do produto clicado nele
    private void showProductDialog(Product p){
        new AlertDialog.Builder(this)// Cria um dialog
                .setTitle(p.title) // Título: nome do produto
                .setMessage("Categoria: " + p.category + "\n" +
                            "Preço: R$ " + p.price
                )
                .setPositiveButton("OK", null) // Botão OK para fechar o dialog
                .show();  // Exibe o dialog
    }
}