
# Projeto Java

Sistema de Vendas (POS) em Java usando Spring MVC

# Execução

1. Baixar o Maven
2. Clonar o repositório
3. Entrar no diretório sistema-vendas
4. Executar o comando
```
mvn clean install
```
5. Executar o comando
```
mvn spring-boot:run
```

Caso dê conflito de porta, checar qual processo está rodando na porta 8080 no terminal
```
netstat -aon | findstr :8080
```
E finalizar o processo com o PID encontrado
