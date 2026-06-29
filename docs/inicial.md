para iniciar : ya se crea un admin
ejecuta: docker compose up -d
luego: el test: .\gradlew.bat build test

.\gradlew.bat build

.\gradlew.bat bootrun

Archivos

.env{
environment:
POSTGRES_DB: ${POSTGRES_DB}
POSTGRES_USER: ${POSTGRES_USER}
POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
}
