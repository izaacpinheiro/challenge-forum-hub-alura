create table topicos (
    id bigint auto_increment primary key,
    titulo varchar(100) not null unique,
    mensagem varchar(250) not null unique,
    data_criacao datetime not null,
    curso varchar(255) not null,
    status varchar(30) not null,
    autor_id bigint not null,
    foreign key (autor_id) references usuarios(id)
);