# Como configurar sua API Key da Riot

Para que a aplicação funcione com dados reais das partidas, você precisa:

## 1. Obter uma chave de API da Riot
- Acesse: https://developer.riotgames.com/
- Faça login com sua conta da Riot
- Copie sua chave de API de desenvolvimento

## 2. Configurar a variável de ambiente

### Windows (PowerShell):
```powershell
$env:RIOT_API_KEY="SUA_CHAVE_AQUI"
```

### Windows (CMD):
```cmd
set RIOT_API_KEY=SUA_CHAVE_AQUI
```

### Git Bash (Linux/Mac):
```bash
export RIOT_API_KEY="SUA_CHAVE_AQUI"
```

## 3. Rodar a aplicação
Depois de configurar a variável de ambiente, rode o backend normalmente:
```bash
mvn spring-boot:run
```

## Nota de Segurança
⚠️ **NUNCA** comite sua chave de API no código-fonte ou no Git!
A chave está configurada para ser lida da variável de ambiente `${RIOT_API_KEY}` para manter a segurança.
