# NexFiscal API

Backend single-tenant para propostas e notas fiscais de serviço (NFS-e), com autenticação JWT e PostgreSQL.

## Requisitos

- Java 21
- Maven (wrapper incluso)
- PostgreSQL 16+ (ou Docker)

## Configuração local

1. Copie as variáveis de ambiente:

```bash
cp .env.example .env
```

2. Ajuste `.env` se necessário (credenciais do banco, `SERVER_PORT`, JWT, admin).

3. Suba o PostgreSQL:

```bash
docker compose up -d postgres
```

Ou use um PostgreSQL local com database `nexfiscal`.

4. Execute a API:

```bash
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8085/api` (porta configurável via `SERVER_PORT` no `.env`).

## Docker (API + PostgreSQL)

```bash
docker compose up --build
```

## Endpoints principais

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/auth/login` | Login (email + password) |
| GET | `/api/auth/me` | Usuário autenticado |
| GET/PUT | `/api/config/prestador` | Configuração do prestador |
| GET/POST/PUT | `/api/propostas` | Propostas comerciais |
| PATCH | `/api/propostas/{id}/status` | Atualizar status |
| POST | `/api/propostas/{id}/duplicar` | Duplicar proposta |
| GET/POST/PUT | `/api/notas-fiscais` | Notas fiscais |
| POST | `/api/notas-fiscais/{id}/emitir` | Emitir NFS-e |
| POST | `/api/notas-fiscais/{id}/cancelar` | Cancelar NFS-e |
| POST | `/api/notas-fiscais/importar` | Importar JSON |
| GET | `/api/notas-fiscais/exportar` | Exportar JSON |

## Swagger

Documentação interativa: `http://localhost:8085/api/swagger-ui.html`

## Usuário admin

Na primeira execução, o `AdminSeedRunner` cria o usuário admin com as credenciais de `ADMIN_EMAIL` e `ADMIN_PASSWORD` do `.env` (padrão: `admin@nexfiscal.local` / `admin123`).

## Migrations

Flyway executa automaticamente as migrations `V1`–`V5` em `src/main/resources/db/migration`.

## Compilar

```bash
./mvnw -q compile
```

## CORS

Permitido para `http://localhost:5173` (configurável via `FRONTEND_URL`).
