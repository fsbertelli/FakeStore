package com.felipe.bertelli.fakestore.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.felipe.bertelli.fakestore.R;
import com.felipe.bertelli.fakestore.adapter.ProductAdapter;
import com.felipe.bertelli.fakestore.model.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listProducts;
    private ProductAdapter productAdapter;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listProducts = findViewById(R.id.listProducts);
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(this, products);
        listProducts.setAdapter(productAdapter);

        listProducts.setOnItemClickListener((parent, view, position, id) -> {
            Product clickedProduct = products.get(position);
            showProductDialog(clickedProduct);
        });

        fetchProductFromApi();
    }

    private void fetchProductFromApi() {

        runOnUiThread(() -> findViewById(R.id.progress).setVisibility(View.VISIBLE));

        new Thread(() -> {
            try {

                URL baseUrl = new URL("https://fakestoreapi.com/products");
                HttpURLConnection conn = (HttpURLConnection) baseUrl.openConnection();
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();

                if (responseCode == 200) {

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) response.append(line);
                    reader.close();

                    JSONArray arr = new JSONArray(response.toString());

                    List<Product> fetchedProdutcs = new ArrayList<>();

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        Product p = new Product();
                        p.id = obj.getInt("id");
                        p.title = obj.getString("title");
                        p.price = obj.getDouble("price");
                        fetchedProdutcs.add(p);
                    }
                    runOnUiThread(() -> {
                        products.clear();
                        products.addAll(fetchedProdutcs);
                        productAdapter.notifyDataSetChanged();
                        findViewById(R.id.progress).setVisibility(View.GONE);
                    });
                } else {
                    showError("Erro ao buscar produtos. HTTP STATUS CODE: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
                showError("Erro: " + e.getMessage());
            }
        }).start();
    }


    private void showError(String messageError) {
        runOnUiThread(() -> {
            findViewById(R.id.progress).setVisibility(View.GONE);
            TextView textError = findViewById(R.id.textError);
            textError.setText(messageError);
            textError.setVisibility(View.VISIBLE);
        });
    }

    private void showProductDialog(Product p) {
        new AlertDialog.Builder(this)
                .setTitle(p.title)
                .setMessage("Categoria: " + p.category + "\n" +
                        "Preço: R$ " + p.price
                )
                .setPositiveButton("OK", null)
                .show();
    }
}