# API - Authorizer

### Propósito

Expor uma API para que os portadores consigam efetuar transações de crédito pela plataforma Caju.


### Arquitetura

Hexagonal

Veja as dependências do [pom](pom.xml).

Veja a documentação da API [swagger] (http://localhost:8080/swagger-ui/index.html)

Java 21

Banco de Dados H2. Caso prefira conectar direto banco, utilizar docker-compose.yml

Para efetuar testes, utilizar Postman e a Collection "Caju - APIAuthorizer"

Importante: Este projeto utiliza Lombok. Para abrir com Eclipe precisa instalar > https://projectlombok.org/setup/eclipse
            Caso utilize IDE IntelliJ não precisa instalar.
            
### Documentação

Fluxo principal, Desafio L4 e sugestões para proximas implementações constam no diagrama Fluxo_Autorizador_Caju.drawio     

Pasta com todos os arquivos > authorizer/docs    


###   
            
            
            

