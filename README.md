### Description
Desafio proposto pela hexacta para um teste seletivo sendo ele, um server socket em java 8 para consultar títulos de filmes.

### Platform
 - Java 8 SDK 1.8 
 - Apache Maven 3

### Package/Project Hierarchy
 - *contabilone* - _Parent project_
 - *contabilone-server* - _Modulo servidor, realiza a comunicação com serviços ‘web’ externo para consultar títulos_
 - *contabilone-client* - _Modulo client, realiza a conexão bidirecional TCP/IP com o serviço local server socket *127.0.0.1:3380*_
 
### Setup
 - Acesse o *parent project* e execute ```$ mvn clean install```
 - Gerar relatorios ```$ mvn surefire-report:report```
 
### Run Project
Após o setup já pode executar o projeto, basta seguir os comandos abaixo:
 1. Ainda no parent note que será criado um diretorio novo com os executaveis _.../packages/contabilone_V1.0.0_ acesso o diretorio e execute os 
 comandos abaixo num terminal (linux/windows):
```shell script
$ java -jar contabilone-server.jar [--port=1024]
$ java -jar contabilone-client.jar [--port=1024]
```

obs.: A quantidade de cliente é independente. 