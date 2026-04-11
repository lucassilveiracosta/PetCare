# Sistema PetCare

## Descrição

Este sistema visa gerenciar as atividades de uma clínica veterinária integrada a um pet shop. Ele controla desde o prontuário médico dos animais (anamnese, evolução, vacinas) até a venda de serviços estéticos (banho e tosa) e produtos comerciais de farmácia veterinária.

O PetCare facilita o acompanhamento de longo prazo da saúde animal, automatizando alertas de vacinação e gerenciando internações. O sistema também integra um ponto de venda para que o tutor receba uma fatura unificada de serviços médicos e compras de produtos.

## Requisitos Funcionais

### 1. Cadastro e Prontuário
- **REQ01**: Cadastrar pets com espécie, raça, data de nascimento e peso.
- **REQ02**: Implementar herança para tipos de animais: Domésticos e Exóticos.
- **REQ03**: Manter prontuário médico (Composição) com histórico clínico.

### 2. Atendimento e Serviços
- **REQ04**: Agendar consultas vinculando animal a veterinário especialista.
- **REQ05**: Gerenciar agenda de banho e tosa com controle de profissionais.
- **REQ06**: Registrar aplicação de vacinas e gerar alertas de reforço.

### 3. Internação e Cirurgia
- **REQ07**: Registrar entradas na ala de internação com monitoramento.
- **REQ08**: Cadastrar procedimentos cirúrgicos, vinculando equipe e insumos.

### 4. Vendas e Financeiro
- **REQ09**: Registrar vendas de medicamentos e acessórios no PDV.
- **REQ10**: Controlar o estoque de medicamentos, incluindo os controlados.
- **REQ11**: Emitir receitas e faturas detalhadas em PDF.

### 5. Relatórios
- **REQ12**: Gerar relatório de produtividade por médico veterinário.
- **REQ13**: Exportar histórico clínico completo em PDF para o tutor.

### 6. Regras e Restrições
- **REQ14**: **Bloquear o agendamento** de banho e tosa para animais sem vacina antirrábica atualizada.
- **REQ15**: **Não permitir a venda** de medicamentos controlados sem receita vinculada.
- **REQ16**: **Impedir a alta** do animal internado sem a quitação dos custos hospitalares.
- **REQ17**: **Bloquear a exclusão** de prontuários com registros de cirurgias realizadas.
- **REQ18**: **Garantir** que cirurgias só sejam agendadas para veterinários com especialidade ativa.
- **REQ19**: **Validar** a idade mínima do animal para protocolos específicos de vacinação.
- **REQ20**: **Bloquear o faturamento** de produtos com estoque zerado no sistema.

## Possíveis APIs/Bibliotecas
- **JFreeChart** – Evolução de peso.
- **Java Time API** – Alertas de vacina.
- **iText** – Geração de prontuários e histórico clínico. 

## Colaboradores
- Lucas Silevira Costa               | lucas.silveiracosta@ufrpe.br
- Vinicius Emanuel Lima Paes Barreto | vinicius.emanuell@ufrpe.br
- Laércio Carlos Annes Medeiros Neto | laercio.neto@ufrpe.br
- João Cavalcanti Buarque            | joao.buarque@ufrpe.br
- Davi Vinícius Modesto Ayres        | davi.vinicius@ufrpe.br