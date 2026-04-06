-- os insert esta em aventureiro

select * from missao;

select id , titulo , status , nivel_perigo , data_criacao , data_inicio , data_termino  from Missao
where status = 'PLANEJADA' and nivel_perigo = 'MEDIO' and data_criacao = '' and data_termino = '';


select * from participacao_em_missao;

-- gerando rank missao
select m.id , m.titulo , m.status , m.nivel_perigo , count(p.id) as total_participacao , sum(p.recompensa_em_ouro) as total_recompensa from participacao_em_missao p
join missao m on m.id = p.missao_id group by m.id , m.titulo , m.status , m.nivel_perigo;

-- girando rank aventureiro
select p.aventureiro_id , a.nome , count(p.id) as total_participacao , sum(p.recompensa_em_ouro) as total_recompensa , sum(case when p.destaqueMVP = true then 1 else 0 end) as total_mvp from participacao_em_missao p join  aventureiro a
on a.id = p.aventureiro_id;


