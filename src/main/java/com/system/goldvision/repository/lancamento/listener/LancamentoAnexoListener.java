package com.system.goldvision.repository.lancamento.listener;

import com.system.goldvision.GoldVisionApplication;
import com.system.goldvision.model.Lancamento;
import com.system.goldvision.storage.GoogleCloudStorage;
import org.springframework.util.StringUtils;

import javax.persistence.PostLoad;

public class LancamentoAnexoListener {

    @PostLoad
    public void postLoad(Lancamento lancamento) {
        if (StringUtils.hasText(lancamento.getAnexo())) {
            GoogleCloudStorage googleCloudStorage = GoldVisionApplication.getBean(GoogleCloudStorage.class);
            lancamento.setUrlAnexo(googleCloudStorage.configurarUrl(lancamento.getAnexo()));
        }
    }

}
