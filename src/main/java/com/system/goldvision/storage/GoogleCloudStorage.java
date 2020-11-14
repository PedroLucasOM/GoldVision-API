package com.system.goldvision.storage;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.system.goldvision.config.property.GoldVisionProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Component
public class GoogleCloudStorage {

    @Autowired
    private GoldVisionProperty property;

    @Autowired
    private Storage storage;

    @Autowired
    private Bucket bucket;

    public String salvar(String anexoNovo) {
        Blob blob = this.getBlobFromStorage(anexoNovo);

        byte[] bytes = blob.getContent();

        this.remover(anexoNovo);

        if (anexoNovo.startsWith(property.getStorageConfig().getTempFilePrefix())) {
            anexoNovo = anexoNovo.replace(property.getStorageConfig().getTempFilePrefix(), "");
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
        BlobId blobId = BlobId.of(property.getStorageConfig().getBucketName(), anexo);
        this.storage.delete(blobId);
    }

    public String substituir(String anexoAntigo, String anexoNovo) {
        if (StringUtils.hasText(anexoAntigo)) {
            this.remover(anexoAntigo);
        }

        Blob blob = this.getBlobFromStorage(anexoNovo);

        byte[] bytes = blob.getContent();

        this.remover(anexoNovo);

        if (anexoNovo.startsWith(property.getStorageConfig().getTempFilePrefix())) {
            anexoNovo = anexoNovo.replace(property.getStorageConfig().getTempFilePrefix(), "");
        }


        this.criar(anexoNovo, bytes);

        return anexoNovo;
    }

    public String configurarUrl(String anexo) {
        return "\\\\storage.googleapis.com/".concat(property.getStorageConfig().getBucketName())
                .concat("/").concat(anexo);
    }

    private void criar(String nomeUnico, byte[] bytes) {
        this.bucket.create(nomeUnico, bytes);
    }

    private String gerarNomeUnico(String originalFilename) {
        return property.getStorageConfig().getTempFilePrefix().
                concat(UUID.randomUUID().toString().concat("_").concat(originalFilename));
    }

    private Blob getBlobFromStorage(String anexo) {
        BlobId blobId = BlobId.of(property.getStorageConfig().getBucketName(), anexo);
        return this.storage.get(blobId);
    }

}
