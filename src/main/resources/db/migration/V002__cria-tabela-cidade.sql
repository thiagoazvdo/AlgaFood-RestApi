CREATE TABLE CIDADE (
	ID BIGINT NOT NULL AUTO_INCREMENT,
    NOME_CIDADE VARCHAR(80) NOT NULL,
    NOME_ESTADO VARCHAR(80) NOT NULL,
    
    PRIMARY KEY (ID)
) ENGINE=InnoDB default charset=utf8;