package com.system.goldvision.model.metamodel;

import com.system.goldvision.model.Categoria;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Categoria.class)
public abstract class Categoria_ {

    public static final String CODIGO = "codigo";
    public static final String NOME = "nome";
    public static volatile SingularAttribute<Categoria, Long> codigo;
    public static volatile SingularAttribute<Categoria, String> nome;

}

