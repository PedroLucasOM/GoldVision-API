create table usuario(
	codigo bigint(20) primary key auto_increment,
	nome varchar(50) not null,
	email varchar(100) not null,
	senha varchar(150) not null
)engine=InnoDB default charset=utf8;

create table permissao(
	codigo bigint(20) primary key auto_increment,
	nome varchar(50) not null
)engine=InnoDB default charset=utf8;

create table usuario_permissao(
	codigo_usuario bigint(20) not null,
	codigo_permissao bigint(20) not null,
	primary key(codigo_usuario, codigo_permissao),
	foreign key(codigo_usuario) references usuario(codigo),
	foreign key(codigo_permissao) references permissao(codigo)
)engine=InnoDB default charset=utf8;

insert into permissao(nome) values 
	("ROLE_SALVAR_CATEGORIA"),
	("ROLE_LISTAR_CATEGORIA"),
	("ROLE_ATUALIZAR_CATEGORIA"),
	("ROLE_DELETAR_CATEGORIA"),
	("ROLE_SALVAR_LANCAMENTO"),
	("ROLE_LISTAR_LANCAMENTO"),
	("ROLE_ATUALIZAR_LANCAMENTO"),
	("ROLE_DELETAR_LANCAMENTO"),
	("ROLE_SALVAR_PERMISSAO"),
	("ROLE_LISTAR_PERMISSAO"),
	("ROLE_ATUALIZAR_PERMISSAO"),
	("ROLE_DELETAR_PERMISSAO"),
	("ROLE_SALVAR_PESSOA"),
	("ROLE_LISTAR_PESSOA"),
	("ROLE_ATUALIZAR_PESSOA"),
	("ROLE_DELETAR_PESSOA");