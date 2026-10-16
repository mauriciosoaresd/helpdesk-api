# 🛠️ HelpDesk OS - Sistema de Gestão de Ordens de Serviço (SaaS)

> Sistema Full Stack para gestão de chamados e ordens de serviço multi-tenant, desenvolvido com foco em arquitetura resiliente, segurança com JWT, containerização com Docker e boas práticas de desenvolvimento Java.

---

## 📌 Sobre o Projeto

O **HelpDesk OS** foi concebido para resolver o problema de gestão de chamados de suporte em pequenas e médias empresas. O sistema permite o gerenciamento completo do ciclo de vida de uma ordem de serviço, desde a abertura pelo cliente, atribuição a um técnico, até o encerramento com emissão de relatórios.

---

## 🚀 Tecnologias Utilizadas

### **Backend**
- **Java 17 / 21** & **Spring Boot 3.x**
- **Spring Security** + **JWT (JSON Web Token)** (Autenticação e Autorização por Roles)
- **Spring Data JPA** + **Hibernate**
- **PostgreSQL** (Banco de dados relacional)
- **Flyway** (Migrations e versionamento de banco)
- **Bean Validation** (Validação de dados de entrada)
- **JUnit 5**, **Mockito** e **Testcontainers** (Testes unitários e de integração)
- **SpringDoc OpenAPI (Swagger)** (Documentação da API)

### **Frontend** *(Em Breve)*
- **ReactJS** + **Vite**
- **TypeScript** / **JavaScript**
- **Material UI (MUI)** ou **TailwindCSS**
- **Context API** / **Zustand** (Gestão de estado global)

### **DevOps & Infraestrutura**
- **Docker** & **Docker Compose**
- **GitHub Actions** (CI/CD simples para execução automática de testes)

---

## 🏗️ Arquitetura e Decisões Técnicas

- **Arquitetura em Camadas:** Separação limpa entre Controller, Service, Repository, DTOs e Entities.
- **Banco de Dados de Testes Isolado:** Utilização do **Testcontainers** para testes de integração reais com um container PostgreSQL efêmero sem impactar o ambiente local.
- **Resiliência e Tratamento de Erros:** `@ControllerAdvice` global para padronização de respostas de erro da API (RFC 7807).

---

## 🗺️ Roadmap de Desenvolvimento

- [ ] Configuração inicial e estrutura de containers Docker
- [ ] Mapeamento das Entidades e Migrations com Flyway
- [ ] Implementação do Spring Security + Autenticação JWT
- [ ] DTOs de cadastro/login de usuários e perfis (ADMIN, TECNICO, CLIENTE)
- [ ] Endpoints de CRUD e ciclo de vida de Ordens de Serviço
- [ ] Cobertura de Testes Unitários e de Integração
- [ ] Documentação com Swagger
- [ ] Interface Frontend em ReactJS integrada à API

---

## ⚙️ Como Executar o Projeto

### **Pré-requisitos**
- Docker e Docker Compose instalados
- Java 17+ (opcional, caso queira rodar fora do Docker)

### **Passos:**
```bash
# 1. Clone o repositório
git clone [https://github.com/mauriciosoaresd/helpdesk-api.git](https://github.com/seu-usuario/helpdesk-os-fullstack.git)

# 2. Acesse a pasta do projeto
cd helpdesk-api

# 3. Suba o ambiente via Docker Compose
docker-compose up -d --build