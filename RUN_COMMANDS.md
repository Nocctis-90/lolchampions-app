# Comandos para rodar a aplicação

## Backend (Spring Boot)
Como o Maven Wrapper (`mvnw`) não está presente no projeto, você deve usar o Maven instalado no seu sistema.

Para rodar o backend, navegue até a pasta raiz do projeto (`lolchampions-app`) e execute:

### Git Bash / Terminal
```bash
mvn spring-boot:run
```

## Frontend (Angular)
Para rodar o frontend, navegue até a pasta `frontend` e execute:

### Git Bash / Terminal
```bash
# Navegue até a pasta do frontend
cd frontend

# Instale as dependências (caso não tenha feito)
npm install

# Rode o servidor de desenvolvimento
npm start
```
Ou diretamente:
```bash
ng serve
```
