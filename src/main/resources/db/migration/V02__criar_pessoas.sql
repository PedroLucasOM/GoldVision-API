create table pessoa(
	codigo bigint(20) primary key auto_increment,
	nome varchar(50) not null,
	logradouro varchar(50),
	numero varchar(30),
	complemento varchar(30),
	cep varchar(30),
	bairro varchar(30),
	estado varchar(30),
	cidade varchar(30),
	ativo boolean not null
)engine=InnoDB default charset=utf8;