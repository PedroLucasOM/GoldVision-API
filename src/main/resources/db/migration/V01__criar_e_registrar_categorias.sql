create table categoria(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome varchar(50) NOT NULL
)engine=InnoDB default charset=utf8;

insert into categoria(nome) values ("Lazer"), ("Alimentação"), ("Supermercado"), ("Farmácia"), ("Outros");