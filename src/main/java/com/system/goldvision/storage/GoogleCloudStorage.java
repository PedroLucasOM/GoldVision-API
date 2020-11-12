package com.system.goldvision.storage;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Component
public class GoogleCloudStorage {

    private final Storage storage;

    private final Bucket bucket;

    private static final Logger logger = LoggerFactory.getLogger(GoogleCloudStorage.class);

    public GoogleCloudStorage() throws IOException {
        Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream("D:\\Google Cloud\\GoldVisionAPI-7fdd71a2068b.json"));
        this.storage = StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId("goldvisionapi").build().getService();
        this.bucket = this.storage.get("goldvisionapi");
    }

    public String salvar(String filename, byte[] bytes, boolean temp) {

        String nomeUnico = filename;

        if (temp) {
            nomeUnico = gerarNomeUnico(filename);
        }

        this.bucket.create(nomeUnico, bytes);

        if (logger.isDebugEnabled()) {
            logger.debug("Arquivo {} enviado com sucesso para o Google Cloud Storage.",
                    filename);
        }

        return nomeUnico;
    }

    public String configurarUrl(String objeto) {
        return "\\\\storage.googleapis.com/".concat("goldvisionapi")
                .concat("/").concat(objeto);
    }


    public void remover(String anexo) {
        BlobId blobId = BlobId.of("goldvisionapi", anexo);
        this.storage.delete(blobId);
    }

    public void substituir(String objetoAntigo, String objetoNovo) {
        if (StringUtils.hasText(objetoAntigo)) {
            this.remover(objetoAntigo);
        }

        BlobId blobId = BlobId.of("goldvisionapi", objetoNovo);
        Blob blob = this.storage.get(blobId);

        byte[] bytes = blob.getContent();

        this.remover(objetoNovo);

        if (objetoNovo.contains("C0233190445D9A8BEB9789D7398E8C9693CF30AB4E1352FF8FF5954AC389AB7F5ECD48D5C8D86835436009F941A3E6869ED54C7C23F3CE300CE6DD8EA517FD90-")) {
            objetoNovo = objetoNovo.replace("C0233190445D9A8BEB9789D7398E8C9693CF30AB4E1352FF8FF5954AC389AB7F5ECD48D5C8D86835436009F941A3E6869ED54C7C23F3CE300CE6DD8EA517FD90", "");
        }

        salvar(objetoNovo, bytes, false);
    }

    private String gerarNomeUnico(String originalFilename) {
        return "C0233190445D9A8BEB9789D7398E8C9693CF30AB4E1352FF8FF5954AC389AB7F5ECD48D5C8D86835436009F941A3E6869ED54C7C23F3CE300CE6DD8EA517FD90-".
                concat(UUID.randomUUID().toString().concat("_").concat(originalFilename));
    }

}
