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
    <img alt="documentation" src="https://img.shields.io/badge/documentation-yes-blue.svg" />
  </a>
  <a href="https://github.com/kefranabg/readme-md-generator/graphs/commit-activity" target="_blank">
    <img alt="maintenance" src="https://img.shields.io/badge/maintained-yes-blue.svg" />
  </a>
  <a href="https://twitter.com/PedroLucasOM" target="_blank">
    <img alt="Twitter: PedroLucasOM" src="https://img.shields.io/twitter/follow/PedroLucasOM.svg?style=social" />
  </a>
</p>

> :computer: API for controlling finances with statistical data  :bar_chart:, routines, e-mail release notices :mailbox_with_no_mail:, security with OAuth2 using JSON Web Tokens :closed_lock_with_key: and usability documentation with SwaggerAPI :clipboard:.

<br/>

# Prerequisites

- docker

# Install

### Environment settings

#### Google Cloud

You will need to create a Google Cloud account to be able to access Google Cloud Storage and run the application.

To do this, you will need to generate and download the authentication keys in the JSON file.

Follow the steps:

<img src="https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/google-cloud-7.png" width="250" />
<br />

If you do not already have a service account, create it by clicking on the top option. If you already have it, proceed to the next step by clicking on the corresponding service account:

<img src="https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/google-cloud3.png" width="800" /> 
<br />

Find the Keys tab and click on the option to add:

<img src="https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/google-cloud4.png" width="600" />
<br />

Click the option to create a new key:

<img src="https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/google-cloud5.png" width="600" /> 
<br />

Select the JSON option to download and click create:

<img src="https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/google-cloud6.png" width="600" />
<br />

After downloading the keys.json to your local machine, paste it at the root of the project and rename it to **goldvision-credentials.json**.

#### Environment variables

You will also need to create a **.env** file in the root path of the project to configure the application execution. The content of the **.env** must be like this:

```
DOCKER_DATABASE_USERNAME= it's recomended set "root" like the example in the picture bellow
DOCKER_DATABASE_PASSWORD= here you can put any password that you prefer

LOCAL_DATABASE_URL= here you will set your database url connection (it's only necessary if you want to run the application locally without docker, if not, you can let it empty)
LOCAL_DATABASE_USERNAME= here you will put your local database username (it's only necessary if you want to run the application locally without docker, if not, you can let it empty)
LOCAL_DATABASE_PASSWORD= here you will put your local database password (it's only necessary if you want to run the application locally without docker, if not, you can let it empty)

ORIGEM_PERMITIDA= here you will put the frontend client url that will consume your application

MAIL_EMAIL= here you will put the email address that will send emails in the application
MAIL_PASSWORD= here you will put the email password

PROJECT_ID= this information can be found in the Google Cloud Storage Service tab
BUCKET_ID= this information can be found in the Google Cloud Storage Service tab
```

Example values:

<img src="https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/env.png" width="600" />

# Run

With the docker started, execute this command at the project root:

```sh
docker-compose up -d --build
```

If you don't want to use the docker, you can run this application locally in your IDE. You just need add this configuration in VM_OPTIONS of your IDE:

```sh
-Dspring.profiles.active=local
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

### Schedule for sending expired entries

<img src="https://github.com/PedroLucasOM/GoldVision-API/blob/master/docs/media/e-mail.png" width="800" /> 

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

Copyright © 2021 [Pedro Lucas](https://github.com/PedroLucasOM).<br />
