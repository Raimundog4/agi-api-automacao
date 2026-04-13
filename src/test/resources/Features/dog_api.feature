#language: pt
@DogAPI
Funcionalidade: Validação dos endpoints da Dog API

  Cenário: Listar todas as raças com sucesso
    Dado que acessei a Dog API "Listar Raças"
    E sendo método HTTP "GET" Dog API
    Quando é efetuada a requisição do serviço Dog API
    Então é validado o status de retorno do serviço Dog API "200"
    E o campo "status" deve retornar "success"
    E deve retornar a lista de raças preenchida

  Cenário: Buscar imagens de uma raça válida
    Dado que acessei a Dog API "Imagens por Raça"
    E sendo método HTTP "GET" Dog API
    E informo a raça "hound" para consulta Dog API
    Quando é efetuada a requisição do serviço Dog API
    Então é validado o status de retorno do serviço Dog API "200"
    E o campo "status" deve retornar "success"
    E deve retornar lista de imagens da raça preenchida

  Cenário: Buscar imagem aleatória com sucesso
    Dado que acessei a Dog API "Imagem Aleatória"
    E sendo método HTTP "GET" Dog API
    Quando é efetuada a requisição do serviço Dog API
    Então é validado o status de retorno do serviço Dog API "200"
    E o campo "status" deve retornar "success"
    E deve retornar uma url de imagem válida

  Cenário: Buscar imagens de uma raça inexistente
    Dado que acessei a Dog API "Imagens por Raça"
    E sendo método HTTP "GET" Dog API
    E informo a raça "raca" para consulta Dog API
    Quando é efetuada a requisição do serviço Dog API
    Então a resposta deve indicar erro para a raça informada

  Cenário: Tentar acessar endpoint com método POST
    Dado que acessei a Dog API "Listar Raças"
    E sendo método HTTP "POST" Dog API
    Quando é efetuada a requisição do serviço Dog API
    Então o serviço deve rejeitar a requisição com erro
