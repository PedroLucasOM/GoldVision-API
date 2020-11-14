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

    public String salvar(String anexoNovo) {
        Blob blob = this.getBlobFromStorage(anexoNovo);

        byte[] bytes = blob.getContent();

        this.remover(anexoNovo);

        if (anexoNovo.startsWith("C0233190445D9A8BEB9789D7398E8C9693CF30AB4E1352FF8FF5954AC389AB7F5ECD48D5C8D86835436009F941A3E6869ED54C7C23F3CE300CE6DD8EA517FD90-")) {
            anexoNovo = anexoNovo.replace("C0233190445D9A8BEB9789D7398E8C9693CF30AB4E1352FF8FF5954AC389AB7F5ECD48D5C8D86835436009F941A3E6869ED54C7C23F3CE300CE6DD8EA517FD90-", "");
        }

        this.criar(anexoNovo, bytes);

        return anexoNovo;
    }

    public String salvarTemporariamente(String filename, byte[] bytes) {

        String nomeUnico = gerarNomeUnico(filename);

        this.criar(nomeUnico, bytes);

        return nomeUnico;
    }

    public void remover(String anexo) {
        BlobId blobId = BlobId.of("goldvisionapi", anexo);
        this.storage.delete(blobId);
    }

    public String substituir(String anexoAntigo, String anexoNovo) {
        if (StringUtils.hasText(anexoAntigo)) {
            this.remover(anexoAntigo);
        }

        Blob blob = this.getBlobFromStorage(anexoNovo);

        byte[] bytes = blob.getContent();

        this.remover(anexoNovo);

        if (anexoNovo.startsWith("C0233190445D9A8BEB9789D7398E8C9693CF30AB4E1352FF8FF5954AC389AB7F5ECD48D5C8D86835436009F941A3E6869ED54C7C23F3CE300CE6DD8EA517FD90-")) {
            anexoNovo = anexoNovo.replace("C0233190445D9A8BEB9789D7398E8C9693CF30AB4E1352FF8FF5954AC389AB7F5ECD48D5C8D86835436009F941A3E6869ED54C7C23F3CE300CE6DD8EA517FD90-", "");
        }


        this.criar(anexoNovo, bytes);

        return anexoNovo;
    }

    public String configurarUrl(String anexo) {
        return "\\\\storage.googleapis.com/".concat("goldvisionapi")
                .concat("/").concat(anexo);
    }

    private void criar(String nomeUnico, byte[] bytes) {
        this.bucket.create(nomeUnico, bytes);
    }

    private String gerarNomeUnico(String originalFilename) {
        return "C0233190445D9A8BEB9789D7398E8C9693CF30AB4E1352FF8FF5954AC389AB7F5ECD48D5C8D86835436009F941A3E6869ED54C7C23F3CE300CE6DD8EA517FD90-".
                concat(UUID.randomUUID().toString().concat("_").concat(originalFilename));
    }

    private Blob getBlobFromStorage(String anexo) {
        BlobId blobId = BlobId.of("goldvisionapi", anexo);
        return this.storage.get(blobId);
    }

}
