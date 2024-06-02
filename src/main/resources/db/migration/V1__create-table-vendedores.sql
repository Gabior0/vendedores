create table vendedores(

    id bigint not null auto_increment,
    matricula varchar(100) not null unique,
    nome varchar(100) not null,
    data_nascimento date,
    cpfCnpj varchar(15) not null,
    email varchar(100) not null unique,
    tipo_contratacao varchar(100) not null,
    primary key(id)

);