package steps;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import config.EndpointsProperties;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DogApiSteps {

    private Response response;
    private String endpoint = "";
    private String metodo = "";
    private String raca = "";

    @Dado("que acessei a Dog API {string}")
    public void queAcesseiADogAPI(String endpoint) {
        this.endpoint = endpoint;

        RestAssured.reset();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(EndpointsProperties.DOG_API_URI)
                .build();
    }

    @E("sendo método HTTP {string} Dog API")
    public void sendoMetodoHTTPDogAPI(String metodo) {
        this.metodo = metodo;
    }

    @E("informo a raça {string} para consulta Dog API")
    public void informoARacaParaConsultaDogAPI(String raca) {
        this.raca = raca;
    }

    @Quando("é efetuada a requisição do serviço Dog API")
    public void eEfetuadaARequisicaoDoServicoDogAPI() {
    	/*
    	 * Como o desafio cobre poucos endpoints, optei por usar um switch para deixar a chamada mais organizada.
    	 * Aqui também deixei tratado o caso de ser informado um método HTTP diferente do que cada endpoint aceita.
    	 */
        switch (this.endpoint) {
            case "Listar Raças":
                if (this.metodo.equalsIgnoreCase("GET")) {
                    response = given()
                            .when()
                            .get(EndpointsProperties.BREEDS_LIST_ALL_PATH);
                } else if (this.metodo.equalsIgnoreCase("POST")) {
                    response = given()
                            .when()
                            .post(EndpointsProperties.BREEDS_LIST_ALL_PATH);
                } else {
                    throw new IllegalArgumentException("Método HTTP não suportado para o endpoint Listar Raças.");
                }
                break;

            case "Imagens por Raça":
                if (this.metodo.equalsIgnoreCase("GET")) {
                    response = given()
                            .pathParam("breed", this.raca)
                            .when()
                            .get(EndpointsProperties.BREED_IMAGES_PATH);
                } else {
                    throw new IllegalArgumentException("Método HTTP não suportado para o endpoint Imagens por Raça.");
                }
                break;

            case "Imagem Aleatória":
                if (this.metodo.equalsIgnoreCase("GET")) {
                    response = given()
                            .when()
                            .get(EndpointsProperties.BREEDS_RANDOM_IMAGE_PATH);
                } else {
                    throw new IllegalArgumentException("Método HTTP não suportado para o endpoint Imagem Aleatória.");
                }
                break;

            default:
                throw new IllegalArgumentException("Endpoint informado não é válido para a Dog API.");
        }
    }

    @Entao("é validado o status de retorno do serviço Dog API {string}")
    public void eValidadoOStatusDeRetornoDoServicoDogAPI(String status) {
        assertEquals("Status code diferente do esperado.",
                Integer.parseInt(status), response.getStatusCode());
    }

    @E("o campo {string} deve retornar {string}")
    public void oCampoDeveRetornar(String campo, String valorEsperado) {
        assertEquals("Valor do campo diferente do esperado.",
                valorEsperado, response.jsonPath().getString(campo));
    }

    @E("deve retornar a lista de raças preenchida")
    public void deveRetornarAListaDeRacasPreenchida() {
        Map<String, Object> racas = response.jsonPath().getMap("message");

        assertNotNull("O campo 'message' não foi retornado.", racas);
        assertFalse("A lista de raças retornou vazia.", racas.isEmpty());
        assertTrue("A raça 'hound' não foi encontrada no retorno.", racas.containsKey("hound"));
    }

    @E("deve retornar lista de imagens da raça preenchida")
    public void deveRetornarListaDeImagensDaRacaPreenchida() {
        List<String> lista = response.jsonPath().getList("message");

        assertNotNull("O campo 'message' não foi retornado.", lista);
        assertFalse("A lista de imagens da raça retornou vazia.", lista.isEmpty());
    }

    @E("deve retornar uma url de imagem válida")
    public void deveRetornarUmaUrlDeImagemValida() {
        String imagem = response.jsonPath().getString("message");

        assertNotNull("A URL da imagem não foi retornada.", imagem);
        assertFalse("A URL da imagem retornou vazia.", imagem.trim().isEmpty());
        assertTrue("A resposta não retornou uma URL válida.",
                imagem.startsWith("http://") || imagem.startsWith("https://"));
    }
    
    // Validação de cenário negativo com raça inexistente
    @Entao("a resposta deve indicar erro para a raça informada")
    public void aRespostaDeveIndicarErroParaARacaInformada() {
        String status = response.jsonPath().getString("status");
        String message = response.jsonPath().getString("message");

        assertTrue("Era esperado retorno de erro para raça inválida.",
                response.getStatusCode() == 404 || "error".equalsIgnoreCase(status));

        assertNotNull("A mensagem de erro não foi retornada.", message);
        assertFalse("A mensagem de erro retornou vazia.", message.trim().isEmpty());
    }

    // Cenário adicional para validar comportamento da API quando utilizado um método HTTP fora do esperado
    @Entao("o serviço deve rejeitar a requisição com erro")
    public void oServicoDeveRejeitarARequisicaoComErro() {
        int statusCode = response.getStatusCode();
        String status = response.jsonPath().getString("status");

        assertTrue("Era esperado erro para método inválido.",
                statusCode >= 400);

        assertTrue("Era esperado status de erro.",
                "error".equalsIgnoreCase(status) || statusCode == 404 || statusCode == 405);
    }
}