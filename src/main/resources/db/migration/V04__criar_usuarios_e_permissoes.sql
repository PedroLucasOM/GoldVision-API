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
	foreign key(codigo_permissao) references permissoe(codigo)
)engine=InnoDB default charset=utf8;