Todas as partes deste trabalho devem ser da autoria do aluno. Qualquer uso de ferramentas generativas de IA, como ChatGPT, é proibido. O uso de IA generativa será considerado má conduta acadêmica e estará sujeito à aplicação do código disciplinar, pois as tarefas deste trabalho foram elaboradas para desafiar o aluno a desenvolver conhecimentos de base, pensamento crítico e habilidades de resolução de problemas. O uso da tecnologia de IA limitaria sua capacidade de desenvolver essas competências e de atingir os objetivos de aprendizagem desta disciplina.

Durante séculos, a Guilda de Aventureiros manteve seus registros em pergaminhos espalhados por salões, cofres e mesas de taverna.
Nomes riscados às pressas, níveis anotados a carvão, companheiros esquecidos nas margens do papel.

Com o aumento das expedições e o surgimento de novas ameaças, o Conselho da Guilda decidiu que não bastava mais confiar na memória dos escribas.

Era necessário um Registro Oficial.

Esse registro deveria:

saber quem pertence à guilda

distinguir ativos dos expulsos

registrar classes e níveis com precisão

e manter, quando existente, o vínculo entre um aventureiro e seu companheiro leal

O Conselho impôs regras claras:

ninguém entra sem ser devidamente registrado

registros inválidos não são aceitos

aventureiros expulsos não desaparecem da história

companheiros não vagam sozinhos pelos arquivos

Você foi convocado para construir esse novo registro.

O Conselho não fornecerá instruções técnicas.
Apenas os requisitos do mundo real.

A Guilda espera clareza, consistência e respeito às regras.
Erros de registro custam vidas em campo.

Conceitos do domínio

Aventureiro
Um aventureiro possui obrigatoriamente:

id — identificador único, gerado pelo sistema
nome
classe — valor obrigatório, pertencente a um conjunto fixo
nível
ativo
companheiro — opcional
Classes permitidas
O campo classe deve aceitar exclusivamente um dos valores abaixo:

GUERREIRO
MAGO
ARQUEIRO
CLERIGO
LADINO
Qualquer outro valor deve ser tratado como inválido.

Companheiro (composição)
O companheiro existe apenas como parte do aventureiro.
Não pode existir isoladamente nem ser compartilhado entre aventureiros.

Um companheiro possui:

nome
especie — valor obrigatório, pertencente a um conjunto fixo
lealdade — valor inteiro entre 0 e 100
Espécies permitidas
O campo especie deve aceitar exclusivamente um dos valores abaixo:

LOBO
CORUJA
GOLEM
DRAGAO_MINIATURA
Regras de negócio
O id é sempre gerado pelo sistema.
O nome do aventureiro é obrigatório e não pode ser vazio.
A classe deve pertencer ao conjunto permitido.
O nível deve ser maior ou igual a 1.
Um aventureiro recém-criado inicia obrigatoriamente como ativo.
Um aventureiro inativo continua existindo no sistema.
Caso exista companheiro:
nome é obrigatório
especie deve pertencer ao conjunto permitido
lealdade deve estar entre 0 e 100
Operações sobre recursos inexistentes devem indicar que o recurso não foi encontrado.
Solicitações com dados inválidos devem ser rejeitadas como inválidas, informando os motivos.
Operações disponíveis
1) Registrar aventureiro
Registra um novo aventureiro na guilda.

O cliente fornece:

nome
classe
nível
O sistema:

gera o id
define o aventureiro como ativo
não permite definir companheiro nessa operação
O sistema deve indicar claramente que um novo recurso foi criado.

2) Listar aventureiros (com filtros e paginação)
Retorna aventureiros cadastrados.

Filtros suportados:

por classe
por ativo
por nível mínimo
Parâmetros de paginação:

page — número da página, iniciando em 0
size — quantidade de itens por página
Valores padrão:

page = 0
size = 10
Restrições:

page não pode ser negativo
size deve estar entre 1 e 50
A resposta deve conter:

lista de aventureiros em formato de resumo, sem informações de companheiro
metadados de paginação retornados via headers:
Headers obrigatórios:

X-Total-Count
X-Page
X-Size
X-Total-Pages
Caso a página solicitada não exista, a resposta deve conter:

lista vazia
headers de paginação corretos
3) Consultar aventureiro por id
Retorna todas as informações do aventureiro, incluindo o companheiro (se existir).

Caso o id informado não exista, o sistema deve indicar recurso não encontrado.

4) Atualizar dados do aventureiro
Permite atualizar exclusivamente:

nome
classe
nível
Não é permitido:

alterar id
alterar estado ativo
alterar companheiro
Dados inválidos devem ser rejeitados.
Caso o recurso não exista, deve ser indicado não encontrado.

5) Encerrar vínculo com a guilda
Altera o estado do aventureiro para ativo = false.

O aventureiro permanece registrado no sistema.

6) Recrutar novamente
Altera o estado do aventureiro para ativo = true.

Composição — gerenciamento do Companheiro
7) Definir ou substituir companheiro
Cria ou substitui o companheiro associado a um aventureiro.

O cliente fornece:

nome
especie
lealdade
Após a operação, o aventureiro passa a possuir exatamente um companheiro.

Caso o aventureiro não exista, deve ser indicado não encontrado.
Caso os dados do companheiro sejam inválidos, a solicitação deve ser rejeitada.

8) Remover companheiro
Remove o companheiro associado ao aventureiro.

Após a operação:

o aventureiro continua existindo
o companheiro deixa de existir
Paginação e ordenação
Todas as listagens devem ser retornadas em ordem crescente de id.
A paginação deve ser aplicada após filtros.
O total de registros considera apenas os itens que atendem aos filtros.
Padrão de erro
Em caso de erro, a resposta deve seguir um formato consistente.

Exemplo:

{

"mensagem": "Solicitação inválida",

"detalhes": [

"classe inválida",

"nivel deve ser maior ou igual a 1"

]

}
