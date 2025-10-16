CREATE DATABASE infobus2;
USE infobus2;

CREATE TABLE usuario(
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(20) NOT NULL UNIQUE, 
    telefone VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE, 
    senha VARCHAR(30) NOT NULL,
    tipo ENUM ('Funcionário', 'Passageiro') NOT NULL
);

INSERT INTO usuario (nome, cpf, telefone, email, senha, tipo) VALUES
('João Silva', '11111111111', '74999990001', 'joao.silva@email.com', '12345', 'Funcionário'),
('Maria Oliveira', '22222222222', '74999990002', 'maria.oliveira@email.com', '12345', 'Funcionário'),
('Carlos Souza', '33333333333', '74999990003', 'carlos.souza@email.com', '12345', 'Funcionário'),
('Ana Costa', '44444444444', '74999990004', 'ana.costa@email.com', '12345', 'Passageiro'),
('Pedro Santos', '55555555555', '74999990005', 'pedro.santos@email.com', '12345', 'Passageiro'),
('Fernanda Lima', '66666666666', '74999990006', 'fernanda.lima@email.com', '12345', 'Funcionário'),
('Lucas Pereira', '77777777777', '74999990007', 'lucas.pereira@email.com', '12345', 'Funcionário');

SELECT * FROM usuario; 

CREATE TABLE funcionario(
    idFuncionario INT PRIMARY KEY,
    matricula VARCHAR(50) NOT NULL UNIQUE,
    cargo VARCHAR(50) NOT NULL,
    salario DECIMAL(10,2),
    FOREIGN KEY(idFuncionario) REFERENCES usuario(idUsuario)
);

INSERT INTO funcionario (idFuncionario, matricula, cargo, salario) VALUES
(1, 'MAT001', 'Motorista', 3500.00),
(2, 'MAT002', 'Atendente', 2000.00),
(3, 'MAT003', 'Financeiro', 2800.00),
(6, 'MAT004', 'Gerente RH', 4500.00),
(7, 'MAT005', 'Gerente ADM', 5000.00);

ALTER TABLE funcionario ADD COLUMN statusPagamento VARCHAR(20) DEFAULT 'Pendente';

SELECT * FROM funcionario; 

SELECT * FROM funcionario f JOIN usuario u ON f.idFuncionario = u.idUsuario; 

CREATE TABLE motorista(
    idMotorista INT PRIMARY KEY,
    cnh VARCHAR(20) NOT NULL,
    FOREIGN KEY(idMotorista) REFERENCES funcionario(idFuncionario)
);

INSERT INTO motorista (idMotorista, cnh) VALUES
(1, 'ABC123456');

SELECT * FROM motorista;

CREATE TABLE atendente(
    idAtendente INT PRIMARY KEY,
    FOREIGN KEY(idAtendente) REFERENCES funcionario(idFuncionario)
);

INSERT INTO atendente (idAtendente) VALUES (2);

SELECT * FROM atendente;

CREATE TABLE financeiro(
    idFinanceiro INT PRIMARY KEY,
    FOREIGN KEY(idFinanceiro) REFERENCES funcionario(idFuncionario)
);

INSERT INTO financeiro (idFinanceiro) VALUES (3);

SELECT * FROM financeiro;

CREATE TABLE gerenteRH(
    idGerenteRH INT PRIMARY KEY,
    FOREIGN KEY(idGerenteRH) REFERENCES funcionario(idFuncionario)
);

INSERT INTO gerenteRH (idGerenteRH) VALUES (6);

SELECT * FROM gerenteRH;

CREATE TABLE gerenteADM(
    idGerenteADM INT PRIMARY KEY,
    FOREIGN KEY(idGerenteADM) REFERENCES funcionario(idFuncionario)
);

INSERT INTO gerenteADM (idGerenteADM) VALUES (7);

SELECT * FROM gerenteADM;

CREATE TABLE passageiro(
    idPassageiro INT PRIMARY KEY,
    FOREIGN KEY(idPassageiro) REFERENCES usuario(idUsuario)
);

INSERT INTO passageiro (idPassageiro) VALUES (4), (5);

SELECT * FROM passageiro;

SELECT * FROM passageiro p JOIN usuario u ON p.idPassageiro = u.idUsuario; 

CREATE TABLE itinerario(
    idItinerario INT PRIMARY KEY AUTO_INCREMENT,
    origem VARCHAR(100) NOT NULL,
    destino VARCHAR(100) NOT NULL,
    distanciaRota DOUBLE,
    tempoPercurso TIME NOT NULL
);

INSERT INTO itinerario (origem, destino, distanciaRota, tempoPercurso) VALUES
('Irecê', 'Salvador', 480.0, '07:00:00'),
('Irecê', 'Feira de Santana', 350.0, '05:00:00');

SELECT * FROM itinerario;

CREATE TABLE onibus(	
    idOnibus INT PRIMARY KEY AUTO_INCREMENT,
    placa VARCHAR(10) NOT NULL,
    modelo VARCHAR(20) NOT NULL,
    capacidade INT NOT NULL,
    classes ENUM ('Econômica', 'Executiva') NOT NULL, 
    fabricante VARCHAR(20) NOT NULL,
    idMotorista INT NOT NULL,
    idItinerario INT NOT NULL,
    FOREIGN KEY(idMotorista) REFERENCES funcionario(idFuncionario),
    FOREIGN KEY(idItinerario) REFERENCES itinerario(idItinerario)
);

INSERT INTO onibus (placa, modelo, capacidade, classes, fabricante, idMotorista, idItinerario) VALUES
('ABC1234', 'Marcopolo G7', 46, 'Econômica', 'Marcopolo', 1, 1),
('XYZ9876', 'Comil Campione', 50, 'Executiva', 'Comil', 1, 2);

SELECT * FROM onibus;

DROP TABLE onibus;

CREATE TABLE viagem (
    idViagem INT PRIMARY KEY AUTO_INCREMENT,
    dataSaida DATE,
    dataChegada DATE,
    horarioSaida TIME NOT NULL,
    horarioChegada TIME NOT NULL,
    idItinerario INT NOT NULL,
    CONSTRAINT fk_viagem_itinerario FOREIGN KEY (idItinerario)
        REFERENCES itinerario(idItinerario)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

INSERT INTO viagem (dataSaida, dataChegada, horarioSaida, horarioChegada, idItinerario) VALUES
('2025-10-05', '2025-10-05', '08:00:00', '15:30:00', 1),  
('2025-10-06', '2025-10-06', '09:00:00', '14:40:00', 2);  

SELECT * FROM viagem;

CREATE TABLE passagem (
    idPassagem INT PRIMARY KEY AUTO_INCREMENT,
    idPassageiro INT NOT NULL,
    idViagem INT NOT NULL,
    dataCompra DATE,
    valor DECIMAL(10,2),
    poltrona VARCHAR(100) NOT NULL,
    plataforma INT NOT NULL,
    classe ENUM('Econômica', 'Executiva') NOT NULL,
    CONSTRAINT fk_passagem_passageiro FOREIGN KEY (idPassageiro)
        REFERENCES passageiro(idPassageiro)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_passagem_viagem FOREIGN KEY (idViagem)
        REFERENCES viagem(idViagem)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

INSERT INTO passagem (idPassageiro, idViagem, dataCompra, valor, poltrona, plataforma, classe) VALUES
(4, 1, '2025-10-01', 120.00, '12A', 1, 'Econômica'),
(5, 2, '2025-10-02', 180.00, '05B', 2, 'Executiva');

SELECT * FROM passagem;

CREATE TABLE pagamento(
    idPagamento INT PRIMARY KEY AUTO_INCREMENT,
    valor DECIMAL(10,2),
    data_Pagamento DATE,
    idFuncionario INT,
    FOREIGN KEY(idFuncionario) REFERENCES funcionario(idFuncionario)
);

INSERT INTO pagamento (valor, data_Pagamento, idFuncionario) VALUES
(3500.00, '2025-09-30', 1),
(2000.00, '2025-09-30', 2),
(2800.00, '2025-09-30', 3),
(4500.00, '2025-09-30', 6),
(5000.00, '2025-09-30', 7);

SELECT * FROM pagamento;

CREATE TABLE despesa(
    idDespesa INT PRIMARY KEY AUTO_INCREMENT,
    valor DECIMAL(10,2),
    data_despesa DATE,
    descricao VARCHAR(100) NOT NULL
);

INSERT INTO despesa (valor, data_despesa, descricao) VALUES
(500.00, '2025-09-25', 'Manutenção preventiva do ônibus'),
(300.00, '2025-09-27', 'Combustível');

SELECT * FROM despesa;

CREATE TABLE ocorrencia(
    idOcorrencia INT PRIMARY KEY AUTO_INCREMENT,
    dataHora DATETIME,
    descricao VARCHAR(100) NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    gravidade VARCHAR(100) NOT NULL,
    idMotorista INT NOT NULL,
    CONSTRAINT fk_ocorrencia_motorista
        FOREIGN KEY (idMotorista)
        REFERENCES funcionario(idFuncionario)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

INSERT INTO ocorrencia (dataHora, descricao, tipo, gravidade, idMotorista) VALUES
('2025-10-03 10:00:00', 'Acidente leve durante viagem', 'Acidente', 'Baixa', 1),
('2025-10-04 12:30:00', 'Atraso por engarrafamento', 'Atraso', 'Média', 1);

SELECT * FROM ocorrencia;

CREATE TABLE escala(
    idEscala INT PRIMARY KEY AUTO_INCREMENT,
    idMotorista INT NOT NULL,
    idViagem INT NOT NULL,
    data_escala DATE,
    turno ENUM ('Matutino', 'Vespertino', 'Noturno') NOT NULL, 
    statusEscala VARCHAR(100) NOT NULL,
	FOREIGN KEY (idMotorista) REFERENCES funcionario(idFuncionario) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (idViagem) REFERENCES viagem(idViagem) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO escala (idMotorista, idViagem, data_escala, turno, statusEscala) VALUES
(1, 1, '2025-10-05', 'Matutino', 'Confirmada'),
(1, 2, '2025-10-05', 'Vespertino', 'Confirmada'),
(1, 1, '2025-10-06', 'Noturno', 'Pendente');

SELECT * FROM escala;

SELECT * 
FROM escala e
JOIN funcionario m ON e.idMotorista = m.idFuncionario
JOIN usuario u ON m.idFuncionario = u.idUsuario
JOIN motorista mot ON m.idFuncionario = mot.idMotorista
WHERE e.idMotorista = 1;

SELECT e.idEscala, e.data_escala, e.turno, e.statusEscala,
       m.idFuncionario AS idMotorista, u.nome AS nomeMotorista, u.cpf, u.email, u.telefone,
       m.matricula, m.cargo, m.salario, m.statusPagamento, mot.cnh,
       v.idViagem, v.dataSaida, v.dataChegada, v.horarioSaida, v.horarioChegada,
       i.idItinerario, i.origem, i.destino, i.distanciaRota, i.tempoPercurso
FROM escala e
LEFT JOIN funcionario m ON e.idMotorista = m.idFuncionario
LEFT JOIN usuario u ON m.idFuncionario = u.idUsuario
LEFT JOIN motorista mot ON m.idFuncionario = mot.idMotorista
LEFT JOIN viagem v ON e.idViagem = v.idViagem
LEFT JOIN itinerario i ON v.idItinerario = i.idItinerario
WHERE e.idMotorista = 1;


