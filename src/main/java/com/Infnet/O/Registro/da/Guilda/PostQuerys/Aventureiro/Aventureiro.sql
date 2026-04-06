-- Para funcionar o arquivo sql precisaria esta Intellij Database integrada no Intellij IDEA
-- Aqui mostrarei que as tabelas estao criadas 100%

-- lembrando que nao vai ter nada nas tabelas e so uma mostra que as tabelas estao criadas

-- Aventureiro


-- para o intellij saber onde esta o schema aventureiro;
set search_path to aventura;

-- mostrando as tabelas criadas
select column_name , data_type from information_schema.columns where table_name = 'aventureiro';
select column_name , data_type from information_schema.columns where table_name = 'companheiro';
select column_name , data_type from information_schema.columns where table_name = 'missao';
select column_name , data_type from information_schema.columns where table_name = 'participacao_em_missao';;


-- aqui eu inserir os nomes nas tabelas para fazer uns teste, e aprender como funiona o banco de dados e os repositorios
INSERT INTO aventura.aventureiro (id, ativo, classe, data_atualizacao, data_criacao, nivel, nome, organizacao_id, usuario_id) VALUES
                                                                                                                                  (1, true, 'GUERREIRO', NOW(), NOW(), 10, 'Alaric Strong', 1, 1),
                                                                                                                                  (2, true, 'MAGO', NOW(), NOW(), 12, 'Elara Mist', 1, 2),
                                                                                                                                  (3, true, 'ARQUEIRO', NOW(), NOW(), 8, 'Kaelen Swift', 2, 3),
                                                                                                                                  (4, true, 'CLERIGO', NOW(), NOW(), 15, 'Brother John', 1, 1),
                                                                                                                                  (5, true, 'LADINO', NOW(), NOW(), 9, 'Shadow Step', 2, 2),
                                                                                                                                  (6, true, 'GUERREIRO', NOW(), NOW(), 5, 'Borg Iron', 1, 3),
                                                                                                                                  (7, true, 'MAGO', NOW(), NOW(), 20, 'Zalthar Wise', 2, 1),
                                                                                                                                  (8, true, 'ARQUEIRO', NOW(), NOW(), 7, 'Lira Moon', 1, 2),
                                                                                                                                  (9, true, 'CLERIGO', NOW(), NOW(), 11, 'Selene Light', 2, 3),
                                                                                                                                  (10, true, 'LADINO', NOW(), NOW(), 14, 'Nyx Night', 1, 1),
                                                                                                                                  (11, true, 'GUERREIRO', NOW(), NOW(), 3, 'Tormund Beard', 2, 2),
                                                                                                                                  (12, true, 'MAGO', NOW(), NOW(), 18, 'Ignis Fatuus', 1, 3),
                                                                                                                                  (13, true, 'ARQUEIRO', NOW(), NOW(), 6, 'Finn Wood', 2, 1),
                                                                                                                                  (14, true, 'CLERIGO', NOW(), NOW(), 9, 'Uther Faith', 1, 2),
                                                                                                                                  (15, true, 'LADINO', NOW(), NOW(), 13, 'Rogue One', 2, 3);

-- 2. Inserções para COMPANHEIRO (10 registros)
INSERT INTO aventura.companheiro (id, especie, lealdade, nome, aventureiro_id, organizacao_id) VALUES
                                                                                                   (1, 'LOBO', 95, 'Fenrir', 1, 1),
                                                                                                   (2, 'CORUJA', 100, 'Hedwig', 2, 1),
                                                                                                   (3, 'GOLEM', 80, 'Stone', 6, 1),
                                                                                                   (4, 'DRAGAO_MINIATURA', 70, 'Sparky', 7, 2),
                                                                                                   (5, 'LOBO', 85, 'Ghost', 3, 2),
                                                                                                   (6, 'CORUJA', 90, 'Athena', 12, 1),
                                                                                                   (7, 'GOLEM', 88, 'Rocky', 11, 2),
                                                                                                   (8, 'DRAGAO_MINIATURA', 60, 'Ember', 15, 2),
                                                                                                   (9, 'LOBO', 99, 'Rex', 4, 1),
                                                                                                   (10, 'CORUJA', 92, 'Luna', 13, 2);

-- 3. Inserções para MISSAO (10 registros)
INSERT INTO aventura.missao (id, data_criacao, data_inicio, data_termino, nivel_perigo, status, titulo, organizacao_id) VALUES
                                                                                                                            (1, NOW(), NOW(), NULL, 'BAIXO', 'PLANEJADA', 'Limpeza de Ratos', 1),
                                                                                                                            (2, NOW(), NOW(), NULL, 'MEDIO', 'EM_ANDAMENTO', 'Resgate na Floresta', 1),
                                                                                                                            (3, NOW(), NOW(), NOW(), 'ALTO', 'CONCLUIDA', 'Matar o Ogro', 2),
                                                                                                                            (4, NOW(), NOW(), NULL, 'CRITICO', 'EM_ANDAMENTO', 'O Despertar do Dragão', 1),
                                                                                                                            (5, NOW(), NOW(), NOW(), 'BAIXO', 'CANCELADA', 'Entrega de Carta', 2),
                                                                                                                            (6, NOW(), NOW(), NULL, 'MEDIO', 'PLANEJADA', 'Escolta de Mercador', 1),
                                                                                                                            (7, NOW(), NOW(), NULL, 'ALTO', 'EM_ANDAMENTO', 'Infiltração no Castelo', 2),
                                                                                                                            (8, NOW(), NOW(), NOW(), 'CRITICO', 'CONCLUIDA', 'Banir o Demônio', 1),
                                                                                                                            (9, NOW(), NOW(), NULL, 'BAIXO', 'PLANEJADA', 'Colheita de Ervas', 2),
                                                                                                                            (10, NOW(), NOW(), NULL, 'MEDIO', 'EM_ANDAMENTO', 'Investigar Ruínas', 1);

-- 4. Inserções para PARTICIPACAO_EM_MISSAO (15 registros)
INSERT INTO aventura.participacao_em_missao (id, data_de_registro, destaque_mvp, papel_na_missao, recompensa_em_ouro, aventureiro_id, missao_id, organizacao_id) VALUES
                                                                                                                                                                     (1, NOW(), false, 'LIDER', 100, 1, 1, 1),
                                                                                                                                                                     (2, NOW(), true, 'ATAQUE', 500, 2, 2, 1),
                                                                                                                                                                     (3, NOW(), false, 'SUPORTE', 50, 3, 3, 2),
                                                                                                                                                                     (4, NOW(), false, 'CURANDEIRO', 200, 4, 2, 1),
                                                                                                                                                                     (5, NOW(), true, 'ATAQUE', 1000, 7, 4, 1),
                                                                                                                                                                     (6, NOW(), false, 'LIDER', 300, 10, 7, 2),
                                                                                                                                                                     (7, NOW(), false, 'SUPORTE', 150, 12, 4, 1),
                                                                                                                                                                     (8, NOW(), true, 'ATAQUE', 800, 15, 8, 1),
                                                                                                                                                                     (9, NOW(), false, 'CURANDEIRO', 400, 9, 8, 1),
                                                                                                                                                                     (10, NOW(), false, 'LIDER', 250, 5, 10, 1),
                                                                                                                                                                     (11, NOW(), false, 'ATAQUE', 120, 6, 6, 1),
                                                                                                                                                                     (12, NOW(), true, 'SUPORTE', 90, 8, 1, 1),
                                                                                                                                                                     (13, NOW(), false, 'LIDER', 600, 12, 7, 2),
                                                                                                                                                                     (14, NOW(), false, 'CURANDEIRO', 300, 14, 4, 1),
                                                                                                                                                                     (15, NOW(), true, 'ATAQUE', 450, 1, 3, 2);



-- aqui esta o truncate para limpar as tabelas
TRUNCATE TABLE
    aventura.participacao_em_missao,
    aventura.companheiro,
    aventura.aventureiro,
    aventura.missao
    RESTART IDENTITY CASCADE;


select * from aventureiro;


-- aqui esta o select para verificar se as tabelas estao funcionando
select usr.nome , org.nome , org.ativo , av.id as id_aventureiro , av.nome , av.nivel , av.classe , av.ativo ,co.especie , co.nome  , co.lealdade , mi.titulo , mi.status , mi.nivel_perigo , mip.papel_na_missao  , mip.recompensa_em_ouro , mip.destaque_mvp
FROM aventura.aventureiro av
         join aventura.companheiro co on av.id = co.aventureiro_id
         JOIN audit.organizacoes org ON av.organizacao_id = org.id
         JOIN audit.usuarios usr ON av.usuario_id = usr.id
         join aventura.missao mi on org.id = mi.organizacao_id
         join aventura.participacao_em_missao mip on mi.id = mip.missao_id;




-- aqui eu estava tendo uma ideia de como faria o filtro do aventureiro

-- filtro de aventureiro
select id , nome , classe , nivel from aventura.aventureiro where ativo = true and classe = 'GUERREIRO' and nivel = 10;




