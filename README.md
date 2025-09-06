# 🛒 FakeStoreApp - Exemplo Simples de Consumo de API em Android Java

Este é um projetinho didático criado para quem está começando no desenvolvimento Android e quer entender, na prática, como consumir uma API REST usando apenas **Java puro**, sem bibliotecas externas como Retrofit, Glide ou Picasso.

## O que o app faz?

- Busca produtos da [Fake Store API](https://fakestoreapi.com/)
- Mostra os produtos em uma lista simples (ListView)
- Ao clicar em um item, exibe os detalhes do produto em um diálogo
- Permite remover um produto da lista localmente (clicando e segurando)

## Como funciona?

1. **Consumo da API:** Os produtos são buscados usando `HttpURLConnection` e tratados com JSON nativo.
2. **Exibição:** Os dados são mostrados em um `ListView` usando um Adapter personalizado.
3. **Sem bibliotecas externas:** Todo o código é feito apenas com recursos nativos do Android.

## Para quem serve?

Para estudantes, curiosos e iniciantes em Android que querem entender como conectar um app com uma API REST de forma simples, comentada e sem "mágica" de bibliotecas.

## Como rodar?

- Abra o projeto no Android Studio
- Rode em um emulador ou dispositivo físico com acesso à internet
- Pronto! Explore, modifique, experimente!

---

Feito com carinho para ajudar outros estudantes 😊  
Se quiser aprender mais ou sugerir melhorias, fique à vontade para abrir uma issue ou fork!
