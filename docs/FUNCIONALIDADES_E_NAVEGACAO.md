# PetCare — Funcionalidades e Navegação

Documentação funcional e navegacional do sistema **PetCare**, um gerenciador de
clínica veterinária integrada a pet shop, desenvolvido em **JavaFX**.

> Este documento cobre: a arquitetura em camadas, o modelo de navegação da
> interface, o mapa completo de telas e todas as funcionalidades mapeadas aos
> requisitos (REQ01–REQ20).

---

## 1. Visão geral

O PetCare gerencia o ciclo completo de uma clínica veterinária + pet shop:

- **Prontuário e cadastro** de tutores, pets (domésticos e exóticos) e vacinas.
- **Atendimento**: agendamento de consultas com veterinário e de serviços de
  banho/tosa com funcionários.
- **Consulta clínica**: anamnese, exame físico, parâmetros vitais, diagnóstico e
  prescrição.
- **Internação e cirurgia**: encaminhamento, registro de cirurgias e alta.
- **Vendas (PDV)** de produtos e medicamentos, com controle de estoque.
- **Financeiro**: faturas unificadas, despesas e dashboard de receita/lucro.
- **Relatórios em PDF**: histórico clínico, recibos e produtividade.
- **Regras de negócio** que bloqueiam operações inválidas via exceções
  customizadas.

### 1.1. Arquitetura em camadas

```
┌──────────────────────────────────────────────────────────┐
│  Apresentação (JavaFX)                                    │
│  gui/ (Navigator, MockDataLoader) · gui.controllers/      │
│  + view/fxml/*.fxml                                       │
└───────────────────────────┬──────────────────────────────┘
                            │  acessa via fachada
┌───────────────────────────▼──────────────────────────────┐
│  Negócio                                                  │
│  ControllerPetCareServer (fachada / singleton)            │
│  business.controller.* + business.interfaces.I*           │
│  business.model.* (animal, person, appointment, invoice)  │
│  business.report.PdfReportService                         │
└───────────────────────────┬──────────────────────────────┘
                            │
┌───────────────────────────▼──────────────────────────────┐
│  Dados                                                    │
│  data.repository.* + data.interfaces.IRepository*         │
│  data.LoadData / data.SaveData  →  database/*.csv         │
└──────────────────────────────────────────────────────────┘
```

- **Fachada**: `ControllerPetCareServer` (singleton) expõe os controladores
  (`getPerson()`, `getAnimal()`, `getAppointment()`, `getInvoice()`,
  `getStock()`, `getExpense()`, `getSurgery()`) e o método `saveAll()`.
- **Persistência**: arquivos CSV na pasta `database/` (delimitador `;`, sublistas
  com `|` e `~`). `LoadData` lê na inicialização; `SaveData` reescreve a cada
  mutação; um *shutdown hook* salva ao fechar; `MockDataLoader` semeia dados na
  primeira execução.

### 1.2. Stack técnica

| Item | Tecnologia |
|------|-----------|
| Linguagem | Java 21 |
| UI | JavaFX (FXML, controllers) |
| Build | Maven (`mvnw`) |
| PDF | iText 7.2.5 (`business.report.PdfReportService`) |
| Validação | Apache Commons Validator (e-mail) |
| Persistência | CSV (pasta `database/`) |
| Módulos | JPMS (`module-info.java`, módulo `app`) |

---

## 2. Modelo de navegação

A aplicação roda em **janela única** (*single-window*). A tela `AppShell.fxml`
contém uma **barra de breadcrumb** no topo e uma **área de conteúdo** central. A
classe utilitária `gui.Navigator` troca o conteúdo dessa área em vez de abrir
novas janelas, mantendo uma trilha de navegação clicável.

### 2.1. Como funciona o `Navigator`

- `Navigator.init(barra, conteudo)` — liga o navegador ao shell.
- `Navigator.reset(titulo, fxml)` — define a página inicial e limpa o histórico.
- `Navigator.navigate(titulo, fxml)` — navega para uma tela:
  - se ela **já está na trilha**, volta para ela (e descarta o que vinha depois);
  - caso contrário, **empilha** a nova tela.
- A **breadcrumb** mostra a trilha (`Home › Admin › Manage Stock`); cada item
  anterior é um link clicável que retorna àquela tela.
- Erros de carregamento de FXML são capturados e exibidos num `Alert`
  ("Navigation error").

### 2.2. Ponto de entrada

```
Launcher / HelloApplication  (mainClass)
        │  carrega
        ▼
AppShell.fxml  (AppShellController)
        │  initialize():
        │    1. new MockDataLoader().load()   → semeia/carrega o database/
        │    2. Navigator.init(breadcrumb, content)
        │    3. Navigator.reset("Home", MenuPrincipal.fxml)
        ▼
   Tela Home
```

### 2.3. Mapa de navegação (árvore de telas)

```
Home  (MenuPrincipal.fxml · MenuController)
│
├─ Consultations ............... Consultation.fxml
│     └─ Surgery Center ........ SurgeryCenter.fxml ⇄ (volta p/ Consultations)
│
├─ Appointment Dashboard ....... SchedulingDashboard.fxml
│     └─ (detalhe) ............. ConsultationDetails.fxml
│
├─ Attendant ................... AttendantMenu.fxml
│     ├─ Pet Shop (PDV) ........ RegisterProductList.fxml → (volta Attendant)
│     ├─ Register Owner ........ RegisterOwner.fxml
│     │       └─ Register Pet .. RegisterPet.fxml
│     ├─ Register Pet .......... RegisterPetQuestion.fxml
│     │       ├─ Pet Owner ..... RegisterPetOwner.fxml ──► RegisterPet.fxml
│     │       └─ Select Owner .. RegisterPetOwnerRegistred.fxml ──► RegisterPet.fxml
│     │             RegisterPet.fxml
│     │                 └─ Pet Vaccines .. RegisterPetVaccine.fxml → (volta Attendant)
│     ├─ Pet Shop Scheduling ... Attendant.fxml          (banho/tosa)
│     ├─ Scheduling ............ Scheduling.fxml          (consulta/serviço)
│     └─ Home .................. MenuPrincipal.fxml
│
└─ Admin ....................... AdminMenu.fxml
      ├─ Manage Veterinarians .. AdminVeterinarians.fxml
      │       └─ Productivity .. VetProductivity.fxml
      ├─ Manage Stock .......... AdminStock.fxml
      ├─ Manage Appointments ... AdminAppointments.fxml
      ├─ Pet Clinical History .. AdminClinicalHistory.fxml
      └─ System & Financial Dashboard .. AdminDashboard.fxml
```

> Telas reutilizam o mesmo controlador quando fazem parte do mesmo fluxo de
> agendamento: `Scheduling.fxml`, `Attendant.fxml` e os popups
> (`GenerateReceiptPopup`, `VaccinePopup`, `RegisterOwnerList`) usam o
> `AppointmentViewController`.

---

## 3. Funcionalidades por módulo

### 3.1. Cadastro e Prontuário — REQ01–REQ03

| REQ | Funcionalidade | Onde |
|-----|----------------|------|
| REQ01 | Cadastro de pets (espécie, raça, nascimento, peso, porte, etc.) | `RegisterPet` + `RegisterOwner`/`RegisterPetOwner` |
| REQ02 | Herança de animais: `DomesticAnimal` e `ExoticAnimal` (de `Animal`) | `business.model.animal` |
| REQ03 | Prontuário médico por composição (histórico clínico) | `MedicalRecord`, `Appointment`, `Anamnesis` |

- **Cadastro de tutor**: `RegisterOwner.fxml` cria um `Owner`.
- **Cadastro de pet**: fluxo iniciado em `RegisterPetQuestion` (tutor novo ou
  existente) → dados do pet em `RegisterPet` → **cartão de vacinas** em
  `RegisterPetVaccine`.

### 3.2. Atendimento e Serviços — REQ04–REQ06

| REQ | Funcionalidade | Onde |
|-----|----------------|------|
| REQ04 | Agendar consulta vinculando animal a veterinário | `Scheduling.fxml` (`AppointmentViewController`) |
| REQ05 | Agenda de banho/tosa com funcionário responsável | `Attendant.fxml` (`AttendantController`) |
| REQ06 | Registro de vacinas e alertas de reforço | `RegisterPetVaccine` + `ControllerAnimal` (`closeToExpire`, `expiredVaccines`) |

- **Consulta clínica** (`Consultation.fxml`): seleção da fila de espera, registro
  de anamnese, **exame físico** (consciência, mucosa) e **parâmetros vitais**
  (FC, FR, temperatura, coagulação, hidratação), diagnóstico, prescrição,
  histórico do prontuário, seleção de procedimento e encaminhamento.

### 3.3. Internação e Cirurgia — REQ07–REQ08

| REQ | Funcionalidade | Onde |
|-----|----------------|------|
| REQ07 | Encaminhamento para internação (marcação na consulta) | `Consultation` → flag `needsHospitalization` |
| REQ08 | Registro de cirurgia (cirurgião, risco, anestesia, insumos, procedimentos) | `SurgeryCenter.fxml` (`SurgeryCenterController`) |

- **Surgery Center**: lista à esquerda as consultas marcadas como
  cirurgia/internação; à direita o formulário de cirurgia. Ao registrar, cria uma
  `Surgery` + gera **fatura** (receita no dashboard). Também tem o fluxo de
  **alta** (ver REQ16).

### 3.4. Vendas e Financeiro — REQ09–REQ11

| REQ | Funcionalidade | Onde |
|-----|----------------|------|
| REQ09 | PDV: venda de produtos/medicamentos com carrinho | `RegisterProductList.fxml` |
| REQ10 | Controle de estoque (incl. medicamentos controlados) | `AdminStock.fxml` + `ControllerStock` |
| REQ11 | Faturas e recibos em PDF | `PdfReportService` (recibo no PDV) |

- **PDV** (`RegisterProductList`): adiciona itens ao carrinho, confirma pagamento,
  baixa o estoque via `ControllerStock.registerSale(...)` e gera recibo PDF.
- **Estoque** (`AdminStock`): incrementa/decrementa quantidades; produtos do
  veterinário têm tipo de medicamento (`COMMON`/`CONTROLLED`).

### 3.5. Relatórios — REQ12–REQ13

| REQ | Funcionalidade | Onde |
|-----|----------------|------|
| REQ12 | Relatório de produtividade por veterinário | `VetProductivity.fxml` + `PdfReportService` |
| REQ13 | Exportar histórico clínico do pet em PDF (mês/semana) | `AdminClinicalHistory.fxml` |

- **Dashboard financeiro** (`AdminDashboard`): receita (consultas, serviços,
  cirurgias, vendas), despesas e lucro, por período.

### 3.6. Regras de negócio (exceções) — REQ14–REQ20

Cada regra é validada na **camada de negócio** (lança exceção customizada) e
**tratada na GUI** (exibe alerta).

| REQ | Regra | Exceção | Onde é lançada → tratada |
|-----|-------|---------|--------------------------|
| REQ14 | Bloquear banho/tosa sem antirrábica válida | `RabiesVaccineExpired` | `ControllerAnimal.validateGroomingAllowed` → `AttendantController` / `AppointmentViewController` |
| REQ15 | Não vender medicamento controlado sem receita | `PrescriptionRequiredException` | `ControllerStock.registerSale` → PDV `RegisterProductListController` |
| REQ16 | Impedir alta sem quitação da fatura | `UnpaidInvoiceException` | `ControllerInvoice.validateDischarge` → `SurgeryCenterController` (botões "Registrar Pagamento" / "Dar Alta") |
| REQ17 | Bloquear exclusão de prontuário com registros | `MedicalRecordDeletionException` | `ControllerAppointment.delete` → `AdminAppointmentsController` |
| REQ18 | Cirurgia só com veterinário de especialidade ativa | `InactiveSpecialtyException` | `ControllerSurgery.post` → `SurgeryCenterController` |
| REQ19 | Idade mínima para protocolo de vacinação | `InvalidAnimalAgeException` | `ControllerAnimal.validateVaccinationAge` → `RegisterPetVaccineController` |
| REQ20 | Bloquear faturamento com estoque insuficiente | `InsufficientStockException` | `ControllerStock.registerSale` → PDV `RegisterProductListController` |

---

## 4. Telas (referência detalhada)

| Tela (título) | FXML | Controlador | Função | Sai para |
|---------------|------|-------------|--------|----------|
| Shell | `AppShell.fxml` | `AppShellController` | Breadcrumb + área de conteúdo; semeia dados | Home |
| Home | `MenuPrincipal.fxml` | `MenuController` | Hub principal | Consultations, Appointment Dashboard, Attendant, Admin |
| Consultations | `Consultation.fxml` | `ConsultationController` | Consulta clínica completa | Surgery Center |
| Surgery Center | `SurgeryCenter.fxml` | `SurgeryCenterController` | Cirurgia + internação/alta | Consultations |
| Appointment Dashboard | `SchedulingDashboard.fxml` | `AppointmentDashboardController` | Painel de consultas agendadas | Consultation Details |
| Consultation Details | `ConsultationDetails.fxml` | `ConsultationDetailsController` | Detalhe de uma consulta | — |
| Attendant | `AttendantMenu.fxml` | `AttendantMenuController` | Menu do atendente | PDV, cadastros, agendamentos, Home |
| Pet Shop (PDV) | `RegisterProductList.fxml` | `RegisterProductListController` | Carrinho/venda + recibo | Attendant |
| Register Owner | `RegisterOwner.fxml` | `RegisterOwnerController` | Cadastro de tutor | Register Pet / Attendant |
| Register Pet (início) | `RegisterPetQuestion.fxml` | `RegisterPetQuestionController` | Tutor novo ou existente | RegisterPetOwner / RegisterPetOwnerRegistred |
| Pet Owner | `RegisterPetOwner.fxml` | `RegisterPetOwnerController` | Tutor do pet | Register Pet |
| Select Owner | `RegisterPetOwnerRegistred.fxml` | `RegisterPetOwnerRegistredController` | Selecionar tutor existente | Register Pet / Attendant |
| Pet Details | `RegisterPet.fxml` | `RegisterPetController` | Dados do pet | Pet Vaccines |
| Pet Vaccines | `RegisterPetVaccine.fxml` | `RegisterPetVaccineController` | Cartão de vacinas | Attendant |
| Pet Shop Scheduling | `Attendant.fxml` | `AttendantController` | Agendar banho/tosa | — |
| Scheduling | `Scheduling.fxml` | `AppointmentViewController` | Agendar consulta/serviço | Attendant |
| Admin | `AdminMenu.fxml` | `AdminMenuController` | Menu administrativo | telas de gestão |
| Manage Veterinarians | `AdminVeterinarians.fxml` | `AdminVeterinariansController` | CRUD de veterinários/especialidades | Productivity |
| Productivity | `VetProductivity.fxml` | `VetProductivityController` | Produtividade por vet (REQ12) | — |
| Manage Stock | `AdminStock.fxml` | `AdminStockController` | Gestão de estoque (REQ10) | — |
| Manage Appointments | `AdminAppointments.fxml` | `AdminAppointmentsController` | CRUD de consultas (REQ17) | — |
| Pet Clinical History | `AdminClinicalHistory.fxml` | `AdminClinicalHistoryController` | Exportar histórico PDF (REQ13) | — |
| System & Financial Dashboard | `AdminDashboard.fxml` | `AdminDashboardController` | Receita/despesa/lucro | — |

---

## 5. Persistência (pasta `database/`)

| Arquivo | Conteúdo |
|---------|----------|
| `owners.csv` / `veterinarians.csv` / `employees.csv` | Pessoas por tipo |
| `animals.csv` | Pets (com vacinas embutidas) |
| `appointments.csv` | Consultas (prontuário, exame, vitais, flags de cirurgia/internação) |
| `petshopServices.csv` | Serviços de banho/tosa |
| `surgeries.csv` | Cirurgias |
| `products.csv` | Estoque |
| `expenses.csv` | Despesas |
| `invoices.csv` | Faturas (com campo `paid` para REQ16) |

- Reabrir o app **mantém** os dados (leitura via `LoadData`).
- Apagar a pasta `database/` força um **re-seed** completo via `MockDataLoader`.

---

## 6. Como executar

```bash
# Windows (PowerShell), com JAVA_HOME apontando para o JDK 21
.\mvnw.cmd -o clean compile      # compila
.\mvnw.cmd -o javafx:run         # executa
```

A aplicação abre na tela **Home**, já com dados de exemplo semeados na primeira
execução.
