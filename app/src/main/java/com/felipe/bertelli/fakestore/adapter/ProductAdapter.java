package com.felipe.bertelli.fakestore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.felipe.bertelli.fakestore.R;
import com.felipe.bertelli.fakestore.model.Product;
import java.util.List;

// Adapter converte cada objeto Product em uma "linha" da lista visual (ListView)
public class ProductAdapter extends ArrayAdapter<Product> {
    // Construtor do adapter, recebe contexto da activity e a lista de produtos
    public ProductAdapter(Context context, List<Product> products){
        // Chama construtor da classe-mãe, passando layout 0 (não usamos um layout padrão)
        super(context, 0, products);
    }

    // Método chamado pelo ListView para montar cada linha da lista

    @Override
    public View getView(int position, View itemProductView, ViewGroup parent){
        // Pega o produto correspondente à posição atual. getItem vem da classe-mãe ArrayAdapter
        Product product = getItem(position);

        // Se não existe view reaproveitada, cria/inflar nova a partir do item_product.xml
        if(itemProductView == null){
            itemProductView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_product, parent, false);
        }

        // Busca as referências dos campos de texto no layout item_product.xml
        //ImageView imageProduct = itemProductView.findViewById(R.id.imgProduct);
        TextView textTitle = itemProductView.findViewById(R.id.textTitle);
        TextView textPrice = itemProductView.findViewById(R.id.textPrice);

        //Verifica se o objeto produto não é nulo antes de acessar seus campos
        if(product != null) {
            textTitle.setText(product.title);
            textPrice.setText(String.format("R$ %.2f", product.price));
        }


        // Retorna a view (linha pronta) para o ListView no activity_main.xml mostrar
        return itemProductView;
    }

}
