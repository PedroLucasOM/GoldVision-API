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

insert into usuario(nome, email, senha) values ("Administrador", "admin@gmail.com", "$2a$10$8sTy7nBtjU94Kcn9wMxIXOpefXkfYjRQbsnt7tlTZDFJRcmws1i1m");

insert into permissao(nome) values 
	("SALVAR_CATEGORIA"),
	("SALVAR_LANCAMENTO"),
	("SALVAR_PERMISSAO"),
	("SALVAR_PESSOA"),
	("SALVAR_USUARIO"),
	("LISTAR_CATEGORIA"),
	("LISTAR_LANCAMENTO"),
	("LISTAR_PERMISSAO"),
	("LISTAR_PESSOA"),	
	("LISTAR_USUARIO"),
	("ATUALIZAR_CATEGORIA"),
	("ATUALIZAR_LANCAMENTO"),
	("ATUALIZAR_PERMISSAO"),
	("ATUALIZAR_PESSOA"),
	("ATUALIZAR_USUARIO"),
	("DELETAR_CATEGORIA"),
	("DELETAR_LANCAMENTO"),
	("DELETAR_PERMISSAO"),
	("DELETAR_PESSOA"),
	("DELETAR_USUARIO");
	
insert into usuario_permissao(codigo_usuario, codigo_permissao) values (1,1), (1,2), (1,3), (1,4), (1,5), (1,6), (1,7), (1,8), (1,9), (1,10)
, (1,11), (1,12), (1,13), (1,14), (1,15), (1,16), (1,17), (1,18), (1,19), (1,20);