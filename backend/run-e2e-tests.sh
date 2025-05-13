#!/bin/bash

# Script para executar os testes E2E localmente

# Verifica se o backend está rodando
if ! curl -s http://localhost:8081/actuator/health > /dev/null; then
  echo "Iniciando o backend..."
  java -jar target/backend-0.0.1-SNAPSHOT.jar > backend.log 2>&1 &
  BACKEND_PID=$!
  echo "Backend iniciado com PID $BACKEND_PID"
  
  # Aguarda o backend iniciar
  echo "Aguardando o backend iniciar..."
  sleep 15
else
  echo "Backend já está rodando"
fi

# Verifica se o frontend está rodando
if ! curl -s http://localhost:3000 > /dev/null; then
  echo "Iniciando o frontend..."
  cd ../frontend
  node app.js > frontend.log 2>&1 &
  FRONTEND_PID=$!
  echo "Frontend iniciado com PID $FRONTEND_PID"
  
  # Aguarda o frontend iniciar
  echo "Aguardando o frontend iniciar..."
  sleep 5
  cd ../backend
else
  echo "Frontend já está rodando"
fi

# Modifica a URL base para apontar para localhost
sed -i.bak 's|private static final String BASE_URL = "http://100.26.194.86:3000";|private static final String BASE_URL = "http://localhost:3000";|' src/test/java/com/frouvier/backend/e2e/config/WebDriverConfig.java

# Executa os testes
echo "Executando os testes E2E..."
./mvnw test -Dtest=com.frouvier.backend.e2e.CucumberTestRunner

# Restaura a URL base original
mv src/test/java/com/frouvier/backend/e2e/config/WebDriverConfig.java.bak src/test/java/com/frouvier/backend/e2e/config/WebDriverConfig.java

echo "Testes E2E concluídos"