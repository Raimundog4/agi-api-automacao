# Testes automatizados - Dog API

Projeto simples de automação criado como parte de um desafio técnico.

A ideia aqui foi validar alguns endpoints da Dog API (https://dog.ceo/dog-api/) de forma direta, sem complicar demais a estrutura, mas mantendo organização e boas práticas.

Foquei em montar o projeto espelhando a forma como atuo no dia a dia, principalmente na organização e na escrita dos testes, buscando algo simples, claro e fácil de manter.

---

## Clonar o repositório

Para clonar o projeto:

`bash`
git clone https://github.com/Raimundog4/agi-api-automacao.git

Acesse a pasta do projeto:
cd agi-api-automacao

---

## Tecnologias utilizadas

- Java
- Maven
- Cucumber
- RestAssured
- JUnit
- GitHub Actions (CI)

---

## Estrutura do projeto

Mantive a estrutura bem direta, separando apenas o necessário:

- `config` → endpoints da API  
- `steps` → onde ficam as validações e chamadas  
- `runners` → execução dos testes  
- `features` → cenários em Gherkin  

Como a API é simples, preferi não adicionar muitas camadas para não deixar o projeto mais complexo do que o necessário. 

---

## Cenários cobertos

Foram implementados os seguintes cenários:

- Listar todas as raças  
- Buscar imagens por uma raça válida  
- Buscar imagem aleatória  
- Buscar imagens com uma raça inexistente (cenário negativo)  

Também incluí um cenário adicional:

- Tentativa de requisição com método HTTP inválido (POST), validando o comportamento da API fora do esperado  

---

## Como executar

Para rodar os testes:

mvn clean test

Caso queira executar os testes e garantir a geração completa do relatório:

mvn clean verify

Ou executar diretamente a classe runner:

RunDogApiTests

---

## Relatório

Após a execução local, o relatório pode ser acessado em:

target/cucumber-reports/cucumber-report.html

---

## Integração contínua (CI)

O projeto está configurado com GitHub Actions.

A cada push, os testes são executados automaticamente e o relatório é gerado como artefato da execução, podendo ser acessado diretamente pela pipeline.

Dessa forma, não é necessário rodar o projeto localmente para visualizar os resultados dos testes.

---

## Observações

Busquei cobrir tanto cenários positivos quanto negativos, validando o comportamento da API de forma simples e direta.

Também procurei deixar os testes bem legíveis, mantendo as validações próximas dos cenários para facilitar o entendimento.

---

## Autor

José Raimundo
