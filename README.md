<h1 align="center" width="100vw" style="background-color: #1e94d2">
  <img alt="Logo: GoldVision" src="https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/logo.png" />
</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000" />
  <img src="https://img.shields.io/badge/java-1.8-blue.svg" />
  <img src="https://img.shields.io/badge/hibernate-5.3.2-blue.svg" />
  <img src="https://img.shields.io/badge/spring-1.5.19-blue.svg" />
  <img src="https://img.shields.io/badge/swagger-2.9.2-blue.svg" />
  <img src="https://img.shields.io/badge/jasperreports-6.15.0-blue.svg" />
  <img src="https://img.shields.io/badge/googlecloud-1.17.0-blue.svg" />
  <a href="https://github.com/PedroLucasOM/MovieViewer#readme" target="_blank">
    <img alt="documentation" src="https://img.shields.io/badge/documentation-yes-green.svg" />
  </a>
  <a href="https://github.com/kefranabg/readme-md-generator/graphs/commit-activity" target="_blank">
    <img alt="maintenance" src="https://img.shields.io/badge/maintained-yes-green.svg" />
  </a>
  <a href="https://twitter.com/PedroLucasOM" target="_blank">
    <img alt="Twitter: PedroLucasOM" src="https://img.shields.io/twitter/follow/PedroLucasOM.svg?style=social" />
  </a>
</p>

> 💻 API para controle de finanças com dados estatísticos 📊, rotinas, avisos de lançamentos por e-mail 📭, segurança com OAuth2 usando o JSON Web Tokens 🔐 e documentação de usabilidade com o SwaggerAPI 📋

# 🏠 [Homepage](https://github.com/PedroLucasOM/GoldVision-API)

# Prerequisites

- docker

# Install

### Environment settings

#### Google Cloud

You will need to create a Google Cloud account to be able to access Google Cloud Storage and run the application.

To do this, you will need to generate and download the authentication keys in the JSON file.

Follow the steps:

<img src="https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/google-cloud1.png" width="250" />
  
Caso você ainda não possua uma conta de serviço, cria-a clicando na opção superior. Caso já possua, siga para o próximo passo clicando na conta de serviço correspondente:

<img src="https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/google-cloud3.png" width="800" /> 

Encontre a aba de chaves e clique na opção de adicionar:

<img src="https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/google-cloud4.png" width="600" /> 

Clique na opção de criar nova chave:

<img src="https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/google-cloud5.png" width="600" /> 

Selecione a opção JSON para download e clique em criar:

<img src="https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/google-cloud6.png" width="600" />

Após ter realizado o download das chaves.json para a sua máquina local, cole-o na raiz do projeto e renomeie-o para **goldvision-credentials.json**.

#### Environment variables

# Run

With the docker started, execute this command at the project root:

```sh
docker-compose up -d --build
```

# Usage

Go to http://localhost:8080/swagger-ui.html

### Login

Follow the following action so that you can authenticate yourself in the system and make requests in the API:

![login](https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/login.gif)
<br />

### Resources requests

Follow the action to make requests on the available resources:

![resources](https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/resources.gif)
<br />

### Models view

Follow the action to view the structure of the models carried in the requisitions:

![models](https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/models.gif)
<br />

### Logout

Follow the action to logout:

![logout](https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/logout.gif)
<br />

# Author

👤 **Pedro Lucas**

* Twitter: [@PedroLucasOM](https://twitter.com/PedroLucasOM)
* Github: [@PedroLucasOM](https://github.com/PedroLucasOM)
* LinkedIn: [@PedroLucasOM](https://linkedin.com/in/PedroLucasOM)

# 🤝 Contributing

Contributions, issues and feature requests are welcome!<br />Feel free to check [issues page](https://github.com/PedroLucasOM/GoldVision-API/issues). 

# Show your support

Give a ⭐️ if this project helped you!

# 📝 License

Copyright © 2020 [Pedro Lucas](https://github.com/PedroLucasOM).<br />
