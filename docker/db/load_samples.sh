#!/bin/bash

echo LOAD SAMLPES FLAG: $LOAD_SAMPLES

if [ "$LOAD_SAMPLES" = "true" ]; then

    sleep 20 # TODO заменить на healthcheck базы данных

    export PGPASSWORD="$POSTGRES_PASSWORD"
    echo ==== START LOAD SAMLPES ====

    # Загрузка данных из дампа
    createdb -h viewpoint-db -p $POSTGRES_PORT -U $POSTGRES_USER dvdrental
    pg_restore -h viewpoint-db -d dvdrental -p $POSTGRES_PORT -U $POSTGRES_USER ./docker-entrypoint-initdb.d/sample_data.tar

    # Загрузка метаданных BI
    echo ==== END LOAD SAMLPES ====

fi
